package com.dcits.smartbip.runtime.impl;

import java.util.ArrayList;
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
/**
 *  设置换签返回报文头SYSHEAD中，成功发送到后端则通讯成功，送000000
 *	对产品代码，原子服务码 以及 产品名称 初始化 
 */
public class SignModifyGeneralInit extends AbstractBaseService implements IService {

	
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(SignModifyGeneralInit.class);

	@Override
	public String getId() {
		return "SignModifyGeneralInit";
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
		
		initProductList();

		if (log.isInfoEnabled()) {
			log.info("开始调用基础服务[SignModifyGeneralInit]......");
		}
		//modifySignType:组合服务码
		String modifySignType = (String)SessionContext.getContext().getValue("ModifySignType");
		
		//初始化RspSysHead的返回信息
		//syshead初始化
		setSysHead(modifySignType,"000000", "通讯成功,产品签约结果详见结果数组", "S");
		
		if(log.isDebugEnabled()){
			log.debug("设置换签属性ModifySignType[" +  modifySignType + "]");
		}

		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[SignModifyGeneralInit]......");
		}

		return null;
	}

	
	/*
	 * 对私综合签约，根据产品代码选择原子服务
	 */
	private void initProductList()
	{
		List<SignObject> productList = new ArrayList<SignObject>();
		productList.add(new SignObject("000001","1003200000115","网上银行"));
		productList.add(new SignObject("000002","1003200000116","手机银行"));
		productList.add(new SignObject("000003","1003200000301","短信银行"));
		productList.add(new SignObject("000004","1003200000116","电话银行"));
		productList.add(new SignObject("000005","3003200001301","信用卡自动还款"));
		productList.add(new SignObject("000007","3003200000406","ATM跨行转账"));
		productList.add(new SignObject("000008","3006200002803","第三方存管"));
		productList.add(new SignObject("000009","3005200000109","鑫元宝 "));
		productList.add(new SignObject("000010","3005200000109","鑫钱宝"));
		productList.add(new SignObject("010011","3006200001815","电费(南京)"));
		productList.add(new SignObject("020011","3006200001815","电费(泰州)"));
		productList.add(new SignObject("010012","3006200001815","水费(南京)"));
		productList.add(new SignObject("010013","3006200001815","联通收费(南京)"));
		productList.add(new SignObject("010014","3006200001815","燃气收费(南京)"));
		productList.add(new SignObject("020014","3006200001815","燃气收费(泰州)"));
		productList.add(new SignObject("010015","3006200001815","移动收费(南京)"));
		productList.add(new SignObject("010016","3006200001815","有线收费(南京)"));
		productList.add(new SignObject("010017","3006200001815","电信收费(南京)"));
		productList.add(new SignObject("010018","3006200001815","江宁燃气收费(南京)"));
		productList.add(new SignObject("000020","3005200000109","基金 "));
		productList.add(new SignObject("000021","3005200000104","理财"));
		productList.add(new SignObject("000022","3005200000115","账户金"));
		productList.add(new SignObject("000023","3005200000118","黄金T+D"));
		productList.add(new SignObject("000027","3006200002104","社保扣款"));
		productList.add(new SignObject("000028","3006200002106","养老金"));
		productList.add(new SignObject("000029","3006200002108","失业金"));

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
		ICompositeData RspICD = (ICompositeData) SessionContext.getContext().getValue("Rsp"+serviceId);	//对公综合签约
		if (null == RspICD)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			RspICD  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			RspICD.setId("Rsp"+serviceId);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			RspICD.setxPath("/Rsp"+serviceId);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			SessionContext.getContext().setValue("Rsp"+serviceId,RspICD);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		} 
		ICompositeData RspSysHead = CompositeDataUtils.mkNodeNotExist(RspICD, "RspSysHead");
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
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/DomainRef", "DomainRef");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/AcceptLang", "AcceptLang");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/GlobalSeq", "GlobalSeq");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/OrgSysID", "OrgSysID");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/SvcScn", "SvcScn");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/TmlCd", "TmlCd");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/TlrNo", "TlrNo");
		CompositeDataUtils.copy(reqICD, RspSysHead, "ReqSysHead/BrId", "BrId");
	}
}
