package com.dcits.smartbip.runtime.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;

/*
 * 对私签约时，鑫钱宝客户未签约基金，进行签约代码重置，进行基金ECIF关系维护
 * */

public class ResetSigningCode_jijin extends AbstractBaseService implements IService {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = -1361090486074995414L;
	private static final Log log = LogFactory.getLog(ResetSigningCode_jijin.class);
	
	@Override
	public String getId() {
		return "ResetSigningCode_jijin";
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
			log.info("开始调用基础服务[ResetSigningCode_jijin]......");
		}

	    //鑫钱宝签约时，对应客户未签约基金，重置签约代码为基金，进行基金ECIF关系维护
		SessionContext.getContext().setValue("SignProTp", "000020");
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[ResetSigningCode_jijin]......");
		}
		
		return null;
	}

	

}
