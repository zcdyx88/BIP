package com.dcits.smartbip.protocol.ws.client;

import com.dcits.smartbip.common.model.IConfiguration;
import com.dcits.smartbip.protocol.IProtocol;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IServiceProxy;
import com.dcits.smartbip.runtime.packer.impl.SoapPacker;
import com.dcits.smartbip.runtime.parser.impl.SoapParser;
import com.dcits.smartbip.utils.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.reficio.ws.SoapContext;
import org.reficio.ws.builder.SoapBuilder;
import org.reficio.ws.builder.SoapOperation;
import org.reficio.ws.builder.core.Wsdl;
import org.reficio.ws.client.core.SoapClient;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by vincentfxz on 16/5/27.
 */
public class SOAPClient implements IProtocol, IServiceProxy<String, String> {

	private static final Log log = LogFactory.getLog(SOAPClient.class);

	private static final String WSDL = "wsdl";
	private static final String PORT = "port";
	private static final String CONTEXT_PATH = "contextPath";
	private static final String BINDING_NAME = "bindingName";
	private static final String OPERATION = "operationName";
	private static final String IP = "ip";

	private volatile boolean isStart = false;
	private IConfiguration<String> configuration;
	private String id;
	private SoapPacker soapPacker;
	private SoapParser soapParser;
	private String[] envelopPart = new String[2];
	private String defaultMsg;
	private String url;

	public SOAPClient() {
		soapParser = new SoapParser();
		soapPacker = new SoapPacker();
	}

	@Override
	public void start() {
		// soap client 启动的时候文件加锁,所以需要同步启动
		synchronized (SOAPClient.class) {
			String port = configuration.getConfig(PORT);
			String contextPath = configuration.getConfig(CONTEXT_PATH);
			String wsdl = configuration.getConfig(WSDL);
			String bindingName = configuration.getConfig(BINDING_NAME);
			String operation = configuration.getConfig(OPERATION);
			String ip = configuration.getConfig(IP);
			url = String.format("http://%s:%s%s", ip, port, contextPath);
			

			// URL wsdlUrl = FileUtils.class.getClassLoader().getResource(wsdl);
			String installPath = System.getProperty("smartbip.install");
			String configPath = installPath + File.separator + wsdl;

			Wsdl parser;
			try {
				parser = Wsdl.parse(new File(configPath).toURL());
				SoapBuilder soapBuilder = parser.binding()
						.localPart(bindingName).find();
				SoapOperation soapOperation = soapBuilder.operation()
						.name(operation).find();
				defaultMsg = soapBuilder.buildInputMessage(soapOperation,
						SoapContext.NO_CONTENT);
				envelopPart[0] = defaultMsg.substring(
						0,
						defaultMsg.indexOf("<soapenv:Body>")
								+ "<soapenv:Body>".length());
				envelopPart[1] = defaultMsg.substring(defaultMsg
						.indexOf("</soapenv:Body>"));
				this.isStart = true;
			} catch (MalformedURLException e) {
				log.error(e);
			}
		}
	}

	@Override
	public String invoke(String req) {
		if (isStart) {
			String response = null;
			SoapClient client = null;
			try
			{
	//			long time1 = System.currentTimeMillis();
				if (log.isInfoEnabled()) {
					log.info("向[" + url + "]发送post请求!");				
					log.info("请求内容为:[" + req + "]");
				}
				client = SoapClient.builder().endpointUri(url).build();
				response = client.post(req);
				if(log.isInfoEnabled()){
					log.info("接收到[" + url + "]响应!");
					log.info("响应内容为:["+response+"]");
				}			
	//			long time2 = System.currentTimeMillis();
	//			if (log.isErrorEnabled()) {
	//				log.error(">>>>>>请求后端系统耗时[" + (time2 - time1) + "]");
	//			}
			}
			catch(Exception e)
        	{
        		log.error("发送报文过程中出错"+e.toString());
        	}finally{
        		if(null != client){
        			client.disconnect();
        		}
        	}
			return response;
		} else {
			log.error("协议[" + this.getId() + "]未启动!");
			return null;
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
	public void stop() {
		this.isStart = false;
	}

	@Override
	public IConfiguration<String> getConfig() {
		return configuration;
	}

	@Override
	public void setConfig(IConfiguration<String> config) {
		configuration = config;
	}

	@Override
	public void reload() {

	}

	public void setSoapPacker(SoapPacker soapPacker) {
		this.soapPacker = soapPacker;
	}

	public void setSoapParser(SoapParser soapParser) {
		this.soapParser = soapParser;
	}

	@Override
	public String pack(ICompositeData compositeData) {
		String body = soapPacker.pack(compositeData);
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

		sb.append(envelopPart[0]);
		sb.append(body);
		sb.append(envelopPart[1]);
		return sb.toString();
	}

	@Override
	public ICompositeData unpack(String msg) {
		return soapParser.parse(msg.getBytes());
	}

}
