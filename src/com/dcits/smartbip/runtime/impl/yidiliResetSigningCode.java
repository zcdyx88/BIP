package com.dcits.smartbip.runtime.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
/*
 * 对私易得利签约后，ecif维护失败，重置操作，进行解约操作
 * */

public class yidiliResetSigningCode extends AbstractBaseService implements IService{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 635633173438871368L;
	private static final Log log = LogFactory.getLog(yidiliResetSigningCode.class);
	
	@Override
	public String getId() {
		return "yidiliResetSigningCode";
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
			log.info("开始调用基础服务[yidiliResetSigningCode]......");
		}

	    //新钱宝签约时，对应客户未签约基金，重置签约代码，进行基金ECIF关系维护
		SessionContext.getContext().setValue("qywhbzhi_DUISI", "2");
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[yidiliResetSigningCode]......");
		}
		
		return null;
	}


}
