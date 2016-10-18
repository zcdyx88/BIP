package com.dcits.smartbip.runtime.impl.p2002201000106;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.utils.CompositeDataUtils;
import com.dcits.smartbip.utils.PropertiesUtils;

/**
 *  对公综合换签参数设置处理
 *  
 * @author srxhx273
 *
 */
public class CorpExchangeSignSetParamsHandle extends AbstractBaseService implements IService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4886128913055452736L;
	/**
	 * 日志
	 */
	private static final Log log = LogFactory.getLog(CorpExchangeSignSetParamsHandle.class);
	

	@Override
	public String getId() {
		return "CorpExchangeSignSetParamsHandle";
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
			log.info("开始调用基础服务[CorpExchangeSignSetParamsHandle]......");
		}
		
		//当前换签产品代码
		String exchgSignProCode = (String)getContext().getValue("exchgSignProCode");
		try {
			ICompositeData Req2002201000106 = (ICompositeData) SessionContext.getContext().getValue("Req2002201000106");
			String ReqAcctNo = CompositeDataUtils.getValue(Req2002201000106, "ReqAppBody/AcctNo");//上送签约帐号
			String AcctSrlNo = CompositeDataUtils.getValue(Req2002201000106, "ReqAppBody/AcctSrlNo");//上送账户序号
			String AcctTp = CompositeDataUtils.getValue(Req2002201000106, "ReqAppBody/AcctTp");//账户类型
			
			HashMap<String, String> acctSrlNoMap = PropertiesUtils.getProductionAcctSrlNo();
			//旧账户判断
			if("A".equals(AcctTp)){
				getContext().setValue("ECIF_AcctNo", ReqAcctNo+AcctSrlNo);
				//私有结构体中是否存在子账户序号：0-不存在（需要送组合账户），1-存在（需要送组合账户）
				if("0".equals(acctSrlNoMap.get(exchgSignProCode))){
					getContext().setValue("CorpComBinationAcctNo", ReqAcctNo+AcctSrlNo);
				}else{
					getContext().setValue("CorpComBinationAcctNo", ReqAcctNo);
				}
			}else{
				getContext().setValue("ECIF_AcctNo", ReqAcctNo);
				getContext().setValue("CorpComBinationAcctNo", ReqAcctNo);
			}
			log.debug("对公综合换签ECIF维护旧账号为["+getContext().getValue("ECIF_AcctNo")+"]");
			log.debug("对公综合换签上送后台系统旧账号为["+getContext().getValue("CorpComBinationAcctNo")+"]");
			
			String NewAcctNo = CompositeDataUtils.getValue(Req2002201000106, "ReqAppBody/NewAcctNo");//上送签约新帐号
			String NewAcctSrlNo = CompositeDataUtils.getValue(Req2002201000106, "ReqAppBody/NewAcctSrlNo");//上送新账户序号
			String NewAcctTp = CompositeDataUtils.getValue(Req2002201000106, "ReqAppBody/NewAcctTp");//新账户类型
			//新账户判断
			if("A".equals(NewAcctTp)){
				getContext().setValue("ECIF_NewAcctNo", NewAcctNo+NewAcctSrlNo);
				//私有结构体中是否存在子账户序号：0-不存在（需要送组合账户），1-存在（需要送组合账户）
				if("0".equals(acctSrlNoMap.get(exchgSignProCode))){
					getContext().setValue("CorpComBinationNewAcctNo", NewAcctNo+NewAcctSrlNo);
				}else{
					getContext().setValue("CorpComBinationNewAcctNo", NewAcctNo);
				}
			}else{
				getContext().setValue("ECIF_NewAcctNo", NewAcctNo);
				getContext().setValue("CorpComBinationNewAcctNo", NewAcctNo);
			}
			log.debug("对公综合换签ECIF维护新账号为["+getContext().getValue("ECIF_NewAcctNo")+"]");
			log.debug("对公综合换签上送后台系统新账号为["+getContext().getValue("CorpComBinationNewAcctNo")+"]");
		} catch (Exception e) {
			log.error("对公综合换签参数设置处理:",e);
			throw new InvokeException();
		}finally{
			if (log.isInfoEnabled()) {
				log.info("调用基础服务结束[CorpExchangeSignSetParamsHandle]......");
			}
		}
		return null;
	}
	
}






