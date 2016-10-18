package com.dcits.smartbip.runtime.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.dcits.smartbip.utils.CompositeDataUtils;
import com.dcits.smartbip.utils.PropertiesUtils;
import com.dcits.smartbip.utils.XMLUtils;

/*
 * 判断所有待签约产品是否均未签约
 * */

public class CheckAllStaySigningProcuct extends AbstractBaseService implements IService{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 7077217625796850437L;
	private static final Log log = LogFactory.getLog(CheckAllStaySigningProcuct.class);
	
	@Override
	public String getId() {
		return "CheckAllStaySigningProcuct";
	}

	@Override
	public String getType() {
		return "base";
	}
	
	@Override
	public void setType(String type) {
		// TODO Auto-generated method stub
	}
	
	@SuppressWarnings("null")
	@Override
	public Object execute(Object req) throws InvokeException {

		if (log.isInfoEnabled()) {
			log.info("开始调用基础服务[CheckAllStaySigningProcuct]......");
		}
		
		SessionContext.getContext().setValue("AllStaySigningProcuctNotSigning", "true");
		
		ICompositeData Rsp2002300000401 = (ICompositeData) SessionContext
		.getContext().getValue("Rsp2002300000401");
		ICompositeData Req2002201000101 = (ICompositeData) SessionContext
		.getContext().getValue("Req2002201000101");
		ICompositeData Rsp2002201000101 = (ICompositeData) SessionContext
		.getContext().getValue("Rsp2002201000101");
		List<ICompositeData> DirectDebitSignArray = CompositeDataUtils.getByPath(                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
				Req2002201000101, "ReqAppBody/DirectDebitSignReq/DirectDebitSignArray");
		String RetCode = CompositeDataUtils.getValue(Rsp2002300000401,
		"RspSysHead/RetCode");
		String RetMsg = CompositeDataUtils.getValue(Rsp2002300000401,
		"RspSysHead/RetMsg");
		if(null != RetCode && "000000".equalsIgnoreCase(RetCode)){
			List<ICompositeData> GRP_SYNTHESIS_INFO = CompositeDataUtils.getByPath(Rsp2002300000401, "RspAppBody/GRP_SYNTHESIS_INFO");
			@SuppressWarnings("unchecked")
			List<String> signCodeTypes = (List<String>)getContext().getValue("signCodeTypes");
			Map<String,Production> productionMap = XMLUtils.getSignCodeInfo("0"); //0为对私
			HashMap<String, String> dliywhMap = new HashMap<String, String>();
			List<String> signcodeTypes = new ArrayList<String>();  //此账户已签约产品代码数组
			HashMap<String, String> CONT_ACCmap = new HashMap<String, String>();
			//所有待签约产品是否均未签约
			if(GRP_SYNTHESIS_INFO!=null && GRP_SYNTHESIS_INFO.size()>0){//说明此账户有已签约产品列表
				SessionContext.getContext().setValue("AllStaySigningProcuctNotSigning", "true");
				SessionContext.getContext().setValue("AllSigningProcuctNotMetux", "true");
				String AcctNo = CompositeDataUtils.getValue(Req2002201000101, "ReqAppBody/AcctNo");  //请求账户
				for(ICompositeData GRP_SYNTHESIS_INFOS:GRP_SYNTHESIS_INFO){
					String SIGN_ACC  = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFOS, "SIGN_ACC");    //已签约账户
					String sIGN_TYPE = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFOS, "SIGN_TYPE");   //已签约产品
					String CstAcctSignFlg = productionMap.get(sIGN_TYPE).getCstAcctSignFlg();// 0客户级   1账户级
					if(CstAcctSignFlg.equalsIgnoreCase("1")){
						if(null !=AcctNo && AcctNo.equalsIgnoreCase(SIGN_ACC)){
							String SIGN_TYPE = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFOS, "SIGN_TYPE");   //已签约产品代码
							String CONT_ACC = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFOS, "CONT_ACC");   //已签约产品代码
							signcodeTypes.add(SIGN_TYPE);  //该账户的已签约产品
							if(CONT_ACC != null && CONT_ACC.length()>0){ //ECIF不返回该字段
								CONT_ACCmap.put(SIGN_TYPE, CONT_ACC);
							}
						}
					}else if (CstAcctSignFlg.equalsIgnoreCase("0")){
						String SIGN_TYPE = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFOS, "SIGN_TYPE");   //已签约产品代码
						String CONT_ACC = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFOS, "CONT_ACC");   //已签约产品代码
						signcodeTypes.add(SIGN_TYPE);  //该账户的已签约产品
						if(CONT_ACC != null && CONT_ACC.length()>0){ //ECIF不返回该字段
							CONT_ACCmap.put(SIGN_TYPE, CONT_ACC);
						}
					}
				}
				if(signcodeTypes!=null && signcodeTypes.size()>0){
					for(String signCodeType : signCodeTypes){
						if(signcodeTypes.contains(signCodeType)){
							String CONT_ACC = CONT_ACCmap.get(signCodeType);
							String OneCdMulSignFlg = productionMap.get(signCodeType).getOneCdMulSignFlg();
							if("0".equalsIgnoreCase(OneCdMulSignFlg)){ //不允许一卡多签
								SessionContext.getContext().setValue("AllStaySigningProcuctNotSigning", "false");
				            	log.error("该账户已签约"+signCodeType+"产品，不能重复签约！");
				            	CompositeDataUtils.setReturn(Rsp2002201000101,ErrorCodes.CODE_BIP01E0002,"该账户已签约"+signCodeType+"产品，不能重复签约！");
				                break;
							}
				            else if("1".equalsIgnoreCase(OneCdMulSignFlg)){
				            	SessionContext.getContext().setValue("AllStaySigningProcuctNotSigning", "true");
				            	//代缴费存在一卡多签
				            	String DSYHBH = null;
				            	String KAHAOO = null;
				            	String SOrganId_sCustAcct = null;
				            	dliywhMap = PropertiesUtils.getProductionAndDLIYWH();
				            	if(dliywhMap.get(signCodeType)!=null){
									for (ICompositeData signComposite : DirectDebitSignArray) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
										String DLIYWH = CompositeDataUtils.getValue(signComposite,"DLIYWH");
										if(DLIYWH.equalsIgnoreCase(dliywhMap.get(signCodeType))){
											DSYHBH = CompositeDataUtils.getValue(signComposite,"DSYHBH");  //唯一标识
										}
									}
								}
								//信用卡自动还款一卡多签
								else if("000005".equalsIgnoreCase(signCodeType)){
									KAHAOO = (String) CompositeDataUtils.getValue(Req2002201000101, "ReqAppBody/CrCardAutoRepaySignReq/KAHAOO").toString();
								}
								else if("000008".equalsIgnoreCase(signCodeType)){
									String sOrganId = (String) CompositeDataUtils.getValue(Req2002201000101, "ReqAppBody/ThirdDepMngSignReq/sOrganId").toString();
									StringBuffer sOrganId_sCustAcct = new StringBuffer(sOrganId);
									String sCustAcct = (String) CompositeDataUtils.getValue(Req2002201000101, "ReqAppBody/ThirdDepMngSignReq/sCustAcct").toString();
									sOrganId_sCustAcct.append("_").append(sCustAcct);
									SOrganId_sCustAcct = sOrganId_sCustAcct.toString();
								}
								if(CONT_ACC.equalsIgnoreCase(DSYHBH)||CONT_ACC.equalsIgnoreCase(KAHAOO)||CONT_ACC.equalsIgnoreCase(SOrganId_sCustAcct)){
									SessionContext.getContext().setValue("AllStaySigningProcuctNotSigning", "false");
									log.error("已签约"+signCodeType+"产品，不能重复签约！");
					            	CompositeDataUtils.setReturn(Rsp2002201000101,ErrorCodes.CODE_BIP01E0002,"已签约"+signCodeType+"产品，不能重复签约！");
					            	break;
								}
				            }		
						}
					}
				}
			}else{
				//说明此客户没有已签约产品
				SessionContext.getContext().setValue("AllStaySigningProcuctNotSigning", "true");
				SessionContext.getContext().setValue("AllSigningProcuctNotMetux", "true");
				log.debug("该客户没有已签约产品");
			}
			SessionContext.getContext().setValue("AllSigningProcuctNotMetux", "true");
			
			if("true".equalsIgnoreCase((String) SessionContext.getContext().getValue("AllStaySigningProcuctNotSigning")) && signcodeTypes!=null && signcodeTypes.size()>0){
				List<String> ExcludeTpNos = new ArrayList<String>();
				for(String signCodeType : signCodeTypes){  //待签约产品
					ArrayList<ExcludeInfo> excludeInfoList = productionMap.get(signCodeType).getExcludeInfoList();
					if(excludeInfoList != null && excludeInfoList.size() > 0){
						for(ExcludeInfo excludeInfo : excludeInfoList){
							ExcludeTpNos.add(excludeInfo.getExcludeVarCd());
						}
					}
				}
	            for(String ExcludeTpNo : ExcludeTpNos){
	            	if(signcodeTypes.contains(ExcludeTpNo)){
	            		SessionContext.getContext().setValue("AllSigningProcuctNotMetux", "false");
				    	log.error("该账户存在已签约产品（"+ExcludeTpNo+"）与待签约产品存在互斥关系，不能签约，请核查！");
				    	CompositeDataUtils.setReturn(Rsp2002201000101,ErrorCodes.CODE_BIP01E0004,"该账户存在已签约产品（"+ExcludeTpNo+"）与待签约产品存在互斥关系，不能签约，请核查！");
						break;
	            	}
	            }				
			}
		} else{
			CompositeDataUtils.setReturn(Rsp2002201000101,ErrorCodes.CODE_BIP00T0001,"ECIF对私签约关系查询交易失败："+RetMsg);
			log.info("ECIF对私签约关系查询交易失败！");
		}
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[CheckAllStaySigningProcuct]......");
		}

		return null;
	}
	
}
