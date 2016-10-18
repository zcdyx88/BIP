package com.dcits.smartbip.runtime.impl.P2002201000105;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;

public class TrdCun_ExchangeSign extends AbstractBaseService implements IService {
	private static final Log log = LogFactory.getLog(TrdCun_ExchangeSign.class);

	@Override
	public String getId() {
		return "TrdCun_ExchangeSign";
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
			log.info("开始调用基础服务[TrdCun_ExchangeSign]......");
		}
		
		String SOrganId_SCustAcct = (String)SessionContext.getContext().getValue("SOrganId_SCustAcct");
		String[] arr = SOrganId_SCustAcct.split("_");
		log.info("sOrganId="+arr[0]+",sCustAcct="+arr[1]);
		SessionContext.getContext().setValue("ThirdDepMng_sOrganId", arr[0]);
		SessionContext.getContext().setValue("ThirdDepMng_sCustAcct", arr[1]);
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[TrdCun_ExchangeSign]......");
		}

		return null;
	}

}


