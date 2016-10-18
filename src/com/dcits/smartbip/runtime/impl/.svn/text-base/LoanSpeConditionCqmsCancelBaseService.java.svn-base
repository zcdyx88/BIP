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
public class LoanSpeConditionCqmsCancelBaseService extends AbstractBaseService
		implements IService {
	private static final Log log = LogFactory
			.getLog(LoanSpeConditionCqmsCancelBaseService.class);
	private static final long serialVersionUID = 1L;

	@Override
	public String getId() {
		return "LoanSpeConditionCqmsCancelBaseService";
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
			log.info("开始调用基础服务[LoanSpeConditionCqmsCancelBaseService]......");
		}
		//判断核心执行失败  并且过了限额系统，不过限额，就不需要去限额取消
		 ICompositeData RespLoanService = (ICompositeData) getContext().getValue("Rsp3002100000104");
		 //核心返回
		 String RetCode = CompositeDataUtils.getValue(RespLoanService, "RspSysHead/RetCode");
		 //没有去限额系统
		 String take_type_flag  = "";
		 take_type_flag = (String)SessionContext.getContext().getValue("TAKE_TYPE_FLAG");
		 //判断核心执行失败  并且过了限额系统
		 if(!"000000".equalsIgnoreCase(RetCode) && "1".equals(take_type_flag)){
			 Conditions = "1";
		}
		getContext().setValue("CONDITION_CANNEL", Conditions);

		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[LoanSpeConditionCqmsCancelBaseService]......");
		}
		return null;

	}
}
