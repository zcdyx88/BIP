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
 * 对公综合签约初始化
 * 
 * @author srxhx611
 *
 */
public class ToCompanySignGeneralInit extends AbstractBaseService implements IService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7243348617696373305L;
	private static final Log log = LogFactory.getLog(ToCompanySignGeneralInit.class);

	@Override
	public String getId() {
		return "ToCompanySignGeneralInit";
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
			log.info("开始调用基础服务[ToCompanySignGeneralInit]......");
		}
		initServiceIds();
		//组合服务码
		String ModifySignType = (String)getContext().getValue("ModifySignType");
		// 获得综合签约请求数据对象
		ICompositeData Req2002201000102 = (ICompositeData) SessionContext
				.getContext().getValue("Req"+ModifySignType);	//对公综合签约
		List<String> signCodeTypes = new ArrayList<String>();
		List<ICompositeData> signProCdArray = CompositeDataUtils.getByPath(
				Req2002201000102, "ReqAppBody/CorpSignProCdArray");
		for (ICompositeData signComposite : signProCdArray) {
			String signCodeType = CompositeDataUtils.getValue(signComposite,
					"SignProTp");
			signCodeTypes.add(signCodeType);
		}
		
		//syshead初始化
		setSysHead(ModifySignType,"000000", "通讯成功,产品签约结果详见结果数组", "S");
		
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
			log.info("调用基础服务结束[ToCompanySignGeneralInit]......");
		}

		return null;
	}
	/*
	 * 对公综合签约，根据产品代码选择原子服务
	 */
	private void initServiceIds()
	{
		List<SignObject> productList = new ArrayList<SignObject>();
		
		productList.add(new SignObject("000001","1003200000102","网上银行"));		
		productList.add(new SignObject("000003","1003200000206","短信银行"));
		productList.add(new SignObject("000004","1003200000207","电话银行"));
		productList.add(new SignObject("000008","3006200002802","第三方存管"));
		productList.add(new SignObject("000020","3005200000106","基金"));
		productList.add(new SignObject("000021","3005200000101","理财"));
		productList.add(new SignObject("000030","3001200000402","账户通"));
		productList.add(new SignObject("000031","3001200000402","天添利"));
		productList.add(new SignObject("000032","3001200000505","协定存款"));
		productList.add(new SignObject("000034","1003200000203","企业网银（普通版）"));
		productList.add(new SignObject("000035","1003200000201","企业网银（专业版）"));
		productList.add(new SignObject("000036","3006200001801","代发代扣"));
		productList.add(new SignObject("000039","3006200002101","财税库行"));
		
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
		ICompositeData Rsp2002201000102 = (ICompositeData) SessionContext.getContext().getValue("Rsp"+serviceId);	//对公综合签约
		if (null == Rsp2002201000102)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			Rsp2002201000102  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			Rsp2002201000102.setId("Rsp"+serviceId);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			Rsp2002201000102.setxPath("/Rsp"+serviceId);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			SessionContext.getContext().setValue("Rsp"+serviceId,Rsp2002201000102);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		} 
		ICompositeData RspSysHead = CompositeDataUtils.mkNodeNotExist(Rsp2002201000102, "RspSysHead");
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