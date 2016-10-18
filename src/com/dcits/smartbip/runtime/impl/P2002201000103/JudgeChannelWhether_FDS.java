package com.dcits.smartbip.runtime.impl.P2002201000103;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.utils.CompositeDataUtils;

/**
 * 对私基金解约时，判断发起方是否为基金
 * */

public class JudgeChannelWhether_FDS extends AbstractBaseService implements IService{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4327113209612266195L;
	private static final Log log = LogFactory.getLog(JudgeChannelWhether_FDS.class);
		
		@Override
		public String getId() {
			return "JudgeChannelWhether_FDS";
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
				log.info("开始调用基础服务[JudgeChannelWhether_FDS]......");
			}

			ICompositeData Req2002201000103 = (ICompositeData) SessionContext.getContext().getValue("Req2002201000103");
			String OrgSysID = CompositeDataUtils.getValue(Req2002201000103, "ReqSysHead/ReqSysID");
			String SignProTp = (String) SessionContext.getContext().getValue("signCodeType");
			String OrgSysID_FDS = "0";//是否基金分销系统：0-否，1-是
			if("B49".equalsIgnoreCase(OrgSysID)){
				OrgSysID_FDS = "1";
				SessionContext.getContext().setValue(("RetCode"+SignProTp), "000000");
				SessionContext.getContext().setValue("SIGN_FLAG", "2");
				SessionContext.getContext().setValue("OPER_CODE", "2");
				SessionContext.getContext().setValue("SignOffProTp", SignProTp);
				SessionContext.getContext().setValue("SIGN_STAT", "2");
				log.info("发起方渠道为基金分销系统，直接去ECIF维护更新");
			}else{
				log.info("发起方渠道不为基金分销系统！");
			}
			SessionContext.getContext().setValue("OrgSysID_FDS", OrgSysID_FDS);
			
			if (log.isInfoEnabled()) {
				log.info("调用基础服务结束[JudgeChannelWhether_FDS]......");
			}
			
			return null;
		}
	
}
