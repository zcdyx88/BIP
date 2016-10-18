package com.dcits.smartbip.runtime.impl.P2002201000101;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;

/*
 * 对私签约时，ECIF维护时重置签约状态为解
 * */

public class ResetECIFSIGN_STAT_jijin extends AbstractBaseService implements IService{


    /**
	 * 
	 */
	private static final long serialVersionUID = -3673400039140999310L;
	private static final Log log = LogFactory.getLog(ResetECIFSIGN_STAT_jijin.class);
	
	@Override
	public String getId() {
		return "ResetECIFSIGN_STAT_jijin";
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
			log.info("开始调用基础服务[ResetECIFSIGN_STAT_jijin]......");
		}

	    //ECIF关系维护时，重置签约状态为解
		SessionContext.getContext().setValue("SIGN_STAT", "2");
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[ResetECIFSIGN_STAT_jijin]......");
		}
		
		return null;
	}
}
