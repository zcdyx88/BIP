package com.dcits.smartbip.runtime.impl;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.utils.CompositeDataUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PreserveECIF extends AbstractBaseService implements IService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2652268096833479307L;
	private static final Log log = LogFactory.getLog(PreserveECIF.class);
	
	@Override
	public String getType() {
		return "base";
	}

	@Override
	public void setType(String type) {
		
	}
	
	@Override
	public String getId() {
		return "PreserveECIF";
	}
	
	@Override
	public Object execute(Object req) throws InvokeException {
		
		if (log.isInfoEnabled()) {
			log.info("开始调用基础服务[PreserveECIF]......");
		}
		// 获得综合签约请求数据对象
		ICompositeData Rsp2002200000401 = (ICompositeData) SessionContext
				.getContext().getValue("Rsp2002200000401");

		String RetCode = CompositeDataUtils.getValue(Rsp2002200000401,
		"RspSysHead/RetCode");
		if ("000000".equals(RetCode)) {
			SessionContext.getContext().setValue("ecifResult", "true");
			if(log.isInfoEnabled()){
				log.info("ECIF维护成功！");
			}
		} else {
			SessionContext.getContext().setValue("ecifResult", "false");
			if(log.isInfoEnabled()){
				log.info("ECIF维护失败！");
			}
		} 
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[PreserveECIF]......");
		}
		return null;
	}

}
