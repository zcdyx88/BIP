package com.dcits.smartbip.runtime.impl.P2002201000101;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.utils.CompositeDataUtils;

public class CustomerSign_ThirdDepMng_ContAcc extends AbstractBaseService implements IService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7054330997884679230L;
	private static final Log log = LogFactory.getLog(CustomerSign_ThirdDepMng_ContAcc.class);

	@Override
	public String getId() {
		return "CustomerSign_ThirdDepMng_ContAcc";
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
			log.info("开始调用基础服务[CustomerSign_ThirdDepMng_ContAcc]......");
		}
		
		
		// 获得综合签约请求数据对象
		ICompositeData Req3006200002801 = (ICompositeData) SessionContext
				.getContext().getValue("Req3006200002801");
		if(null == Req3006200002801)
		{
			
		}else
		{
			String sOrganId = CompositeDataUtils.getValue(Req3006200002801,
			"ReqAppBody/sOrganId");
			
			String sCustAcct = CompositeDataUtils.getValue(Req3006200002801,
			"ReqAppBody/sCustAcct");
			
			SessionContext.getContext().setValue("ECIF_CONT_ACC", sOrganId+"_"+sCustAcct);
		}	
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[CustomerSign_ThirdDepMng_ContAcc]......");
		}
		return null;
	}

}
