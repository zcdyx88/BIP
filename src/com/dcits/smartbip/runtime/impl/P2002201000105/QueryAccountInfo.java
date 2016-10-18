package com.dcits.smartbip.runtime.impl.P2002201000105;

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
import com.dcits.smartbip.utils.CompositeDataUtils;
/**
 * 换签根据客户号查询出所有该客户号下的帐号信息
 * @author srxhx611
 *
 */
public class QueryAccountInfo extends AbstractBaseService implements IService{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(QueryAccountInfo.class);
		
	@Override
	public String getId(){
		return "QueryAccountInfo";
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
			log.info("开始调用基础服务[QueryAccountInfo]......");
		}
		
		
		//获取组合服务码
		String modifySignType = (String)SessionContext.getContext().getValue("ModifySignType");
		//获取组合服务     请求    对象
		ICompositeData ReqModifySignType = (ICompositeData) SessionContext
				.getContext().getValue("Req" +modifySignType);
		
		// 获得综合解约请求数据对象
		ICompositeData Rsp3001300000103 = (ICompositeData) SessionContext
				.getContext().getValue("Rsp3001300000103");
		
		
		/** 获取换签种类     新旧帐号**/
		String AcctNo = (String)CompositeDataUtils.getValue(ReqModifySignType, "ReqAppBody/AcctNo");
		
		String NewAcctNo = (String)CompositeDataUtils.getValue(ReqModifySignType, "ReqAppBody/NewAcctNo");
		
		/**获取该客户号下的所有帐号信息，并对其新旧帐号进行校验，看是否在该客户号下**/
		String sameCustFlag = "0"; //同一个客户下标志
		if (null == Rsp3001300000103){                          
			log.error("后端返回结果为空!");
		}
		List<ICompositeData> fzzhxxmcArray = CompositeDataUtils.getByPath(Rsp3001300000103, "RspAppBody/fzzhxxmc");
		String kehuzhao = null;
		if(fzzhxxmcArray.size() == 0 ){
			if(log.isDebugEnabled()){
				log.debug("查询客户号下所有账户记录为空");
				CompositeDataUtils.setReturn(modifySignType, ErrorCodes.CODE_BIP03E0002, "");
			}
		}else{
			boolean hasOldAccFlag = false;
			boolean hasNewAccFlag = false;
			for (ICompositeData fzzhxxmc : fzzhxxmcArray) {
				kehuzhao = CompositeDataUtils.getValue(fzzhxxmc,"kehuzhao");
				if(kehuzhao!=null ){
					if(kehuzhao.equalsIgnoreCase(AcctNo))
					{
						hasOldAccFlag = true;
					}
					else if(kehuzhao.equalsIgnoreCase(NewAcctNo))
					{
						hasNewAccFlag = true;
					}					
				}				
			}
			if(hasOldAccFlag && hasNewAccFlag)
			{
				log.info("旧账号AcctNo=["+AcctNo+"],新帐号NewAcctNo=["+NewAcctNo+"]" + "新旧帐号都属于该客户号");
				sameCustFlag = "1";
			}
			else
			{
				log.info("新旧帐号不在该客户号下");
				CompositeDataUtils.setReturn(modifySignType, ErrorCodes.CODE_BIP03E0002, "");
			}
		}
		SessionContext.getContext().setValue("sameCustFlag", sameCustFlag);
		if (log.isInfoEnabled()) {
			log.info("结束调用基础服务[QueryAccountInfo]......");
		}
		return null;
	}
}
