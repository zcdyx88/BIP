package com.dcits.smartbip.runtime.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;

public class DCResetOperation extends AbstractBaseService implements IService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1404143501146338095L;
	private static final Log log = LogFactory.getLog(DCResetOperation.class);
	
	@Override
	public String getId() {
		return "DCResetOperation";
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
			log.info("开始调用基础服务[DCResetOperation]......");
		}

	    //ECIF维护失败并且此账户之前未签约，重置信用卡操作标志进行反交易
	    SessionContext.getContext().setValue("XYCZBZ_DUISI", "4");
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[DCResetOperation]......");
		}
		
		return null;
	}
	

}
