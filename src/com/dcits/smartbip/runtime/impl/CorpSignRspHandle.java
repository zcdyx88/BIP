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
import com.dcits.smartbip.utils.PropertiesUtils;

/**
 *  对公综合签约响应处理
 *  如果调用此基础服务，需要将在上一个原子交易的服务码配置到上下文的变量CurYZSrvId中
 * @author srxhx273
 *
 */
public class CorpSignRspHandle extends AbstractBaseService implements IService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2683682814393965622L;
	private static final Log log = LogFactory.getLog(CorpSignRspHandle.class);
	ICompositeData rspService;
	String signPro;

	@Override
	public String getId() {
		return "CorpSignRspHandle";
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
			log.info("开始调用基础服务[CorpSignRspHandle]......");
		}
		
		String sucFlag = "ERR000";//签约结果
		String isSigned = "N";//是否已签约:Y-是，N-否
		//当前产品代码
		String signCodeType = (String)getContext().getValue("signCodeType");
		String curYZSrvId = "";
		try {
			
			ICompositeData Rsp2002201000102 = (ICompositeData) SessionContext.getContext().getValue("Rsp2002201000102"); 
			
			if (null == Rsp2002201000102)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
				Rsp2002201000102  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
				Rsp2002201000102.setId("Rsp2002201000102");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
				Rsp2002201000102.setxPath("/Rsp2002201000102");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
				getContext().setValue("Rsp2002201000102",Rsp2002201000102);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			} 
			//获取当前原子服务码
			curYZSrvId = (String)SessionContext.getContext().getValue("CurYZSrvId");
			if (null == curYZSrvId || "".equals(curYZSrvId.trim()))                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			{                          
				log.error("当前原子服务码为空!");
				return null;
			} 
			
			rspService = (ICompositeData) SessionContext.getContext().getValue("Rsp"+ curYZSrvId);
			
			signPro = (String)SessionContext.getContext().getValue("signCodeType");//
			SessionContext.getContext().setValue("SignProTp", signPro);
			
			if (null == rspService)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			{                          
				log.error("原子服务["+curYZSrvId+"]返回结果为空!");
				return null;
			}                             
			
			
			String retCode = CompositeDataUtils.getValue(rspService, "RspSysHead/RetCode");
			String retMsg = CompositeDataUtils.getValue(rspService, "RspSysHead/RetMsg");
			log.info("对公产品代码["+signPro+"]，原子服务码=["+curYZSrvId+"]，返回码=["+retCode+"]，返回结果=["+retMsg+"]");
			List<String> retCodeList = PropertiesUtils.getReSignRetCode(curYZSrvId);
			if("000000".equalsIgnoreCase(retCode)){
				log.info("原子服务["+curYZSrvId+"]交易成功");
				sucFlag = "000000";
			}else if(retCodeList != null && retCode != null && retCodeList.contains(retCode)){
				log.info("原子服务["+curYZSrvId+"]交易成功，已签约");
				sucFlag = "000000";
				isSigned = "Y";
			}else{
				log.info("原子服务["+curYZSrvId+"]交易失败");
				ICompositeData destArrayParent = CompositeDataUtils.mkNodeNotExist(Rsp2002201000102, "RspAppBody");
				ICompositeData arrayiItem = new SoapCompositeData();
				arrayiItem.setId("CorpSignRsltArray");
				destArrayParent.setChild("CorpSignRsltArray", arrayiItem);
				
				CompositeDataUtils.setValue(arrayiItem, "TransStatus", "F");
				CompositeDataUtils.setValue(arrayiItem, "RetCode", retCode);
				CompositeDataUtils.setValue(arrayiItem, "RetMsg", retMsg);
				CompositeDataUtils.setValue(arrayiItem, "SignProTp", signPro);
			}
		} catch (Exception e) {
			log.error("对公综合签约响应处理异常:",e);
			throw new InvokeException();
		}finally{
			if(!"1003200000205".equals(curYZSrvId)){
				SessionContext.getContext().setValue(signCodeType==null?"RetCode":("RetCode"+signCodeType), sucFlag);
				SessionContext.getContext().setValue("IsSigned"+signCodeType, isSigned);
			}
			if (log.isInfoEnabled()) {
				log.info("调用基础服务结束[CorpSignRspHandle]......");
			}
		}
		return null;
	}
}






