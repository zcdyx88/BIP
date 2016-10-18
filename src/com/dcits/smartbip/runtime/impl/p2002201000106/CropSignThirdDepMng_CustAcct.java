package com.dcits.smartbip.runtime.impl.p2002201000106;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;

public class CropSignThirdDepMng_CustAcct extends AbstractBaseService implements IService {
	private static final Log log = LogFactory.getLog(CropSignThirdDepMng_CustAcct.class);

	@Override
	public String getId() {
		return "CropSignThirdDepMng_CustAcct";
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
			log.info("开始调用基础服务[CropSignThirdDepMng_CustAcct]......");
		}
		
		String sOrganId_sCustAcct = (String)SessionContext.getContext().getValue("sOrganId_sCustAcct");
		String[] arr = sOrganId_sCustAcct.split("_");
		log.info("sOrganId="+arr[0]+",sCustAcct="+arr[1]);
		SessionContext.getContext().setValue("ThirdDepMng_sOrganId", arr[0]);
		SessionContext.getContext().setValue("ThirdDepMng_sCustAcct", arr[1]);
		
		//设置ECIF唯一标识
		SessionContext.getContext().setValue("ECIF_CONT_ACC", sOrganId_sCustAcct);
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[CropSignThirdDepMng_CustAcct]......");
		}
		
	
		

		return null;
	}

}


