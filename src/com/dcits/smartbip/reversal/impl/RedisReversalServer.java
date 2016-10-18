package com.dcits.smartbip.reversal.impl;

import com.dcits.smartbip.journal.entity.BipTranLog;
import com.dcits.smartbip.journal.entity.BipTranMsg;
import com.dcits.smartbip.journal.impl.JournalConstants;
import com.dcits.smartbip.journal.service.BipTranLogService;
import com.dcits.smartbip.journal.service.BipTranMsgService;
import com.dcits.smartbip.reversal.ReversalServer;
import com.dcits.smartbip.utils.ApplicationUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by vincentfxz on 16/9/1.
 */
public class RedisReversalServer extends JedisPubSub implements ReversalServer {
    private static final Log log = LogFactory.getLog(RedisReversalServer.class);
    private static RedisReversalServer server;
    private ExecutorService threadpool;
    private AtomicInteger count = new AtomicInteger(0);
    private volatile boolean reversalEnable;
    private JedisPool pool;

    public static RedisReversalServer getInstance() {
        if (null == server) {
            server = new RedisReversalServer();
        }
        return server;
    }

    public void start() {
        ReversalConfig reversalConfig= ReversalConfig.getInstance();
        reversalEnable= "true".equalsIgnoreCase(reversalConfig.getConfig(ReversalConstants.REVERSAL_ENABLED));
        String ip = reversalConfig.getConfig(ReversalConstants.QUEUE_IP);
        String port = reversalConfig.getConfig(ReversalConstants.QUEUE_PORT);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(Integer.parseInt(reversalConfig.getConfig(ReversalConstants.MAX_ACTIVE)));
        jedisPoolConfig.setMaxIdle(Integer.parseInt(reversalConfig.getConfig(ReversalConstants.MAX_IDLE)));
        jedisPoolConfig.setMaxWaitMillis(Integer.parseInt(reversalConfig.getConfig(ReversalConstants.MAX_WAIT)));
        String threadCount = reversalConfig.getConfig(ReversalConstants.REVERSAL_POOL_CAPACITY);
        threadpool = Executors.newFixedThreadPool(Integer.parseInt(threadCount), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, ReversalConstants.REVERSAL_THREAD_NAME+ count.incrementAndGet());
            }
        });
        pool = new JedisPool(jedisPoolConfig, ip, Integer.parseInt(port));
        Jedis jedis = null;
        try {
            if (log.isInfoEnabled()) {
                log.info("异步冲正启动");
            }
            jedis = pool.getResource();
            jedis.subscribe(server, JournalConstants.JOURNAL_CHANNEL);
        } catch (Exception e) {
            log.error(e, e);
        } finally {
            pool.returnResource(jedis);
        }
    }

    /**
     * 获取到异步冲正请求
     * 1.根据Key读取redis中的上下文信息
     * 2.删除redis中的缓存
     * 3.解析 冲正STACK入库
     *
     * @param channel
     * @param key
     */
    @Override
    public void onMessage(String channel, String key) {
        if (log.isInfoEnabled()) {
            log.info("收到流水请求[" + key + "]");
        }
        SaveJournalRunnable saveJournalRunnable = new SaveJournalRunnable(key);
        threadpool.execute(saveJournalRunnable);
    }

    class SaveJournalRunnable implements Runnable {
        private String key;

        public SaveJournalRunnable(String key) {
            this.key = key;
        }

        @Override
        public void run() {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                Map<String, String> map = jedis.hgetAll(key);
                String processFlowId = map.get(JournalConstants.UNIQUE_FLOW_NO);
                String flowTime = map.get(JournalConstants.FLOW_TIME);
                String flowStep = map.get(JournalConstants.FLOW_STEP);
                String compositeServiceId = map.get(JournalConstants.FLOW_COMPOSITE_SERVICE_ID);
                String serviceId = map.get(JournalConstants.FLOW_SERVICE_ID);
                String revServiceId = "";
                String serviceFlowNo = map.get(JournalConstants.ATOM_FLOW_NO);
                String retCode = "";
                String retMsg = "";
                String retStatus = map.get(JournalConstants.JOURNAL_RET_CODE);
                if (log.isInfoEnabled()) {
                    StringBuilder sb = new StringBuilder("收到流水[" + key + "]\n");
                    sb.append("流程流水号[").append(processFlowId).append("]\n");
                    sb.append("流水时间[").append(flowTime).append("]\n");
                    sb.append("流水步骤[").append(flowStep).append("]\n");
                    log.info(sb.toString());
                }
                BipTranLogService service = (BipTranLogService) ApplicationUtils.getInstance().getBean("bipTranLogService");
                BipTranLog tranLog = new BipTranLog(key, compositeServiceId, serviceId, revServiceId, processFlowId, serviceFlowNo,
                        flowTime, retCode, retMsg, retStatus, flowStep);
                service.save(tranLog);
                String msg = map.get(JournalConstants.FLOW_MSG);
                BipTranMsgService bipTranMsgService = (BipTranMsgService) ApplicationUtils.getInstance().getBean("bipTranMsgService");
                BipTranMsg bipTranMsg = new BipTranMsg(key, msg.getBytes());
                bipTranMsgService.save(bipTranMsg);
                jedis.del(key);
            } catch (Exception e) {
                log.error("流水入库失败", e);
            } finally {
                if (null != pool && null != jedis)
                    pool.returnResource(jedis);
            }
        }
    }

    @Override
    public void onPMessage(String s, String s1, String s2) {

    }

    @Override
    public void onSubscribe(String s, int i) {

    }

    @Override
    public void onUnsubscribe(String s, int i) {

    }

    @Override
    public void onPUnsubscribe(String s, int i) {

    }

    @Override
    public void onPSubscribe(String s, int i) {

    }
}
