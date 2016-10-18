package com.dcits.smartbip.runtime.impl.P2002201000101;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;

public class WriteaECIFParameter_sancun extends AbstractBaseService implements IService{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6212594208314560559L;
	private static final Log log = LogFactory.getLog(WriteaECIFParameter_sancun.class);
	
	@Override
	public String getId() {
		return "WriteaECIFParameter_sancun";
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
			log.info("开始调用基础服务[WriteaECIFParameter_sancun]......");
		}
		
		SessionContext.getContext().setValue("SIGN_FLAG", "2");
		SessionContext.getContext().setValue("OPER_CODE", "1");
		SessionContext.getContext().setValue("SignProTp", "000008");
		SessionContext.getContext().setValue("SIGN_STAT", "1");
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[WriteaECIFParameter_sancun]......");
		}
		
		return null;
	}

}
