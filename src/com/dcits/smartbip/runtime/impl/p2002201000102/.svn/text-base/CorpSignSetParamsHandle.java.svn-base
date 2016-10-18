package com.dcits.smartbip.runtime.impl.p2002201000102;

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
 *  对公综合签约参数设置处理
 *  
 * @author srxhx273
 *
 */
public class CorpSignSetParamsHandle extends AbstractBaseService implements IService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6210868363670987431L;
	/**
	 * 日志
	 */
	private static final Log log = LogFactory.getLog(CorpSignSetParamsHandle.class);
	

	@Override
	public String getId() {
		return "CorpSignSetParamsHandle";
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
			log.info("开始调用基础服务[CorpSignSetParamsHandle]......");
		}
		
		//当前产品代码
		String signCodeType = (String)getContext().getValue("signCodeType");
		try {
			ICompositeData Req2002201000102 = (ICompositeData) SessionContext.getContext().getValue("Req2002201000102");
			String ReqAcctNo = CompositeDataUtils.getValue(Req2002201000102, "ReqAppBody/AcctNo");//上送签约帐号
			String AcctSrlNo = CompositeDataUtils.getValue(Req2002201000102, "ReqAppBody/AcctSrlNo");//上送账户序号
			String AcctTp = CompositeDataUtils.getValue(Req2002201000102, "ReqAppBody/AcctTp");//账户类型
			if("A".equals(AcctTp)){
				HashMap<String, String> acctSrlNoMap = (HashMap<String, String>)getContext().getValue("AcctSrlNoMap");
				if(acctSrlNoMap == null){
					acctSrlNoMap = PropertiesUtils.getProductionAcctSrlNo();
					getContext().setValue("AcctSrlNoMap", acctSrlNoMap);
				}
				getContext().setValue("ECIF_AcctNo", ReqAcctNo+AcctSrlNo);
				//私有结构体中是否存在子账户序号：0-不存在（需要送组合账户），1-存在（需要送组合账户）
				if("0".equals(acctSrlNoMap.get(signCodeType))){
					getContext().setValue("CorpComBinationAcctNo", ReqAcctNo+AcctSrlNo);
				}else{
					getContext().setValue("CorpComBinationAcctNo", ReqAcctNo);
				}
			}else{
				getContext().setValue("ECIF_AcctNo", ReqAcctNo);
				getContext().setValue("CorpComBinationAcctNo", ReqAcctNo);
			}
			log.debug("对公综合签约ECIF维护账号为["+getContext().getValue("ECIF_AcctNo")+"]");
			log.debug("对公综合签约上送后台系统账号为["+getContext().getValue("CorpComBinationAcctNo")+"]");
		} catch (Exception e) {
			log.error("对公综合签约参数设置处理:",e);
			throw new InvokeException();
		}finally{
			if (log.isInfoEnabled()) {
				log.info("调用基础服务结束[CorpSignSetParamsHandle]......");
			}
		}
		return null;
	}
	
}






