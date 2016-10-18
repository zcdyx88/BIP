package com.dcits.smartbip.runtime.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.entity.Production;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.utils.CompositeDataUtils;
import com.dcits.smartbip.utils.PropertiesUtils;
/**
 * 判断解约产品是否已签约
 * @author srxhx273
 *
 */
public class ChkProductionIsSigned extends AbstractBaseService implements IService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2601747963426056507L;
	/**
	 * 日志打印
	 */
	private static final Log log = LogFactory.getLog(ChkProductionIsSigned.class);
	/**
	 * ECIF对私签约关系查询：Rsp+原子服务码
	 */
	private static final String SIGN_QRY_YZ_SERVICE_ID = "Rsp2002300000401";
	
	@Override                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
	public String getId() {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
		return "ChkProductionIsSigned";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
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
			log.info("开始调用基础服务[ChkProductionIsSigned]......");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
		}
		//isSignedFlag：0-已签约，1-未签约
		String isSignedFlag = "0";
		String retCode = "BIP00T0001";//响应码
		String retMsg = "";//结果信息
		//组合服务码
		String ModifySignType = (String)getContext().getValue("ModifySignType");
		ICompositeData rspICD = (ICompositeData) SessionContext.getContext().getValue(SIGN_QRY_YZ_SERVICE_ID);
		try {
			
			if (null == rspICD){
				log.info("返回结构体["+SIGN_QRY_YZ_SERVICE_ID+"]为空");
				retMsg = "交易失败！";
				isSignedFlag = "1";
			}else{
				retCode = CompositeDataUtils.getValue(rspICD, "RspSysHead/RetCode");
				log.info("ECIF对私签约关系查询结果retCode=["+retCode+"]，提示信息["
						+CompositeDataUtils.getValue(rspICD, "RspSysHead/RetMsg")+"]");
				if("000000".equals(retCode)){//交易成功
					//返回笔数
					String resultSize = CompositeDataUtils.getValue(rspICD, "RspAppBody/ALL_RESULT_SIZE");
					if(resultSize != null && Integer.parseInt(resultSize) == 0){
						log.info("该账户下无任何签约关系，不能进行解约操作");
						retMsg = "该账户下无任何签约关系";
						isSignedFlag = "1";
						retCode = "BIP02E0007";
						return null;
					}
					ICompositeData Req2002201000103 = (ICompositeData) SessionContext.getContext().getValue("Req2002201000103");
					
					String AcctNo = CompositeDataUtils.getValue(Req2002201000103, "ReqAppBody/AcctNo");//上送解约帐号
					//待解约产品信息
					List<Production> signCodeList = (List<Production>)getContext().getValue("ProductionList");
					if(signCodeList == null || signCodeList.size() == 0){
						log.info("待解约产品为空，业务流程将结束");
						retMsg = "待解约产品为空，请核实";
						isSignedFlag = "1";
						return null;
					}
					//ECIF对私签约关系查询返回数组GRP_SYNTHESIS_INFO信息
					List<ICompositeData> GRP_SYNTHESIS_INFO_ARRAY = CompositeDataUtils.getByPath(rspICD, "RspAppBody/GRP_SYNTHESIS_INFO");
					getContext().setValue("GRP_SYNTHESIS_INFO_ARRAY", GRP_SYNTHESIS_INFO_ARRAY);
					if(null == GRP_SYNTHESIS_INFO_ARRAY || GRP_SYNTHESIS_INFO_ARRAY.size() == 0){
						log.info("该账户下无任何签约关系，不能进行解约操作");
						retMsg = "该账户下无任何签约关系";
						isSignedFlag = "1";
						retCode = "BIP02E0007";
						return null;
					}
					List<String> reqContAccList = null;
					//是否签约鑫元宝：0-是，1-否
					String isSignedXYB = "1";
					//是否签约鑫钱宝：0-是，1-否
					String isSignedXQB = "1";
					ProductionLoop : for(Production production : signCodeList){
						//当待解约产品为基金时，需要判断鑫钱宝、鑫元宝是否已签约，若已签约则本次综合解约中必须在基金之前解约
						if("000020".equals(production.getSignVarCd())){
							List<String> signCodeTypes = (List<String>)getContext().getValue("signCodeTypes");
							for (ICompositeData GRP_SYNTHESIS_INFO : GRP_SYNTHESIS_INFO_ARRAY) {
								String SIGN_TYPE = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFO,"SIGN_TYPE"); //签约类型即签约产品代码
								String SIGN_ACC = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFO,"SIGN_ACC");//签约凭证号
								if("000009".equals(SIGN_TYPE) && AcctNo.equals(SIGN_ACC)){//鑫元宝
									isSignedXYB = "0";
									getContext().setValue("isSignedXYB", isSignedXYB);
									if(!signCodeTypes.contains(SIGN_TYPE)){
										log.info("如要解约基金，请提前解约已签约基金类产品鑫元宝");
										retMsg = "如要解约基金，请提前解约已签约基金类产品鑫元宝、鑫钱宝";
										retCode = "BIP02E0006";
										isSignedFlag = "1";
										return null;
									}else{
										log.info("基金解约时，已签约鑫元宝存在于待签产品列表中");
									}
								}else if("000010".equals(SIGN_TYPE) && AcctNo.equals(SIGN_ACC)){//鑫钱宝
									isSignedXQB = "0";
									getContext().setValue("isSignedXQB", isSignedXQB);
									if(!signCodeTypes.contains(SIGN_TYPE)){
										log.info("如要解约基金，请提前解约已签约基金类产品鑫钱宝");
										retMsg = "如要解约基金，请提前解约已签约基金类产品鑫元宝、鑫钱宝";
										retCode = "BIP02E0006";
										isSignedFlag = "1";
										return null;
									}else{
										log.info("基金解约时，已签约鑫钱宝存在于待签产品列表中");
									}
								}
							}
						}
						
						if("1".equals(production.getOneCdMulSignFlg())){//一卡多签
							reqContAccList = new ArrayList<String>();
							if("000005".equals(production.getSignVarCd())){//信用卡自动还款
								List<ICompositeData> CrCardArray = CompositeDataUtils.getByPath(Req2002201000103, "ReqAppBody/CrCardAutoRepaySignOffReq/CrCardArray");
								for(ICompositeData icd : CrCardArray){
									reqContAccList.add(CompositeDataUtils.getValue(icd, "KAHAOO"));
								}
							}else {//其他为代缴费
								HashMap<String,String> proMap = PropertiesUtils.getProductionAndDLIYWH();
								List<ICompositeData> DirectDebitSignOffArray = CompositeDataUtils.getByPath(Req2002201000103, "ReqAppBody/DirectDebitSignOffReq/DirectDebitSignOffArray");
								String DLIYWH = "";
								for(ICompositeData icd : DirectDebitSignOffArray){
									DLIYWH = CompositeDataUtils.getValue(icd, "DLIYWH");
									if(DLIYWH == null || "".equals(DLIYWH)){
										retMsg = "代理业务号为空，请核实";
										log.info("代理业务号为空，业务流程将结束");
										isSignedFlag = "1";
										return null;
									}
									if(DLIYWH.equals(proMap.get(production.getSignVarCd()))){
										List<ICompositeData> ThirdUserIdArray = CompositeDataUtils.getByPath(icd, "ThirdUserIdArray");
										for(ICompositeData iCData : ThirdUserIdArray){
											reqContAccList.add(CompositeDataUtils.getValue(iCData, "DSYHBH"));
										}
									}
								}
							}
							if(reqContAccList == null || reqContAccList.size() == 0){
								retMsg = "代理业务号不合法，请核实";
								log.info("代理业务号不合法，导致待解约唯一识别标志(第三方用户编号)为空，业务流程将结束");
								isSignedFlag = "1";
								return null;
							}
							log.info("待解约唯一识别标志列表["+reqContAccList.toString()+"]");
							ContAccLoop : for(String reqContAcc : reqContAccList){
								for (ICompositeData GRP_SYNTHESIS_INFO : GRP_SYNTHESIS_INFO_ARRAY) {
									String SIGN_TYPE = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFO,"SIGN_TYPE"); //签约类型即签约产品代码
									String SIGN_ACC = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFO,"SIGN_ACC");//签约状态
									String CONT_ACC = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFO,"CONT_ACC");//唯一识别标志
									/*
									 * 此处需要判断待解约的产品是否已签约，任一待解约产品未签约，则跳出循环并设置isSignedFlag=1，
									 * 且给出以下提示：XXXX产品未签约或已解约，请核实（对于一卡多签产品，需要判断到唯一标识级，报错信息“唯一标识XXXX未签约或已解约，请核实”）
									 * retMsg = "XXXX产品未签约或已解约，请核实"
									 * retMsg = "唯一标识XXXX未签约或已解约，请核实"
									 */
									if(production.getSignVarCd().equals(SIGN_TYPE) && AcctNo.equals(SIGN_ACC)
											&& reqContAcc.equals(CONT_ACC)){
										log.info("待解约产品"+SIGN_TYPE+"唯一标志"+CONT_ACC+"已签约，可以进行解约");
										continue ContAccLoop;
									}
								}
								retMsg = production.getSignVarCd()+"产品唯一标识"+reqContAcc+"未签约或已解约，请核实";
								log.info(production.getSignVarCd()+"产品唯一标识"+reqContAcc+"未签约或已解约，请核实");
								isSignedFlag = "1";
								retCode = "BIP02E0003";
								return null;
							}
						}else{
							for (ICompositeData GRP_SYNTHESIS_INFO : GRP_SYNTHESIS_INFO_ARRAY) {
								String SIGN_TYPE = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFO,"SIGN_TYPE"); //签约类型即签约产品代码
								String SIGN_ACC = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFO,"SIGN_ACC");//签约状态
								
								/*
								 * 此处需要判断待解约的产品是否已签约，任一待解约产品未签约，则跳出循环并设置isSignedFlag=1，
								 * 且给出以下提示：XXXX产品未签约或已解约，请核实（对于一卡多签产品，需要判断到唯一标识级，报错信息“唯一标识XXXX未签约或已解约，请核实”）
								 * retMsg = "XXXX产品未签约或已解约，请核实"
								 * retMsg = "唯一标识XXXX未签约或已解约，请核实"
								 */
								if("0".equals(production.getCstAcctSignFlg())){//客户级
									if(production.getSignVarCd().equals(SIGN_TYPE)){//已签约
										log.info("待解约产品"+SIGN_TYPE+"已签约，可以进行解约");
										continue ProductionLoop;
									}
								}else if("1".equals(production.getCstAcctSignFlg())){//账户级
									if(production.getSignVarCd().equals(SIGN_TYPE) && AcctNo.equals(SIGN_ACC)){
										log.info("待解约产品"+SIGN_TYPE+"已签约，可以进行解约");
										continue ProductionLoop;
									}
								}
							}
							retMsg = production.getSignVarCd()+"产品未签约或已解约，请核实";
							log.info(production.getSignVarCd()+"产品未签约或已解约，请核实");
							isSignedFlag = "1";
							retCode = "BIP02E0002";
							return null;
						}
					}
					
				}else{
					isSignedFlag = "1";
					retMsg = CompositeDataUtils.getValue(rspICD, "RspSysHead/RetMsg");
				}
			}
		} catch (Exception e) {
			log.error("判断解约产品是否已签约异常：", e);
			retMsg = "系统异常，请确认解约是否成功";
			isSignedFlag = "1";
			throw new InvokeException();
		}finally{
			getContext().setValue("isSignedFlag", isSignedFlag);
			
			if("1".equals(isSignedFlag)){
				CompositeDataUtils.setReturn(ModifySignType, "000000".equals(retCode)?"BIP00T0001":retCode, retMsg);
			}
			
			if (log.isInfoEnabled()) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
				log.info("调用基础服务结束[ChkProductionIsSigned]......");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
		return null;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
	}  
}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               

	


