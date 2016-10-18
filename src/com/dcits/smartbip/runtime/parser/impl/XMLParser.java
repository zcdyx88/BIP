package com.dcits.smartbip.runtime.parser.impl;

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
public class XMLParser {

    private static final Log log = LogFactory.getLog(XMLParser.class);
//    private XMLInputFactory factory ;
    static private XMLInputFactory factory = XMLInputFactory.newInstance(); 
    
    public ICompositeData parse(byte[] content) {
    	
        ICompositeData root = null;
        try {
            XMLStreamReader reader = null;     
            factory = XMLInputFactory.newInstance();
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
            int lastEvent = -1;//最近一次事件
            String lastText = "";//解析的上一个事件值
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
                            /*    if (log.isDebugEnabled()) {
                                    log.debug("获取标签[" + tag + "]");
                                }*/
                                stack.push(soapCompositeData);
                            }
                        }
                        if ("BIPXMLZZ".equalsIgnoreCase(tag)) {
                            isStart = true;
                            isPayload = true;
                        }
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        if (isStart) {
                        	String text = "";
                        	if(XMLStreamConstants.CHARACTERS == lastEvent){//上一次事件也是XMLStreamConstants.CHARACTERS，代表是一个节点值
                        		text += lastText;
                        	}else{//否则清空上次事件值
                        		lastText="";
                        	}
                            text += reader.getText();
                            if (!"".equalsIgnoreCase(text) && !"\n".equalsIgnoreCase(text.trim()) && !"".equalsIgnoreCase(text.trim())) {
                                stack.peek().setValue(text);
                            }
                            lastText = text;
                        }
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        String endTag = reader.getLocalName();
                        if ("BIPXMLZZ".equalsIgnoreCase(endTag)) {
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
                lastEvent = event;
            }
/*          log.info("接收到的报文[" + new String(content) + "]");
            log.info("拆包后的结果[" + root.toString() + "]");*/                
        } catch (XMLStreamException e) {
            log.error("拆包失败", e);
        }
        log.debug("接收到的报文[" + new String(content) + "]");
        log.debug("拆包后的结果[" + root.toString() + "]");
