package com.dcits.smartbip.runtime.impl;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.ErrorCodes;
import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.limit.LimitCtrlService;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.utils.CompositeDataUtils;
/***
 * 一般贷款开户交易前处理
 * @author xhx102
 * date 20160616
 */
public class LoanGeneralInitBaseService extends AbstractBaseService implements IService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7907803865981846010L;
	private static final Log log = LogFactory.getLog(LoanGeneralInitBaseService.class);

	@Override
	public String getId() {
		return "LoanGeneralInitBaseService";
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
		INFO("开始调用基础服务[LoanGeneralInitBaseService]......");
		/**************以下内容为各贷款公共 start*******************/
		//获取贷款类型
		String LoanService = "";
		LoanService =  (String) getContext().getValue("__LOAN_KIND__");
		INFO("贷款类型[" + LoanService + "]"); 
		ICompositeData ReqLoanService = (ICompositeData) getContext().getValue("Req"+LoanService);
		
		// 获得交易类型   交易类型 1-开户;2-还款;6-查询;8-撤销;9-冲正。贷款开户 默认1
		String aczTranType  = "1";   /* 生成交易类型,取内部交易码  */
		getContext().setValue("__TRAN_TYPE__", aczTranType);  
		 //放款标志
		String aczMakeLoans = "0";
		int iTakeCheck = 0;         /* 0-不检查限额控制表, 1-检查限额控制表 */
		String aczTakeType = "0";  //限额标志,默认不调用
		String succFlag = "1";     //校验处理结果标志,默认成功1
		String takeTypeRes = "0"; //生成限额调用响应判断规则
		
		//检查必输字段
		Boolean  isAccessed =  isSysHeadAccess(ReqLoanService);
		if(!isAccessed){
			succFlag = "0";
			getContext().setValue("LOAN_SUCC_FLAG", succFlag);
			INFO("报文头必输项检查失败");
			CompositeDataUtils.setReturn(LoanService,ErrorCodes.CODE_BIP04E0001,"");
			return null;
		}
		//获取业务操作标志    业务操作方式 [1-录入;2-修改;3-复核;4-直通]
		String aczBusiOprType = CompositeDataUtils.getValue(ReqLoanService,"ReqAppBody/dkkhczbz");
		INFO("业务操作标志:1-录入;2-修改;3-复核;4-直通->[" + aczBusiOprType + "]");
		if(isNull(aczBusiOprType)){
			succFlag = "0";
			getContext().setValue("LOAN_SUCC_FLAG", succFlag);
			DEBUG("[dkkhczbz]业务操作标志,上送为空");
			CompositeDataUtils.setReturn(LoanService,ErrorCodes.CODE_BIP04E0001,"[dkkhczbz]业务操作标志");
			return null;
		}
		
		String aczLoansAmt = CompositeDataUtils.getValue(ReqLoanService, "ReqAppBody/bencfkje"); ///* 本次放款金额 */
		Double aczLoansAmtD = 0.00;
		if(!isNull(aczLoansAmt)){
			 aczLoansAmtD = Double.parseDouble(aczLoansAmt); /* 本次放款金额 */
		 }
		DEBUG("本次放款金额[" + aczLoansAmtD + "]");
		 if(isEquals(aczBusiOprType, "1")){
			 aczMakeLoans = "0";
		 }else{ /* 复合 or 直通 */
			 if(aczLoansAmtD >0){
				 aczMakeLoans = "1";
			 }else{
				 aczMakeLoans = "0";
			 }
		 }
		//生成限额标志  所有业务相同判断
		if (isEquals(aczBusiOprType, "1") || isEquals(aczBusiOprType, "4")) {  /* 录入 or 直通 */
		    	if(aczLoansAmtD >0){ /* 本次放款金额大于零 */
		    		 iTakeCheck = 1;
				 }else{
					 iTakeCheck = 0;
				 }
		}
		String orgSysID = CompositeDataUtils.getValue(ReqLoanService, "ReqSysHead/OrgSysID");
		//村镇银行 不走限额
		if(isEquals(orgSysID, "B79")){
			iTakeCheck = 0;
			INFO("发起方系统编码[" + orgSysID +"]为村镇银行不走限额");
		}

		 if (iTakeCheck == 0) {
			 aczTakeType = "0";
		     DEBUG("不检查限额控制表,设置为不调用限额占用");
		 }else{
			DEBUG("只有录入或直通(同时放款金额>0)的情况下, 才检查限额控制表");
			DEBUG("装载额度控制信息");
			String aczChannelID = "";
			aczChannelID = CompositeDataUtils.getValue(ReqLoanService, "ReqSysHead/ChannelID");//渠道
			String aczYngyjigo = "";
			aczYngyjigo = CompositeDataUtils.getValue(ReqLoanService, "ReqAppBody/yngyjigo"); //营业机构
			DEBUG("对渠道维度进行控制ChannelID[" + aczChannelID + "]");
			DEBUG("对机构维度进行控制Yngyjigo[" + aczYngyjigo + "]");
			//生成限额调用响应判断规则,根据这个标志来判断是否调用核心  // 限额占用标志 0-不占用;1-占用但忽略应答;2-占用且允许通讯故障;3-占用且必须成功
			takeTypeRes = LimitCtrlService.getLimit(aczChannelID, aczYngyjigo);
			INFO("限额调用响应判断规则:" + takeTypeRes);
			//一定调用限额
			aczTakeType = "1";
		 } 
		/**************以上内容为各贷款公共 end*******************/
		 
		/**************以下内容为各贷款单独交易前处理 start*******************/
		//一般贷款开户   银团贷款开户放款
		if(isEquals("3002101000101", LoanService) || isEquals("3002101000104", LoanService)){
			String aczStzf = CompositeDataUtils.getValue(ReqLoanService, "ReqAppBody/fkzjclfs"); //放款资金处理方式
			if(isEquals(aczStzf, "4")){  //0--自主支付   4--受托支付
				DEBUG("[fkzjclfs]放款资金处理方式为受托支付");
				// 获取贷款受托支付数组
				List<ICompositeData> aczList = CompositeDataUtils.getByPath(
						ReqLoanService, "ReqAppBody/lstdkstzf");
				if(aczList.size() == 0){
					DEBUG("受托支付数组记录数为0,不调用二代");
					aczMakeLoans = "0";
				}else{
					int iCount = 0;
					for (ICompositeData aczFldComposite : aczList) {
						String aczFldVl = CompositeDataUtils.getValue(aczFldComposite,"stzffshi"); //受托支付方式
//						1--手工支付
//						2--自动支付
//						3--立即支付
						String aczFldVl1 = CompositeDataUtils.getValue(aczFldComposite,"dfzhhzhl"); //对方账号种类
//						1--本行账号
//						2--他行账号
						DEBUG("[stzffshi]受托支付方式[" + aczFldVl + "],[dfzhhzhl]对方账号种类[" + aczFldVl1 + "]");

						//受托支付方式为立即支付且为他行账户,调用二代支付
						if(isEquals(aczFldVl, "3") && isEquals(aczFldVl1, "2")){
							aczMakeLoans = "1";
							//如果存在一条这样的记录 就调用二代
							INFO("受托支付方式为立即支付且为他行账户,调用二代支付");
							iCount ++ ;
						}else{
							INFO("受托支付方式不是立即支付且为他行账户,不调用二代支付");
						}
					}
					if(iCount >= 2){
						succFlag = "0";
						getContext().setValue("LOAN_SUCC_FLAG", succFlag);
						INFO("立即支付中他行卡大于1张,请使用手工支付");
						CompositeDataUtils.setReturn(LoanService,ErrorCodes.CODE_BIP04E0003,"");
						return null;
					}
				}
			}else{
				INFO("不是受托支付,不调用二代支付系统");
				aczMakeLoans = "0";
			}
		}	

		//特殊贷款  垫款开户   供应链垫款开户，不到老核心同步分录
		if( isEquals("3002101000102", LoanService) || isEquals("3002101000103", LoanService) || isEquals("3002101000105", LoanService)){
			aczMakeLoans = "0";
		} 
		
		/**************以上内容为各贷款单独交易前处理 end*******************/
		
//	    a.把请求方系统编码ReqSysID的值写入OrgSysID发起方系统编码   b.请求方系统编码ReqSysID统一变更为B98 BIP
		getContext().setValue("OrgSysID", CompositeDataUtils.getValue(ReqLoanService,"ReqSysHead/ReqSysID"));
		getContext().setValue("ReqSysID","B98");
		
		getContext().setValue("MAKE_LOANS_FLAG", aczMakeLoans);
		getContext().setValue("TAKE_TYPE_FLAG", aczTakeType); 
		getContext().setValue("LOAN_SUCC_FLAG", succFlag);
		getContext().setValue("TAKE_TYPE_RES", takeTypeRes);  //判断限额的调用结果，放款类都要去限额
		
		INFO("限额调用标志_TAKE_TYPE_FLAG=[" + getContext().getValue("TAKE_TYPE_FLAG")+"]");
		INFO("放款标志_MAKE_LOANS_FLAG=[" + getContext().getValue("MAKE_LOANS_FLAG") + "]");
		INFO("基础服务校验标志_LOAN_SUCC_FLAG=[" + getContext().getValue("LOAN_SUCC_FLAG") + "]");
		INFO("限额结果判断标志_TAKE_TYPE_RES=[" + getContext().getValue("TAKE_TYPE_RES")+"]");
		
		INFO("调用基础服务结束[LoanGeneralInitBaseService]......");
		return null;
	}

	
	/**
	 * 判断是否相等
	 * @param srcStr 源值
	 * @param destStr  目标值
	 * @return
	 */
	public Boolean isEquals(String srcStr,String destStr){
		if((!"".equalsIgnoreCase(srcStr)) && !"".equalsIgnoreCase(destStr)){
			return destStr.equalsIgnoreCase(srcStr);
		}
		return false;
	}
	
	/**
	 * 判断是否为空 必输
	 * @param srcStr
	 * @return
	 */
	public Boolean isNull(String srcStr){
		if((srcStr != null && srcStr != "")){
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
	public void DEBUG(String mess){
		if(log.isDebugEnabled()){
			log.debug(mess);
		}
	}
	
	public void INFO(String mess){
		if(log.isInfoEnabled()){
			log.info(mess);
		}
	}
}
