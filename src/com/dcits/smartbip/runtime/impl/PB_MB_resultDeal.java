package com.dcits.smartbip.runtime.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.utils.CompositeDataUtils;

/**
 * 个人网银/手机银行渠道维护[组合级]
 * ECIF更新结果处理
 * @author srxhx611
 *
 */
public class PB_MB_resultDeal extends AbstractBaseService implements IService{
	private final static Log log = LogFactory.getLog(PB_MB_resultDeal.class);
	
	@Override
	public String getId() {
		return "PB_MB_resultDeal";
	}


	@Override
	public String getType() {
		return "baseService";
	}

	@Override
	public void setType(String type) {
		
	}


	@Override
	public Object execute(Object Rsp) throws InvokeException {
		if(log.isInfoEnabled()){
			log.info("开始调用基础服务PB_MB_resultDeal.....START");
		}
		
		//获取请求原子服务对象
		ICompositeData Rsp1003200000114 = (ICompositeData) SessionContext
		.getContext().getValue("Rsp1003200000114");
		if (null == Rsp1003200000114){                          
			log.error("后端返回结果为空!!");
		}
		//获取错误码
		String retCode = CompositeDataUtils.getValue(Rsp1003200000114, "RspSysHead/RetCode");
		if("000000".equals(retCode)){
			log.info("原子服务1003200000114调用成功！");
			SessionContext.getContext().setValue("Result", "true");
			
			//****************设置ECIF更新所需数据
			
			//获取渠道标识 PB MB
			ICompositeData Req1003201000101 = (ICompositeData)SessionContext
			.getContext().getValue("Req1003201000101");
			String state = CompositeDataUtils.getValue(Req1003201000101,"ReqAppBody/state");
			String channel = CompositeDataUtils.getValue(Req1003201000101,"ReqAppBody/channel");
			
			//（state）签约操作码：111：网上手机银行开通	1：个人网银开通   4：关闭
			if("111".equals(state)){
				SessionContext.getContext().setValue("OPER_CODE", "1");
				SessionContext.getContext().setValue("SIGN_TYPE", "000001");
				SessionContext.getContext().setValue("SIGN_STAT", "1");
			}else if("1".equals(state)){
				SessionContext.getContext().setValue("OPER_CODE", "1");
				SessionContext.getContext().setValue("SIGN_TYPE", "000002");
				SessionContext.getContext().setValue("SIGN_STAT", "1");
			}else if("4".equals(state)){
				SessionContext.getContext().setValue("OPER_CODE", "2");
				String SIGN_TYPE = returnSignPro(channel);
				SessionContext.getContext().setValue("SIGN_TYPE",SIGN_TYPE);
				SessionContext.getContext().setValue("SIGN_STAT", "2");
			}else {
				SessionContext.getContext().setValue("Result", "false");
				log.info("渠道状态不合法 state="+state);
			}
			
			
		}else{
			log.info("原子服务1003200000114调用失败！");
		}
		
		if(log.isInfoEnabled()){
			log.info("结束调用基础服务PB_MB_resultDeal.....END");
		}
		return false;
	}

	
	private String returnSignPro(String channel){
		if("PB".equalsIgnoreCase(channel)){
			return "000001";
		}else if("MB".equalsIgnoreCase(channel)){
			return "000002";
		}else{
			log.info("渠道标识不合法 ，channel="+channel);
		}
		return "false";
	}
}
