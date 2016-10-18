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

public class RetErrorMessage_xinqianbao extends AbstractBaseService implements IService{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7428308806331181573L;
	private static final Log log = LogFactory.getLog(RetErrorMessage_xinqianbao.class);
	private String signPro = "000010";
	private String signProNm ="鑫钱宝";
	@Override
	public String getId() {
		return "RetErrorMessage_xinqianbao";
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
			log.info("开始调用基础服务[RetErrorMessage_xinqianbao]......");
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

		ICompositeData destArrayParent = CompositeDataUtils.mkNodeNotExist(Rsp2002201000101, "RspAppBody");
		ICompositeData arrayiItem = new SoapCompositeData();
		arrayiItem.setId("PrivSignRsltArray");
		destArrayParent.setChild("PrivSignRsltArray", arrayiItem);
		CompositeDataUtils.setValue(arrayiItem, "RetCode", "BIP9999");
		CompositeDataUtils.setValue(arrayiItem, "TransStatus", "F");			
		CompositeDataUtils.setValue(arrayiItem, "RspSysHead/RetMsg", "基金ECIF维护失败，不进行鑫钱宝ECIF维护！");
		CompositeDataUtils.setValue(arrayiItem, "SignProTp", signPro);
		CompositeDataUtils.setValue(arrayiItem, "SignProNm", signProNm);
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[RetErrorMessage_xinqianbao]......");
		}
		
		return null;
		
	}
}
