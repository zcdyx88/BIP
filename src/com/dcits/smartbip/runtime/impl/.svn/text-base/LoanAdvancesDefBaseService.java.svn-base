package com.dcits.smartbip.runtime.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;


/***
 * 垫款贷款
 * @author xhx102
 *
 */
public class LoanAdvancesDefBaseService  extends AbstractBaseService implements IService{
	private static final Log log = LogFactory.getLog(LoanAdvancesDefBaseService.class);
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getId() {
		return "LoanAdvancesDefBaseService";
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
			log.info("开始调用基础服务[LoanAdvancesDefBaseService]......");
		}
		
		getContext().setValue("__LOAN_KIND__", "3002101000103");  
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[LoanAdvancesDefBaseService]......");
		}
		return null;
		
	}
}
