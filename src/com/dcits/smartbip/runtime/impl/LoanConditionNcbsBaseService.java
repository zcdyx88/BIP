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
 * 判断是否调用核心。限额占用标志 0-不占用;1-占用但忽略应答;2-占用且允许通讯故障;3-占用且必须成功
 * 
 * @author xhx102
 * 
 */
public class LoanConditionNcbsBaseService extends AbstractBaseService
		implements IService {
	private static final Log log = LogFactory
			.getLog(LoanConditionNcbsBaseService.class);
	private static final long serialVersionUID = 1L;

	@Override
	public String getId() {
		return "LoanConditionNcbsBaseService";
	}

	@Override
	public String getType() {
		return "base";
	}

	@Override
	public void setType(String type) {

	}

	/***
	 * 判断是否到核心
	 */
	@Override
	public Object execute(Object req) throws InvokeException {
		String Conditions = "1"; //默认去核心
		if (log.isInfoEnabled()) {
			log.info("开始调用基础服务[LoanConditionNcbsBaseService]......");
		}
		String take_type_flag  = "0";
		String take_type_res = "0";  //限额调用结果判断标志
		take_type_flag = (String)SessionContext.getContext().getValue("TAKE_TYPE_FLAG");
		// 限额占用标志 0-不占用;1-占用但忽略应答;2-占用且允许通讯故障;3-占用且必须成功  
		take_type_res = (String)SessionContext.getContext().getValue("TAKE_TYPE_RES");
		if(log.isDebugEnabled()){
			log.debug("限额调用标志["  + take_type_flag + "],限额响应判断标志[" + take_type_res + "]");
		}
		if("1".equals(take_type_flag)){ //调用了限额
			if("0".equals(take_type_res)){
				if(log.isDebugEnabled()){
					log.debug("忽视限额占用结果,直接到核心");
				}
				//不到限额 直接到核心
				Conditions = "1";
			}else if("1".equals(take_type_res)){
				//不判断核心返回，直接到核心
				Conditions = "1";
			}else if("2".equals(take_type_res)){
				 ICompositeData Rsp4001100000401 = (ICompositeData) getContext().getValue("Rsp4001100000401");
				 String RetCode = CompositeDataUtils.getValue(Rsp4001100000401, "RspSysHead/RetCode");
				 if(log.isDebugEnabled()){
					 log.debug("限额系统返回码[" + RetCode + "]");
				 }
				 //调用限额系统接口的返回结果为成功,同时属于 超时成功
				if("000000".equalsIgnoreCase(RetCode) || "ESB-E-000052".equalsIgnoreCase(RetCode)){
					 if(log.isDebugEnabled()){
						 log.debug("限额返回成功或超时，也认为成功，交易继续");
					 }
					 Conditions = "1";
				}else{
					 //如果返回失败，不到核心 需要给前端返回
					 Conditions = "0";
				}
			}else if("3".equals(take_type_res)){ //3-占用且必须成功  
				 ICompositeData Rsp4001100000401 = (ICompositeData) getContext().getValue("Rsp4001100000401");
				 String RetCode = CompositeDataUtils.getValue(Rsp4001100000401, "RspSysHead/RetCode");
				 if(log.isDebugEnabled()){
					 log.debug("限额系统返回码[" + RetCode + "]");
				 }
				 //调用限额系统接口的返回结果为成功,同时属于 超时成功
				if("000000".equalsIgnoreCase(RetCode)){
					 if(log.isDebugEnabled()){
						 log.debug("限额返回成功，交易继续");
					 }
					 Conditions = "1";
				}else{
					 //如果返回失败，不到核心 需要给前端返回
					 if(log.isDebugEnabled()){
						 log.debug("返回失败，不到核心");
					 }
					 Conditions = "0";
				}
			}else{ //其他的限额标志类型
				Conditions = "0";
				if(log.isDebugEnabled()){
					log.debug("未定义的限额调用标志");
				}
			}
		}else{
			if(log.isInfoEnabled()){
				log.info("没有调用限额系统,直接到核心");
			}
		}
		
		getContext().setValue("CONDITION_NCBS", Conditions);

		if (log.isInfoEnabled()) {
			log.info("CONDITION_NCBS=[" + Conditions + "]");
			log.info("调用基础服务结束[LoanConditionNcbsBaseService]......");
		}
		return null;

	}
}
