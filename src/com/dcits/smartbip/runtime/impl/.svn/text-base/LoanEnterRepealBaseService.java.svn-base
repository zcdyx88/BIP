package com.dcits.smartbip.runtime.impl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcits.smartbip.buss.accountopen.dao.BipTransAccountOpenDAO;
import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.utils.ApplicationUtils;
import com.dcits.smartbip.utils.CompositeDataUtils;

/***
 * 贷款录入撤销,保存流水号与录入编号对应关系
 * 
 * @author xhx102
 * 
 */
public class LoanEnterRepealBaseService extends AbstractBaseService
		implements IService {
	private static final long serialVersionUID = -386326412120265696L;

	private static final Log log = LogFactory
			.getLog(LoanEnterRepealBaseService.class);

    private BipTransAccountOpenDAO bipTransAccountOpenDAO;
	
	@Override
	public String getId() {
		return "LoanEnterRepealBaseService";
	}

	@Override
	public String getType() {
		return "base";
	}

	@Override
	public void setType(String type) {

	}

	/***
	 * 获取录入编号，根据流水号,并赋值给撤销用的录入编号
	 */
	@Override
	public Object execute(Object req) throws InvokeException {
		if(null == bipTransAccountOpenDAO){
			bipTransAccountOpenDAO = (BipTransAccountOpenDAO) ApplicationUtils.getInstance().getBean("bipTransAccountOpenDAO");
		}
		if (log.isInfoEnabled()) {
			log.info("开始调用基础服务[LoanEnterRepealBaseService]......");
		}
		String LoanService = "";
		LoanService =  (String) getContext().getValue("__LOAN_KIND__");
		if(log.isDebugEnabled()){
			log.debug("贷款类型[" + LoanService + "]"); 
		}
		String outAcctNo = null;
		ICompositeData ReqLoanService = (ICompositeData) getContext().getValue("Req" + LoanService);
	
		String SeqNo = "";
		//出账的时候 保存 ReqLocalHead/waiblius 以及响应的 RspLocalHead/jiaoyils跟 出账号 的对应关系
		//录入撤销的时候，取响应的交易流水，冲正的通用撤销与通用冲正 用核心响应的流水好
		if("3002201000101".equals(LoanService)){
			//贷款开户录入撤销
			if(log.isDebugEnabled()){
				log.debug("取交易流水号,核心返回流水号");
			}
			SeqNo = CompositeDataUtils.getValue(ReqLoanService,"ReqAppBody/jiaoyils");
			if(SeqNo != null){
				SeqNo = SeqNo.trim();
			}else{
				SeqNo = "";
			}
			if(log.isDebugEnabled()){
				log.debug("冲正流水号[" + SeqNo + "]");
			}
			outAcctNo = bipTransAccountOpenDAO.getDkczhzhhByJiaoyils(SeqNo);
		}else if("3002501000102".equals(LoanService)){
			//贷款通用撤销
			if(log.isDebugEnabled()){
				log.debug("取原柜员流水号");
			}
			SeqNo = CompositeDataUtils.getValue(ReqLoanService,"ReqAppBody/ygyliush");
			if(SeqNo != null){
				SeqNo = SeqNo.trim();
			}else{
				SeqNo = "";
			}
			if(log.isDebugEnabled()){
				log.debug("冲正流水号[" + SeqNo + "]");
			}
			outAcctNo = bipTransAccountOpenDAO.getDkczhzhhByWaiblius(SeqNo);
		}else if("3002501000101".equals(LoanService)){ 
			//贷款通用冲正
			if(log.isDebugEnabled()){
				log.debug("取原前置流水号");
			}
			SeqNo = CompositeDataUtils.getValue(ReqLoanService,"ReqAppBody/yqzhlshu");
			if(SeqNo != null){
				SeqNo = SeqNo.trim();
			}else{
				SeqNo = "";
			}
			if(log.isDebugEnabled()){
				log.debug("冲正流水号[" + SeqNo + "]");
			}
			outAcctNo = bipTransAccountOpenDAO.getDkczhzhhByWaiblius(SeqNo);
		}
		
		if(log.isDebugEnabled()){
			log.debug("获取出账号结果[" + outAcctNo + "]");
		}
		CompositeDataUtils.setValue(ReqLoanService, "ReqAppBody/PutoutNo", outAcctNo);
		if (log.isInfoEnabled()) {
			log.info("源流水号keySeq[" + SeqNo +"],录入编号dkczhzhh[" + outAcctNo + "]");
			log.info("调用基础服务结束[LoanEnterRepealBaseService]......");
		}

		
		return null;
	}
}
