package com.dcits.smartbip.runtime.impl.p2002201000102;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.runtime.model.impl.SoapCompositeData;
import com.dcits.smartbip.utils.CompositeDataUtils;

public class CropSign_ThirdDepMng_ContAcc extends AbstractBaseService implements IService {

	private static final Log log = LogFactory.getLog(CropSign_ThirdDepMng_ContAcc.class);

	@Override
	public String getId() {
		return "CropSign_ThirdDepMng_ContAcc";
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
			log.info("开始调用基础服务[CropSign_ThirdDepMng_ContAcc]......");
		}
		
		
		// 获得综合签约请求数据对象
		ICompositeData Req3006200002802 = (ICompositeData) SessionContext
				.getContext().getValue("Req3006200002802");
		if(null == Req3006200002802)
		{
			
		}else
		{
			String sOrganId = CompositeDataUtils.getValue(Req3006200002802,
			"ReqAppBody/sOrganId");
			
			String sCustAcct = CompositeDataUtils.getValue(Req3006200002802,
			"ReqAppBody/sCustAcct");
			
			SessionContext.getContext().setValue("ECIF_CONT_ACC", sOrganId+"_"+sCustAcct);
		}	
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[CropSign_ThirdDepMng_ContAcc]......");
		}
		return null;
	}

}
