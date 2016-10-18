package com.dcits.smartbip.runtime.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.utils.CompositeDataUtils;

public class CheckSignResult extends AbstractBaseService implements IService {

	private static final Log log = LogFactory.getLog(CheckSignResult.class);

	@Override
	public String getId() {
		return "CheckSignResult";
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
			log.info("开始调用基础服务[PriserveECIF]......");
		}
		
		
		// 获得综合签约请求数据对象
		ICompositeData Rsp2002200000401 = (ICompositeData) SessionContext
				.getContext().getValue("Rsp2002200000401");

		String RetCode = CompositeDataUtils.getValue(Rsp2002200000401,
		"RspSysHead/RetCode");
		
		String ecifResult = null ;
		
		if ("000000".equals(RetCode)) {
			getContext().setValue(ecifResult, "true");
		} else {
			getContext().setValue(ecifResult, "false");
		}
		
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[PriserveECIF]......");
		}
		return null;
	}

}
