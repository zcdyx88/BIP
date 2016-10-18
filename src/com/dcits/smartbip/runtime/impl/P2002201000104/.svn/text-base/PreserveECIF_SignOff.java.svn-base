package com.dcits.smartbip.runtime.impl.P2002201000104;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.utils.CompositeDataUtils;

public class PreserveECIF_SignOff extends AbstractBaseService implements IService{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5804391562790023625L;
	private static final Log log = LogFactory.getLog(PreserveECIF_SignOff.class);
	
	@Override
	public String getType() {
		return "base";
	}

	@Override
	public void setType(String type) {
		
	}
	
	@Override
	public String getId() {
		return "PreserveECIF_SignOff";
	}
	
	@Override
	public Object execute(Object req) throws InvokeException {
		
		if (log.isInfoEnabled()) {
			log.info("开始调用基础服务[PreserveECIF_SignOff]......");
		}
		
		
		// 获得综合签约请求数据对象
		ICompositeData Rsp2002200000402 = (ICompositeData) SessionContext
				.getContext().getValue("Rsp2002200000402");

		String RetCode = CompositeDataUtils.getValue(Rsp2002200000402,
		"RspSysHead/RetCode");
		
		if ("000000".equals(RetCode)) {
			SessionContext.getContext().setValue("ecifResult_SignOff", "true");
		} else {
			SessionContext.getContext().setValue("ecifResult_SignOff", "false");
		} 
		
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[PreserveECIF_SignOff]......");
		}
		return null;
	}


}
