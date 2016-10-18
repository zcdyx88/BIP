package com.dcits.smartbip.protocol.http.server;

import com.dcits.smartbip.common.model.IConfiguration;
import com.dcits.smartbip.protocol.IServerProtocol;
import com.dcits.smartbip.protocol.ProtocolConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;


/**
 * Implements an HTTP transport service using Jetty.
 * Http 接入容器,同样也用于WebService接入
 */
public class JettyServer implements IServerProtocol {
    private static final Log log = LogFactory.getLog(JettyServer.class);
    private static final String PORT = "port";
    private static final String CONTEXT_PATH = "contextPath";
    private static final String THREAD_POOL_MIN = "minThreads";
    private static final String THREAD_POOL_MAX = "maxThreads";
    private static final String ACCEPT_QUEUE_SIZE = "acceptQueueSize";
    private static final String ACCEPTER_SIZE = "acceptSize";
    private Server server;
    private String id;
    private volatile IConfiguration<String> configuration;
    private volatile boolean isStart = false;
    private static final IConfiguration<String> defaultConfig = new ProtocolConfig();

    static {
        defaultConfig.setConfig(PORT, "8080");
        defaultConfig.setConfig(CONTEXT_PATH, "/ws");
        defaultConfig.setConfig(THREAD_POOL_MIN, "50");
        defaultConfig.setConfig(THREAD_POOL_MAX, "200");
        defaultConfig.setConfig(ACCEPT_QUEUE_SIZE, "50");
        defaultConfig.setConfig(ACCEPTER_SIZE, "2");
    }

    public JettyServer() {
    }

    @Override
    public void start() {
        isStart = true;
        if (log.isInfoEnabled()) {
            log.info("开始启动协议[" + this.getId() + "]");
        }
        synchronized (JettyServer.class) {
            configuration = configuration == null ? defaultConfig : configuration;
            try {
                String port = configuration.getConfig(PORT);
                String contextPath = configuration.getConfig(CONTEXT_PATH);
                String minThreads = configuration.getConfig(THREAD_POOL_MIN);
                String maxThreads = configuration.getConfig(THREAD_POOL_MAX);
                String acceptQueueSize = configuration.getConfig(ACCEPT_QUEUE_SIZE);
                String accepterSize = configuration.getConfig(ACCEPTER_SIZE);
                if (log.isInfoEnabled()) {
                    log.info("协议参数:\n端口[" + port + "]\n路径[" + contextPath + "]\n最小处理线程[" + minThreads
                            + "]\n最大处理线程[" + maxThreads + "]\naccept队列深度[" + acceptQueueSize + "]\nacceptor的个数["
                            + accepterSize + "]");
                }
                if (StringUtils.isNumeric(port) && StringUtils.isNumeric(minThreads)
                        && StringUtils.isNumeric(maxThreads) && StringUtils.isNumeric(acceptQueueSize)) {
                    int portInt = Integer.parseInt(port);
                    int minThreadsInt = Integer.parseInt(minThreads);
                    int maxThreadsInt = Integer.parseInt(maxThreads);
                    int acceptQueueSizeInt = Integer.parseInt(acceptQueueSize);
                    int accepterSizeInt = Integer.parseInt(accepterSize);
                    server = new Server();
                    ServletHandler handler = new ServletHandler();
                    //Jetty的线程池设置,通过Jetty接入,不需要在做线程池分派,直接使用jetty接入的线程池
                    QueuedThreadPool threadPool = new QueuedThreadPool();
                    threadPool.setMinThreads(minThreadsInt);
                    threadPool.setMaxThreads(maxThreadsInt);
                    server.setThreadPool(threadPool);
                    SelectChannelConnector connector = new SelectChannelConnector();
//                    SocketConnector connector = new SocketConnector();
                    connector.setPort(portInt);
                    //每个请求被accept前允许等待的连接数
                    connector.setAcceptQueueSize(acceptQueueSizeInt);
                    //同事监听read事件的线程数
                    connector.setAcceptors(accepterSizeInt);
                    //连接最大空闲时间，默认是200000，-1表示一直连接
                    connector.setMaxIdleTime(3000);
                    server.addConnector(connector);
                    server.setHandler(handler);
                    handler.addServletWithMapping(HttpServerServlet.class, contextPath);
                    server.start();
                    server.join();
                }
            } catch (Exception e) {
                isStart = false;
                log.error("协议[" + getId() + "]启动失败!", e);
            }
        }
        if (log.isInfoEnabled()) {
            log.info("协议[" + this.getId() + "]启动完成");
        }
    }

    /**
     * Stop all the started servers.
     */
    public void stop() {
        if (null != server) {
            isStart = false;
            try {
                server.stop();
            } catch (Exception e) {
                log.error(e, e);
            }
        }
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public IConfiguration<String> getConfig() {
        return this.configuration == null ? defaultConfig : configuration;
    }

    @Override
    public void setConfig(IConfiguration<String> config) {
        this.configuration = config;
    }

    @Override
    public void reload() {

    }


    public static void main(String[] args) {
        JettyServer server = new JettyServer();
        server.setConfiguration(null);
        server.start();
    }

    public IConfiguration<String> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(IConfiguration<String> configuration) {
        this.configuration = configuration;
    }
}

