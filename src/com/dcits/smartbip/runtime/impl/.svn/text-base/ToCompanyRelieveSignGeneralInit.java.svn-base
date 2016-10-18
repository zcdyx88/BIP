package com.dcits.smartbip.runtime.impl;

import java.util.ArrayList;
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
 *  对公综合解约
 * @author srxhx611
 *
 */
public class ToCompanyRelieveSignGeneralInit extends AbstractBaseService implements IService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2259168321027257607L;
	private static final Log log = LogFactory.getLog(ToCompanyRelieveSignGeneralInit.class);

	@Override
	public String getId() {
		return "ToCompanyRelieveSignGeneralInit";
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
			log.info("开始调用基础服务[ToCompanyRelieveSignGeneralInit]......");
		}
		initServiceIds();

		// 获得综合签约请求数据对象
		ICompositeData Req2002201000104 = (ICompositeData) SessionContext
				.getContext().getValue("Req2002201000104");	//对公综合解约
		List<String> signoffCodeTypes = new ArrayList<String>();
		List<ICompositeData> signoffProCdArray = CompositeDataUtils.getByPath(
				Req2002201000104, "ReqAppBody/CorpSignOffProCdArray");
		
		//初始化RspSysHead的返回信息
		ICompositeData Rsp2002201000104 = (ICompositeData) SessionContext
		.getContext().getValue("Rsp2002201000104");
		if (null == Rsp2002201000104)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			Rsp2002201000104  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			Rsp2002201000104.setId("Rsp2002201000104");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			Rsp2002201000104.setxPath("/Rsp2002201000104");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			getContext().setValue("Rsp2002201000104",Rsp2002201000104);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		}
		
		//syshead初始化
		setSysHead(Req2002201000104,Rsp2002201000104,"000000", "通讯成功,产品签约结果详见结果数组", "S");
		
		
		for (ICompositeData signoffComposite : signoffProCdArray) {
			String signoffCodeType = CompositeDataUtils.getValue(signoffComposite,
					"SignProTp");
			signoffCodeTypes.add(signoffCodeType);
		}

		SessionContext.getContext().setValue("SignoffCodeTypesNull", "false");
		if(null == signoffCodeTypes || signoffCodeTypes.size() ==0){
			SessionContext.getContext().setValue("SignoffCodeTypesNull", "true");
			CompositeDataUtils.setReturn(Rsp2002201000104,ErrorCodes.CODE_BIP00T0001,"请求解约产品代码为空，请核实！");
		}
		
		if (log.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder();
			sb.append("签约产品代码[");
			for (String signCodeType : signoffCodeTypes) {
				sb.append(signCodeType).append(",");
			}
			sb.append("]");
			log.debug(sb.toString());
		}
		
		getContext().setValue("signoffCodeTypes", signoffCodeTypes);

		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[ToCompanyRelieveSignGeneralInit]......");
		}

		return null;
	}
	/*
	 * 对公综合解约，根据产品代码选择原子服务
	 */
	
	private void initServiceIds()
	{
		List<SignObject> productList = new ArrayList<SignObject>();
		
		productList.add(new SignObject("000003","1003200000206","短信银行","25"));
		productList.add(new SignObject("000004","1003200000208","电话银行","8004"));
		productList.add(new SignObject("000020","3005200000110","基金","1107"));
		productList.add(new SignObject("000021","3005200000105","理财","1107"));
		productList.add(new SignObject("000030","3001200000402","账户通","FnE2005"));
		productList.add(new SignObject("000031","3001200000402","天添利","FnE2005"));
		productList.add(new SignObject("000032","3001200000505","协定存款","DpE0004"));
		productList.add(new SignObject("000034","1003200000204","企业网银（普通版）","ZH0200"));
		productList.add(new SignObject("000035","1003200000202","企业网银（专业版）","ZH0200"));
		productList.add(new SignObject("000036","3006200001801","代发代扣","DpE9164,DpE9163"));
		productList.add(new SignObject("000039","3006200002101","财税库行","TIPS004"));		
		
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
		CompositeDataUtils.copy(reqservice, RspSysHead, "ReqSysHead/DomainRef", "DomainRef");
		CompositeDataUtils.copy(reqservice, RspSysHead, "ReqSysHead/AcceptLang", "AcceptLang");
		CompositeDataUtils.copy(reqservice, RspSysHead, "ReqSysHead/GlobalSeq", "GlobalSeq");
		CompositeDataUtils.copy(reqservice, RspSysHead, "ReqSysHead/OrgSysID", "OrgSysID");
		CompositeDataUtils.copy(reqservice, RspSysHead, "ReqSysHead/SvcScn", "SvcScn");
		CompositeDataUtils.copy(reqservice, RspSysHead, "ReqSysHead/TmlCd", "TmlCd");
		CompositeDataUtils.copy(reqservice, RspSysHead, "ReqSysHead/TlrNo", "TlrNo");
		CompositeDataUtils.copy(reqservice, RspSysHead, "ReqSysHead/BrId", "BrId");
	}
}

	
