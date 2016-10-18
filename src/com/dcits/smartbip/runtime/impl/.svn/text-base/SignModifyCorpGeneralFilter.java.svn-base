package com.dcits.smartbip.runtime.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.runtime.model.impl.SoapCompositeData;
import com.dcits.smartbip.utils.CompositeDataUtils;

public class SignModifyCorpGeneralFilter extends AbstractBaseService implements IService {

	/**
	 * 获取ECIF返回签约状态List：SIGN_ACC_TYPE|SIGN_ACC|SIGN_TYPE|SIGN_STAT
	 * 通过请求对象中的AcctTp|AcctNo|PrivExchgSignProCdArray.ExchgSignProTp来过滤对应的签约状态,老账户必须签约，新账户必须解约
	 * 返回换签的产品代码
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(SignModifyCorpGeneralFilter.class);

	@Override
	public String getId() {
		return "SignModifyCorpGeneralFilter";
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
			log.info("开始调用基础服务[SignModifyCorpGeneralFilter]......");
		}
		
		String modifySignType = (String)SessionContext.getContext().getValue("ModifySignType");
		
		ICompositeData ReqModifySignType = (ICompositeData) SessionContext
				.getContext().getValue("Req" +modifySignType);
		
		/** 获取换签种类**/

		String AcctTp = (String)CompositeDataUtils.getValue(ReqModifySignType, "ReqAppBody/AcctTp");
		String AcctNo = (String)CompositeDataUtils.getValue(ReqModifySignType, "ReqAppBody/AcctNo");
		
		String NewAcctTp = (String)CompositeDataUtils.getValue(ReqModifySignType, "ReqAppBody/NewAcctTp");
		String NewAcctNo = (String)CompositeDataUtils.getValue(ReqModifySignType, "ReqAppBody/NewAcctNo");
		//返回的可签约的产品代码
		List<String> exchgSignProCodes = new ArrayList<String>();
		List<String> oexchgSignProCodes = new ArrayList<String>();
		List<String> nexchgSignProCodes = new ArrayList<String>();
		
		List<ICompositeData> corpExchgSignProCdArray = CompositeDataUtils.getByPath(
				ReqModifySignType, "ReqAppBody/CorpExchgSignProCdArray");
		
		if(corpExchgSignProCdArray.size() == 0){
			if(log.isDebugEnabled()){
				log.debug("请求换签产品代码为空，请核实！");
			}
			setErrorCode("BIP00E0000","请求换签产品代码为空，请核实！");
			return "请求换签产品代码为空，请核实！";
		}
			
		for (ICompositeData corpExchgSignComposite : corpExchgSignProCdArray) {
				StringBuilder oprivExchgsignsbd = new StringBuilder();
				StringBuilder nprivExchgsignsbd = new StringBuilder();
				String exchgSignProTp = CompositeDataUtils.getValue(corpExchgSignComposite,
						"ExchgSignProTp");
				oprivExchgsignsbd.append(AcctTp).append("#").append(AcctNo).append("#").append(exchgSignProTp);
				nprivExchgsignsbd.append(NewAcctTp).append("#").append(NewAcctNo).append("#").append(exchgSignProTp);
				//旧账户
				oexchgSignProCodes.add(oprivExchgsignsbd.toString());
				//新账户
				nexchgSignProCodes.add(nprivExchgsignsbd.toString());
			}
			
		/** 获取换签种类完成**/
		
		/** 获取目标产品状态**/
		ICompositeData rsp2002300000402 = (ICompositeData) SessionContext
		.getContext().getValue("Rsp2002300000402");
		List<String> grpSynthesisInfos = new ArrayList<String>();
		Map<String,String> grpSynthesisInfoMap = new HashMap<String,String>();
		
		List<ICompositeData> grpSynthesisInfoArray = CompositeDataUtils.getByPath(
				rsp2002300000402, "RspAppBody/GRP_SYNTHESIS_INFO");
		
