package com.dcits.smartbip.runtime.impl;

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
 * 卡凭证查询的基础服务
 * 字段kadxiang 为1 或者字段 kadengji 为1 
 * 判断上送的换签产品种类是否仅有“短信”和“卡卡转账”（附卡和单位结算卡仅能签这两种产品），否则直接进入待签产品参数信息查询
 * @author srxhx402
 *
 */
public class CheckCardProof extends AbstractBaseService implements IService  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(CheckCardProof.class);
	StringBuilder ExchangeSignNm = new StringBuilder("");//上送换签产品名称
	@Override
	public String getType() {
		return "base";
	}

	@Override
	public void setType(String type) {
		
	}
	
	@Override
	public String getId(){
		return "CheckCardProof";
	}
	
	@Override
	public Object execute(Object req) throws InvokeException{
		
		if(log.isInfoEnabled()){
			log.info("开始调用基础服务[CheckCardProof]");
		}
		
		//获取上下文返回数据对象
		ICompositeData Rsp3003300000305 = (ICompositeData) SessionContext.getContext()
		.getValue("Rsp3003300000305");
		
	 	//获取卡对象和卡等级
		String kadxiang = CompositeDataUtils.getValue(Rsp3003300000305, "RspAppBody/kadxiang");
		String kadengji = CompositeDataUtils.getValue(Rsp3003300000305, "RspAppBody/kadengji");
		
		
		if (null == Rsp3003300000305)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			Rsp3003300000305  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			Rsp3003300000305.setId("Rsp3003300000305");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			Rsp3003300000305.setxPath("/Rsp3003300000305");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			getContext().setValue("Rsp3003300000305",Rsp3003300000305);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		}
		
		
		
		//if("0".equals(kadxiang) && "0".equals(kadengji)){//既不是附卡也不是单位结算卡
			SessionContext.getContext().setValue("ProofResult", "0");//进入待签产品参数信息查询
		/*}else { 
			//是附卡或单位结算卡//只能签约短信  和卡卡转账
			SessionContext.getContext().setValue("ProofResult", "1");
			

//			获取请求组合服务码
			String modifySignType = (String)SessionContext.getContext().getValue("ModifySignType");
			ICompositeData ReqModifySignType = (ICompositeData) SessionContext
			.getContext().getValue("Req" +modifySignType);
//			获取上送换签产品代码
			List<ICompositeData> privExchgSignProCdArray = CompositeDataUtils.getByPath(
					ReqModifySignType, "ReqAppBody/PrivExchgSignProCdArray");
			int count = 0;
			for(ICompositeData PriExgSignTp : privExchgSignProCdArray){
				String ExchangeSignTp = CompositeDataUtils.getValue(PriExgSignTp,"ExchgSignProTp");//上送换签产品代码
				String SignNm = CompositeDataUtils.getValue(PriExgSignTp,"ExchgSignProNm");//上送换签产品名称
				//判断上送的换签产品种类是否仅有“短信”和“卡卡转账”（附卡和单位结算卡仅能签这两种产品），否则直接进入待签产品参数信息查询
				if("000003".equals(ExchangeSignTp) || "000007".equals(ExchangeSignTp)){
					count++;
				}else{
					ExchangeSignNm.append("["+SignNm+"]");
				}
			}
			if(count == 2){
				//签约视图查询（新账户）
				SessionContext.getContext().setValue("card_sms","0");
			}
			else{
				CompositeDataUtils.setReturn(modifySignType, ErrorCodes.CODE_BIP03E0002, "附卡/单位结算卡不允许签约"+ExchangeSignNm+"产品");
				log.info("附卡/单位结算卡不允许签约"+ExchangeSignNm+"产品");
				return null;
			}
		}*/
		
		if(log.isDebugEnabled()){
			StringBuilder sb = new StringBuilder();
			sb.append("卡对象kadxiang["+kadxiang+"], ");
			sb.append("卡等级kadengji["+kadengji+"], ");
			log.debug(sb.toString());
		}
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[CheckCardProof]......");
		}
		return null;
	}
	

}
