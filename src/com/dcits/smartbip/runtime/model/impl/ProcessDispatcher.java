package com.dcits.smartbip.runtime.model.impl;

import com.dcits.smartbip.engine.impl.BaseProcessRepository;
import com.dcits.smartbip.exception.InstanceNotFoundException;
import com.dcits.smartbip.journal.FlowNoGenerator;
import com.dcits.smartbip.journal.JournalService;
import com.dcits.smartbip.journal.impl.JournalConstants;
import com.dcits.smartbip.journal.impl.RedisJournalService;
import com.dcits.smartbip.journal.impl.UniqueInnerFlowNoGenerator;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IDispatchable;
import com.dcits.smartbip.runtime.model.IDispatcher;
import com.dcits.smartbip.runtime.model.IProcessInstance;
import com.dcits.smartbip.runtime.packer.impl.SoapPacker;
import com.dcits.smartbip.runtime.parser.impl.SoapParser;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.reficio.ws.SoapContext;
import org.reficio.ws.builder.SoapBuilder;
import org.reficio.ws.builder.SoapOperation;
import org.reficio.ws.builder.core.Wsdl;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by vincentfxz on 16/5/25.
 * 协议分派类该类需要添加以下功能:
 * 1)服务识别
 * 2)流水配置
 */
public class ProcessDispatcher implements IDispatcher<byte[]> {
    private static final Log log = LogFactory.getLog(ProcessDispatcher.class);

    private final SoapParser parser;
    private final FlowNoGenerator flowNoGenerator;
    private IDispatchable dispatchee;
    private IProcessInstance processInstance;
    private JournalService journalService;
    private OutputStream outputStream;
    private byte[] content;
    private final String[] envelopPart = new String[2];

    public ProcessDispatcher(byte[] dispatchee, OutputStream outputStream) {
        //TODO 拆组包应该根据协议中的报文类型去确定,默认的拆组包为SOAP拆组包
        parser = new SoapParser();
        //默认的内部流水号生成器
        flowNoGenerator = UniqueInnerFlowNoGenerator.getInstance();
        //默认的流水应用redis流水应用
        journalService = RedisJournalService.getInstance();
        this.content = dispatchee;
        this.outputStream = outputStream;
    }

    @Override
    public void setDispatchee(byte[] content) {
        this.content = content;
    }

    public byte[] getDispatchee() {
        return this.content;
    }

    @Override
    public void dispatch(byte[] content) {
        //TODO 增加流水记录
        //生成内部流水号
    	SessionContext.getContext().clear();
        String innerFlowNo = flowNoGenerator.generate();
        SessionContext.getContext().setValue(JournalConstants.UNIQUE_FLOW_NO, innerFlowNo);
    	//获取报文头与尾,响应与请求使用相同的报文头
    	String  soapMessage = new String(content);
    	if(soapMessage.indexOf("<Body>") >-1 )
    	{
    		envelopPart[0] = soapMessage.substring(0, soapMessage.indexOf("<Body>") + "<Body>".length());
    	    envelopPart[1] = soapMessage.substring(soapMessage.indexOf("</Body>"));
    	}
    	else if( soapMessage.indexOf("<soapenv:Body>") >-1 )
    	{
    		envelopPart[0] = soapMessage.substring(0, soapMessage.indexOf("<soapenv:Body>") + "<soapenv:Body>".length());
    	    envelopPart[1] = soapMessage.substring(soapMessage.indexOf("</soapenv:Body>"));
    	}
/*    	envelopPart[0] = soapMessage.substring(0, soapMessage.indexOf("<soapenv:Body>") + "<soapenv:Body>".length());
	    envelopPart[1] = soapMessage.substring(soapMessage.indexOf("</soapenv:Body>"));*/
    	
        dispatchee = parser.parse(content);
        //TODO 增加服务识别
        String dispatchId = dispatchee.getDispatchId();             
        dispatchId = StringUtils.removeStart(dispatchId, "Req");
        if (log.isInfoEnabled()) {
            log.info("组合服务 [" + dispatchId + "]处理开始");
        }
        //记录流水
        journalService.invoke(innerFlowNo,"",dispatchId, "", soapMessage, "1");
        try {
            processInstance = BaseProcessRepository.getRepository().get(dispatchId);
            SessionContext.getContext().setValue(dispatchee.getDispatchId(), dispatchee);
            SessionContext.getContext().setValue("DISPATCH_ID", dispatchId);
            if (log.isInfoEnabled()) {
                log.info("开始执行流程[" + dispatchId + "],流程输入为[" + new String(content) + "]");
            }
            processInstance.execute();

        } catch (InstanceNotFoundException e) {
            log.error("流程[" + dispatchId + "]未找到");
        } finally {
            if (log.isInfoEnabled()) {
                log.info("开始结束处理");
            }
            try {
                ICompositeData compositeData = (ICompositeData) SessionContext.getContext().getValue("Rsp" + dispatchId);
                StringBuilder sb = new StringBuilder();
                if (null != compositeData) {
                    SoapPacker packer = new SoapPacker();
                    String payLoad = packer.pack(compositeData);
                    sb.append(envelopPart[0]);
                    sb.append(payLoad);
                    sb.append(envelopPart[1]);
                } else {
    /*                String defaultErrorMsg =
                            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                            "  <soapenv:Header/>\n" +
                            "  <soapenv:Body>\n" +
                            "    <soapenv:Fault>\n" +
                            "      <faultcode>error</faultcode>\n" +
                            "      <faultstring>error</faultstring>\n" +
                            "    </soapenv:Fault>\n" +
                            "  </soapenv:Body>\n" +
                            "</soapenv:Envelope>";*/
                    sb.append(envelopPart[0]);
                    sb.append("<").append("Rsp"+dispatchId).append(">");
                    sb.append("<RspSysHead>");
                    sb.append("<RetCode>").append("BIP0001").append("</RetCode>");
                    sb.append("<RetMsg>").append("未知错误").append("</RetMsg>");
                    sb.append("</RspSysHead>");
                    sb.append("</").append("Rsp"+dispatchId).append(">");
                    sb.append(envelopPart[1]);
                }
                if (log.isInfoEnabled()) {
                    log.info("响应报文:");
                    log.info("\n"+sb.toString());
                }
                outputStream.write(sb.toString().getBytes());
                outputStream.flush();
                if (log.isInfoEnabled()) {
                    log.info("组合服务[" + dispatchId + "]处理结束");
                }
                //返回后的流水
                journalService.invoke(innerFlowNo, "", dispatchId, "", sb.toString(), "2");
            } catch (IOException e) {
                log.error(e, e);
            } finally {
                if (null != outputStream) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        log.error(e, e);
                    }
                }
            }
        }
//        ProcessPool.getInstance().dispatch(this);
    }
    @Override
    public void run() {


    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }
}
