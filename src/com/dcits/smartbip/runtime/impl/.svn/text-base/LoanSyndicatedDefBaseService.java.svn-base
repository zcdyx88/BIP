package com.dcits.smartbip.runtime.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;


/***
 * 银团
 * @author xhx102
 *
 */
public class LoanSyndicatedDefBaseService  extends AbstractBaseService implements IService{
	private static final Log log = LogFactory.getLog(LoanSyndicatedDefBaseService.class);
	private static final long serialVersionUID = 1L;

	
	@Override
	public String getId() {
		return "LoanSyndicatedDefBaseService";
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
			log.info("开始调用基础服务[LoanSyndicatedDefBaseService]......");
		}
		
		getContext().setValue("__LOAN_KIND__", "3002101000104");  
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[LoanSyndicatedDefBaseService]......");
		}
		return null;
		
	}
}
