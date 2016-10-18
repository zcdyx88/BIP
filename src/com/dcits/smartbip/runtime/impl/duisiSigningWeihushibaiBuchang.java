package com.dcits.smartbip.runtime.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;

/*
 * 对私签约维护失败补偿
 * */

public class duisiSigningWeihushibaiBuchang extends AbstractBaseService implements IService{

	private static final Log log = LogFactory.getLog(duisiSigningWeihushibaiBuchang.class);

	@Override
	public String getId() {
		return "duisiSigningWeihushibaiBuchang";
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
			log.info("开始调用基础服务[duisiSigningWeihushibaiBuchang]......");
		}
		
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[duisiSigningWeihushibaiBuchang]......");
		}
		return null;
	}
}
