package com.dcits.smartbip.runtime.impl;

import java.util.ArrayList;
import java.util.Collections;
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

public class GetSignOffProductionType extends AbstractBaseService implements IService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8994865801372901708L;
	private static final Log log = LogFactory.getLog(GetSignOffProductionType.class);

	@Override
	public String getId() {
		return "GetSignOffProductionType";
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
			log.info("开始调用基础服务[GetSignOffProductionType]......");
		}
		
		initServiceIds();
		
		//组合服务码
		String ModifySignType = (String)getContext().getValue("ModifySignType");
		// 获得综合解约请求数据对象
		ICompositeData Req2002201000103 = (ICompositeData) SessionContext
				.getContext().getValue("Req"+ModifySignType);
		List<String> signCodeTypes = new ArrayList<String>();
		List<ICompositeData> signProCdArray = CompositeDataUtils.getByPath(
				Req2002201000103, "ReqAppBody/PrivSignOffProCdArray");
		for (ICompositeData signComposite : signProCdArray) {
			String signCodeType = CompositeDataUtils.getValue(signComposite,
					"SignOffProTp");
			if(!signCodeTypes.contains(signCodeType)){
				signCodeTypes.add(signCodeType);
			}
		}
		//syshead初始化
		setSysHead(ModifySignType,"000000", "通讯成功,产品签约结果详见结果数组", "S");
		
		Collections.sort(signCodeTypes);//待解约产品重新排序：按产品代码正序，使鑫钱宝、鑫元宝排在基金之前
		if (log.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder();
			sb.append("解约产品代码[");
			for (String signCodeType : signCodeTypes) {
				sb.append(signCodeType).append(",");
			}
			sb.append("]");
			log.debug(sb.toString());
		}
		getContext().setValue("signCodeTypes", signCodeTypes);

		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[GetSignOffProductionType]......");
		}

		return null;
	}
	
	/*
	 * 对私综合解约，根据产品代码选择原子服务
	 */
	
	private void initServiceIds()
	{
		List<SignObject> productList = new ArrayList<SignObject>();
		
		productList.add(new SignObject("000001","1003200000103","网上银行 "));
		productList.add(new SignObject("000002","1003200000103","手机银行"));
		productList.add(new SignObject("000003","1003200000106","短信银行"));
		productList.add(new SignObject("000004","1003200000108","个人电话银行"));
		productList.add(new SignObject("000005","3003200001301","信用卡自动还款"));
		productList.add(new SignObject("000006","3004200002202","支付宝卡通"));
//		productList.add(new SignObject("000007","3003200000404","ATM跨行转账"));
		productList.add(new SignObject("000009","3005200000113","鑫元宝"));
		productList.add(new SignObject("000010","3005200000112","鑫钱宝"));
//		productList.add(new SignObject("0000011","3006200001811","电费(南京)"));
//		productList.add(new SignObject("0000012","3006200001811","水费（南京）"));
//		productList.add(new SignObject("0000013","3006200001811","联通收费(南京)"));
//		productList.add(new SignObject("0000014","3006200001811","燃气收费(南京)"));
//		productList.add(new SignObject("0000015","3006200001811","移动收费(南京)"));
//		productList.add(new SignObject("0000016","3006200001811","有线收费(南京)"));
//		productList.add(new SignObject("0000017","3006200001811","电信收费(南京)"));
//		productList.add(new SignObject("0000018","3006200001811","江宁燃气收费"));
//		productList.add(new SignObject("0000019","3003200000405","市民卡转账(南京)"));
		
		productList.add(new SignObject("000020","3005200000110","基金"));
		productList.add(new SignObject("000021","3005200000105","理财"));
		productList.add(new SignObject("000022","3005200000116","账户金"));
		productList.add(new SignObject("000023","3005200000119","黄金T+D"));
		productList.add(new SignObject("000024","3001200000404","易得利"));
//		productList.add(new SignObject("000025","3001200000402","易得利升级版"));
//		productList.add(new SignObject("000026","3001200000402","卡内活转定"));
		
		
		SessionContext.getContext().setValue("productList", productList);		
	}
	
	/**
	 * 初始化返回头
	 * @param retCode
	 * @param retMsg
	 * @param tranStatus
	 */
	public static void setSysHead(String serviceId,String retCode,String retMsg,String tranStatus){
		ICompositeData reqICD = (ICompositeData) SessionContext.getContext().getValue("Req"+serviceId);
		ICompositeData Rsp2002201000103 = (ICompositeData) SessionContext.getContext().getValue("Rsp"+serviceId);	//对私综合解约
		if (null == Rsp2002201000103)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			Rsp2002201000103  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			Rsp2002201000103.setId("Rsp"+serviceId);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			Rsp2002201000103.setxPath("/Rsp"+serviceId);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			SessionContext.getContext().setValue("Rsp"+serviceId,Rsp2002201000103);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		} 
		ICompositeData RspSysHead = CompositeDataUtils.mkNodeNotExist(Rsp2002201000103, "RspSysHead");
		CompositeDataUtils.setValue(RspSysHead, "RetCode", retCode);
		CompositeDataUtils.setValue(RspSysHead, "RetMsg", retMsg);
		CompositeDataUtils.setValue(RspSysHead, "TransStatus", tranStatus);
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/ReqSeq", "ReqSeq");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/ServiceID", "ServiceID");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/ChannelID", "ChannelID");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/LegOrgID", "LegOrgID");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/ReqDate", "ReqDate");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/ReqTime", "ReqTime");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/Version", "Version");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/ReqSysID", "ReqSysID");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/AcceptLang", "AcceptLang");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/DomainRef", "DomainRef");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/GlobalSeq", "GlobalSeq");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/OrgSysID", "OrgSysID");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/SvcScn", "SvcScn");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/TmlCd", "TmlCd");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/TlrNo", "TlrNo");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/BrId", "BrId");
	}
}
