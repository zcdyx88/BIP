package com.dcits.smartbip.runtime.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.runtime.model.impl.SoapCompositeData;
import com.dcits.smartbip.utils.CompositeDataUtils;

/*
 * 对私账户金签约，判断风险评估是否在有效期内或风险等级是否对应
 * */

public class ZhanghujinErrorMessage extends AbstractBaseService implements IService{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 3623286958383937200L;
	private static final Log log = LogFactory.getLog(ZhanghujinErrorMessage.class);
	
	 @Override
		public String getId() {
			return "ZhanghujinErrorMessage";
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
				log.info("开始调用基础服务[ZhanghujinErrorMessage]......");
			}
			
			ICompositeData Rsp2002201000101 = (ICompositeData) SessionContext
			.getContext().getValue("Rsp2002201000101");
			
			ICompositeData Rsp4001300000503  = (ICompositeData) SessionContext
			.getContext().getValue("Rsp4001300000503");
			
			if (null == Rsp2002201000101)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
				Rsp2002201000101  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
				Rsp2002201000101.setId("Rsp2002201000101");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
				Rsp2002201000101.setxPath("/Rsp2002201000101");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
				getContext().setValue("Rsp2002201000101",Rsp2002201000101);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			}
			
			String errormessage = "风险评估失败,账户不再有效期内或风险等级错误";
			
			log.info(errormessage);
		    
			String retCode = CompositeDataUtils.getValue(Rsp4001300000503, "RspSysHead/RetCode");
			
			ICompositeData destArrayParent = CompositeDataUtils.mkNodeNotExist(Rsp2002201000101, "RspAppBody");
			ICompositeData arrayiItem = new SoapCompositeData();
			arrayiItem.setId("PrivSignRsltArray");
			destArrayParent.setChild("PrivSignRsltArray", arrayiItem);
			if(null != retCode)
			{
				CompositeDataUtils.setValue(arrayiItem, "RetCode", retCode);
			}
			else
			{
				CompositeDataUtils.setValue(arrayiItem, "RetCode", "BIP9999");
			}
			
			CompositeDataUtils.setValue(arrayiItem, "TransStatus", "F");			
			CompositeDataUtils.setValue(arrayiItem, "RetMsg", errormessage);
			CompositeDataUtils.setValue(arrayiItem, "SignProTp", "000022");
			CompositeDataUtils.setValue(arrayiItem, "SignProNm", "账户金");
			
			if (log.isInfoEnabled()) {
				log.info("调用基础服务结束[ZhanghujinErrorMessage]......");
			}

			return null;
		}

}
