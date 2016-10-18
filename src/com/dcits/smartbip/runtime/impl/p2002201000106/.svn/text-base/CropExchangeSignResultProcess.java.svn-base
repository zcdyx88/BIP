package com.dcits.smartbip.runtime.impl.p2002201000106;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.impl.SignObject;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.runtime.model.impl.SoapCompositeData;
import com.dcits.smartbip.utils.CompositeDataUtils;

public class CropExchangeSignResultProcess extends AbstractBaseService implements IService {
	private static final Log log = LogFactory.getLog(CropExchangeSignResultProcess.class);

	@Override
	public String getId() {
		return "CropExchangeSignResultProcess";
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
			log.info("开始调用基础服务[ThirdDepMngResultProcess]......");
		}

		ICompositeData Rsp2002201000106 = (ICompositeData) SessionContext.getContext().getValue("Rsp2002201000106"); 
		ICompositeData Req2002201000106 = (ICompositeData) SessionContext.getContext().getValue("Req2002201000106");
		
		if (null == Rsp2002201000106)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			Rsp2002201000106  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			Rsp2002201000106.setId("Rsp2002201000106");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			Rsp2002201000106.setxPath("/Rsp2002201000106");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			getContext().setValue("Rsp2002201000106",Rsp2002201000106);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		}
		
		/*
		 * 获取原子服务对象
		 */
		String signPro = (String)SessionContext.getContext().getValue("exchgSignProCode");
		//ECIF状态更新使用
		SessionContext.getContext().setValue("SignProTp", signPro);
		
		List serviceIds = (List)SessionContext.getContext().getValue("productList");
		String serviceId =  null;
		String signProNm= null;
		ICompositeData rspService= null;
		for(Object tmp :serviceIds)
		{
			SignObject signObject = (SignObject)tmp;
			if(signObject.getProductId().equalsIgnoreCase(signPro))
			{
				serviceId = signObject.getServiceId();//服务码
				signProNm = signObject.getProductName();//产品名称
				rspService = (ICompositeData) SessionContext.getContext().getValue("Rsp"+ serviceId);
				break;
			}
		}		 
		
		if (null == rspService)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                          
			log.error("返回结果为空!!");
			return null;
		}                             
		
		
		String retCode = CompositeDataUtils.getValue(rspService, "RspSysHead/RetCode");
		if(!"000000".equalsIgnoreCase(retCode))
		{			
			ICompositeData destArrayParent = CompositeDataUtils.mkNodeNotExist(Rsp2002201000106, "RspAppBody");
			ICompositeData arrayiItem = new SoapCompositeData();
			arrayiItem.setId("CorpExchgSignOffRsltArray");
			destArrayParent.setChild("CorpExchgSignOffRsltArray", arrayiItem);
			
			CompositeDataUtils.copy(rspService, arrayiItem, "RspSysHead/TransStatus", "TransStatus");
			CompositeDataUtils.copy(rspService, arrayiItem, "RspSysHead/RetCode", "RetCode");
			CompositeDataUtils.copy(rspService, arrayiItem, "RspSysHead/RetMsg", "RetMsg");
			CompositeDataUtils.setValue(arrayiItem, "ExchgSignOffProTp", signPro);
			CompositeDataUtils.setValue(arrayiItem, "ExchgSignOffProNm",signProNm );
			//三方存管设置唯一标识
			if(signPro.equalsIgnoreCase("000008"))
			{
				ICompositeData reqService = (ICompositeData) SessionContext.getContext().getValue("Req"+ serviceId);
				String sOrganId = CompositeDataUtils.getValue(reqService,"ReqAppBody/sOrganId");   
				String sCustAcct = CompositeDataUtils.getValue(reqService,"ReqAppBody/sCustAcct");  
				CompositeDataUtils.setValue(arrayiItem, "CONT_ACC",sOrganId+"_"+sCustAcct);
			}
		}
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[CropExchangeSignResultProcess]......");
		}		

		return null;
	}

}


