package com.dcits.smartbip.runtime.model.impl;

import com.dcits.smartbip.engine.impl.BaseProcessRepository;
import com.dcits.smartbip.engine.impl.ServiceIdentifyRepository;
import com.dcits.smartbip.exception.InstanceNotFoundException;
import com.dcits.smartbip.journal.FlowNoGenerator;
import com.dcits.smartbip.journal.JournalService;
import com.dcits.smartbip.journal.impl.JournalConstants;
import com.dcits.smartbip.journal.impl.RedisJournalService;
import com.dcits.smartbip.journal.impl.UniqueInnerFlowNoGenerator;
import com.dcits.smartbip.protocol.tcp.TCPHandler;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IDispatchable;
import com.dcits.smartbip.runtime.model.IDispatcher;
import com.dcits.smartbip.runtime.model.IProcessInstance;
import com.dcits.smartbip.runtime.packer.impl.SoapPacker;
import com.dcits.smartbip.runtime.packer.impl.XMLPacker;
import com.dcits.smartbip.runtime.parser.impl.XMLParser;
import com.dcits.smartbip.utils.CompositeDataUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by vincentfxz on 16/9/19.
 * TODO 暂时解决方案,xml拆组包,需要优化
 */
public class TCPProcessDispatcher implements IDispatcher<byte[]> {

    private static final Log log = LogFactory.getLog(TCPProcessDispatcher.class);
    private final XMLParser parser;
    private final FlowNoGenerator flowNoGenerator;
    private final JournalService journalService;
    private IDispatchable dispatchee;
    private IProcessInstance processInstance;
    private byte[] content;
    private TCPHandler tcpHandler;
    private String identifyId = "TCPServerIdentify";

    public TCPProcessDispatcher(byte[] dispatchee, TCPHandler tcpHandler) {
        parser = new XMLParser();
        //默认的内部流水号生成器
        flowNoGenerator = UniqueInnerFlowNoGenerator.getInstance();
        //默认的流水应用redis流水应用
        journalService = RedisJournalService.getInstance();
        this.content = dispatchee;
        this.tcpHandler = tcpHandler;
    }

    @Override
    public void setDispatchee(byte[] content) {
        this.content = content;
    }

    @Override
    public void dispatch(byte[] bytes) {
        //生成内部流水号
        String innerFlowNo = flowNoGenerator.generate();
        SessionContext.getContext().setValue(JournalConstants.UNIQUE_FLOW_NO, innerFlowNo);
        //获取报文头与尾,响应与请求使用相同的报文头
        String soapMessage = null;
		try {
			soapMessage = new String(content,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			log.error("读取请求异常,[" + e1 + "]");
		}
//		setIdentifyId("TCPServerIdentify");
        StringBuilder sbd = new StringBuilder();
    	int endIndex = soapMessage.indexOf("?>");
    	String xmlhead = soapMessage.substring(0,endIndex + 2);
    	soapMessage = soapMessage.substring(xmlhead.length());
    	sbd.append("<BIPXMLZZ>").append(soapMessage).append("</BIPXMLZZ>");
    	String destStr = xmlhead + sbd.toString();
        dispatchee = parser.parse(destStr.getBytes());
        //服务识别
        if(null != identifyId){
            try {
                IdentifyService identifyService = ServiceIdentifyRepository.getRepository().get(identifyId);
                identifyService.identify((ICompositeData) dispatchee);
            } catch (InstanceNotFoundException e) {
                log.error(e,e);
            }
        }
        String dispatchId = dispatchee.getDispatchId();
        dispatchId = StringUtils.removeStart(dispatchId, "Req");
        if (log.isDebugEnabled()) {
            log.debug("组合服务 [" + dispatchId + "]处理开始");
        }
        //记录流水
        journalService.invoke(innerFlowNo, "", dispatchId, "", soapMessage, "1");
        try {
            processInstance = BaseProcessRepository.getRepository().get(dispatchId);
            SessionContext.getContext().setValue("Req" + dispatchee.getDispatchId(), dispatchee);
            SessionContext.getContext().setValue("DISPATCH_ID", dispatchId);
            if (log.isDebugEnabled()) {
                log.debug("开始执行流程[" + dispatchId + "],流程输入为[" + new String(content) + "]");
            }
            processInstance.execute();
        } catch (InstanceNotFoundException e) {
            log.error("流程[" + dispatchId + "]未找到");
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("开始结束处理");
            }
            try {
                ICompositeData compositeData = (ICompositeData) SessionContext.getContext().getValue("Rsp" + dispatchId);
                StringBuilder sb = new StringBuilder();
                if (null != compositeData) {
                    XMLPacker packer = new XMLPacker();
                    
                    ICompositeData compositeDataa = null;
                	Set<String> set = compositeData.getChildren().keySet();
                	for (Iterator it = set.iterator(); it.hasNext();) {
            	    	String sid = (String) it.next();
            	    	//去掉req + serviceid
            	    	compositeDataa = compositeData.getChildren().get(sid).get(0);
                	}
                	
                    String payLoad = packer.pack(compositeDataa);
                    sb.append(xmlhead);
                    sb.append(payLoad);
                } else {
                    String defaultErrorMsg =
                            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                            		" <Reply_Msg>\n" +
                                    "  <Sys_Head>\n" +
                                    "  <HX_ARRAY>\n" +
                                    "    <Row>\n" +
                                    "     <RET_CODE>error</RET_CODE>\n" +
                                    "      <RET_MSG>bip-error</RET_MSG>\n" +
                                    "    </Row>\n" +
                                    "  </HX_ARRAY>\n" +
                                    " </Sys_Head>\n"+
                                    "</Reply_Msg>";
                    sb.append(defaultErrorMsg);
                }
                if (log.isDebugEnabled()) {
                    log.debug("响应报文:");
                    log.debug(sb.toString());
                }
                tcpHandler.writeAndShutdown(sb.toString().getBytes());
                if (log.isDebugEnabled()) {
                    log.debug("组合服务[" + dispatchId + "]处理结束");
                }
                //返回后的流水
                journalService.invoke(innerFlowNo, "", dispatchId, "", sb.toString(), "2");
            } catch (IOException e) {
                log.error(e, e);
            }
        }
    }


    @Override
    public void run() {

    }

    public void setIdentifyId(String identifyId) {
        this.identifyId = identifyId;
    }
}
