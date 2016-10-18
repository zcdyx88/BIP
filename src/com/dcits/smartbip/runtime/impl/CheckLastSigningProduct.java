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
 * 对私签约中短信银行
 * 判断是否最后一个签约短信账户
 * */

public class CheckLastSigningProduct extends AbstractBaseService implements IService{

	private static final Log log = LogFactory.getLog(CheckLastSigningProduct.class);
	
	@Override
	public String getId() {
		return "CheckLastSigningProduct";
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
			log.info("开始调用基础服务[CheckLastSigningProduct]......");
		}
		
		ICompositeData Rsp2002300000401 = (ICompositeData) SessionContext.getContext().getValue("Rsp2002300000401");
		SessionContext.getContext().setValue("OnlySigningProduct", "true");
		
		List<ICompositeData> GRP_SYNTHESIS_INFO = CompositeDataUtils.getByPath(Rsp2002300000401, "RspAppBody/GRP_SYNTHESIS_INFO");
		if(GRP_SYNTHESIS_INFO!=null && GRP_SYNTHESIS_INFO.size()>0){
			for(ICompositeData GRP_SYNTHESIS_INFOS:GRP_SYNTHESIS_INFO){
				String SIGN_TYPE = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFOS, "SIGN_TYPE");
				if("000003".equalsIgnoreCase(SIGN_TYPE)){	
					SessionContext.getContext().setValue("OnlySigningProduct", "false");
				    break;
				}
			}
		}
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[CheckLastSigningProduct]......");
		}

		return null;
	}
	
}
