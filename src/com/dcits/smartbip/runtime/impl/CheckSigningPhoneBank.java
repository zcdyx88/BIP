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
 * 对私签约，个人电话银行
 * 判断该客户是否签约电话银行
 * */

public class CheckSigningPhoneBank extends AbstractBaseService implements IService{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2080227117751795945L;
	private static final Log log = LogFactory.getLog(CheckSigningPhoneBank.class);
	
	@Override
	public String getId() {
		return "CheckSigningPhoneBank";
	}

	@Override
	public String getType() {
		return "base";
	}
	
	@Override
	public void setType(String type) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public Object execute(Object req) throws InvokeException {

		if (log.isInfoEnabled()) {
			log.info("开始调用基础服务[CheckSigningPhoneBank]......");
		}
		
		ICompositeData Rsp2002300000401 = (ICompositeData) SessionContext
		.getContext().getValue("Rsp2002300000401");
		
		SessionContext.getContext().setValue("CustomerNotSigningPhoneBank", "true");
		
		List<ICompositeData> GRP_SYNTHESIS_INFO = CompositeDataUtils.getByPath(Rsp2002300000401, "RspAppBody/GRP_SYNTHESIS_INFO");
		if(GRP_SYNTHESIS_INFO!=null && GRP_SYNTHESIS_INFO.size()>0){
			for(ICompositeData GRP_SYNTHESIS_INFOS:GRP_SYNTHESIS_INFO){
					String SIGN_TYPE = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFOS, "SIGN_TYPE");
					String SIGN_STAT = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFOS, "SIGN_STAT");
					if(SIGN_TYPE.equalsIgnoreCase("000004") && SIGN_STAT.equalsIgnoreCase("1")){
						SessionContext.getContext().setValue("CustomerNotSigningPhoneBank", "false");
					    log.info("该客户已签约个人电话银行！");
					    break;
					}
			}
		}
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[CheckSigningPhoneBank]......");
		}

		return null;
	}
	
}
