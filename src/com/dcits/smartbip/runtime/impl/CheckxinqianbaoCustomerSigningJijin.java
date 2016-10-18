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
 * 对私签约时，鑫钱宝客户是否签约基金
 * */

public class CheckxinqianbaoCustomerSigningJijin extends AbstractBaseService implements IService{


	/**
	 * 
	 */
	private static final long serialVersionUID = 4734875856635586034L;
	private static final Log log = LogFactory.getLog(CheckxinqianbaoCustomerSigningJijin.class);

	@Override
	public String getId() {
		return "CheckxinqianbaoCustomerSigningJijin";
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
			log.info("开始调用基础服务[CheckxinqianbaoCustomerSigningJijin]......");
		}
		
		SessionContext.getContext().setValue("XinqianbaoCustomerSigningJijin", "false");
		
		ICompositeData Req2002201000101 = (ICompositeData) SessionContext
		.getContext().getValue("Req2002201000101");
		ICompositeData Rsp2002300000401 = (ICompositeData) SessionContext
		.getContext().getValue("Rsp2002300000401");
		
		List<ICompositeData> GRP_SYNTHESIS_INFO = CompositeDataUtils.getByPath(Rsp2002300000401, "RspAppBody/GRP_SYNTHESIS_INFO");
		String AcctNo = CompositeDataUtils.getValue(Req2002201000101, "ReqAppBody/AcctNo");  //请求账户
		SessionContext.getContext().setValue("CustomerSigningJijin_duisi", "false");  
		if(GRP_SYNTHESIS_INFO!=null && GRP_SYNTHESIS_INFO.size()>0){
			for(ICompositeData GRP_SYNTHESIS_INFOS:GRP_SYNTHESIS_INFO){
				String SIGN_TYPE = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFOS, "SIGN_TYPE");
				String SIGN_STAT = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFOS, "SIGN_STAT");
				String SIGN_ACC  = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFOS, "SIGN_ACC");
		        if(SIGN_TYPE.equalsIgnoreCase("000020") && SIGN_STAT.equalsIgnoreCase("1") && AcctNo.equalsIgnoreCase(SIGN_ACC)){
		        	SessionContext.getContext().setValue("XinqianbaoCustomerSigningJijin", "true");
		        	log.info("该账户已签约基金！");
		            break;
		        }
			}
			for(ICompositeData GRP_SYNTHESIS_INFOS:GRP_SYNTHESIS_INFO){
				String SIGN_TYPE = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFOS, "SIGN_TYPE");
				String SIGN_STAT = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFOS, "SIGN_STAT");
		        if(SIGN_TYPE.equalsIgnoreCase("000020") && SIGN_STAT.equalsIgnoreCase("1")){
		        	SessionContext.getContext().setValue("CustomerSigningJijin_duisi", "true");
		        	log.info("该客户已签约过基金！");
		            break;
		        }
			}
		}
		//在此交易中，签鑫钱宝之前签过基金时，让只走鑫钱宝维护
		SessionContext.getContext().setValue("ecifResult", "true");
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[CheckxinqianbaoCustomerSigningJijin]......");
		}
		return null;
	}
	
}
