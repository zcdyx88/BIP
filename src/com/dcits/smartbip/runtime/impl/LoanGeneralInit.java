package com.dcits.smartbip.runtime.impl;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.utils.CompositeDataUtils;
/***
 * 一般贷款开户交易前处理
 * @author xhx102
 * date 20160616
 */
public class LoanGeneralInit extends AbstractBaseService implements IService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7907803865981846010L;
	private static final Log log = LogFactory.getLog(LoanGeneralInit.class);

	@Override
	public String getId() {
		return "LoanGeneralInit";
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
			log.info("开始调用基础服务[LoanGeneralInit]......");
		}
		ICompositeData Req3002101000101 = (ICompositeData) getContext().getValue("Req3002101000101");
		
		/** COM_PUB_Init start**/ 
		
		// 获得交易类型   交易类型 1-开户;2-还款;6-查询;8-撤销;9-冲正。贷款开户 默认1
		/** PRV_GenTranType  **/
		String aczTranType  = "1";   /* 生成 交易类型,取内部交易码  */
		SessionContext.getContext().setValue("__TRAN_TYPE__", aczTranType);
		
		/** PRV_GenBusiOprFlg **/
		//获取业务操作标志    业务操作方式 1-录入;2-修改;3-复核;4-直通
		 if (isEquals(aczTranType, "1")) {   /* 开户交易的业务操作方式, 存放在字段 dkkhczbz 中 */
			 getContext().setValue("__BUSI_OPR_TYPE__", CompositeDataUtils.getValue(Req3002101000101,"ReqAppBody/dkkhczbz"));
		    }

		 /** PRV_GenMakeLoans **/
		 //放款标
		 String aczMakeLoans = "0";
		 
		 String aczBusiOprType = (String)getContext().getValue("__BUSI_OPR_TYPE__"); /* 业务操作类型: 录入,复合,直通等 */
		 String aczLoansAmt = CompositeDataUtils.getValue(Req3002101000101, "ReqAppBody/bencfkje"); ///* 本次放款金额 */
		 Double aczLoansAmtD = Double.parseDouble(aczLoansAmt); /* 本次放款金额 */
		 if(isEquals(aczBusiOprType, "1")){
			 aczMakeLoans = "0";
		 }else{ /* 复合 or 直通 */
			 if(aczLoansAmtD >0){
				 aczMakeLoans = "1";
			 }else{
				 aczMakeLoans = "0";
			 }
		 }
		
		 /** PRV_GenTakeType() **/
		 int iTakeCheck = 0;         /* 0-不检查限额控制表, 1-检查限额控制表 */
		   /** 只有录入或直通(同时放款金额>0)的情况下, 才检查限额控制表 **/
		    if (isEquals(aczBusiOprType, "1") || isEquals(aczBusiOprType, "4")) {  /* 录入 or 直通 */
		    	 if(aczLoansAmtD >0){ /* 本次放款金额大于零 */
		    		 iTakeCheck = 1;
				 }else{
					 iTakeCheck = 0;
				 }
		    }
		String orgSysID = CompositeDataUtils.getValue(Req3002101000101, "ReqSysHead/OrgSysID");
		String aczTakeType = "0";  //限额标志,默认不调用
		//村镇银行 不走限额
		if(isEquals(orgSysID, "B79")){
			iTakeCheck = 0;
			DEBUD("发起方系统编码[" + orgSysID +"]为村镇银行不走限额");
		}
		 if (iTakeCheck == 0) {
			 getContext().setValue("__TAKE_TYPE__", aczTakeType); 
		        DEBUD("不检查限额控制表,设置为不调用限额占用");
		 }else{
			 /** 装载额度控制信息 **/
			String aczChannelID = CompositeDataUtils.getValue(Req3002101000101, "ReqSysHead/ChannelID");
			String aczYngyjigo = CompositeDataUtils.getValue(Req3002101000101, "ReqAppBody/zhngjigo");
			if(aczYngyjigo.startsWith("7") || isEquals(aczChannelID, "1131001") || isEquals(aczChannelID, "1131002") || isEquals(aczChannelID, "1131003")
					|| isEquals(aczChannelID, "1131004") || isEquals(aczChannelID, "1131009")){
				aczTakeType = "2";
			}
			getContext().setValue("__TAKE_TYPE__", aczTakeType); 
		 } 

		//检查必输字段
		Boolean  isAccessed =  isSysHeadAccess(Req3002101000101);
		if(!isAccessed){
			return "必输项检查失败";
		}
		/** COM_PUB_Init end**/ 
		
		/** COM_Init COM_ln11_Init start**/
		String aczStzf = CompositeDataUtils.getValue(Req3002101000101, "ReqAppBody/fkzjclfs"); //放款资金处理方式
		if(isEquals(aczStzf, "4")){  //0--自主支付   4--受托支付
			// 获取贷款受托支付数组
			List<ICompositeData> aczList = CompositeDataUtils.getByPath(
					Req3002101000101, "ReqAppBody/lstdkstzf");
			int iCount = 0;
			for (ICompositeData aczFldComposite : aczList) {
				String aczFldVl = CompositeDataUtils.getValue(aczFldComposite,"stzffshi"); //受托支付方式
				String aczFldVl1 = CompositeDataUtils.getValue(aczFldComposite,"dfzhhzhl"); //对方账号种类
				if(isEquals(aczFldVl, "3") && isEquals(aczFldVl1, "2")){
					iCount ++ ;
				}
				//如果受托方是1 2不调支付，受托支付方式为不为立即支付,不需要调用二代支付
				if(isEquals(aczFldVl, "1") || isEquals(aczFldVl, "2")){
					aczMakeLoans = "0";
				}
			}
			if(iCount >= 2){
				return "B19STZFERR-立即支付中他行卡大于1张,请使用手工支付";
			}
		}else{
			aczMakeLoans = "0";
		}
		/** COM_Init COM_ln11_Init end**/
		
		/** COM_Process COM_ln11_Process start **/
