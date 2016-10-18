package com.dcits.smartbip.runtime.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.utils.CompositeDataUtils;

/**
 *  对私综合签约
 *  从上送报文中得到账户类型，判断非卡情况
 *
 */

public class DecideAccountypeCard extends AbstractBaseService implements IService{
	
    private static final Log log = LogFactory.getLog(DecideAccountypeCard.class);
	
	@Override
	public String getType() {
		return "base";
	}

	@Override
	public void setType(String type) {
		
	}
	
	@Override
	public String getId() {
		return "DecideAccountypeCard";
	}
	
	@Override
	public Object execute(Object req) throws InvokeException {
		
		if (log.isInfoEnabled()) {
			log.info("开始调用基础服务[DecideAccountypeCard]......");
		}
		
		ICompositeData Req2002201000101 = (ICompositeData) SessionContext
				.getContext().getValue("Req2002201000101");

		String AcctTp = CompositeDataUtils.getValue(Req2002201000101,
		"ReqAppBody/AcctTp");
		
		
		if ("1".equals(AcctTp)) {
			SessionContext.getContext().setValue("AccountypeCard", "true");
			log.debug("该账户类型为卡类型");
		} else {
			SessionContext.getContext().setValue("AccountypeCard", "false");
			log.debug("该账户类型为非卡类型");
		} 
		
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[DecideAccountypeCard]......");
		}
		return null;
	}

}
