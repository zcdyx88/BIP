package com.dcits.smartbip.runtime.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.utils.CompositeDataUtils;

/***
 * 一般贷款开户
 * 
 * @author xhx102
 * 
 */
public class LoanConditionCqmsTradeCorrectBaseService extends AbstractBaseService
		implements IService {
	private static final Log log = LogFactory
			.getLog(LoanConditionCqmsTradeCorrectBaseService.class);
	private static final long serialVersionUID = 1L;

	@Override
	public String getId() {
		return "LoanConditionCqmsTradeCorrectBaseService";
	}

	@Override
	public String getType() {
		return "base";
	}

	@Override
	public void setType(String type) {

	}

	/***
	 * 判断是否走限额取消
	 */
	@Override
	public Object execute(Object req) throws InvokeException {
		String Conditions = "0"; //默认不走
		if (log.isInfoEnabled()) {
			log.info("开始调用基础服务[LoanConditionCqmsTradeCorrectBaseService]......");
		}
		//判断核心是否执行成功，成功再去限额取消占用
		 ICompositeData RespLoanService = (ICompositeData) getContext().getValue("Rsp5001500000201");
		 //核心返回
		 String RetCode = CompositeDataUtils.getValue(RespLoanService, "RspSysHead/RetCode");
		 if("000000".equalsIgnoreCase(RetCode)){
			 Conditions = "1";
		}
		getContext().setValue("CONDITION_CQMSTRADECORRECT", Conditions);

		if (log.isInfoEnabled()) {
			log.info("CONDITION_CQMSTRADECORRECT=["  + Conditions + "]");
			log.info("调用基础服务结束[LoanConditionCqmsTradeCorrectBaseService]......");
		}
		return null;

	}
}
