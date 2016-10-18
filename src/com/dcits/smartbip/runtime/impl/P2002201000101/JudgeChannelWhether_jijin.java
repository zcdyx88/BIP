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
 * 对私基金签约时，判断发起方是否为基金
 * */

public class JudgeChannelWhether_jijin extends AbstractBaseService implements IService{

	 /**
	 * 
	 */
	private static final long serialVersionUID = -6159956025989681317L;
	private static final Log log = LogFactory.getLog(JudgeChannelWhether_jijin.class);
		
		@Override
		public String getId() {
			return "JudgeChannelWhether_jijin";
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
				log.info("开始调用基础服务[JudgeChannelWhether_jijin]......");
			}

			ICompositeData Req2002201000101 = (ICompositeData) SessionContext.getContext().getValue("Req2002201000101");
			String OrgSysID = CompositeDataUtils.getValue(Req2002201000101, "ReqSysHead/ReqSysID");
			String SignProTp = (String) SessionContext.getContext().getValue("signCodeType");
			if(OrgSysID != null){				
				if(OrgSysID.equalsIgnoreCase("B49")){
					SessionContext.getContext().setValue("OrgSysID_jijin", "true");
					SessionContext.getContext().setValue("SIGN_FLAG", "2");
					SessionContext.getContext().setValue("OPER_CODE", "1");
					SessionContext.getContext().setValue("SignProTp", SignProTp);
					SessionContext.getContext().setValue("SIGN_STAT", "1");
					log.info("发起方渠道为基金分销系统！");
				}
				else
				{
					SessionContext.getContext().setValue("OrgSysID_jijin", "false");
					log.info("发起方渠道不为基金分销系统！");
				}
			}
			
			if (log.isInfoEnabled()) {
				log.info("调用基础服务结束[JudgeChannelWhether_jijin]......");
			}
			
			return null;
		}
	
}
