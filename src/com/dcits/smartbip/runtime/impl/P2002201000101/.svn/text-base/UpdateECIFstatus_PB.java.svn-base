package com.dcits.smartbip.runtime.impl.P2002201000101;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;

public class UpdateECIFstatus_PB extends AbstractBaseService implements IService{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -988784433230443746L;
	private static final Log log = LogFactory.getLog(UpdateECIFstatus_PB.class);

	@Override
	public String getId() {
		return "UpdateECIFstatus_PB";
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
			log.info("开始调用基础服务[UpdateECIFstatus_PB]......");
		}
		//进行赋值，后面ECIF维护使用
	    SessionContext.getContext().setValue("SignProTp", "000001"); 
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[UpdateECIFstatus_PB]......");
		}

		return null;
	}
	
}
