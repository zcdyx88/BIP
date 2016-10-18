package com.dcits.smartbip.reversal.impl;

import com.dcits.smartbip.journal.impl.JournalConstants;
import com.dcits.smartbip.journal.impl.RedisJournalService;
import com.dcits.smartbip.reversal.ReversalService;
import com.dcits.smartbip.runtime.model.IContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.SerializationUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by vincentfxz on 16/8/16.
 * <p/>
 * Redis冲正服务:
 * 1.发送冲正服务和组合服务的调用栈到redis
 * 2.发送冲正服务的上下文信息到redis
 */
public class RedisReversalService implements ReversalService {

    private static final Log log = LogFactory.getLog(RedisReversalService.class);

    private volatile boolean reversalEnabled;
    private JedisPool pool;
    private ExecutorService threadpool;
    private static RedisReversalService instance;
    private AtomicInteger count = new AtomicInteger(0);

    private RedisReversalService() {

    }

    public static RedisReversalService getInstance() {
        if (null == instance) {
            instance = new RedisReversalService();
        }
        return instance;
    }

    /**
     * 启动冲正客户端
     */
    @Override
    public void start() {
        try {
            ReversalConfig reversalConfig = ReversalConfig.getInstance();
            reversalEnabled = "true".equalsIgnoreCase(reversalConfig.getConfig(ReversalConstants.REVERSAL_ENABLED));
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
                    return new Thread(r, ReversalConstants.REVERSAL_THREAD_NAME);
                }
            });
            pool = new JedisPool(jedisPoolConfig, ip, Integer.parseInt(port));
        } catch (Exception e) {
            log.error("启动冲正客户端失败!", e);
        }
    }

    /**
     * 激发异步冲正,激发异步冲正将完成以下:
     * 1.将上下文推送到redis
     * 2.发送异步冲正事件
     * @param context
     * @param msg
     */
    @Override
    public void invoke(IContext context, String msg) {
        if(reversalEnabled){
            String uniqueFlowNo = "REVERSAL:" + (String) context.getValue(ReversalConstants.UNIQUE_FLOW_NO);
            Jedis jedis = pool.getResource();
            try{
                //推送上下文
                jedis.set(uniqueFlowNo.getBytes(), SerializationUtils.serialize(context));
                //发送事件通知
                jedis.publish(ReversalConstants.REVERSAL_CHANNEL,uniqueFlowNo);
            }catch(Exception e){
                log.error("异步冲正发送失败!", e);
            }finally {
                pool.returnBrokenResource(jedis);
            }

        }
    }
}
