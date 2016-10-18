package com.dcits.smartbip.protocol.tcp.client;

import com.dcits.smartbip.common.model.IConfiguration;
import com.dcits.smartbip.protocol.IProtocol;
import com.dcits.smartbip.protocol.tcp.StreamPolicy;
import com.dcits.smartbip.protocol.tcp.TCPHandler;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IServiceProxy;
import com.dcits.smartbip.runtime.packer.impl.XMLPacker;
import com.dcits.smartbip.runtime.parser.impl.XMLParser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by vincentfxz on 16/9/20.
 */
public class TCPClient implements IProtocol, IServiceProxy<String, String> {

    private static final Log log = LogFactory.getLog(TCPClient.class);

    private static final String PORT = "port";
    private static final String IP = "ip";
    //读策略
    private static final String READ_POLICY = "readPolicy";
    //写策略
    private static final String WRITE_POLICY = "writePolicy";
    private static final String READ_TIME_OUT = "readTimeOut";
    private static final String CONNECTION_TIME_OUT = "connectionTimeOut";
    private static final String ENCODING = "UTF-8";

    private volatile boolean isStart = false;
    private IConfiguration<String> configuration;
    private String id;
    private XMLPacker xmlPacker;
    private XMLParser xmlParser;
    private String readTimeOut;
    private String connectionTimeOut;
    private StreamPolicy readPolicy;
    private StreamPolicy writePolicy;

    public TCPClient() {
        xmlPacker = new XMLPacker();
        xmlParser = new XMLParser();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void start() {
        synchronized (TCPClient.class) {
            this.isStart = true;
            this.readPolicy = new StreamPolicy(configuration.getConfig(READ_POLICY));
            this.writePolicy = new StreamPolicy(configuration.getConfig(WRITE_POLICY));
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

    @Override
    public String pack(ICompositeData compositeData) {
    	ICompositeData compositeDataa = null;
    	Set<String> set = compositeData.getChildren().keySet();
    	for (Iterator it = set.iterator(); it.hasNext();) {
	    	String sid = (String) it.next();
	    	//去掉req + serviceid
	    	compositeDataa = compositeData.getChildren().get(sid).get(0);
    	}
    	StringBuilder sbd = new StringBuilder();
    	String reqStr= xmlPacker.pack(compositeDataa);
    	String xmlhead = "<?xml version=\"1.0\" encoding=\"" + ENCODING +"\"?>";
        return sbd.append(xmlhead).append(reqStr).toString();
    }

    @Override
    public String invoke(String req) {
        if (isStart) {
            String ip = configuration.getConfig(IP);
            int port = Integer.parseInt(configuration.getConfig(PORT));
//            StringBuilder builder = new StringBuilder();
//            req = builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append(req).toString();
            if (log.isInfoEnabled()) {
                log.info("向[" + ip + ":" + port + "]发送请求!请求内容为[" + req + "]");
            }
            Socket client = null;
            try {
                client = new Socket(ip, port);
                TCPHandler tcpHandler = new TCPHandler(client, readPolicy, writePolicy);
                tcpHandler.writeAndShutdown(req.getBytes());
                byte[] bytes = tcpHandler.read(client);
                if (log.isInfoEnabled()) {
                    log.info("向[" + ip + ":" + port + "]发送请求!响应内容为[" + new String(bytes) + "]");
                }
                return new String(bytes);
            } catch (IOException e) {
                log.error(e, e);
                return null;
            } finally {
                if (null != client) {
                    try {
                        client.close();
                    } catch (IOException e) {
                        log.error(e, e);
                    }
                }
            }
        } else {
            log.error("协议[" + this.getId() + "]未启动!");
            return null;
        }

    }

    @Override
    public ICompositeData unpack(String msg) {
    	StringBuilder sbd = new StringBuilder();
    	String xmlhead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    	msg = msg.replace(xmlhead, "");
    	sbd.append("<BIPXMLZZ>").append(msg).append("</BIPXMLZZ>");
    	String destStr = xmlhead + sbd.toString();
        return xmlParser.parse(destStr.getBytes());
    }
}
