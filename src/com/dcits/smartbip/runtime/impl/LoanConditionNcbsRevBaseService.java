package com.dcits.smartbip.runtime.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.utils.CompositeDataUtils;

/***
 * 一般贷款开户
 * 
 * @author xhx102
 * 
 */
public class LoanConditionNcbsRevBaseService extends AbstractBaseService
		implements IService {
	private static final Log log = LogFactory
			.getLog(LoanConditionNcbsRevBaseService.class);
	private static final long serialVersionUID = 1L;

	@Override
	public String getId() {
		return "LoanConditionNcbsRevBaseService";
	}

	@Override
	public String getType() {
		return "base";
	}

	@Override
	public void setType(String type) {

	}

	/***
	 * 判断是否走二代
	 */
	@Override
	public Object execute(Object req) throws InvokeException {
		String Conditions = "0"; //默认不走
		if (log.isInfoEnabled()) {
			log.info("开始调用基础服务[LoanConditionNcbsRevBaseService]......");
		}
		 ICompositeData RespLoanService = (ICompositeData) getContext().getValue("Rsp3004100001820");
		 String RetCode = CompositeDataUtils.getValue(RespLoanService, "RspSysHead/RetCode");
		 if(!"000000".equalsIgnoreCase(RetCode)){
			 Conditions = "1";
		}
		getContext().setValue("CONDITION_NCBSREV", Conditions);

		if (log.isInfoEnabled()) {
			log.info("CONDITION_NCBSREV=[" + Conditions + "]");
			log.info("调用基础服务结束[LoanConditionNcbsRevBaseService]......");
		}
		return null;

	}
}
