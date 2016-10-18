package com.dcits.smartbip.runtime.parser;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.impl.SoapCompositeData;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Stack;

/**
 * Created by vincentfxz on 16/5/25.
 */
public class SoapParser {

    private static final Log log = LogFactory.getLog(SoapParser.class);

    public ICompositeData parse(byte[] content) {
        ICompositeData root = null;
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = null;

            reader = factory.createXMLStreamReader(new ByteArrayInputStream(content));
            boolean isStart = false;
            boolean isPayload = false;
            Stack<ICompositeData> stack = new Stack<ICompositeData>();
            Stack<String> indexStack = new Stack<String>();
            String index = "";
            ICompositeData compositeData = new SoapCompositeData();
            root = compositeData;
            stack.push(compositeData);
            indexStack.push(index);
            while (reader.hasNext()) {
                int event = reader.next();
                switch (event) {
                    case XMLStreamConstants.COMMENT:
                        break;
                    case XMLStreamConstants.START_ELEMENT:
                        String tag = reader.getLocalName();
                        if (isStart) {
//                            index = indexStack.peek() + "/" + tag;
                            indexStack.push(index);
                            compositeData = stack.peek();
                            if (isPayload) {
                                compositeData.setPayLoad(tag);
                                compositeData.setId(tag);
                                isPayload = false;
                                String xpath = "/" + tag;
                                stack.peek().setxPath(xpath);
                            } else {
                                List<ICompositeData> compositeDatas = compositeData.getChild(index);
                                int length = compositeDatas.size();
                                String xpath = compositeData.getxPath() + "/" + tag + "[" + length + "]";
                                SoapCompositeData soapCompositeData = new SoapCompositeData();
                                soapCompositeData.setId(tag);
                                soapCompositeData.setxPath(xpath);
                                compositeData.setChild(tag, soapCompositeData);
//                                //在根节点中加入索引
//                                for(ICompositeData superCompositeData : stack){
//                                    superCompositeData.setChild(index, soapCompositeData);
//                                }
                                if (log.isDebugEnabled()) {
                                    log.debug("获取标签[" + tag + "]");
                                }
                                stack.push(soapCompositeData);
                            }
                        }
                        if ("Body".equalsIgnoreCase(tag)) {
                            isStart = true;
                            isPayload = true;
                        }
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        if (isStart) {
                            String text = reader.getText();
                            if (!"".equalsIgnoreCase(text) && !"\n".equalsIgnoreCase(text.trim()) && !"".equalsIgnoreCase(text.trim())) {
                                stack.peek().setValue(text);
                            }
                        }
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        String endTag = reader.getLocalName();
                        if ("Body".equalsIgnoreCase(endTag)) {
                            isStart = false;
                        }
                        if (isStart) {
                            indexStack.pop();
                            stack.pop();
                        }
                        break;
                    case XMLStreamConstants.START_DOCUMENT:
                        break;
                }
            }
/*            log.info("接收到的报文[" + new String(content) + "]");
            log.info("拆包后的结果[" + root.toString() + "]");*/                
        } catch (XMLStreamException e) {
            log.error("拆包失败", e);
        }
        log.debug("接收到的报文[" + new String(content) + "]");
        log.debug("拆包后的结果[" + root.toString() + "]");
        return root;
    }
}
