package com.dcits.smartbip.runtime.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;


/***
 * 一般贷款开户
 * @author xhx102
 *
 */
public class LoanTradeCorrectDefBaseService  extends AbstractBaseService implements IService{
	private static final Log log = LogFactory.getLog(LoanTradeCorrectDefBaseService.class);
	private static final long serialVersionUID = 1L;

	@Override
	public String getId() {
		return "LoanTradeCorrectDefBaseService";
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
			log.info("开始调用基础服务[LoanTradeCorrectDefBaseService]......");
		}
		
		getContext().setValue("__LOAN_KIND__", "3002501000102");  
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[LoanTradeCorrectDefBaseService]......");
		}
		return null;
		
	}
}
