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

public class ToPrivateSignInformation_PBMB extends AbstractBaseService implements IService{


	/**
	 * 
	 */
	private static final long serialVersionUID = 4614679195256200989L;
	private static final Log log = LogFactory.getLog(ToPrivateSignInformation_PBMB.class);
	@Override
	public String getId() {
		return "ToPrivateSignInformation_PBMB";
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
			log.info("开始调用基础服务[ToPrivateSignInformation_PBMB]......");
		}
		
		ICompositeData Rsp2002201000101 = (ICompositeData) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		.getContext().getValue("Rsp2002201000101"); 
		ICompositeData Rsp1003200000102 = (ICompositeData) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		.getContext().getValue("Rsp1003200000102"); 
		
		if (null == Rsp2002201000101)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			Rsp2002201000101  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			Rsp2002201000101.setId("Rsp2002201000101");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			Rsp2002201000101.setxPath("/Rsp2002201000101");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			getContext().setValue("Rsp2002201000101",Rsp2002201000101);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		} 
                     
		
		String retCode = CompositeDataUtils.getValue(Rsp1003200000102, "RspSysHead/RetCode");
		
		
		if(!retCode.equalsIgnoreCase("000000"))
		{	
			if((String)SessionContext.getContext().getValue("PBResult")!=null && ((String)SessionContext.getContext().getValue("PBResult")).equalsIgnoreCase("true")){
				ICompositeData destArrayParent = CompositeDataUtils.mkNodeNotExist(Rsp2002201000101, "RspAppBody");
				ICompositeData arrayiItem = new SoapCompositeData();
				arrayiItem.setId("PrivSignRsltArray");
				destArrayParent.setChild("PrivSignRsltArray", arrayiItem);
				CompositeDataUtils.setValue(arrayiItem, "TransStatus", "F");
				CompositeDataUtils.copy(Rsp1003200000102, arrayiItem, "RspSysHead/RetMsg", "RetMsg");
				if(retCode!=null){
					CompositeDataUtils.copy(Rsp1003200000102, arrayiItem, "RspSysHead/RetCode" , "RetCode");
					if(retCode.equals("ZH0100"))
						CompositeDataUtils.setValue(arrayiItem, "RetMsg", "网银/手机银行客户信息已存在，请直接调用渠道维护交易！");
				}
				CompositeDataUtils.setValue(arrayiItem, "SignProTp", "000001");
				CompositeDataUtils.setValue(arrayiItem, "SignProNm", "个人银行");
			}
			if((String)SessionContext.getContext().getValue("MBResult")!=null && ((String)SessionContext.getContext().getValue("MBResult")).equalsIgnoreCase("true")){
				ICompositeData destArrayParent = CompositeDataUtils.mkNodeNotExist(Rsp2002201000101, "RspAppBody");
				ICompositeData arrayiItem = new SoapCompositeData();
				arrayiItem.setId("PrivSignRsltArray");
				destArrayParent.setChild("PrivSignRsltArray", arrayiItem);
				CompositeDataUtils.setValue(arrayiItem, "TransStatus", "F");
				CompositeDataUtils.copy(Rsp1003200000102, arrayiItem, "RspSysHead/RetMsg", "RetMsg");
				if(retCode!=null){
					CompositeDataUtils.copy(Rsp1003200000102, arrayiItem, "RspSysHead/RetCode" , "RetCode");
					if(retCode.equals("ZH0100"))
						CompositeDataUtils.setValue(arrayiItem, "RetMsg", "网银/手机银行客户信息已存在，请直接调用渠道维护交易！");
				}
				CompositeDataUtils.setValue(arrayiItem, "SignProTp", "000002");
				CompositeDataUtils.setValue(arrayiItem, "SignProNm", "手机银行");
			}
		}	
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[ToPrivateSignInformation_PBMB]......");
		}
		
		return null;
		
	}

}
