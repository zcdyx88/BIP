package com.dcits.smartbip.runtime.impl.P2002201000104;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.runtime.model.impl.SoapCompositeData;
import com.dcits.smartbip.utils.CompositeDataUtils;

public class ToCompanySignOffInformation_licai extends AbstractBaseService implements IService{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4845777409429636923L;
	private static final Log log = LogFactory.getLog(ToCompanySignOffInformation_licai.class);
	ICompositeData rspService;
	String signPro;
	String signProTp;
	String signProNm;

	@Override
	public String getId() {
		return "ToCompanySignOffInformation_licai";
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
			log.info("开始调用基础服务[ToCompanySignOffInformation_licai]......");
		}
		
		ICompositeData Rsp2002201000104 = (ICompositeData) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		.getContext().getValue("Rsp2002201000104"); 
		
		if (null == Rsp2002201000104)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			Rsp2002201000104  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			Rsp2002201000104.setId("Rsp2002201000104");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			Rsp2002201000104.setxPath("/Rsp2002201000104");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			getContext().setValue("Rsp2002201000104",Rsp2002201000104);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		} 
		ICompositeData rspService = null;
		
		if("true".equalsIgnoreCase((String) SessionContext.getContext().getValue("LicaiduigjieyLastSigningAcct"))){
			rspService = (ICompositeData) SessionContext.getContext().getValue("Rsp3005200000105"); 
		}else if ("false".equalsIgnoreCase((String) SessionContext.getContext().getValue("LicaiduigjieyLastSigningAcct"))){
			rspService = (ICompositeData) SessionContext.getContext().getValue("Rsp3005200000103");
		}
		
		
		String retCode = CompositeDataUtils.getValue(rspService, "RspSysHead/RetCode");
		if(null != retCode && "000000".equalsIgnoreCase(retCode))
		{
			log.info("000021"+"_"+"理财"+"解约成功，返回码="+retCode);
		} else 
		{
			log.info("产品代码="+"000021"+"_"+"理财"+"解约失败，返回码="+retCode+"服务码="+rspService.toString().substring(4)); 
			
			ICompositeData destArrayParent = CompositeDataUtils.mkNodeNotExist(Rsp2002201000104, "RspAppBody");
			ICompositeData arrayiItem = new SoapCompositeData();
			arrayiItem.setId("CorpSignOffRsltArray");
			destArrayParent.setChild("CorpSignOffRsltArray", arrayiItem);
			
			CompositeDataUtils.setValue(arrayiItem, "TransStatus", "F");
			//CompositeDataUtils.copy(rspService, arrayiItem, "RspSysHead/RetCode", "RetCode");
			if(null != retCode)
			{
				CompositeDataUtils.setValue(arrayiItem, "RetCode", retCode);
			}
			else
			{
				CompositeDataUtils.setValue(arrayiItem, "RetCode", "BIP9999");
			}
			CompositeDataUtils.copy(rspService, arrayiItem, "RspSysHead/RetMsg", "RetMsg");
			CompositeDataUtils.setValue(arrayiItem, "SignOffProTp", "000021");
			CompositeDataUtils.setValue(arrayiItem, "SignOffProNm", "理财");
		}
		
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[ToCompanySignOffInformation_licai]......");
		}
		
		return null;
		
	}
}
