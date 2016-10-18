package com.dcits.smartbip.protocol.tcp.server;

import com.dcits.smartbip.common.model.IConfiguration;
import com.dcits.smartbip.protocol.IServerProtocol;
import com.dcits.smartbip.protocol.ProtocolConfig;
import com.dcits.smartbip.protocol.tcp.StreamPolicy;
import com.dcits.smartbip.protocol.tcp.TCPHandler;
import com.dcits.smartbip.runtime.model.impl.TCPProcessDispatcher;
import com.dcits.smartbip.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TCP 短连接接入协议
 * Created by vincentfxz on 16/9/18.
 */
public class TCPShortServer implements IServerProtocol {

    private static final Log log = LogFactory.getLog(TCPShortServer.class);
    //端口
    private static final String PORT = "port";
    //IP
    private static final String IP = "ip";
    //处理线程数
    private static final String THREAD_COUNT = "threadCount";
    //等待队列长度
    private static final String QUEUE_SIZE = "queueSize";
    //读策略
    private static final String READ_POLICY = "readPolicy";
    //写策略
    private static final String WRITE_POLICY = "writePolicy";
    private static final String IDENTIFY_ID = "identifyID";

    //协议ID
    private String id;
    //TCP短连接接入协议的配置信息
    private volatile IConfiguration<String> configuration;
    //启动标志
    private volatile boolean isStart = false;
    //默认的协议配置信息
    private static final IConfiguration<String> defaultConfig = new ProtocolConfig();
    //读策略
    private StreamPolicy readPolicy;
    //写策略
    private StreamPolicy writePolicy;
    //协议的SocketServer
    private ServerSocket server;
    //协议接入线程池
    private ExecutorService threadPool;

    static {
        defaultConfig.setConfig(PORT, "10001");
        defaultConfig.setConfig(IP, "127.0.0.1");
        defaultConfig.setConfig(THREAD_COUNT, "50");
        defaultConfig.setConfig(QUEUE_SIZE, "0");
        defaultConfig.setConfig(READ_POLICY, "length:s=0,e=7");
        defaultConfig.setConfig(WRITE_POLICY, "length:s=0,e=7");
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 启动协议
     */
    @Override
    public void start() {
        isStart = true;
        if (log.isInfoEnabled()) {
            log.info("开始启动协议[" + this.getId() + "]");
            log.info("协议详细信息[" + configuration.getConfig(IP)+ "],[" +  configuration.getConfig(PORT)+ "],["
            		+ configuration.getConfig(READ_POLICY) + "],[" + configuration.getConfig(WRITE_POLICY) + "]");
        }
        //协议要同步启停
        synchronized (TCPShortServer.class) {
            configuration = configuration == null ? configuration : defaultConfig;
            try {
                String port = configuration.getConfig(PORT);
                String threadCount = configuration.getConfig(THREAD_COUNT);
                String readPolicyStr = configuration.getConfig(READ_POLICY);
                String writePolicyStr = configuration.getConfig(WRITE_POLICY);
                this.readPolicy = new StreamPolicy(readPolicyStr);
                this.writePolicy = new StreamPolicy(writePolicyStr);
                if (StringUtils.isNumeric(port) && StringUtils.isNumeric(threadCount)) {
                    //构建serversocket
                    int portInt = Integer.parseInt(port);
                    this.server = new ServerSocket(portInt);
                    //构建线程池
                    int threadCountInt = Integer.parseInt(threadCount);
                    this.threadPool = Executors.newFixedThreadPool(threadCountInt);
                    TCPServerRunnable serverRunnable = new TCPServerRunnable(this.server, this.threadPool, this.readPolicy, this.writePolicy, configuration.getConfig(IDENTIFY_ID));
                    new Thread(serverRunnable).start();
                } else {
                    throw new Exception("协议配置错误");
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

    class TCPServerRunnable implements Runnable{
        private ServerSocket socket;
        private ExecutorService threadPool;
        private StreamPolicy readPolicy;
        private StreamPolicy writePolicy;
        private String identifyID;

        public TCPServerRunnable(ServerSocket socket, ExecutorService threadPool, StreamPolicy readPolicy, StreamPolicy writePolicy, String identifyID){
            this.socket = socket;
            this.threadPool = threadPool;
            this.readPolicy = readPolicy;
            this.writePolicy = writePolicy;
            this.identifyID = identifyID;
        }

        @Override
        public void run() {
            while(true){
                try {
                    Socket client = socket.accept();
                    threadPool.execute(new TCPHandleRunnable(client, readPolicy, writePolicy));
                } catch (IOException e) {
                    log.error(e,e);
                }
            }
        }
    }

    class TCPHandleRunnable implements Runnable{
        private Socket socket;
        private StreamPolicy readPolicy;
        private StreamPolicy writePolicy;

        TCPHandleRunnable(Socket socket, StreamPolicy readPolicy, StreamPolicy writePolicy) {
            this.socket = socket;
            this.readPolicy = readPolicy;
            this.writePolicy = writePolicy;
        }

        @Override
        public void run() {
            try {
                TCPHandler tcpHandler = new TCPHandler(this.socket, this.readPolicy, this.writePolicy);
                byte[] readBytes = tcpHandler.read(socket);
                TCPProcessDispatcher dispatcher = new TCPProcessDispatcher(readBytes, tcpHandler);
                dispatcher.dispatch(readBytes);
            } catch (IOException e) {
                log.error("协议读取失败!", e);
            }
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public IConfiguration<String> getConfig() {
        return this.configuration;
    }

    @Override
    public void setConfig(IConfiguration<String> config) {
        this.configuration = config;
    }

    @Override
    public void reload() {

    }
}
