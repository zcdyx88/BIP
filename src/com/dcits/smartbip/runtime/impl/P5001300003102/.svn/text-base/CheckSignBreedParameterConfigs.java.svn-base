package com.dcits.smartbip.runtime.impl.P5001300003102;

import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.ErrorCodes;
import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.entity.Production;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.runtime.model.impl.SoapCompositeData;
import com.dcits.smartbip.utils.CompositeDataUtils;
import com.dcits.smartbip.utils.XMLUtils;

public class CheckSignBreedParameterConfigs extends AbstractBaseService implements IService {
	
	/**
	 * 签约品种参数配置查询
	 */
	private static final long serialVersionUID = 6623421290907086997L;
	private static final Log log = LogFactory.getLog(CheckSignBreedParameterConfigs.class);
	@Override
	public String getId() {
		return "CheckSignBreedParameterConfigs";
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
			log.info("开始调用基础服务[CheckSignBreedParameterConfigs]......");
		}
		CompositeDataUtils.setSysHead("5001300003102", ErrorCodes.CODE_BIP00T0001, "交易失败", "F");
		ICompositeData Req5001300003102 = (ICompositeData) SessionContext.getContext().getValue("Req5001300003102");
		ICompositeData Rsp5001300003102 = (ICompositeData) SessionContext.getContext().getValue("Rsp5001300003102");
		
