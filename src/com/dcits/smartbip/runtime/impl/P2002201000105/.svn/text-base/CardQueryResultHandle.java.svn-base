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
 * 卡凭证信息查询结果处理
 * @author srxhx273
 *
 */
public class CardQueryResultHandle extends AbstractBaseService implements IService{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1379541078453825761L;
	/**
	 * 
	 */
	private static final Log log = LogFactory.getLog(CardQueryResultHandle.class);
		
	@Override
	public String getId(){
		return "CardQueryResultHandle";
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
			log.info("开始调用基础服务[CardQueryResultHandle]......");
		}
		String isCardFlag = "1";
		String modifySignType = (String)SessionContext.getContext().getValue("ModifySignType");
		try {
			ICompositeData Rsp3003300000305 = (ICompositeData) SessionContext.getContext().getValue("Rsp3003300000305");
			if (null == Rsp3003300000305){                          
				log.error("调用后端服务3003300000305(卡凭证信息查询)返回结果为空!");
				CompositeDataUtils.setReturn(modifySignType, ErrorCodes.CODE_BIP00T0001, "对私综合签约账户绑定关系变更失败");
				return null;
			}
			String retCode = CompositeDataUtils.getValue(Rsp3003300000305, "RspSysHead/RetCode");
			String retMsg = CompositeDataUtils.getValue(Rsp3003300000305, "RspSysHead/RetMsg");
			if("000000".equals(retCode)){
				String kadxiang = CompositeDataUtils.getValue(Rsp3003300000305, "RspAppBody/kadxiang");//卡对象:0个人卡, 1单位卡
				String kadengji = CompositeDataUtils.getValue(Rsp3003300000305, "RspAppBody/kadengji");//卡等级:0--主卡, 1--附卡
				if("1".equals(kadxiang) || "1".equals(kadengji)){
					//判断换签产品是否仅为短信和卡卡转账
					List<String> exchgSignProCodes = (List<String>)getContext().getValue("exchgSignProCodes");
					for(String signCode : exchgSignProCodes){
						if(!"000003".equals(signCode) && !"000007".equals(signCode)){
							log.info("附卡/单位结算卡不允许签约"+signCode+"产品");
							CompositeDataUtils.setReturn(modifySignType, ErrorCodes.CODE_BIP00T0001, "附卡/单位结算卡不允许签约"+signCode+"产品");
							return null;
						}
					}
				}
				isCardFlag = "0";
			}else{
				log.info("原子服务3003300000305(卡凭证信息查询)交易失败，错误码["+retCode+"]，错误信息["+retMsg+"]");
				CompositeDataUtils.setReturn(modifySignType, retCode, retMsg);
				return null;
			}
		} catch (Exception e) {
			log.error("卡凭证信息查询结果处理异常：", e);
			isCardFlag = "1";
			CompositeDataUtils.setReturn(modifySignType, ErrorCodes.CODE_BIP00T0001, "对私综合签约账户绑定关系变更失败");
		}finally{
			getContext().setValue("isCardFlag", isCardFlag);
			
			if (log.isInfoEnabled()) {
				log.info("结束调用基础服务[CardQueryResultHandle]......");
			}
		}
		return null;
	}
}