		String resultSize = CompositeDataUtils.getValue(rsp2002300000402, "RspAppBody/RESULT_SIZE");
		String AllResultSize = CompositeDataUtils.getValue(rsp2002300000402, "RspAppBody/ALL_RESULT_SIZE");
//		根据返回条数进行分页查询处理,根据rsp2002300000402的返回判断是否继续发送交易rsp2002300000402
		if(log.isDebugEnabled()){
			log.debug("实际总记录数[" + AllResultSize + "]");
			log.debug("返回的签约记录数[" + resultSize + "]");
		}
		for (ICompositeData grpSynthesisInfo : grpSynthesisInfoArray) {
			StringBuilder grpSynthesisInfosbd = new StringBuilder();
			//签约凭证类型
			String signAccType = CompositeDataUtils.getValue(grpSynthesisInfo,"SIGN_ACC_TYPE");
			//签约凭证号
			String signAcc = CompositeDataUtils.getValue(grpSynthesisInfo,"SIGN_ACC");
			//签约类型
			String signType = CompositeDataUtils.getValue(grpSynthesisInfo,"SIGN_TYPE");
			//签约状态
			String signStat = CompositeDataUtils.getValue(grpSynthesisInfo,"SIGN_STAT");
			grpSynthesisInfosbd.append(signAccType).append("#").append(signAcc).append("#").append(signType);
			//目标签约数组
			grpSynthesisInfos.add(grpSynthesisInfosbd.toString());
			grpSynthesisInfoMap.put(grpSynthesisInfosbd.toString(), signStat);
		}
		if (log.isDebugEnabled()) {
			for (String grpSynthesisInfo : grpSynthesisInfos) {
				log.debug("目标客户关联签约属性[账户类型]#[账号]#[换签产品代码]:["+grpSynthesisInfo + "]");
			}
		}
		
		//开始判断，新的不签约，旧的必须签约
		for (String oexchgSignProCode : oexchgSignProCodes) {
			log.debug("旧帐号签约属性[账户类型]#[账号]#[换签产品代码]:"+oexchgSignProCode );
			//查询参考map中是否含有需要签约的
			if(grpSynthesisInfoMap.containsKey(oexchgSignProCode)){
				//在查询数组中，已有签约
				if(grpSynthesisInfoMap.get(oexchgSignProCode) != null){
					//老账号必须签约,签约产品代码
					String SignProCode = StringUtils.split(oexchgSignProCode, "#")[2];
					//签约状态
					log.debug("旧帐号["+ oexchgSignProCode +"]签约状态["+grpSynthesisInfoMap.get(oexchgSignProCode) + "],满足换签条件");
					//判断新的不能签约，判断的应该同一产品代码的新帐号是否未签约
					for (String nexchgSignProCode : nexchgSignProCodes) {
						log.debug("新帐号签约属性[账户类型]#[账号]#[换签产品代码]:"+nexchgSignProCode);
						//先判断同产品代码的
						if(SignProCode.equalsIgnoreCase(StringUtils.split(nexchgSignProCode, "#")[2])){
							if(grpSynthesisInfoMap.get(nexchgSignProCode) == null){
								log.debug("新帐号["+ nexchgSignProCode +"]未签约[" + SignProCode + "],满足换签条件");
								exchgSignProCodes.add(SignProCode);
							}else{
								log.debug("新帐号["+nexchgSignProCode+"]已签约,不能换签!");
								setErrorCode("BP9999","新帐号["+nexchgSignProCode+"]已签约,不能换签!");
							}
						}
					}
				}else{
					setErrorCode("BP9999","旧帐号["+oexchgSignProCode+"]未签约,不能换签!");
					log.debug("旧帐号["+oexchgSignProCode+"]存在记录,但是没有任何签约状态!");
				}
			}else{
				setErrorCode("BP9999","旧帐号["+oexchgSignProCode+"]未签约,不能换签!");
				log.debug("旧帐号["+oexchgSignProCode+"]未签约,不能换签!");
			}
		}
		getContext().setValue("exchgSignProCodes", exchgSignProCodes);
		
		if(log.isDebugEnabled()){
			for(String destexchgSignProCode : exchgSignProCodes){
				log.debug("可签约产品代码["+destexchgSignProCode + "]");
			}
		}
		/** 获取目标产品状态**/
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[SignModifyCorpGeneralFilter]......");
		}
		return null;
	}
	
	private void setErrorCode(String retCode,String retMsg)
	{
		String modifySignType = (String)SessionContext.getContext().getValue("ModifySignType");
		ICompositeData RspmodifySignType = (ICompositeData) SessionContext.getContext().getValue("Rsp"+modifySignType); 
		
		if (null == RspmodifySignType)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			RspmodifySignType  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			RspmodifySignType.setId("Rsp"+modifySignType);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			RspmodifySignType.setxPath("/Rsp"+modifySignType);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			getContext().setValue("Rsp"+modifySignType,RspmodifySignType);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		} 
		
		CompositeDataUtils.setValue(RspmodifySignType, "RspSysHead/TransStatus", "F");
		CompositeDataUtils.setValue(RspmodifySignType, "RspSysHead/RetCode", retCode);
		CompositeDataUtils.setValue(RspmodifySignType, "RspSysHead/RetMsg", retMsg);
	}

}