		if (null == Rsp5001300003102)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			Rsp5001300003102  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			Rsp5001300003102.setId("Rsp5001300003102");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			Rsp5001300003102.setxPath("/Rsp5001300003102");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			getContext().setValue("Rsp5001300003102",Rsp5001300003102);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		}
		
		//SignVarCd-签约品种代码   PrivyCorpFlg-个人对公标志
		String SignVarCd = CompositeDataUtils.getValue(Req5001300003102, "ReqAppBody/SignVarCd");
		String PrivyCorpFlg = CompositeDataUtils.getValue(Req5001300003102, "ReqAppBody/PrivyCorpFlg");
		if(null == PrivyCorpFlg || "0".equalsIgnoreCase(PrivyCorpFlg) || "1".equalsIgnoreCase(PrivyCorpFlg)){
			Map<String,Production> productionMap0 = XMLUtils.getSignCodeInfo("0"); //0为对私
			Map<String,Production> productionMap1 = XMLUtils.getSignCodeInfo("1"); //1为对公
			if(null == SignVarCd && null == PrivyCorpFlg){ //产品代码和标志均为空
	        	for (String CodeType_ds : productionMap0.keySet()){
	        		SetParametConfigs(productionMap0.get(CodeType_ds),Rsp5001300003102);
	        	}
	        	for (String CodeType_dg : productionMap1.keySet()){
	        		SetParametConfigs(productionMap1.get(CodeType_dg),Rsp5001300003102);
	        	}
	        }else if(null != SignVarCd && null != PrivyCorpFlg){ //产品代码和标志均不为空
	        	if(!productionMap0.containsKey(SignVarCd) && !productionMap1.containsKey(SignVarCd)){
	        		log.error("签约品种代码["+SignVarCd+"]不存在，查询失败请核实！");
					CompositeDataUtils.setReturn(Rsp5001300003102, ErrorCodes.CODE_BIP00T0001, "签约品种代码["+SignVarCd+"]不存在，查询失败请核实！");
					if (log.isInfoEnabled()) {
						log.info("调用基础服务结束[CheckSignBreedParameterConfigs]......");
					}
					return null;
	        	}else{
	        		Map<String,Production> productionMap = "0".equals(PrivyCorpFlg)?productionMap0:productionMap1;
		        	SetParametConfigs(productionMap.get(SignVarCd),Rsp5001300003102);
	        	}
	        }else if(null == SignVarCd && null != PrivyCorpFlg) {  //产品代码为空  标志不为空
	        	Map<String,Production> productionMap = XMLUtils.getSignCodeInfo(PrivyCorpFlg);
	        	for (String CodeType: productionMap.keySet()){  
	        		SetParametConfigs(productionMap.get(CodeType),Rsp5001300003102);
	        	}
	        }else if(null != SignVarCd && null == PrivyCorpFlg){  //产品代码不为空  标志为空
	        	if(!productionMap0.containsKey(SignVarCd) && !productionMap1.containsKey(SignVarCd)){
	        		log.error("签约品种代码["+SignVarCd+"]不存在，查询失败请核实！");
					CompositeDataUtils.setReturn(Rsp5001300003102, ErrorCodes.CODE_BIP00T0001, "签约品种代码["+SignVarCd+"]不存在，查询失败请核实！");
					if (log.isInfoEnabled()) {
						log.info("调用基础服务结束[CheckSignBreedParameterConfigs]......");
					}
					return null;
	        	}else{
		        	SetParametConfigs(productionMap0.get(SignVarCd),Rsp5001300003102);
		        	SetParametConfigs(productionMap1.get(SignVarCd),Rsp5001300003102);
	        	}
	        }
			CompositeDataUtils.setValue(Rsp5001300003102, "RspSysHead/RetCode", "000000");
			CompositeDataUtils.setValue(Rsp5001300003102, "RspSysHead/RetMsg", "查询成功");
			CompositeDataUtils.setValue(Rsp5001300003102, "RspSysHead/TransStatus", "S");
		}else{
			CompositeDataUtils.setReturn(Rsp5001300003102,ErrorCodes.CODE_BIP00T0001,"签约品种参数配置查询参数上送错误，请核实");
			log.info("签约品种参数配置查询参数上送错误，请核实");
		}
		log.info("签约品种参数配置查询完成！");
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[CheckSignBreedParameterConfigs]......");
		}
		
		return null;
	}
	

	public static void SetParametConfigs(Production production,ICompositeData src){
	   try{
		   	if(production == null){
		   		return;
		   	}
			ICompositeData destArrayParent = CompositeDataUtils.mkNodeNotExist(src, "RspAppBody");
			ICompositeData arrayiItem = new SoapCompositeData();
			arrayiItem.setId("SignVarInfoList");
			destArrayParent.setChild("SignVarInfoList", arrayiItem);
			
			CompositeDataUtils.setValue(arrayiItem, "SignVarCd", production.getSignVarCd());
			CompositeDataUtils.setValue(arrayiItem, "SignVarNm", production.getSignVarNm());
			CompositeDataUtils.setValue(arrayiItem, "PrivyCorpFlg", production.getPrivyCorpFlg());
			CompositeDataUtils.setValue(arrayiItem, "BlngSysId", production.getBlngSysId());
			CompositeDataUtils.setValue(arrayiItem, "BlngLglPrsnCd", production.getBlngLglPrsnCd());
			CompositeDataUtils.setValue(arrayiItem, "CstAcctSignFlg", production.getCstAcctSignFlg());
			CompositeDataUtils.setValue(arrayiItem, "InOutBankFlg", production.getInOutBankFlg());
			CompositeDataUtils.setValue(arrayiItem, "SignOffFlg", production.getSignOffFlg());
			CompositeDataUtils.setValue(arrayiItem, "CroRegSignOffFlg", production.getCroRegSignOffFlg());
			CompositeDataUtils.setValue(arrayiItem, "AvlMntnFlg", production.getAvlMntnFlg());
			CompositeDataUtils.setValue(arrayiItem, "QryListVisionFlg", production.getQryListVisionFlg());
			CompositeDataUtils.setValue(arrayiItem, "ClsMstSignOffFlg", production.getClsMstSignOffFlg());
			CompositeDataUtils.setValue(arrayiItem, "AvlTrsfrFlg", production.getAvlTrsfrFlg());
			CompositeDataUtils.setValue(arrayiItem, "OneCdMulSignFlg", production.getOneCdMulSignFlg());
			CompositeDataUtils.setValue(arrayiItem, "AvlSignChlFlg", production.getAvlSignChlFlg());
			CompositeDataUtils.setValue(arrayiItem, "AvlSignOffChlFlg", production.getAvlSignOffChlFlg());
			CompositeDataUtils.setValue(arrayiItem, "AvlSignExchgChlFlg", production.getAvlSignExchgChlFlg());
			
			ArrayList<String> BlngBrchBnkList= production.getBlngBrchBnkList();
			if(BlngBrchBnkList != null){
				for(String BlngBrchBnkNo : BlngBrchBnkList){
					ICompositeData arrayiItemBlngBrchBnk = new SoapCompositeData();
					arrayiItemBlngBrchBnk.setId("BlngBrchBnkList");
					arrayiItem.setChild("BlngBrchBnkList", arrayiItemBlngBrchBnk);
					CompositeDataUtils.setValue(arrayiItemBlngBrchBnk, "BlngBrchBnkNo", BlngBrchBnkNo);
				}
			}
			ArrayList<String> AvlSignChlList= production.getAvlSignChlList();
			if(AvlSignChlList != null){
				for(String ChlId : AvlSignChlList){
					ICompositeData arrayiItemAvlSignChl = new SoapCompositeData();
					arrayiItemAvlSignChl.setId("AvlSignChlList");
					arrayiItem.setChild("AvlSignChlList", arrayiItemAvlSignChl);
					CompositeDataUtils.setValue(arrayiItemAvlSignChl, "ChlId", ChlId);
				}
			}
			ArrayList<String> AvlSignOffChllist = production.getAvlSignOffChlList();
			if(AvlSignOffChllist != null){
				for(String ChlId : AvlSignOffChllist){
					ICompositeData arrayiItemAvlSignOffChl = new SoapCompositeData();
					arrayiItemAvlSignOffChl.setId("AvlSignOffChlList");
					arrayiItem.setChild("AvlSignOffChlList", arrayiItemAvlSignOffChl);
					CompositeDataUtils.setValue(arrayiItemAvlSignOffChl, "ChlId", ChlId);
				}
			}
			ArrayList<String> AvlSignExchgChllist = production.getAvlSignExchgChlList();
			if(AvlSignExchgChllist != null){
				for(String ChlId : AvlSignExchgChllist){
					ICompositeData arrayiItemAvlSignExchgChl = new SoapCompositeData();
					arrayiItemAvlSignExchgChl.setId("AvlSignExchgChlList");
					arrayiItem.setChild("AvlSignExchgChlList", arrayiItemAvlSignExchgChl);
					CompositeDataUtils.setValue(arrayiItemAvlSignExchgChl, "ChlId", ChlId);
				}
			}
	   }catch(Exception e){
		   log.error("本次遍历查询失败，具体配置信息不存在",e);
	   }
	}
	
}
