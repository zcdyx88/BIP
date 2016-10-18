package com.dcits.smartbip.runtime.impl;

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
 *  对私综合签约
 *  从上下文中得到签约产品代码，获取各个原子服务对象
 *  调用失败则返回错误信息
 *  调用成功则进行ECIF状态更新，然后在返回成功信息
 * @author srxhx611
 *
 */
public class ToPrivateSignInformation extends AbstractBaseService implements IService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1352319362610207312L;
	private static final Log log = LogFactory.getLog(ToPrivateSignInformation.class);
	private ICompositeData rspService;
	private String signPro;
	private String signProTp;
	private String signProNm;
	@Override
	public String getId() {
		return "ToPrivateSignInformation";
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
			log.info("开始调用基础服务[ToPrivateSignInformation]......");
		}
		
		ICompositeData Rsp2002201000101 = (ICompositeData) SessionContext.getContext().getValue("Rsp2002201000101"); 
		if (null == Rsp2002201000101)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			Rsp2002201000101  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			Rsp2002201000101.setId("Rsp2002201000101");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			Rsp2002201000101.setxPath("/Rsp2002201000101");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			getContext().setValue("Rsp2002201000101",Rsp2002201000101);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		} 
		/*
		 * 获取原子服务对象
		 */
		String signedRetCode = null;
		try{
			signPro = (String)SessionContext.getContext().getValue("signCodeType");//签约产品代码
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
					signedRetCode = signObject.getSigningRetCode();
					break;
				}
			}		
		}
		catch(Exception e){
			log.info(signProNm+"签约失败......" +"签约产品代码SignProTp="+signProTp);
		}
		if (null == rspService)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                          
			log.error("返回结果为空!!");
			return null;
		}                             
		
		String retCode = CompositeDataUtils.getValue(rspService, "RspSysHead/RetCode");
		if("000000".equals(retCode)|| (null != signedRetCode && signedRetCode.equalsIgnoreCase(retCode)))
		{
			String SignProTp = (String)SessionContext.getContext().getValue("SignProTp");
			log.info(SignProTp+"签约成功或已签约，返回码="+retCode);
		} 
		else
		{
			String SignProTp = (String)SessionContext.getContext().getValue("SignProTp");
			log.info(SignProTp+"签约失败，返回码="+retCode); 
			ICompositeData destArrayParent = CompositeDataUtils.mkNodeNotExist(Rsp2002201000101, "RspAppBody");
			ICompositeData arrayiItem = new SoapCompositeData();
			arrayiItem.setId("PrivSignRsltArray");
			destArrayParent.setChild("PrivSignRsltArray", arrayiItem);
			if(null != retCode){
				CompositeDataUtils.setValue(arrayiItem, "RetCode", retCode);
			}
			else{
				CompositeDataUtils.setValue(arrayiItem, "RetCode", "BIP9999");
			}
			CompositeDataUtils.setValue(arrayiItem, "TransStatus", "F");			
			CompositeDataUtils.copy(rspService, arrayiItem, "RspSysHead/RetMsg", "RetMsg");
			CompositeDataUtils.setValue(arrayiItem, "SignProTp", signPro);
			CompositeDataUtils.setValue(arrayiItem, "SignProNm", signProNm);
		}	
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[ToPrivateSignInformation]......");
		}
		
		return null;
		
	}


}


