package com.dcits.smartbip.runtime.impl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.buss.accountopen.entity.BipTransAccountOpen;
import com.dcits.smartbip.buss.accountopen.service.BipTransAccountOpenServcie;
import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.utils.ApplicationUtils;
import com.dcits.smartbip.utils.CompositeDataUtils;

/***
 * 贷款录入撤销,保存流水号与录入编号对应关系 1.需要根据重启标志进行备份到文件 2.需要定时备份map
 * 
 * @author xhx102
 * 
 */
public class LoanPutoutNoRecordBaseService extends AbstractBaseService
		implements IService {
	private static final Log log = LogFactory
			.getLog(LoanPutoutNoRecordBaseService.class);
	private static final long serialVersionUID = 1L;
//	String fileName = "PutoutNoRecord.properties";

	@Override
	public String getId() {
		return "LoanPutoutNoRecordBaseService";
	}

	@Override
	public String getType() {
		return "base";
	}

	@Override
	public void setType(String type) {

	}

	/***
	 * 获取录入编号，根据流水号,并赋值给撤销用的录入编号 每次执行贷款录入就要进行保存
	 */
	@Override
	public Object execute(Object req) throws InvokeException {
		if (log.isInfoEnabled()) {
			log.info("开始调用基础服务[LoanPutoutNoRecordBaseService]......");
		}
		// PutoutNo 出账号 ChannelNo 系统标示 : 在执行冲正限额前，判断是否调用限额系统成功允许冲正
		//出账的时候 保存 ReqLocalHead/waiblius 以及响应的 RspLocalHead/jiaoyils跟 出账号 的对应关系
		//录入撤销的时候，取响应的交易流水，冲正的通用撤销与通用冲正 用核心响应的流水好
		String LoanService = "";
		LoanService =  (String) getContext().getValue("__LOAN_KIND__");
		if(log.isDebugEnabled()){
			log.debug("贷款类型[" + LoanService + "]"); 
		}
		ICompositeData ReqLoanService = (ICompositeData) getContext().getValue("Req" + LoanService);
		ICompositeData RespLoanService = null;
		String outAcctNo = CompositeDataUtils.getValue(ReqLoanService,"ReqAppBody/dkczhzhh");
		String reqSeqNo = "";
		String rspSeqNo = "";
		
	
		//请求的
		reqSeqNo = CompositeDataUtils.getValue(ReqLoanService,"ReqLocalHead/waiblius");
		if(reqSeqNo!=null){
			reqSeqNo = reqSeqNo.trim();
		}
		
		if(log.isDebugEnabled()){
			log.debug("记录请求流水号[" + reqSeqNo + "]");
		}
		
		//响应的，不同的核心不用的记录
		if("3002101000101".equals(LoanService) ){
			//贷款开户
			RespLoanService = (ICompositeData) getContext().getValue("Rsp3002100000103");
		}else if("3002101000102".equals(LoanService)){
			//特殊贷款开户
			RespLoanService = (ICompositeData) getContext().getValue("Rsp3002100000104");
		}else if("3002101000103".equals(LoanService)){
			//垫款贷款开户
			//直接到核心，不到限额
			RespLoanService = (ICompositeData) getContext().getValue("Rsp3002100000105");
		}else if("3002101000104".equals(LoanService)){
			//银团贷款开户
			RespLoanService = (ICompositeData) getContext().getValue("Rsp3002100000102");
		}else if("3002101000105".equals(LoanService)){
			//供应链垫款开户
			//直接到核心。不到限额
			RespLoanService = (ICompositeData) getContext().getValue("Rsp3002100000106");
		}
		
		if(RespLoanService != null){
			rspSeqNo = CompositeDataUtils.getValue(RespLoanService,"RspLocalHead/jiaoyils");
			if(rspSeqNo!=null){
				rspSeqNo = rspSeqNo.trim();
			}
		}else{
			if(log.isDebugEnabled()){
				log.debug("没有返回交易流水，不记录");
			}
		}
		
		if(log.isDebugEnabled()){
			log.debug("记录核心流水号[" + rspSeqNo + "]");
		}
		
		if(rspSeqNo != null && !"".equals(rspSeqNo) && !"null".equals(rspSeqNo)){
			if(log.isDebugEnabled()){
				log.debug("出账号[" + outAcctNo + "]对应的核心流水号[" +  rspSeqNo+ "]");
			}
		}else{
			if(log.isDebugEnabled()){
				log.debug("出账号[" + outAcctNo + "]对应的核心流水号为空");
			}
		}
	
		if(reqSeqNo != null && !"".equals(reqSeqNo) && !"null".equals(reqSeqNo)){
			if(log.isDebugEnabled()){
				log.debug("出账号[" + outAcctNo + "]对应的交易流水号[" + reqSeqNo + "]");
			}
		}else{
			if(log.isDebugEnabled()){
				log.debug("出账号[" + outAcctNo + "]对应的交易流水号为空");
			}
		}
		/** save to db **/
		BipTransAccountOpenServcie service = (BipTransAccountOpenServcie) ApplicationUtils.getInstance()
		.getBean("bipTransAccountOpenServcie");
		String now = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new java.util.Date());
		BipTransAccountOpen bipTransAccountOpen = new BipTransAccountOpen(reqSeqNo,outAcctNo,rspSeqNo,now);
		service.save(bipTransAccountOpen);
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[LoanPutoutNoRecordBaseService]......");
		}
		return null;

	}
}
