package com.dcits.smartbip.runtime.impl.P2002201000105;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.dcits.smartbip.exception.ErrorCodes;
import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.utils.CompositeDataUtils;

/**
 * 判断新旧卡号是不是属于同一个账户
 * @author srxhx273
 *
 */
public class PrivateExchgChkNewOldCard extends AbstractBaseService implements IService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4206181788467572622L;
	private static final Log log = LogFactory.getLog(PrivateExchgChkNewOldCard.class);

	@Override
	public String getId() {
		return "PrivateExchgChkNewOldCard";
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
			log.info("开始调用基础服务[PrivateExchgChkNewOldCard]......");
		}
		/**
		 * BIP调用核心（3003300000311）换卡信息查询交易，使用新卡号查询，检查旧卡号是否在返回列表中，
		 * 如果存在，则调用后端换卡交易时字段” Reserve”送0，反之则送1
		 */
		String modifySignType = (String)SessionContext.getContext().getValue("ModifySignType");
		String Reserve = "1";
		try {
			ICompositeData Rsp3003300000311 = (ICompositeData) SessionContext.getContext().getValue("Rsp3003300000311");
			if (null == Rsp3003300000311){                          
				log.error("调用后端服务3003300000311(换卡信息查询)返回结果为空!");
				CompositeDataUtils.setReturn(modifySignType, ErrorCodes.CODE_BIP00T0001, "对私综合签约账户绑定关系变更失败");
				return null;
			}
			ICompositeData ReqCD = (ICompositeData) SessionContext.getContext().getValue("Req"+modifySignType);
			String acctNo = CompositeDataUtils.getValue(ReqCD, "ReqAppBody/AcctNo");
			if(acctNo == null || "".equals(acctNo.trim())){
				log.error("上送旧卡为空");
				CompositeDataUtils.setReturn(modifySignType, ErrorCodes.CODE_BIP00T0001, "对私综合签约账户绑定关系变更失败");
				return null;
			}
			String retCode = CompositeDataUtils.getValue(Rsp3003300000311, "RspSysHead/RetCode");
			String retMsg = CompositeDataUtils.getValue(Rsp3003300000311, "RspSysHead/RetMsg");
			if("000000".equals(retCode)){
				List<ICompositeData> listnm00 = CompositeDataUtils.getByPath(Rsp3003300000311, "RspAppBody/listnm00");
				if(listnm00 == null || listnm00.size() == 0){
					log.info("调用后端服务3003300000311(换卡信息查询)返回listnm00数组为空，则认为旧卡号不存在，Reserve=1");
					return null;
				}
				String oldAcctNo = "";
				for(ICompositeData listnm : listnm00){
					oldAcctNo = CompositeDataUtils.getValueNoNull(listnm, "kahaoooo");
					if(oldAcctNo.equals(acctNo)){
						Reserve = "0";
						break;
					}
				}
			}else if("CdE0003".equals(retCode)){//该卡没有换卡信息
				log.info("该卡没有换卡信息，设置Reserve=1");
			}else{
				log.error("原子服务3003300000311(换卡信息查询)交易失败，错误码["+retCode+"]，错误信息["+retMsg+"]");
				CompositeDataUtils.setReturn(modifySignType, retCode, retMsg);
				return null;
			}
		} catch (Exception e) {
			log.error("调用基础服务[PrivateExchgChkNewOldCard]异常：", e);
			CompositeDataUtils.setReturn(modifySignType, ErrorCodes.CODE_BIP00T0001, "对私综合签约账户绑定关系变更失败");
		}finally{
			getContext().setValue("Reserve", Reserve);
			if (log.isInfoEnabled()) {
				log.info("调用基础服务结束[PrivateExchgChkNewOldCard]......");
			}
		}
		
		return null;
	}
	
}
