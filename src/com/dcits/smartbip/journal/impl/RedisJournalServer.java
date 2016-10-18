package com.dcits.smartbip.journal.impl;

import com.dcits.smartbip.journal.JournalServer;
import com.dcits.smartbip.journal.entity.BipTranLog;
import com.dcits.smartbip.journal.entity.BipTranMsg;
import com.dcits.smartbip.journal.service.BipTranLogService;
import com.dcits.smartbip.journal.service.BipTranMsgService;
import com.dcits.smartbip.utils.ApplicationUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by vincentfxz on 16/8/8.
 */
public class RedisJournalServer extends JedisPubSub implements JournalServer {

	private static final Log log = LogFactory.getLog(RedisJournalServer.class);
	private static RedisJournalServer server;
	private ExecutorService threadpool;
	private AtomicInteger count = new AtomicInteger(0);
	private volatile boolean journalEnabled;
	private JedisPool pool;
	private boolean journalMsgEnabled = true;

	public static RedisJournalServer getInstance() {
		if (null == server) {
			server = new RedisJournalServer();
		}
		return server;
	}

	public void start() {
		JournalConfig journalConfig = JournalConfig.getInstance();
		journalEnabled = "true".equalsIgnoreCase(journalConfig.getConfig(JournalConstants.JOURNAL_ENABLED));
		journalMsgEnabled = "true".equalsIgnoreCase(journalConfig.getConfig(JournalConstants.JOURNAL_MSG_ENABLED));
		String ip = journalConfig.getConfig(JournalConstants.QUEUE_IP);
		String port = journalConfig.getConfig(JournalConstants.QUEUE_PORT);
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(Integer.parseInt(journalConfig.getConfig(JournalConstants.MAX_ACTIVE)));
		jedisPoolConfig.setMaxIdle(Integer.parseInt(journalConfig.getConfig(JournalConstants.MAX_IDLE)));
		jedisPoolConfig.setMaxWaitMillis(Integer.parseInt(journalConfig.getConfig(JournalConstants.MAX_WAIT)));
		String threadCount = journalConfig.getConfig(JournalConstants.JOURNAL_POOL_CAPACITY);
		threadpool = Executors.newFixedThreadPool(Integer.parseInt(threadCount), new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, JournalConstants.JOURNAL_THREAD_NAME + count.incrementAndGet());
			}
		});
		pool = new JedisPool(jedisPoolConfig, ip, Integer.parseInt(port),30000,"redis");
		Jedis jedis = null;
		try {
			if (log.isInfoEnabled()) {
				log.info("流水启动");
			}
			jedis = pool.getResource();
			jedis.subscribe(server, JournalConstants.JOURNAL_CHANNEL);
		} catch (Exception e) {
			log.error(e, e);
		} finally {
			if (jedis != null) {
				pool.returnResource(jedis);
			}
		}
	}

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
				SimpleDateFormat flatDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date flatDate = flatDateFormat.parse(map.get(JournalConstants.FLAT_DATE));
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
				BipTranLogService service = (BipTranLogService) ApplicationUtils.getInstance()
						.getBean("bipTranLogService");
				BipTranLog tranLog = new BipTranLog(key,flatDate,compositeServiceId, serviceId, revServiceId, processFlowId,
						serviceFlowNo, flowTime, retCode, retMsg, retStatus, flowStep);
				
				service.save(tranLog);
				if(journalMsgEnabled)
				{
					String msg = map.get(JournalConstants.FLOW_MSG);
					BipTranMsgService bipTranMsgService = (BipTranMsgService) ApplicationUtils.getInstance()
							.getBean("bipTranMsgService");
					BipTranMsg bipTranMsg = null;
					bipTranMsg = new BipTranMsg(key, msg.getBytes(),flatDate);		
					bipTranMsgService.save(bipTranMsg);
				}

				jedis.del(key);
			} catch (Exception e) {				
				log.error("删除流水缓存失败:", e);
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
