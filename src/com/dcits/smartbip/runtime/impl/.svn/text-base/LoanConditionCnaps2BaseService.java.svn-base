package com.dcits.smartbip.runtime.impl;

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

/***
 * 一般贷款开户
 * 
 * @author xhx102
 * 
 */
public class LoanConditionCnaps2BaseService extends AbstractBaseService
		implements IService {
	private static final Log log = LogFactory
			.getLog(LoanConditionCnaps2BaseService.class);
	private static final long serialVersionUID = 1L;

	@Override
	public String getId() {
		return "LoanConditionCnaps2BaseService";
	}

	@Override
	public String getType() {
		return "base";
	}

	@Override
	public void setType(String type) {

	}

	/***
	 * 判断是否走二代
	 */
	@Override
	public Object execute(Object req) throws InvokeException {
		String Conditions = "0"; //默认不走
		if (log.isInfoEnabled()) {
			log.info("开始调用基础服务[LoanConditionCnaps2BaseService]......");
		}
		//获取贷款类型
		String LoanService = "";
		LoanService =  (String) getContext().getValue("__LOAN_KIND__");	
		 String makeLoansFlag = (String)getContext().getValue("MAKE_LOANS_FLAG");
		 //是否执行核心
		 String ncbsFlag = (String)getContext().getValue("CONDITION_NCBS");
		 if(log.isDebugEnabled()){
			 log.debug("核心执行标志[" + ncbsFlag  + "],放款标志[" + makeLoansFlag + "]取3002100000103服务返回");
		 }
		 if("1".equals(ncbsFlag)){
			 ICompositeData RespLoanService = (ICompositeData) getContext().getValue("Rsp3002100000103");
		 	 String RetCode = CompositeDataUtils.getValue(RespLoanService, "RspSysHead/RetCode");
			 if("1".equalsIgnoreCase(makeLoansFlag) && "000000".equalsIgnoreCase(RetCode)){
				 if(log.isInfoEnabled()){
					 log.info("核心执行成功且受托支付放款,需要到二代");
				 }
				 Conditions = "1";
				 //只映射行外账户
					ICompositeData Req3004100001820 = (ICompositeData) SessionContext.getContext().getValue("Req3004100001820");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
					if (null == Req3004100001820)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
					{               
						Req3004100001820  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
						Req3004100001820.setId("Req3004100001820");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
						Req3004100001820.setxPath("/Req3004100001820");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
						getContext().setValue("Req3004100001820",Req3004100001820);   
					} 
				 //如果需要到二代，对金额进行格式化,只有行外帐号才会取帐号，取单笔行外账户到二代
				 ICompositeData ReqLoanService = (ICompositeData) getContext().getValue("Req"+LoanService);
				 List<ICompositeData> aczList = CompositeDataUtils.getByPath(
						 ReqLoanService, "ReqAppBody/lstdkstzf");
				 for (ICompositeData aczFldComposite : aczList) {
					 String dfzhhzhlFld = CompositeDataUtils.getValue(aczFldComposite,"dfzhhzhl"); //对方账号种类
					 String stzfjineFld = CompositeDataUtils.getValue(aczFldComposite,"stzfjine"); //受托金额
					 if(log.isDebugEnabled()){
						 log.debug("对方账号种类为[" +  dfzhhzhlFld + "]");
					 }
					 if("2".equalsIgnoreCase(dfzhhzhlFld)){//1 行内  2行外
						 //对整个数组进行处理，包含金额
							//如果上送为空，返回0.00
							if (stzfjineFld == null || "null".equals(stzfjineFld) || "".equals(stzfjineFld)) {
								if(log.isDebugEnabled()){
									log.debug("[stzfjine]源金额为空.");
								}
								stzfjineFld = "0.00";
							}else{
								stzfjineFld = formatAmt(stzfjineFld,2);
							}
					
							CompositeDataUtils.setValue(Req3004100001820,"ReqAppBody/Amount", stzfjineFld);//金额
							CompositeDataUtils.copy(aczFldComposite, Req3004100001820,"dfzhhkhh", "ReqAppBody/Payee_Brno");
							CompositeDataUtils.copy(aczFldComposite, Req3004100001820,"dfzhhmch", "ReqAppBody/Payee_Name");
							CompositeDataUtils.copy(aczFldComposite, Req3004100001820,"dfzhangh", "ReqAppBody/Payee_Actno");
							CompositeDataUtils.copy(aczFldComposite, Req3004100001820,"dfzhhkhh", "ReqAppBody/Payee_Acc_Brno");
							CompositeDataUtils.copy(aczFldComposite, Req3004100001820,"dfzhkhhm", "ReqAppBody/Payee_Acc_Brname");
					 }
				 }
				//其中Payer_Actno付款人账号  Payer_Name付款人名称取的核心返回的lstdkstzf 这个list的zjzrzhao  
					List<ICompositeData> aczNcbsList = CompositeDataUtils.getByPath(
							RespLoanService, "RspAppBody/lstdkstzf");
					 for (ICompositeData aczNcbsFldComposite : aczNcbsList) {
							CompositeDataUtils.copy(aczNcbsFldComposite, Req3004100001820,"zjzrzhao", "ReqAppBody/Payer_Actno");
							CompositeDataUtils.copy(aczNcbsFldComposite, Req3004100001820,"zjzrzhmc", "ReqAppBody/Payer_Name");
					 }
			}else{
				if(log.isInfoEnabled()){
					 log.info("核心执行失败或者没有进行放款,不到二代");
					 
				 }
			}
		 }else{
			 if(log.isDebugEnabled()){
				 log.debug("核心没有执行,不去二代");
			 }
		 }
		getContext().setValue("CONDITION_CNAPS2", Conditions);

		if (log.isInfoEnabled()) {
			log.info("CONDITION_CNAPS2=[" + Conditions + "]");
			log.info("调用基础服务结束[LoanConditionCnaps2BaseService]......");
		}
		return null;

	}
	
	public String formatAmt(String srcAmt,int scale){
		//格式正确
		String destAmt = "0.00";
		String regex = "\\-?\\d*[.]?\\d+"; 
		if(!srcAmt.toString().matches(regex)){
			log.error("无法识别金额数据[" + srcAmt +"].");
			return srcAmt;
		}
		//如果没有小数部分，直接加.00返回
		if(srcAmt.indexOf(".")==-1){
			return srcAmt +".00";
		}
		//有小数部分进行处理
		String[] str = srcAmt.split("\\.");
		int len = str[1].length();
		if( len > scale){
			destAmt = str[0] + "." + str[1].substring(0,2);
		}else{			
		//返回的数据长度小于精度，则需补位
			destAmt = srcAmt + fillChar("0",scale-len);
		}
		if(log.isDebugEnabled()){
			log.debug("源金额[" + srcAmt + "]转换后的目标金额为[" + destAmt + "]");
		}
		return destAmt;
	}
	
	/**
	 * 按照需要补位的个数，组装
	 * @param str
	 * @param num
	 * @return
	 */
	public static String fillChar(String str,int num){
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < num; i++) {
			sb.append(str);
		}
		return sb.toString();
	}

}
