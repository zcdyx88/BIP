package com.dcits.smartbip.runtime.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.utils.CompositeDataUtils;

public class CheckCustomerAlreadySigningJijin extends AbstractBaseService implements IService{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6602082517240196007L;
	private static final Log log = LogFactory.getLog(CheckCustomerAlreadySigningJijin.class);

	@Override
	public String getId() {
		return "CheckCustomerAlreadySigningJijin";
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
			log.info("开始调用基础服务[CheckCustomerAlreadySigningJijin]......");
		}
		
		ICompositeData Rsp2002300000401 = (ICompositeData) SessionContext.getContext().getValue("Rsp2002300000401");
		SessionContext.getContext().setValue("CustomerAlreadySigningJijin", "false");
		
		List<ICompositeData> GRP_SYNTHESIS_INFO = CompositeDataUtils.getByPath(Rsp2002300000401, "RspAppBody/GRP_SYNTHESIS_INFO");
		
		if(GRP_SYNTHESIS_INFO!=null && GRP_SYNTHESIS_INFO.size()>0){
			for(ICompositeData GRP_SYNTHESIS_INFOS:GRP_SYNTHESIS_INFO){
				String SIGN_TYPE = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFOS, "SIGN_TYPE");
				if("000020".equalsIgnoreCase(SIGN_TYPE)){	
					SessionContext.getContext().setValue("CustomerAlreadySigningJijin", "true");
					log.info("此客户已有账户签约基金！");
				    break;
				}
			}
		}
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[CheckCustomerAlreadySigningJijin]......");
		}
		return null;
	}
	
}
