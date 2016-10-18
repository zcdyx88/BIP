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

public class ErrorCheckParameter extends AbstractBaseService implements IService{

	private static final Log log = LogFactory.getLog(ErrorCheckParameter.class);
	ICompositeData rspService;
	
	@Override
	public String getId() {
		return "ErrorCheckParameter";
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
			log.info("开始调用基础服务[ErrorCheckParameter]......");
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
        
		if(SessionContext.getContext().getValue("Duanxin-Kazhzhang").equals("false")){
			
		    String temp = "参数检查失败:附卡/单位卡签约产品是否不仅为短信和卡卡转账";
			log.info("参数检查失败:"+temp); 
					
			CompositeDataUtils.setValue(Rsp2002201000101,"RspSysHead/TransStatus", "F");
			CompositeDataUtils.setValue(Rsp2002201000101,"RspSysHead/RetCode", "BIP0003");
			CompositeDataUtils.setValue(Rsp2002201000101,"RspSysHead/RetMsg", temp);
		}
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[ErrorCheckParameter]......");
		}
		
		return null;
	}
}
