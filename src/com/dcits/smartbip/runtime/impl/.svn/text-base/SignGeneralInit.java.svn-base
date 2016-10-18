package com.dcits.smartbip.runtime.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.ErrorCodes;
import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.runtime.model.impl.SoapCompositeData;
import com.dcits.smartbip.utils.CompositeDataUtils;
/**
 * 对私综合签约
 * 获取签约约产品代码
 * @author srxhx611
 *
 */
public class SignGeneralInit extends AbstractBaseService implements IService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5503491251271499160L;
	private static final Log log = LogFactory.getLog(SignGeneralInit.class);

	@Override
	public String getId() {
		return "SignGeneralInit";
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
			log.info("开始调用基础服务[SignGeneralInit]......");
		}
		initProductList();

		// 获得综合签约请求数据对象
		ICompositeData Req2002201000101 = (ICompositeData) SessionContext
				.getContext().getValue("Req2002201000101");
		List<String> signCodeTypes = new ArrayList<String>();
		List<ICompositeData> signProCdArray = CompositeDataUtils.getByPath(
				Req2002201000101, "ReqAppBody/PrivSignProCdArray");
		
		//初始化RspSysHead的返回信息
		ICompositeData Rsp2002201000101 = (ICompositeData) SessionContext
		.getContext().getValue("Rsp2002201000101");
		if (null == Rsp2002201000101)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			Rsp2002201000101  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			Rsp2002201000101.setId("Rsp2002201000101");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			Rsp2002201000101.setxPath("/Rsp2002201000101");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			getContext().setValue("Rsp2002201000101",Rsp2002201000101);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		}
		
		//syshead初始化
		setSysHead(Req2002201000101,Rsp2002201000101,"000000", "通讯成功,产品签约结果详见结果数组", "S");
		
		for (ICompositeData signComposite : signProCdArray) {
			String signCodeType = CompositeDataUtils.getValue(signComposite,
					"SignProTp");
			signCodeTypes.add(signCodeType);
			//个人网银/手机银行签约特殊处理
			if("000001".equals(signCodeType)||"000002".equals(signCodeType)){
				  SessionContext.getContext().setValue("PB-MBResult", "true");
				  if("000001".equals(signCodeType))
					  SessionContext.getContext().setValue("PBResult", "true");
				  else
					  SessionContext.getContext().setValue("MBResult", "true");
	         }
		}
		SessionContext.getContext().setValue("SignCodeTypesNull", "false");
		//信用卡自动还款初始化操作标志为签约
	    SessionContext.getContext().setValue("XYCZBZ_DUISI", "3");
	    //ATM跨行转账初始化操作标志为签约
	    SessionContext.getContext().setValue("JIOYBZ_DUISI", "0");
	    //易得利初始化操作标志为签约
	    SessionContext.getContext().setValue("qywhbzhi_DUISI", "1");
		//同时发起鑫钱宝和基金签约时，做个标志基金未签约
		SessionContext.getContext().setValue("JijinAlreadySign_duisi", "false");
		SessionContext.getContext().setValue("Jijin_Xinqianbao", "false");
		SessionContext.getContext().setValue("sign_xinqianbao", "true");
		if(null == signCodeTypes || signCodeTypes.size() ==0){
			SessionContext.getContext().setValue("SignCodeTypesNull", "true");
			CompositeDataUtils.setReturn(Rsp2002201000101,ErrorCodes.CODE_BIP00T0001,"请求签约产品代码为空，请核实！");
		}else if (signCodeTypes.contains("000020") && signCodeTypes.contains("000010")){
			Collections.sort(signCodeTypes);
			Collections.reverse(signCodeTypes);
			//同时发起基金鑫钱宝交易标识
			SessionContext.getContext().setValue("Jijin_Xinqianbao", "true");
		}
		
		if (log.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder();
			sb.append("签约产品代码[");
			for (String signCodeType : signCodeTypes) {
				sb.append(signCodeType).append(",");
			}
			sb.append("]");
			log.debug(sb.toString());
		}
		
		SessionContext.getContext().setValue("signCodeTypes", signCodeTypes);
		
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[SignGeneralInit]......");
		}

		return null;
	}
	
	/*
	 * 对私综合签约，根据产品代码选择原子服务
	 */
	private void initProductList()
	{
		List<SignObject> productList = new ArrayList<SignObject>();
		productList.add(new SignObject("000001","1003200000102","个人银行"));
		productList.add(new SignObject("000002","1003200000102","手机银行"));
		productList.add(new SignObject("000003","1003200000104","短信银行","16"));
		productList.add(new SignObject("000004","1003200000107","个人电话银行","1002"));
		productList.add(new SignObject("000005","3003200001301","信用卡自动还款"));
		productList.add(new SignObject("000006","3004200002201","支付宝卡通","ZH0100"));
		productList.add(new SignObject("000007","3003200000404","ATM跨行转账","ERRCONT"));
		productList.add(new SignObject("000008","3006200002801","第三方存管","5032"));
		productList.add(new SignObject("000010","3005200000111","鑫钱宝","1106"));
		productList.add(new SignObject("010011","3006200001810","电费(南京)","ERRCONT"));
		productList.add(new SignObject("020011","3006200001810","电费(泰州)","ERRCONT"));
		productList.add(new SignObject("010012","3006200001810","水费(南京)","ERRCONT"));
		productList.add(new SignObject("010013","3006200001810","联通收费(南京)","ERRCONT"));
		productList.add(new SignObject("010014","3006200001810","燃气收费(南京)","ERRCONT"));
		productList.add(new SignObject("010015","3006200001810","移动收费(南京)","ERRCONT"));
		productList.add(new SignObject("010016","3006200001810","有线收费(南京)","ERRCONT"));
		productList.add(new SignObject("010017","3006200001810","电信收费(南京)","ERRCONT"));
		productList.add(new SignObject("010018","3006200001810","江宁燃气收费(南京)","ERRCONT"));
		productList.add(new SignObject("020014","3006200001810","燃气收费(泰州)","ERRCONT"));
		productList.add(new SignObject("000020","3005200000106","基金","1106"));
		productList.add(new SignObject("000021","3005200000101","理财","1106"));
		productList.add(new SignObject("000022","3005200000114","账户金","2005"));
		productList.add(new SignObject("000023","3005200000117","黄金T+D","ERRGOLDCONT"));
		productList.add(new SignObject("000024","3001200000404","易得利","FnE0590"));
		productList.add(new SignObject("000025","3001200000402","易得利升级版","FnE2004"));
		productList.add(new SignObject("000026","3001200000402","卡内活转定","FnE2004"));
		productList.add(new SignObject("000027","3006200002103","社保扣款","E2004"));
		productList.add(new SignObject("000028","3006200002105","养老金","E2004"));
		productList.add(new SignObject("000029","3006200002107","失业金","E2004"));
		SessionContext.getContext().setValue("productList", productList);
	}
	/**
	 * 初始化返回头
	 * @param retCode
	 * @param retMsg
	 * @param tranStatus
	 */
	public static void setSysHead(ICompositeData reqservice,ICompositeData rspservice,String retCode,String retMsg,String tranStatus){

		
		ICompositeData RspSysHead = CompositeDataUtils.mkNodeNotExist(rspservice, "RspSysHead");
		CompositeDataUtils.setValue(RspSysHead, "RetCode", retCode);
		CompositeDataUtils.setValue(RspSysHead, "RetMsg", retMsg);
		CompositeDataUtils.setValue(RspSysHead, "TransStatus", tranStatus);
		CompositeDataUtils.copy(reqservice, RspSysHead, "ReqSysHead/ReqSeq", "ReqSeq");
		CompositeDataUtils.copy(reqservice, RspSysHead, "ReqSysHead/ServiceID", "ServiceID");
		CompositeDataUtils.copy(reqservice, RspSysHead, "ReqSysHead/ChannelID", "ChannelID");
		CompositeDataUtils.copy(reqservice, RspSysHead, "ReqSysHead/LegOrgID", "LegOrgID");
		CompositeDataUtils.copy(reqservice, RspSysHead, "ReqSysHead/ReqDate", "ReqDate");
		CompositeDataUtils.copy(reqservice, RspSysHead, "ReqSysHead/ReqTime", "ReqTime");
		CompositeDataUtils.copy(reqservice, RspSysHead, "ReqSysHead/Version", "Version");
		CompositeDataUtils.copy(reqservice, RspSysHead, "ReqSysHead/ReqSysID", "ReqSysID");
		CompositeDataUtils.copy(reqservice, RspSysHead, "ReqSysHead/AcceptLang", "AcceptLang");
		CompositeDataUtils.copy(reqservice, RspSysHead, "ReqSysHead/DomainRef", "DomainRef");
		CompositeDataUtils.copy(reqservice, RspSysHead, "ReqSysHead/GlobalSeq", "GlobalSeq");
		CompositeDataUtils.copy(reqservice, RspSysHead, "ReqSysHead/OrgSysID", "OrgSysID");
		CompositeDataUtils.copy(reqservice, RspSysHead, "ReqSysHead/SvcScn", "SvcScn");
		CompositeDataUtils.copy(reqservice, RspSysHead, "ReqSysHead/TmlCd", "TmlCd");
		CompositeDataUtils.copy(reqservice, RspSysHead, "ReqSysHead/TlrNo", "TlrNo");
		CompositeDataUtils.copy(reqservice, RspSysHead, "ReqSysHead/BrId", "BrId");
	}
	
}