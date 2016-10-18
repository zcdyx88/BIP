package com.dcits.smartbip.runtime.impl.P2002201000104;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;

/*
 * 对公解约，让进行原子服务交易
 * */

public class ExecuteSonTrade_duigongjieyue extends AbstractBaseService implements IService{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7123223163297626947L;
	private static final Log log = LogFactory.getLog(ExecuteSonTrade_duigongjieyue.class);
	
	@Override
	public String getId() {
		return "ExecuteSonTrade_duigongjieyue";
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
			log.info("开始调用基础服务[ExecuteSonTrade_duigongjieyue]......");
		}
		
		SessionContext.getContext().setValue("ExecuteSonTrade_duigongjieyue", "true");
		log.info("参数检查通过，进行对公解约子交易！");
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[ExecuteSonTrade_duigongjieyue]......");
		}

		return null;
	}
}
