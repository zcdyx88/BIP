package com.dcits.smartbip.runtime.impl.p2002201000106;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;

public class exchgSignSonTrade extends AbstractBaseService implements IService{

	
    private static final Log log = LogFactory.getLog(exchgSignSonTrade.class);
	
	@Override
	public String getId() {
		return "exchgSignSonTrade";
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
			log.info("开始调用基础服务[exchgSignSonTrade]......");
		}
		
		SessionContext.getContext().setValue("exchgSignSonTrade", "true");
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[exchgSignSonTrade]......");
		}

		return null;
	}
}
