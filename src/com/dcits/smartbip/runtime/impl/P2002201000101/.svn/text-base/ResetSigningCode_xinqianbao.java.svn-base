package com.dcits.smartbip.runtime.impl.P2002201000101;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;

/*
 * 对私签约时，鑫钱宝客户未签约基金，进行签约代码重置，又要重置回去，进行鑫钱宝ECIF关系维护
 * */

public class ResetSigningCode_xinqianbao extends AbstractBaseService implements IService {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 7937368535686566722L;
	private static final Log log = LogFactory.getLog(ResetSigningCode_xinqianbao.class);
	
	@Override
	public String getId() {
		return "ResetSigningCode_xinqianbao";
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
			log.info("开始调用基础服务[ResetSigningCode_xinqianbao]......");
		}

	    //新钱宝签约时，重置签约代码为鑫钱宝，进行鑫钱宝ECIF关系维护
		SessionContext.getContext().setValue("SignProTp", "000010");
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[ResetSigningCode_xinqianbao]......");
		}
		
		return null;
	}

}
