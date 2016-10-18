package com.dcits.smartbip.runtime.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.runtime.model.impl.SoapCompositeData;
import com.dcits.smartbip.utils.CompositeDataUtils;

public class DuisiPhoneBankErrorMessage extends AbstractBaseService implements IService{
	
    private static final Log log = LogFactory.getLog(DuisiPhoneBankErrorMessage.class);
	
	@Override
	public String getId() {
		return "DuisiPhoneBankErrorMessage";
	}

	@Override
	public String getType() {
		return "base";
	}
	
	@Override
	public void setType(String type) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public Object execute(Object req) throws InvokeException {

		if (log.isInfoEnabled()) {
			log.info("开始调用基础服务[DuisiPhoneBankErrorMessage]......");
		}
		
		ICompositeData Rsp2002201000101 = (ICompositeData) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		.getContext().getValue("Rsp2002201000101"); 
		
		String errormessage = "该客户已签约电话银行，无需进行签约！";
		log.info(errormessage);
			
		ICompositeData destArrayParent = CompositeDataUtils.mkNodeNotExist(Rsp2002201000101, "RspAppBody");
		ICompositeData arrayiItem = new SoapCompositeData();
		arrayiItem.setId("PrivSignRsltArray");
		destArrayParent.setChild("PrivSignRsltArray", arrayiItem);
			
		CompositeDataUtils.setValue(arrayiItem, "TransStatus", "F");
		CompositeDataUtils.setValue(arrayiItem, "RspSysHead/RetCode" , "BIP9999");
		CompositeDataUtils.setValue(arrayiItem, "RetMsg", errormessage);
		CompositeDataUtils.setValue(arrayiItem, "SignProTp", "000004");
		CompositeDataUtils.setValue(arrayiItem, "SignProNm", "电话银行");
			
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[DuisiPhoneBankErrorMessage]......");
		}

		return null;
	}

}
