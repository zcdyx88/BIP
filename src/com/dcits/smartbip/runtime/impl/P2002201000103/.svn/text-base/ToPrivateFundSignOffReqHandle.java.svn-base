package com.dcits.smartbip.runtime.impl.P2002201000103;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.runtime.model.impl.SoapCompositeData;
import com.dcits.smartbip.utils.CompositeDataUtils;
import com.dcits.smartbip.utils.PropertiesUtils;

/**
 *  对私基金解约请求报文处理
 *  解约基金必须先成功解约鑫钱宝和鑫元宝
 * @author srxhx273
 *
 */
public class ToPrivateFundSignOffReqHandle extends AbstractBaseService implements IService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4399115001972032987L;
	/**
	 * 日志
	 */
	private static final Log log = LogFactory.getLog(ToPrivateFundSignOffReqHandle.class);
	

	@Override
	public String getId() {
		return "ToPrivateFundSignOffReqHandle";
	}

	@Override
	public String getType() {
		return "base";

	}
	
	@Override
	public void setType(String type) {
		
	}
	@Override
	public Object execute(Object req) throws InvokeException {
		
		if (log.isInfoEnabled()) {
			log.info("开始调用基础服务[ToPrivateFundSignOffReqHandle]......");
		}
		//鑫钱宝和鑫元宝是否已解约：0-是，1-否
		String isSignOff = "1";
		//当前产品代码
		String signCodeType = (String)getContext().getValue("signCodeType");
		String retMsg = "";
		String retCode = "BIP00E0000";//解约结果
		try {
			//首先判断鑫元宝、鑫钱宝是否已签约，已签约则必须先成功解约方可进行基金解约
			//是否签约鑫元宝：0-是，1-否
			String isSignedXYB = (String)SessionContext.getContext().getValue("isSignedXYB");
			String retCode1 = "";
			if("0".equals(isSignedXYB)){
				ICompositeData Rsp3005200000113 = (ICompositeData) SessionContext.getContext().getValue("Rsp3005200000113");
				if (null == Rsp3005200000113)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
				{                          
					log.error("鑫元宝解约失败!");
					retMsg = "鑫元宝解约失败，不能解约基金";
					return null;
				} 
				retCode1 = CompositeDataUtils.getValue(Rsp3005200000113, "RspSysHead/RetCode");
				if(!"000000".equals(retCode1) && !PropertiesUtils.getSignOffRetCode("3005200000113").contains(retCode1)){
					log.error("鑫元宝解约失败!");
					retMsg = "鑫元宝解约失败，不能解约基金";
					return null;
				}
			}
			
			//是否签约鑫钱宝：0-是，1-否
			String isSignedXQB = (String)SessionContext.getContext().getValue("isSignedXQB");;
			if("0".equals(isSignedXQB)){
				ICompositeData Rsp3005200000112 = (ICompositeData) SessionContext.getContext().getValue("Rsp3005200000112");
				if (null == Rsp3005200000112)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
				{                          
					log.error("鑫钱宝解约失败!");
					retMsg = "鑫钱宝解约失败，不能解约基金";
					return null;
				}
				retCode1 = CompositeDataUtils.getValue(Rsp3005200000112, "RspSysHead/RetCode");
				if(!"000000".equals(retCode1) && !PropertiesUtils.getSignOffRetCode("3005200000112").contains(retCode1)){
					log.error("鑫钱宝解约失败!");
					retMsg = "鑫钱宝解约失败，不能解约基金";
					return null;
				}
			}
			isSignOff = "0";
			
			log.info("鑫钱宝、鑫元宝已成功解约或未签约，可进行基金解约");
		} catch (Exception e) {
			log.error("判断鑫钱宝、鑫元宝是否已解约异常:",e);
			retMsg = "系统异常";
			throw new InvokeException();
		}finally{
			SessionContext.getContext().setValue("isSignOff"+signCodeType, isSignOff);
			if("1".equals(isSignOff)){
				ICompositeData Rsp2002201000103 = (ICompositeData) SessionContext.getContext().getValue("Rsp2002201000103"); 
				
				if (null == Rsp2002201000103){                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
					Rsp2002201000103  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
					Rsp2002201000103.setId("Rsp2002201000103");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
					Rsp2002201000103.setxPath("/Rsp2002201000103");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
					getContext().setValue("Rsp2002201000103",Rsp2002201000103);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
				} 
				ICompositeData destArrayParent = CompositeDataUtils.mkNodeNotExist(Rsp2002201000103, "RspAppBody");
				ICompositeData arrayiItem = new SoapCompositeData();
				arrayiItem.setId("PrivSignOffRsltArray");
				destArrayParent.setChild("PrivSignOffRsltArray", arrayiItem);
				
				CompositeDataUtils.setValue(arrayiItem, "TransStatus", "F");
				CompositeDataUtils.setValue(arrayiItem, "RetCode", retCode);
				CompositeDataUtils.setValue(arrayiItem, "RetMsg", retMsg);
				CompositeDataUtils.setValue(arrayiItem, "SignOffProTp", signCodeType);
			}
			if (log.isInfoEnabled()) {
				log.info("调用基础服务结束[ToPrivateFundSignOffReqHandle]......");
			}
		}
		
		return null;
	}
}






