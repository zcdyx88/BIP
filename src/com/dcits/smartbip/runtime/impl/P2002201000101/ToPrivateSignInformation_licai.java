package com.dcits.smartbip.runtime.impl.P2002201000101;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.runtime.model.impl.SoapCompositeData;
import com.dcits.smartbip.utils.CompositeDataUtils;

public class ToPrivateSignInformation_licai extends AbstractBaseService implements IService{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3577000324192409685L;
	private static final Log log = LogFactory.getLog(ToPrivateSignInformation_licai.class);
	private String signPro = "000021";
	private String signProNm = "理财";
	@Override
	public String getId() {
		return "ToPrivateSignInformation_licai";
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
			log.info("开始调用基础服务[ToPrivateSignInformation_licai]......");
		}
		
		ICompositeData Rsp2002201000101 = (ICompositeData) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		.getContext().getValue("Rsp2002201000101"); 
		
		if (null == Rsp2002201000101)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			Rsp2002201000101  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			Rsp2002201000101.setId("Rsp2002201000101");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			Rsp2002201000101.setxPath("/Rsp2002201000101");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			getContext().setValue("Rsp2002201000101",Rsp2002201000101);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		} 

        ICompositeData rspService = null;
		if("true".equalsIgnoreCase((String) SessionContext.getContext().getValue("CustomerAlreadySigningLicai"))){
			rspService = (ICompositeData) SessionContext.getContext().getValue("Rsp3005200000102"); 
		}else if ("false".equalsIgnoreCase((String) SessionContext.getContext().getValue("CustomerAlreadySigningLicai"))){
			rspService = (ICompositeData) SessionContext.getContext().getValue("Rsp3005200000101");
		}
		
		String retCode = CompositeDataUtils.getValue(rspService, "RspSysHead/RetCode");
		if(null != retCode && ("000000".equalsIgnoreCase(retCode) || "1106".equalsIgnoreCase(retCode)))
		{
			String SignProTp = (String)SessionContext.getContext().getValue("SignProTp");
			log.info(SignProTp+"签约成功或已签约，返回码="+retCode);
		} 
		else
		{
			String SignProTp = (String)SessionContext.getContext().getValue("SignProTp");
			log.info(SignProTp+"签约失败，返回码="+retCode); 
			
			ICompositeData destArrayParent = CompositeDataUtils.mkNodeNotExist(Rsp2002201000101, "RspAppBody");
			ICompositeData arrayiItem = new SoapCompositeData();
			arrayiItem.setId("PrivSignRsltArray");
			destArrayParent.setChild("PrivSignRsltArray", arrayiItem);
			if(null != retCode)
			{
				CompositeDataUtils.setValue(arrayiItem, "RetCode", retCode);
			}
			else
			{
				CompositeDataUtils.setValue(arrayiItem, "RetCode", "BIP9999");
			}
			
			CompositeDataUtils.setValue(arrayiItem, "TransStatus", "F");			
			CompositeDataUtils.copy(rspService, arrayiItem, "RspSysHead/RetMsg", "RetMsg");
			CompositeDataUtils.setValue(arrayiItem, "SignProTp", signPro);
			CompositeDataUtils.setValue(arrayiItem, "SignProNm", signProNm);
		}	
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[ToPrivateSignInformation_licai]......");
		}
		
		return null;
		
	}


}
