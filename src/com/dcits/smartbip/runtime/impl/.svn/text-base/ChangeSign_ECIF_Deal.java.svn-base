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
 *  对私综合换签
 *  从上下文中得到换签产品代码，获取各个原子服务对象
 *  调用失败则返回错误信息
 *  调用成功则进行ECIF状态更新，然后在返回成功信息
 * @author srxhx611
 *
 */
public class ChangeSign_ECIF_Deal extends AbstractBaseService implements IService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4280327260212465637L;
	private static final Log log = LogFactory.getLog(ChangeSign_ECIF_Deal.class);
	ICompositeData rspService;
	String signPro;
	String signProTp;
	String signProNm;

	@Override
	public String getId() {
		return "ChangeSign_ECIF_Deal";
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
			log.info("开始调用基础服务[ChangeSign_ECIF_Deal]......");
		}
		//服务码
		String modifySignType = (String)SessionContext.getContext().getValue("ModifySignType");
		
		ICompositeData RspmodifySignType = (ICompositeData) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		.getContext().getValue("Rsp"+modifySignType); 
		
		if (null == RspmodifySignType)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			RspmodifySignType  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			RspmodifySignType.setId("Rsp"+modifySignType);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			RspmodifySignType.setxPath("/Rsp"+modifySignType);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			getContext().setValue("Rsp"+modifySignType,RspmodifySignType);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		} 
		/*
		 * 获取原子服务对象
		 */
		try{
			signPro = (String)SessionContext.getContext().getValue("exchgSignProCode");//返回码
			SessionContext.getContext().setValue("SignProTp", signPro);
			List serviceIds = (List)SessionContext.getContext().getValue("productList");
			for(Object tmp :serviceIds)
			{
				SignObject signObject = (SignObject)tmp;
				if(signObject.getProductId().equalsIgnoreCase(signPro))
				{
					signProTp = signObject.getServiceId();//服务码
					signProNm = signObject.getProductName();//产品名称
					rspService = (ICompositeData) SessionContext.getContext().getValue("Rsp"+ signProTp);
					break;
				}
			}		   
		}
		catch(Exception e){
			log.info("换签失败.........." +
					"换签产品代码SignProTp="+ signPro);
		}
		
		if (null == rspService)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                          
			log.error("返回结果为空!!");
			return null;
		}                             
		
		
		String retCode = CompositeDataUtils.getValue(rspService, "RspSysHead/RetCode");
			if("000000".equalsIgnoreCase(retCode))
			{
				String SignProTp = signPro;
				log.info(signProNm+"_"+SignProTp+"换签成功，返回码="+retCode);
				
			} else {
				log.info("产品代码="+signPro+"换签失败，返回码="+retCode+"服务码="+signProTp); 
				
				ICompositeData destArrayParent = CompositeDataUtils.mkNodeNotExist(RspmodifySignType, "RspAppBody");
				ICompositeData arrayiItem = new SoapCompositeData();
				arrayiItem.setId("PrivExchgSignOffRsltArray");
				destArrayParent.setChild("PrivExchgSignOffRsltArray", arrayiItem);
				
				CompositeDataUtils.copy(rspService, arrayiItem, "RspSysHead/TransStatus", "TransStatus");
				CompositeDataUtils.copy(rspService, arrayiItem, "RspSysHead/RetCode", "RetCode");
				CompositeDataUtils.copy(rspService, arrayiItem, "RspSysHead/RetMsg", "RetMsg");
				CompositeDataUtils.setValue(arrayiItem, "ExchgSignOffProTp", signPro);
				CompositeDataUtils.setValue(arrayiItem, "ExchgSignOffProNm",signProNm );
			}
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[ChangeSign_ECIF_Deal]......");
		}
		
		return null;
		
	}
}






