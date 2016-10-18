package com.dcits.smartbip.runtime.impl;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.ErrorCodes;
import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.entity.ExcludeInfo;
import com.dcits.smartbip.runtime.entity.Production;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.runtime.model.impl.SoapCompositeData;
import com.dcits.smartbip.utils.CompositeDataUtils;
import com.dcits.smartbip.utils.XMLUtils;

/*
 * 查询所有待签约品种参数信息
 * */

public class QueryAllSigningParamInformation extends AbstractBaseService implements IService{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3727836087752374031L;
	private static final Log log = LogFactory.getLog(QueryAllSigningParamInformation.class);
	
	@Override
	public String getId() {
		return "QueryAllSigningParamInformation";
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
			log.info("开始调用基础服务[QueryAllSigningParamInformation]......");
		}
		
		ICompositeData Req2002201000101 = (ICompositeData) SessionContext.getContext().getValue("Req2002201000101"); 
		ICompositeData Rsp2002201000101 = (ICompositeData) SessionContext.getContext().getValue("Rsp2002201000101"); 
		
		if (null == Rsp2002201000101)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			Rsp2002201000101  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			Rsp2002201000101.setId("Rsp2002201000101");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			Rsp2002201000101.setxPath("/Rsp2002201000101");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			getContext().setValue("Rsp2002201000101",Rsp2002201000101);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		}
		
		@SuppressWarnings("unchecked")
		List<String> signCodeTypes = (List<String>)getContext().getValue("signCodeTypes");
		Map<String,Production> productionMap = XMLUtils.getSignCodeInfo("0"); //0为对私
		//检查待签约产品是否都存在在库列表中
		for(String signCodeType : signCodeTypes){
		    SessionContext.getContext().setValue("NotFindProductCode", "false");
			if(!productionMap.containsKey(signCodeType)){
				SessionContext.getContext().setValue("NotFindProductCode", "true");
				log.info("该"+signCodeType+"签约产品不存在，请核实！");
				CompositeDataUtils.setReturn(Rsp2002201000101,ErrorCodes.CODE_BIP02E0000,"该"+signCodeType+"签约产品不存在，请核实！");
			    break;
		    }
		}
		//判断任一渠道是否合法
		if(("false").equalsIgnoreCase((String) SessionContext.getContext().getValue("NotFindProductCode"))){
			SessionContext.getContext().setValue("AllChannlSigningLegal", "true");
			String reqchannel = CompositeDataUtils.getValue(Req2002201000101, "ReqSysHead/ChannelID");
			if(reqchannel!=null){
				for(String signCodeType : signCodeTypes){
					String AvlSignChlFlg = productionMap.get(signCodeType).getAvlSignChlFlg();
					if(AvlSignChlFlg != null && "0".equalsIgnoreCase(AvlSignChlFlg))  //0代表此产品不控制签约渠道
						continue;
					ArrayList<String> channels = productionMap.get(signCodeType).getAvlSignChlList();
					if(!channels.contains(reqchannel)){
						SessionContext.getContext().setValue("AllChannlSigningLegal", "false");
					    log.debug(reqchannel+"请求渠道不能请求签约"+signCodeType+"产品");
					    CompositeDataUtils.setReturn(Rsp2002201000101,ErrorCodes.CODE_BIP01E0001,reqchannel+"请求渠道不能请求签约"+signCodeType+"产品");
					    break;
					}
				}	
			}
		}
		//判断签约产品之间是否互斥
		OK:
		if(("true").equalsIgnoreCase((String) SessionContext.getContext().getValue("AllChannlSigningLegal"))){
			for(String signCodeType : signCodeTypes){
				SessionContext.getContext().setValue("SigningProductMutex", "false");
				ArrayList<ExcludeInfo> excludeInfoList = productionMap.get(signCodeType).getExcludeInfoList();
				if(excludeInfoList != null){
					for(ExcludeInfo excludeInfo : excludeInfoList){
						if(signCodeTypes.contains(excludeInfo.getExcludeVarCd())){
							SessionContext.getContext().setValue("SigningProductMutex", "true");
							log.debug(excludeInfo.getExcludeVarCd()+"产品和"+signCodeType+"产品互斥，不能同时签约，请核实");
							CompositeDataUtils.setReturn(Rsp2002201000101,ErrorCodes.CODE_BIP01E0004,excludeInfo.getExcludeVarCd()+"产品和"+signCodeType+"产品互斥，不能同时签约，请核实");
							break OK;							
						}
					}
				}
			}
		}
		
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[QueryAllSigningParamInformation]......");
		}

		return null;
	}
}
