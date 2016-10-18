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

public class ToPrivateSignInformation_jijin extends AbstractBaseService implements IService{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4257883535528075333L;
	private static final Log log = LogFactory.getLog(ToPrivateSignInformation_jijin.class);
	private String signPro = "000020";
	private String signProNm ="基金";
	@Override
	public String getId() {
		return "ToPrivateSignInformation_jijin";
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
			log.info("开始调用基础服务[ToPrivateSignInformation_jijin]......");
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
		if("true".equalsIgnoreCase((String) SessionContext.getContext().getValue("CustomerAlreadySigningJijin"))){
			rspService = (ICompositeData) SessionContext.getContext().getValue("Rsp3005200000107"); 
		}else if ("false".equalsIgnoreCase((String) SessionContext.getContext().getValue("CustomerAlreadySigningJijin"))){
			rspService = (ICompositeData) SessionContext.getContext().getValue("Rsp3005200000106");
		}
		
		String retCode = CompositeDataUtils.getValue(rspService, "RspSysHead/RetCode");
		String Jij_Xinqb_type = (String) SessionContext.getContext().getValue("Jijin_Xinqianbao");
		if(null != retCode && ("000000".equalsIgnoreCase(retCode) || "1106".equalsIgnoreCase(retCode)))
		{
			String SignProTp = (String)SessionContext.getContext().getValue("SignProTp");
			//同时发起鑫钱宝和基金交易，基金成功，该状态使签鑫钱宝时不去做基金ecif维护
			SessionContext.getContext().setValue("JijinAlreadySign_duisi", "true");
			log.info(SignProTp+"签约成功或已签约，返回码="+retCode);
		} 
		else
		{
			String SignProTp = (String)SessionContext.getContext().getValue("SignProTp");
			log.info(SignProTp+"签约失败，返回码="+retCode); 
			if("false".equalsIgnoreCase(Jij_Xinqb_type)){
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
			}else if("true".equalsIgnoreCase(Jij_Xinqb_type)){
				SessionContext.getContext().setValue("sign_xinqianbao", "false");
				for(int i= 0;i<2; i++){
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
					if(i==0){
					    CompositeDataUtils.copy(rspService, arrayiItem, "RspSysHead/RetMsg", "RetMsg");
					    CompositeDataUtils.setValue(arrayiItem, "SignProTp", signPro);
						CompositeDataUtils.setValue(arrayiItem, "SignProNm", signProNm);
					}else if (i==1){
					    CompositeDataUtils.setValue(arrayiItem, "RetMsg", "基金签约失败，鑫钱宝无法签约！");
					    CompositeDataUtils.setValue(arrayiItem, "SignProTp", "000010");
						CompositeDataUtils.setValue(arrayiItem, "SignProNm", "鑫钱宝");
						CompositeDataUtils.setValue(arrayiItem, "RetCode", "BIP9999");
					}
				}
			}
			
		}
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[ToPrivateSignInformation_jijin]......");
		}
		
		return null;
		
	}


}
