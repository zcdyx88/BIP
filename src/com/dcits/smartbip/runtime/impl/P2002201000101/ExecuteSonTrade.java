package com.dcits.smartbip.runtime.impl.P2002201000101;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;

/*
 * 对私签约，让进行原子服务交易
 * */

public class ExecuteSonTrade extends AbstractBaseService implements IService{

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 4374271424105121778L;
	private static final Log log = LogFactory.getLog(ExecuteSonTrade.class);
	
	@Override
	public String getId() {
		return "ExecuteSonTrade";
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
			log.info("开始调用基础服务[ExecuteSonTrade]......");
		}
		
		SessionContext.getContext().setValue("ExecuteSonTrade", "true");
		log.info("参数检查通过，进行对私签约子交易！");
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[ExecuteSonTrade]......");
		}

		return null;
	}
	
}
