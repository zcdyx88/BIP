package com.dcits.smartbip.runtime.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.runtime.model.impl.SoapCompositeData;
import com.dcits.smartbip.utils.CompositeDataUtils;

/**
 *  对公综合解约
 *  从上下文中得到解约产品代码，获取各个原子服务对象
 *  调用失败则返回错误信息
 *  调用成功则进行ECIF状态更新，然后在返回成功信息
 * @author srxhx611
 *
 */
public class ToCompanySignOffInformation extends AbstractBaseService implements IService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3263321476086050974L;
	private static final Log log = LogFactory.getLog(ToCompanySignOffInformation.class);
	ICompositeData rspService;
	String signPro;
	String signProTp;
	String signProNm;

	@Override
	public String getId() {
		return "ToCompanySignOffInformation";
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
			log.info("开始调用基础服务[ToCompanySignOffInformation]......");
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
		/*
		 * 获取原子服务对象
		 */
		List<String> signedRetCode = null;
		try{
			signPro = (String)SessionContext.getContext().getValue("signCodeType");//返回码
			SessionContext.getContext().setValue("SignProTp", signPro);
			List<?> serviceIds = (List<?>)SessionContext.getContext().getValue("productList");
			for(Object tmp :serviceIds)
			{
				SignObject signObject = (SignObject)tmp;
				if(signObject.getProductId().equalsIgnoreCase(signPro))
				{
					signProTp = signObject.getServiceId();//服务码
					signProNm = signObject.getProductName();//产品名称
					rspService = (ICompositeData) SessionContext.getContext().getValue("Rsp"+ signProTp);
					signedRetCode = Arrays.asList(signObject.getSigningRetCode().split(","));
					break;
				}
			}		  
		}
		catch(Exception e){
			log.info("解约失败.........." +
					"解约产品代码SignProTp="+ signPro);
		}
		
		if (null == rspService)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                          
			log.error("返回结果为空!!");
			return null;
		}                             
		
		String retCode = CompositeDataUtils.getValue(rspService, "RspSysHead/RetCode");
		if("000000".equalsIgnoreCase(retCode) || (signedRetCode !=null && signedRetCode.contains(retCode)))
		{
			log.info(signPro+"_"+signProNm+"解约成功，返回码="+retCode);
		} else 
		{
			log.info("产品代码="+signPro+signProNm+"解约失败，返回码="+retCode+"服务码="+signProTp); 
			
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
			CompositeDataUtils.setValue(arrayiItem, "SignOffProTp", signPro);
			CompositeDataUtils.setValue(arrayiItem, "SignOffProNm",signProNm );
		}
		
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[ToCompanySignOffInformation]......");
		}
		
		return null;
		
	}
}






