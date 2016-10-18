package com.dcits.smartbip.runtime.impl;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.ErrorCodes;
import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.entity.Production;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.utils.CompositeDataUtils;
import com.dcits.smartbip.utils.PropertiesUtils;
import com.dcits.smartbip.utils.XMLUtils;

/**
 * 对私综合换签参数检查
 * 获取ECIF返回签约状态List：SIGN_ACC_TYPE|SIGN_ACC|SIGN_TYPE|SIGN_STAT
 * 通过请求对象中的AcctTp|AcctNo|PrivExchgSignProCdArray.ExchgSignProTp来过滤对应的签约状态,老账户必须签约，新账户必须解约
 * 返回换签的产品代码
 * @author srxhx273
 *
 */
public class SignModifyGeneralFilter extends AbstractBaseService implements IService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7344407826696211415L;
	/**
	 * 
	 */
	private static final Log log = LogFactory.getLog(SignModifyGeneralFilter.class);
	@Override
	public String getId() {
		return "SignModifyGeneralFilter";
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
			log.info("开始调用基础服务[SignModifyGeneralFilter]......");
		}
		
		String modifySignType = (String)SessionContext.getContext().getValue("ModifySignType");
		
		ICompositeData ReqModifySignType = (ICompositeData) SessionContext
				.getContext().getValue("Req" +modifySignType);
		String succ_flag = "1";
		getContext().setValue("MSIGN_SUCC_FLAG", succ_flag); //默认参数校验成功
		try {
			String CstNo = (String)CompositeDataUtils.getValue(ReqModifySignType, "ReqAppBody/CstNo");
			/** 获取换签种类**/
			String AcctTp = (String)CompositeDataUtils.getValue(ReqModifySignType, "ReqAppBody/AcctTp");
			String AcctNo = (String)CompositeDataUtils.getValue(ReqModifySignType, "ReqAppBody/AcctNo");
			
			String NewAcctTp = (String)CompositeDataUtils.getValue(ReqModifySignType, "ReqAppBody/NewAcctTp");
			String NewAcctNo = (String)CompositeDataUtils.getValue(ReqModifySignType, "ReqAppBody/NewAcctNo");
			/**
			 * 获取该客户号下的所有帐号信息，并对其新旧帐号进行校验，看是否在该客户号下
			 */
			int sameCustFlag = 0; //同一个客户下标志：0-否，1-是（同一客户）
			ICompositeData Rsp3001300000103 = (ICompositeData) SessionContext.getContext().getValue("Rsp3001300000103");
			if (null == Rsp3001300000103){                          
				log.error("调用后端服务3001300000103返回结果为空!");
				succ_flag = "0";
				getContext().setValue("MSIGN_SUCC_FLAG", succ_flag);
				CompositeDataUtils.setReturn(modifySignType, ErrorCodes.CODE_BIP00T0001, "客户下账户概要信息查询失败");
				return null;
			}
			String retCode = CompositeDataUtils.getValue(Rsp3001300000103, "RspSysHead/RetCode");
			String retMsg = CompositeDataUtils.getValue(Rsp3001300000103, "RspSysHead/RetMsg");
			if("000000".equals(retCode)){//客户下账户概要信息查询交易成功
				
				List<ICompositeData> fzzhxxmcArray = CompositeDataUtils.getByPath(Rsp3001300000103, "RspAppBody/fzzhxxmc");
				if(fzzhxxmcArray == null || fzzhxxmcArray.size() == 0 ){
					log.debug("该客户下无账户记录");
					succ_flag = "0";
					getContext().setValue("MSIGN_SUCC_FLAG", succ_flag);
					CompositeDataUtils.setReturn(modifySignType, ErrorCodes.CODE_BIP03E0002, "该客户下无账户记录");
					return null;
				}
				List<String> AcctNoList = new ArrayList<String>();
				for (ICompositeData fzzhxxmc : fzzhxxmcArray) {
					String kehuzhao = CompositeDataUtils.getValue(fzzhxxmc,"kehuzhao");
					if(kehuzhao != null && !"".equals(kehuzhao.trim())){
						AcctNoList.add(kehuzhao);
					}else{
						if(log.isWarnEnabled()){
							log.warn("返回无账户记录");
						}
					}
				}
				if(AcctNoList.contains(AcctNo) && AcctNoList.contains(NewAcctNo)){
					sameCustFlag= 1;
				}
				//只允许同一客户换签
				if(sameCustFlag == 1){
					
					//返回的可换签的产品代码
					List<String> exchgSignProCodes = new ArrayList<String>();
					//上送老账号签约产品代码列表信息
					List<String> oexchgSignProCodes = new ArrayList<String>();
					List<String> nexchgSignProCodes = new ArrayList<String>();
					
					//前端上送待换签产品代码列表
					List<ICompositeData> privExchgSignProCdArray = CompositeDataUtils.getByPath(
							ReqModifySignType, "ReqAppBody/PrivExchgSignProCdArray");
					
					if(privExchgSignProCdArray == null || privExchgSignProCdArray.size() == 0){
						log.debug("待换签产品代码未上送!");
						succ_flag = "0";
						getContext().setValue("MSIGN_SUCC_FLAG", succ_flag);
						CompositeDataUtils.setReturn(modifySignType,ErrorCodes.CODE_BIP03E0000,"待换签产品代码未上送");
						return null;
					}
					String exchgSignProTp = "";
					Map<String, Production> signCodeMap = XMLUtils.getSignCodeInfo("0");//全部对私产品信息
					getContext().setValue("SignCodeMap", signCodeMap);
					Map<String, String> dliywhMap = PropertiesUtils.getProductionAndDLIYWH();
					StringBuilder oprivExchgsignsbd = null;
					StringBuilder nprivExchgsignsbd = null;
					Production product = null;
					String channelId = CompositeDataUtils.getValue(ReqModifySignType, "ReqSysHead/ChannelID");
					
					for (ICompositeData privExchgsignComposite : privExchgSignProCdArray) {
						exchgSignProTp = CompositeDataUtils.getValue(privExchgsignComposite,"ExchgSignProTp");
						if(exchgSignProTp == null || "".equals(exchgSignProTp)){
							log.info("遍历产品代码ExchgSignProTp失败!exchgSignProTp="+exchgSignProTp);
							succ_flag = "0";
							getContext().setValue("MSIGN_SUCC_FLAG", succ_flag);
							CompositeDataUtils.setReturn(modifySignType,ErrorCodes.CODE_BIP03E0000,"待换签产品代码未上送");
							return null;
						}
						if(!exchgSignProCodes.contains(exchgSignProTp)){
							exchgSignProCodes.add(exchgSignProTp);
						}else{
							log.debug("换签产品"+exchgSignProTp+"已处理");
							continue;
						}
						oprivExchgsignsbd = new StringBuilder();
						nprivExchgsignsbd = new StringBuilder();
						if(!signCodeMap.containsKey(exchgSignProTp)){
							log.info(exchgSignProTp+"产品不存在，请核实");
							succ_flag = "0";
							getContext().setValue("MSIGN_SUCC_FLAG", succ_flag);
							CompositeDataUtils.setReturn(modifySignType,ErrorCodes.CODE_BIP00T0001,exchgSignProTp+"产品不存在，请核实");
							return null;
						}
						product = signCodeMap.get(exchgSignProTp);
						
						//不支持客户级别换签 
						if("0".equals(product.getCstAcctSignFlg())){
							log.info("不支持客户级产品"+exchgSignProTp+"的换签，请核实");
							succ_flag = "0";
							getContext().setValue("MSIGN_SUCC_FLAG", succ_flag);
							CompositeDataUtils.setReturn(modifySignType, ErrorCodes.CODE_BIP00T0001, "不支持客户级产品"+exchgSignProTp+"的换签，请核实");
							return null;
						}
						if(!"1".equals(product.getAvlMntnFlg())){
							log.info(exchgSignProTp+"产品不允许维护，请核实");
							succ_flag = "0";
							getContext().setValue("MSIGN_SUCC_FLAG", succ_flag);
							CompositeDataUtils.setReturn(modifySignType,ErrorCodes.CODE_BIP00T0001,exchgSignProTp+"产品不允许维护，请核实");
							return null;
						}
						if(!"1".equals(product.getAvlTrsfrFlg())){
							log.info(exchgSignProTp+"产品不允许签约关系转移，请核实");
							succ_flag = "0";
							getContext().setValue("MSIGN_SUCC_FLAG", succ_flag);
							CompositeDataUtils.setReturn(modifySignType,ErrorCodes.CODE_BIP00T0001,exchgSignProTp+"产品不允许签约关系转移，请核实");
							return null;
						}
						if("1".equals(product.getAvlSignExchgChlFlg())
								&& !product.getAvlSignExchgChlList().contains(channelId)){
							log.info(exchgSignProTp+"产品不能通过本渠道换签，请核实");
							succ_flag = "0";
							getContext().setValue("MSIGN_SUCC_FLAG", succ_flag);
							CompositeDataUtils.setReturn(modifySignType,ErrorCodes.CODE_BIP00T0001,exchgSignProTp+"产品不能通过本渠道换签，请核实");
							return null;
						}
						oprivExchgsignsbd.append(AcctTp).append("#").append(AcctNo).append("#").append(exchgSignProTp);
						nprivExchgsignsbd.append(NewAcctTp).append("#").append(NewAcctNo).append("#").append(exchgSignProTp);
						if("1".equals(product.getOneCdMulSignFlg())){//一卡多签
							String contAcc = "";//上送唯一标志
							if("000005".equals(exchgSignProTp)){//信用卡自动还款
								List<ICompositeData> CrCardArray = CompositeDataUtils.getByPath(
										ReqModifySignType, "ReqAppBody/CrCdAutoRepayExchgSignReq/CrCardArray");
								if(CrCardArray == null || CrCardArray.size() == 0){
									log.info("上送信用卡卡号数组为空");
									succ_flag = "0";
									getContext().setValue("MSIGN_SUCC_FLAG", succ_flag);
									CompositeDataUtils.setReturn(modifySignType,ErrorCodes.CODE_BIP00T0001,"信用卡卡号数组，请核实");
									return null;
								}
								StringBuilder oprivExchgsignsbd_xyk = null;
								StringBuilder nprivExchgsignsbd_xyk = null;
								for(ICompositeData card : CrCardArray){
									oprivExchgsignsbd_xyk = new StringBuilder();
									nprivExchgsignsbd_xyk = new StringBuilder();
									contAcc = CompositeDataUtils.getValue(card, "KAHAOO");
									oprivExchgsignsbd_xyk.append(oprivExchgsignsbd).append("#").append(contAcc);
									nprivExchgsignsbd_xyk.append(nprivExchgsignsbd).append("#").append(contAcc);
									//旧账户
									oexchgSignProCodes.add(oprivExchgsignsbd_xyk.toString());
									//新账户
									nexchgSignProCodes.add(nprivExchgsignsbd_xyk.toString());
								}
							}else if("000008".equals(exchgSignProTp)){//第三方存管
								List<ICompositeData> ThirdDepMngFlgArray = CompositeDataUtils.getByPath(
										ReqModifySignType, "ReqAppBody/ThirdDepMngExchgSignReq/ThirdDepMngFlgArray");
								if(ThirdDepMngFlgArray == null || ThirdDepMngFlgArray.size() == 0){
									log.info("上送三存唯一标识数组为空");
									succ_flag = "0";
									getContext().setValue("MSIGN_SUCC_FLAG", succ_flag);
									CompositeDataUtils.setReturn(modifySignType,ErrorCodes.CODE_BIP00T0001,"三存唯一标识数组为空，请核实");
									return null;
								}
								StringBuilder oprivExchgsignsbd_sc = null;
								StringBuilder nprivExchgsignsbd_sc = null;
								for(ICompositeData thirdFlg : ThirdDepMngFlgArray){
									oprivExchgsignsbd_sc = new StringBuilder();
									nprivExchgsignsbd_sc = new StringBuilder();
									contAcc = CompositeDataUtils.getValue(thirdFlg, "sOrganId")+"_"+CompositeDataUtils.getValue(thirdFlg, "sCustAcct");
									oprivExchgsignsbd_sc.append(oprivExchgsignsbd).append("#").append(contAcc);
									nprivExchgsignsbd_sc.append(nprivExchgsignsbd).append("#").append(contAcc);
									//旧账户
									oexchgSignProCodes.add(oprivExchgsignsbd_sc.toString());
									//新账户
									nexchgSignProCodes.add(nprivExchgsignsbd_sc.toString());
								}
							}else if(dliywhMap.containsKey(exchgSignProTp)){//代缴费
								String dilywh = "";
								List<ICompositeData> DirectDebitExchgSignArray = CompositeDataUtils.getByPath(
										ReqModifySignType, "ReqAppBody/DirectDebitExchgSignReq/DirectDebitExchgSignArray");
								if(DirectDebitExchgSignArray == null || DirectDebitExchgSignArray.size() == 0){
									log.info("上送代缴费业务换签数组为空");
									succ_flag = "0";
									getContext().setValue("MSIGN_SUCC_FLAG", succ_flag);
									CompositeDataUtils.setReturn(modifySignType,ErrorCodes.CODE_BIP00T0001,"代缴费业务换签数组为空，请核实");
									return null;
								}
								StringBuilder oprivExchgsignsbd_djf = null;
								StringBuilder nprivExchgsignsbd_djf = null;
								for(ICompositeData djfICD : DirectDebitExchgSignArray){
									dilywh = CompositeDataUtils.getValue(djfICD, "DLIYWH");
									if(dilywh != null && dilywh.equals(dliywhMap.get(exchgSignProTp))){
										List<ICompositeData> ThirdUserIdArray = CompositeDataUtils.getByPath(djfICD,"ThirdUserIdArray");
										if(ThirdUserIdArray == null || ThirdUserIdArray.size() == 0){
											log.info("上送第三方用户编号为空");
											succ_flag = "0";
											getContext().setValue("MSIGN_SUCC_FLAG", succ_flag);
											CompositeDataUtils.setReturn(modifySignType,ErrorCodes.CODE_BIP00T0001,"代缴费第三方用户编号为空，请核实");
											return null;
										}
										for(ICompositeData thirdUserId : ThirdUserIdArray){
											oprivExchgsignsbd_djf = new StringBuilder();
											nprivExchgsignsbd_djf = new StringBuilder();
											contAcc = CompositeDataUtils.getValue(thirdUserId, "DSYHBH");
											oprivExchgsignsbd_djf.append(oprivExchgsignsbd).append("#").append(contAcc);
											nprivExchgsignsbd_djf.append(nprivExchgsignsbd).append("#").append(contAcc);
											//旧账户
											oexchgSignProCodes.add(oprivExchgsignsbd_djf.toString());
											//新账户
											nexchgSignProCodes.add(nprivExchgsignsbd_djf.toString());
										}
									}
								}
							}
						}else{
							//旧账户
							oexchgSignProCodes.add(oprivExchgsignsbd.toString());
							//新账户
							nexchgSignProCodes.add(nprivExchgsignsbd.toString());
						}
					}
					
					if(exchgSignProCodes.size() == 0){
						log.info("换签产品代码exchgSignProCodes为空，请核查产品检查流程");
						succ_flag = "0";
						getContext().setValue("MSIGN_SUCC_FLAG", succ_flag);
						CompositeDataUtils.setReturn(modifySignType,ErrorCodes.CODE_BIP00T0001,"对私综合签约账户绑定关系变更失败，请核实");
						return null;
					}
					
					if(log.isDebugEnabled()){
						log.info("前端上送BIP去重后的换签产品代码列表["+exchgSignProCodes + "]");
					}
					
					/** 获取目标产品状态**/
					ICompositeData rsp2002300000401 = (ICompositeData) SessionContext
					.getContext().getValue("Rsp2002300000401");
					if (null == rsp2002300000401){                          
						log.error("调用后端服务2002300000401返回结果为空!");
						succ_flag = "0";
						getContext().setValue("MSIGN_SUCC_FLAG", succ_flag);
						CompositeDataUtils.setReturn(modifySignType, ErrorCodes.CODE_BIP00T0001, "ECIF对私签约关系查询失败");
						return null;
					}
					String retCodeECIF = CompositeDataUtils.getValue(rsp2002300000401, "RspSysHead/RetCode");
					String retMsgECIF = CompositeDataUtils.getValue(rsp2002300000401, "RspSysHead/RetMsg");;
					if("000000".equals(retCodeECIF)){//ECIF对私签约关系查询成功
						
						List<String> grpSynthesisInfos = new ArrayList<String>();
						
						List<ICompositeData> grpSynthesisInfoArray = CompositeDataUtils.getByPath(
								rsp2002300000401, "RspAppBody/GRP_SYNTHESIS_INFO");
						
						String AllResultSize = CompositeDataUtils.getValue(rsp2002300000401, "RspAppBody/ALL_RESULT_SIZE");
//				根据返回条数进行分页查询处理,根据2002300000401的返回判断是否继续发送交易2002300000401
						log.debug("返回的签约记录数[" + AllResultSize + "]");
						if("0".equals(AllResultSize) || grpSynthesisInfoArray == null 
								|| grpSynthesisInfoArray.size() == 0){
							log.info("该客户"+CstNo+"无签约记录");
							succ_flag = "0";
							getContext().setValue("MSIGN_SUCC_FLAG", succ_flag);
							CompositeDataUtils.setReturn(modifySignType,ErrorCodes.CODE_BIP00T0001,"该客户"+CstNo+"无签约记录");
							return null;
						}
						//签约凭证类型
						String signAccType = "";
						//签约凭证号
						String signAcc = "";
						//签约类型
						String signType = "";
						//唯一识别标志
						String CONT_ACC = "";
						StringBuilder grpSynthesisInfosbd = null;
						List<String> jiJinCodeList = getJiJinCode();
						
						List<String> jiJinCodeToECIF = new ArrayList<String>();//换签账户下已签约基金类产品
						List<String> newAccSignedCode = new ArrayList<String>();//新账户已签约产品
						for (ICompositeData grpSynthesisInfo : grpSynthesisInfoArray) {
							grpSynthesisInfosbd = new StringBuilder();
							signAccType = CompositeDataUtils.getValue(grpSynthesisInfo,"SIGN_ACC_TYPE");
							signAcc = CompositeDataUtils.getValue(grpSynthesisInfo,"SIGN_ACC");
							signType = CompositeDataUtils.getValue(grpSynthesisInfo,"SIGN_TYPE");
							CONT_ACC = CompositeDataUtils.getValue(grpSynthesisInfo,"CONT_ACC");
							grpSynthesisInfosbd.append(signAccType).append("#").append(signAcc).append("#").append(signType);
							product = signCodeMap.get(signType);
							if("1".equals(product.getOneCdMulSignFlg())){
								if(CONT_ACC != null && !"".equals(CONT_ACC.trim())){
									grpSynthesisInfosbd.append("#").append(CONT_ACC);
								}
							}
							
							//目标签约数组
							grpSynthesisInfos.add(grpSynthesisInfosbd.toString());
							if(AcctNo != null && AcctNo.equals(signAcc) 
									&& jiJinCodeList.contains(signType)){
								jiJinCodeToECIF.add(signType);
							}
							if(NewAcctNo.equals(signAcc)){
								newAccSignedCode.add(signType);
							}
						}
						log.debug("目标客户关联签约属性[账户类型]#[账号]#[换签产品代码]#[唯一标识]:["+grpSynthesisInfos + "]");
						
						
						String SignProCode = "";
						//判断换签产品与老账户是否存在签约关系
						for (String oexchgSignProCode : oexchgSignProCodes) {
							log.debug("旧帐号签约属性[账户类型]#[账号]#[换签产品代码]:"+oexchgSignProCode );
							//老账号签约产品代码
							SignProCode = StringUtils.split(oexchgSignProCode, "#")[2];
							if(grpSynthesisInfos.contains(oexchgSignProCode)){
								log.info("旧帐号["+ oexchgSignProCode +"]已签约[" + SignProCode + "],满足换签条件");
							}else{
								log.debug(SignProCode+"产品在"+AcctNo+"账户上不存在签约关系");
								succ_flag = "0";
								getContext().setValue("MSIGN_SUCC_FLAG", succ_flag);
								CompositeDataUtils.setReturn(modifySignType, ErrorCodes.CODE_BIP00T0001, SignProCode+"产品在"+AcctNo+"账户上不存在签约关系");
								return null;
							}
						}
						
						//判断新账户与待换签产品的签约关系是否已存在
						for (String nexchgSignProCode : nexchgSignProCodes) {
							log.debug("新帐号签约属性[账户类型]#[账号]#[换签产品代码]:"+nexchgSignProCode);
							//新账号签约产品代码
							SignProCode = StringUtils.split(nexchgSignProCode, "#")[2];
							if(!grpSynthesisInfos.contains(nexchgSignProCode)){
								log.info("新帐号["+ nexchgSignProCode +"]未签约[" + SignProCode + "],满足换签条件");
							}else{
								log.info(NewAcctNo+"账号已签约"+SignProCode+"产品，该产品不能换签，请核实");
								succ_flag = "0";
								getContext().setValue("MSIGN_SUCC_FLAG", succ_flag);
								CompositeDataUtils.setReturn(modifySignType,ErrorCodes.CODE_BIP00T0001,NewAcctNo+"账号已签约"+SignProCode+"产品，该产品不能换签，请核实");
								return null;
							}
						}
						
						//判断换签产品与新账户已签产品是否存在互斥
						if(newAccSignedCode.size() > 0){
							Production production = null;
							for(String signCode : exchgSignProCodes){//待换签产品
								production = signCodeMap.get(signCode);//待换签产品信息
								for(String signedCode : newAccSignedCode){//新账户已签约产品
									if(production.getExcludeInfoList().contains(signedCode)){
										log.info(NewAcctNo+"账号已签约"+signedCode+"产品，换签产品"+signCode+"和该产品互斥，不能换签，请核实");
										succ_flag = "0";
										getContext().setValue("MSIGN_SUCC_FLAG", succ_flag);
										CompositeDataUtils.setReturn(modifySignType,ErrorCodes.CODE_BIP00T0001,NewAcctNo+"账号已签约"+signedCode+"产品，换签产品"+signCode+"和该产品互斥，不能换签，请核实");
										return null;
									}
								}
							}
						}
						boolean jjFlag = false;//本次换签是否存在基金类产品
						//基金类产品（鑫钱宝、鑫元宝、基金）换签只当基金换签去理财系统去换签即可
						for(String jiJinCode : jiJinCodeList){
							if(exchgSignProCodes.contains(jiJinCode)){
								jjFlag = true;
								if(!"000020".equals(jiJinCode)){//非基金产品的其他基金类产品都移除
									exchgSignProCodes.remove(jiJinCode);
								}
								if(!exchgSignProCodes.contains("000020")){
									exchgSignProCodes.add("000020");
								}
							}
						}
						getContext().setValue("exchgSignProCodes", exchgSignProCodes);
						if(log.isDebugEnabled()){
							log.info("前端上送BIP去重、基金类特殊处理后的换签产品代码列表["+exchgSignProCodes + "]");
						}
						if(jjFlag){//存在基金类产品，则需要换签所有客户该账户下已签基金类产品
							getContext().setValue("JiJinCodeToECIF", jiJinCodeToECIF);
						}
						if("1".equals(NewAcctTp)){
							//是否为卡标志:1-是，0-否
							getContext().setValue("isCardFlag", "1");
						}else{
							getContext().setValue("isCardFlag", "0");
						}
					}else{//ECIF对私签约关系查询失败
						log.info("原子服务2002300000401交易失败，错误码["+retCodeECIF+"]，错误信息["+retMsgECIF+"]");
						succ_flag = "0";
						getContext().setValue("MSIGN_SUCC_FLAG", succ_flag);
						CompositeDataUtils.setReturn(modifySignType, retCodeECIF, retMsgECIF!=null?retMsgECIF:"ECIF对私签约关系查询失败");
						return null;
					}
					
				}else{
					if(!AcctNoList.contains(AcctNo)){
						log.info(CstNo+"客户无"+AcctNo+"账号");
						retMsg = CstNo+"客户无"+AcctNo+"账号";
					}else if(!AcctNoList.contains(NewAcctNo)){
						log.info(CstNo+"客户无"+NewAcctNo+"账号");
						retMsg = CstNo+"客户无"+NewAcctNo+"账号";
					}
					succ_flag = "0";
					getContext().setValue("MSIGN_SUCC_FLAG", succ_flag);
					CompositeDataUtils.setReturn(modifySignType, ErrorCodes.CODE_BIP03E0002, retMsg);
					return null;
				}
			}else{//交易
				log.info("原子服务3001300000103交易失败，错误码["+retCode+"]，错误信息["+retMsg+"]");
				succ_flag = "0";
				getContext().setValue("MSIGN_SUCC_FLAG", succ_flag);
				CompositeDataUtils.setReturn(modifySignType, retCode, retMsg!=null?retMsg:"客户下账户概要信息查询失败");
			}
			
		} catch (Exception e) {
			log.error("对私综合签约账户绑定关系变更异常：",e);
			succ_flag = "0";
			getContext().setValue("MSIGN_SUCC_FLAG", succ_flag);
			CompositeDataUtils.setReturn(modifySignType, ErrorCodes.CODE_BIP00T0001, "对私综合签约账户绑定关系变更失败");
		}
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[SignModifyGeneralFilter]......");
		}
		return null;
	}

	private List<String> getJiJinCode(){
		List<String> list = new ArrayList<String>();
		list.add("000009");//鑫元宝
		list.add("000010");//鑫钱宝
		list.add("000020");//基金
		return list;
	}
}
