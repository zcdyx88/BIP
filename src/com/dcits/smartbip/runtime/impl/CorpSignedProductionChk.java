package com.dcits.smartbip.runtime.impl;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.entity.ExcludeInfo;
import com.dcits.smartbip.runtime.entity.Production;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.utils.CompositeDataUtils;
/**
 * 对公待签约产品检查
 * @author srxhx273
 *
 */
public class CorpSignedProductionChk extends AbstractBaseService implements IService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2436955516683955665L;
	/**
	 * 日志打印
	 */
	private static final Log log = LogFactory.getLog(CorpSignedProductionChk.class);
	/**
	 * ECIF对公签约关系查询：Rsp+原子服务码
	 */
	private static final String SIGN_QRY_YZ_SERVICE_ID = "Rsp2002300000402";
	
	@Override                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
	public String getId() {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
		return "CorpSignedProductionChk";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
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
			log.info("开始调用基础服务[CorpSignedProductionChk]......");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
		}
		//是否合法：0-是，1-否
		String isLegal = "1";
		String retCode = "BIP00T0001";//响应码
		String retMsg = "";//响应信息
		try {
			ICompositeData rspICD = (ICompositeData) SessionContext.getContext().getValue(SIGN_QRY_YZ_SERVICE_ID);
			if (null == rspICD){
				log.info("返回结构体["+SIGN_QRY_YZ_SERVICE_ID+"]为空");
				retMsg = "交易失败！";
				return null;
			}
			retCode = CompositeDataUtils.getValue(rspICD, "RspSysHead/RetCode");
			log.info("ECIF对公签约关系查询结果retCode=["+retCode+"]");
			if("000000".equals(retCode)){//交易成功
				
				/*
				 * signCodeList从本地数据库中只查询待签产品信息
				 * 可在基础服务CorpSignProductionParamsChk中将所有待签产品信息存到上下文里
				 */
				List<Production> signCodeList = (List<Production>)getContext().getValue("ProductionList");
				//返回笔数
				String resultSize = CompositeDataUtils.getValue(rspICD, "RspAppBody/ALL_RESULT_SIZE");
				if(resultSize != null && Integer.parseInt(resultSize) == 0){
					log.info("当前客户无签约记录");
					isLegal = "0";
					return null;
				}
				ICompositeData Req2002201000102 = (ICompositeData) SessionContext.getContext().getValue("Req2002201000102");
				
				String sOrganId = CompositeDataUtils.getValue(Req2002201000102, "ReqAppBody/ThirdDepMngSignReq/sOrganId");//证券编号
				String sCustAcct = CompositeDataUtils.getValue(Req2002201000102, "ReqAppBody/ThirdDepMngSignReq/sCustAcct");//证券资金账号
				
				/*
				 * 客户级判断只需要判断该客户是否已签约待签产品
				 * 账户级判断则需要判断该客户下这个账户是否已签约待签产品
				 * 1、此处需要判断待解约的产品是否已签约，任一待解约产品未签约，则业务结束
				 * 且给出以下提示：XX产品已签约，不能重复签约（其中，一卡多签应比较到唯一标识级，报错信息为“产品唯一标识XXXXXX已签约，不能重复签约”）
				 * retMsg = "XX产品已签约，不能重复签约"
				 * retMsg = "产品唯一标识XXXXXX已签约，不能重复签约"
				 * 2、待签产品与已签产品之前存在互斥则业务结束，报错信息“XX产品签约时，XX产品不能签约，存在互斥，请重新选择”
				 * retMsg = XX产品签约时，XX产品不能签约，存在互斥，请重新选择
				 */
				//签约关系查询返回数组GRP_SYNTHESIS_INFO信息
				List<ICompositeData> GRP_SYNTHESIS_INFO_ARRAY = CompositeDataUtils.getByPath(rspICD, "RspAppBody/GRP_SYNTHESIS_INFO");
				getContext().setValue("GRP_SYNTHESIS_INFO_ARRAY", GRP_SYNTHESIS_INFO_ARRAY);
				if(null == GRP_SYNTHESIS_INFO_ARRAY){
					log.info("当前客户无签约记录");
					isLegal = "0";
					return null;
				}
				//某一待签产品的互斥产品列表
				ArrayList<ExcludeInfo>  excludeInfoList = null;
				String SIGN_TYPE = "";//签约类型即签约产品代码
				String CONT_ACC = "";//唯一识别标志
				String SIGN_ACC = "";//签约凭证号
				String ReqAcctNo = CompositeDataUtils.getValue(Req2002201000102, "ReqAppBody/AcctNo");//上送签约帐号
				String AcctSrlNo = CompositeDataUtils.getValue(Req2002201000102, "ReqAppBody/AcctSrlNo");//上送账户序号
				String AcctTp = CompositeDataUtils.getValue(Req2002201000102, "ReqAppBody/AcctTp");//账户类型
				String AcctNo = "";//实际ECIF签约账号
				//若为天添利等组合账户产品，则账户需要加上AcctSrlNo	账户序号
				if("A".equals(AcctTp)){
					AcctNo = ReqAcctNo + AcctSrlNo;
				}else{
					AcctNo = ReqAcctNo;
				}
				for (ICompositeData GRP_SYNTHESIS_INFO : GRP_SYNTHESIS_INFO_ARRAY) {
					SIGN_TYPE = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFO,"SIGN_TYPE"); 
					CONT_ACC = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFO,"CONT_ACC");
					SIGN_ACC = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFO,"SIGN_ACC");
					for(Production product : signCodeList){
						if(product.getSignVarCd().equals(SIGN_TYPE)){
							//客户账户签约标志：0-客户1-账户
							if("0".equals(product.getCstAcctSignFlg())){//客户级
								log.info(SIGN_TYPE+"产品已签约，不能重复签约");
								retMsg = SIGN_TYPE+"产品已签约，不能重复签约";
								retCode = "BIP01E0002";
								return null;
							}else{//账户级
								if(AcctNo!=null && AcctNo.equals(SIGN_ACC)){
									//是否允许一卡多签:1-是0-否
									if("1".equals(product.getOneCdMulSignFlg())){
										//目前对公一卡多签只有三方存款
										if((sOrganId+"_"+sCustAcct).equals(CONT_ACC) && "000008".equals(SIGN_TYPE)){
											log.info(SIGN_TYPE+"产品唯一标识"+CONT_ACC+"已签约，不能重复签约");
											retMsg = SIGN_TYPE+"产品唯一标识"+CONT_ACC+"已签约，不能重复签约";
											retCode = "BIP01E0003";
											return null;
										}
									}else{
										log.info(SIGN_TYPE+"产品已签约，不能重复签约");
										retMsg = SIGN_TYPE+"产品已签约，不能重复签约";
										retCode = "BIP01E0002";
										return null;
									}
								}
							}
						}
						//判断已签产品与待签产品是否存在互斥
						excludeInfoList = product.getExcludeInfoList();
						if(AcctNo!=null && AcctNo.equals(SIGN_ACC) 
								&& excludeInfoList != null && excludeInfoList.contains(SIGN_TYPE)){
							log.info("已签约"+SIGN_TYPE+"产品，"+product.getSignVarCd()+"产品签约和该产品存在互斥，请核实");
							retMsg = "已签约"+SIGN_TYPE+"产品，"+product.getSignVarCd()+"产品签约和该产品存在互斥，请核实";
							retCode = "BIP01E0005";
							return null;
						}
					}
				}
				
				//检查通过则设置isLegal=0
				isLegal = "0";
			}else{
				retMsg = CompositeDataUtils.getValue(rspICD, "RspSysHead/RetMsg");
			}
			
			
		} catch (Exception e) {
			log.error("对公待签约产品检查异常", e);
			throw new InvokeException();
		}finally{
			getContext().setValue("isLegal", isLegal);
			
			if("1".equals(isLegal)){
				CompositeDataUtils.setReturn((String)getContext().getValue("ModifySignType"), "000000".equals(retCode)?"BIP00T0001":retCode, retMsg);
			}
			
			if (log.isInfoEnabled()) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
				log.info("调用基础服务结束[CorpSignedProductionChk]......");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
		return null;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
	}
}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               

	


