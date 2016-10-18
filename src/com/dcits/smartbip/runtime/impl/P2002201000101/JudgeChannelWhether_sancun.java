package com.dcits.smartbip.runtime.impl.P2002201000101;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.utils.CompositeDataUtils;

/*
 * 对私三方存管签约时，判断发起方是否为三存
 * */

public class JudgeChannelWhether_sancun extends AbstractBaseService implements IService{

    /**
	 * 
	 */
	private static final long serialVersionUID = 117214049230403617L;
	private static final Log log = LogFactory.getLog(JudgeChannelWhether_sancun.class);
	
	@Override
	public String getId() {
		return "JudgeChannelWhether_sancun";
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
			log.info("开始调用基础服务[JudgeChannelWhether_sancun]......");
		}

		ICompositeData Req2002201000101 = (ICompositeData) SessionContext
		.getContext().getValue("Req2002201000101");
		String OrgSysID = CompositeDataUtils.getValue(Req2002201000101, "ReqSysHead/ReqSysID");
		if(OrgSysID != null){			
			if(OrgSysID.equalsIgnoreCase("B50")){
				SessionContext.getContext().setValue("OrgSysID_sancun", "true");
				SessionContext.getContext().setValue("SIGN_FLAG", "2");
				SessionContext.getContext().setValue("OPER_CODE", "1");
				SessionContext.getContext().setValue("SignProTp", "000008");
				SessionContext.getContext().setValue("SIGN_STAT", "1");
			}   
			else
				SessionContext.getContext().setValue("OrgSysID_sancun", "false");
		}	
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[JudgeChannelWhether_sancun]......");
		}
		
		return null;
	}
}