//	    a.把请求方系统编码ReqSysID的值写入OrgSysID发起方系统编码   b.请求方系统编码ReqSysID统一变更为B98 BIP
		getContext().setValue("OrgSysID", CompositeDataUtils.getValue(Req3002101000101,"ReqSysHead/ReqSysID"));
		getContext().setValue("ReqSysID","B19");
		/** COM_Process COM_ln11_Process end **/
		
		// 放款资金处理方式[%s]不是 4-受托支付  不需要调二代支付
		 //是否调用跨行放款-二代支付标志,
		 getContext().setValue("MAKE_LOANS_FLAG", aczMakeLoans);
		 
		 
		 /* 占用标志:0-不占用;1-占用但忽略应答;2-占用且允许通讯故障;3-占用且必须成功 */
		 /** COM_LmtCancel **/
		 //是否调用占用解除标志
		 getContext().setValue("TAKE_TYPE_FLAG", "1"); 
		
		if (log.isInfoEnabled()) {
			log.info("__TAKE_TYPE_FLAG__= " + getContext().getValue("TAKE_TYPE_FLAG"));
			log.info("__MAKE_LOANS_FLAG__= " + getContext().getValue("MAKE_LOANS_FLAG"));
			log.info("调用基础服务结束[LoanGeneralInit]......");
		}
		return null;
	}

	
	/**
	 * 判断是否相等
	 * @param srcStr 源值
	 * @param destStr  目标值
	 * @return
	 */
	public Boolean isEquals(String srcStr,String destStr){
		if((srcStr != null || srcStr != "") && (destStr != null || destStr != "")){
			return srcStr.equalsIgnoreCase(destStr);
		}
		return false;
	}
	
	/**
	 * 判断是否为空 必输
	 * @param srcStr
	 * @return
	 */
	public Boolean isNull(String srcStr){
		if((srcStr != null || srcStr != "")){
			return false;
		}
		return true;
	}
	
	/***
	 * 检查系统头必输  工具类
	 * @param compositeData
	 * @return  true
	 */
	public Boolean isSysHeadAccess(ICompositeData compositeData){
		String reqSeq = CompositeDataUtils.getValue(compositeData, "ReqSysHead/ReqSeq");
		String serviceID = CompositeDataUtils.getValue(compositeData, "ReqSysHead/ServiceID");
		String channelID = CompositeDataUtils.getValue(compositeData, "ReqSysHead/ChannelID");
		String legOrgID = CompositeDataUtils.getValue(compositeData, "ReqSysHead/LegOrgID");
		String reqDate = CompositeDataUtils.getValue(compositeData, "ReqSysHead/ReqDate");
		String reqTime = CompositeDataUtils.getValue(compositeData, "ReqSysHead/ReqTime");
		String version = CompositeDataUtils.getValue(compositeData, "ReqSysHead/Version");
		String reqSysID = CompositeDataUtils.getValue(compositeData, "ReqSysHead/ReqSysID");
		String domainRef = CompositeDataUtils.getValue(compositeData, "ReqSysHead/DomainRef");
		String acceptLang = CompositeDataUtils.getValue(compositeData, "ReqSysHead/AcceptLang");
		String globalSeq = CompositeDataUtils.getValue(compositeData, "ReqSysHead/GlobalSeq");
		String orgSysID = CompositeDataUtils.getValue(compositeData, "ReqSysHead/OrgSysID");

		if(isNull(reqSeq) || isNull(serviceID) || isNull(channelID) || isNull(legOrgID)
		    || isNull(reqDate) || isNull(reqTime) || isNull(version) || isNull(reqSysID)
		    || isNull(domainRef) || isNull(acceptLang) || isNull(globalSeq) || isNull(orgSysID)){
			return false;
		}
		return true;
	}
	
	/***
	 * debug
	 * @param mess
	 */
	public void DEBUD(String mess){
		if(log.isDebugEnabled()){
			log.debug(mess);
		}
	}
}
