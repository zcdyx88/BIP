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

/*
 * 对私签约理财时，判断客户是否已有账户签约理财
 * */

public class CheckCustomerAlreadySigningLicai extends AbstractBaseService implements IService{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7451949408919249326L;
	private static final Log log = LogFactory.getLog(CheckCustomerAlreadySigningLicai.class);

	@Override
	public String getId() {
		return "CheckCustomerAlreadySigningLicai";
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
			log.info("开始调用基础服务[CheckCustomerAlreadySigningLicai]......");
		}
		
		ICompositeData Rsp2002300000401 = (ICompositeData) SessionContext.getContext().getValue("Rsp2002300000401");
		SessionContext.getContext().setValue("CustomerAlreadySigningLicai", "false");
		
		List<ICompositeData> GRP_SYNTHESIS_INFO = CompositeDataUtils.getByPath(Rsp2002300000401, "RspAppBody/GRP_SYNTHESIS_INFO");
		if(GRP_SYNTHESIS_INFO!=null && GRP_SYNTHESIS_INFO.size()>0){
			for(ICompositeData GRP_SYNTHESIS_INFOS:GRP_SYNTHESIS_INFO){
				String SIGN_TYPE = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFOS, "SIGN_TYPE");
				if("000021".equalsIgnoreCase(SIGN_TYPE)){	
					SessionContext.getContext().setValue("CustomerAlreadySigningLicai", "true");
					log.info("此客户已有账户签约理财！");
				    break;
				}
			}
		}
		
		
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[CheckCustomerAlreadySigningLicai]......");
		}
		return null;
	}

}