//        System.out.println("拆包后的结果[" + root.toString() + "]");
        return root;
    }
    
    public static void main(String[] agrs)
    {
    	XMLParser soapParser = new XMLParser();
//    	byte[] contentArray = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><BIPXMLZZ><Reply_Msg><Sys_Head><LAST_CONSUMER_ID>ZJY</LAST_CONSUMER_ID><TRAN_TIMESTAMP>153426881</TRAN_TIMESTAMP><USER_ID>120588</USER_ID><SOURCE_BRANCH_NO>PINPAD.1111700120.zpk</SOURCE_BRANCH_NO><BRANCH_ID>411222</BRANCH_ID><CUSTOMER_ID></CUSTOMER_ID><RET_STATUS>S</RET_STATUS><DEST_BRANCH_NO>AJY</DEST_BRANCH_NO><RET_ARRAY><Row><RET_MSG>Success</RET_MSG><RET_CODE>000000</RET_CODE></Row></RET_ARRAY><TRAN_DATE>20191231</TRAN_DATE><TRAN_MODE>ONLINE</TRAN_MODE><SEQ_NO>PTL19123112058800070</SEQ_NO><SOURCE_TYPE>BC</SOURCE_TYPE><WS_ID>10.64.24.53</WS_ID><TRAN_CODE>010103</TRAN_CODE><SERVER_ID>10.101.8.87</SERVER_ID><CONSUMER_ID>PTL</CONSUMER_ID><SERVICE_CODE>SVR_FINANCIAL</SERVICE_CODE><FILE_PATH></FILE_PATH></Sys_Head><Body><REF_NO>PTL19123112058800070</REF_NO><LEDGER_BAL>391842.76</LEDGER_BAL><CASH_LEDGER_BAL>391842.76</CASH_LEDGER_BAL><AVAIL_BAL>271842.76</AVAIL_BAL><OTH_AVAIL_BAL_IND>CR</OTH_AVAIL_BAL_IND><ACCT_NAME>510101198810070181</ACCT_NAME><CLIENT_NAME>510101198810070181</CLIENT_NAME><CASH_ACTUAL_BAL_IND>CR</CASH_ACTUAL_BAL_IND><LEDGER_BAL_IND>CR</LEDGER_BAL_IND><OTH_CASH_ACTUAL_BAL>1452628.73</OTH_CASH_ACTUAL_BAL><OTH_CLIENT_NAME>411099zyyh</OTH_CLIENT_NAME><OTH_CASH_ACTUAL_BAL_IND>CR</OTH_CASH_ACTUAL_BAL_IND><OTH_CASH_LEDGER_BAL_IND>CR</OTH_CASH_LEDGER_BAL_IND><CASH_ACTUAL_BAL>391842.76</CASH_ACTUAL_BAL><AVAIL_BAL_IND>CR</AVAIL_BAL_IND><OTH_LEDGER_BAL_IND>CR</OTH_LEDGER_BAL_IND><APP_HEAD><MODULE_ID>RB</MODULE_ID><APPR_FLAG></APPR_FLAG><CURRENT_NUM>0</CURRENT_NUM><PROGRAM_ID>60601</PROGRAM_ID><AUTH_FLAG>N</AUTH_FLAG><PAGE_START>0</PAGE_START><TRAN_TYPE>Z005</TRAN_TYPE><USER_LANG>CHINESE</USER_LANG><TOTAL_NUM>-1</TOTAL_NUM><APPR_USER_ID></APPR_USER_ID><AUTH_USER_ID></AUTH_USER_ID><PAGE_END>0</PAGE_END><PGUP_OR_PGDN>0</PGUP_OR_PGDN><REVERSAL_TRAN_TYPE></REVERSAL_TRAN_TYPE></APP_HEAD><P1><Row><BASE_ACCT_NO>410520019520002801</BASE_ACCT_NO><TRAN_SEQ_NO>839963359</TRAN_SEQ_NO></Row><Row><BASE_ACCT_NO>411099010980000055</BASE_ACCT_NO><TRAN_SEQ_NO>839963360</TRAN_SEQ_NO></Row></P1><OTH_LEDGER_BAL>1452628.73</OTH_LEDGER_BAL><OTH_CASH_LEDGER_BAL>1452628.73</OTH_CASH_LEDGER_BAL><OTH_ACCT_NAME>许昌市电力代收费过渡户</OTH_ACCT_NAME><OTH_ALT_ACCT_NAME>许昌市电力代收费过渡户</OTH_ALT_ACCT_NAME><OTH_AVAIL_BAL>1452628.73</OTH_AVAIL_BAL><OTH_CH_CLIENT_NAME>411099zyyh</OTH_CH_CLIENT_NAME><CASH_LEDGER_BAL_IND>CR</CASH_LEDGER_BAL_IND></Body></Reply_Msg></BIPXMLZZ>".getBytes();
    	byte[] contentArray ="<?xml version=\"1.0\" encoding=\"GB18030\"?><BIPXMLZZ><Message><Body><CUSTOMER_NAME>510101198810070181</CUSTOMER_NAME><BUSI_CODE>4112000070000002</BUSI_CODE><USER_CODE>0020112</USER_CODE><PAY_AMT>1157.24</PAY_AMT><APP_HEAD><OPER_CODE>60601</OPER_CODE><PROGRAM_ID>60601</PROGRAM_ID><AUTH_FLAG>N</AUTH_FLAG></APP_HEAD><ACCT_TYPE>02</ACCT_TYPE><CCY>CNY</CCY><ACCNO>6236600010089217</ACCNO><TURN_FLAG>1</TURN_FLAG><MUST_AMT>1157.24</MUST_AMT><PAY_DATE>2016</PAY_DATE><BILL_NO></BILL_NO><PASSWORD>861E44E7C4AE2601</PASSWORD><PAY_PROJ>Z</PAY_PROJ></Body><Sys_Head><TRAN_TIMESTAMP>153426881</TRAN_TIMESTAMP><USER_ID>120588</USER_ID><SOURCE_BRANCH_NO>PINPAD.1111700120.zpk</SOURCE_BRANCH_NO><BRANCH_ID>411222</BRANCH_ID><CUSTOMER_ID></CUSTOMER_ID><DEST_BRANCH_NO>AJY</DEST_BRANCH_NO><TRAN_DATE>20191231</TRAN_DATE><SEQ_NO>PTL19123112058800070</SEQ_NO><TRAN_MODE>ONLINE</TRAN_MODE><SOURCE_TYPE>BC</SOURCE_TYPE><WS_ID>10.64.24.53</WS_ID><TRAN_CODE>01007031</TRAN_CODE><SERVER_ID>10.101.8.87</SERVER_ID><SERVICE_CODE>12</SERVICE_CODE><CONSUMER_ID>PTL</CONSUMER_ID></Sys_Head></Message></BIPXMLZZ>".getBytes();
//    	byte[] contentArray ="<?xml version=\"1.0\" encoding=\"GB18030\"?><BIPXMLZZ><Message><Body><ACCNO>11001051200011205</ACCNO><BIZ_TYPE>2016</BIZ_TYPE><INPUT_BAL_TYPE>000000115724</INPUT_BAL_TYPE><PASSWORD>201912310020112</PASSWORD><TRANSFER_MODE>00000028041070</TRANSFER_MODE><TRAN_AMT>Z</TRAN_AMT></Body><Sys_Head><TRAN_CODG>000001</TRAN_CODG></Sys_Head></Message></BIPXMLZZ>".getBytes();
    	soapParser.parse(contentArray);
    }
}
