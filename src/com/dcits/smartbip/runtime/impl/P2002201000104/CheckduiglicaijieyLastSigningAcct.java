package com.dcits.smartbip.runtime.impl.P2002201000104;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.utils.CompositeDataUtils;

public class CheckduiglicaijieyLastSigningAcct extends AbstractBaseService implements IService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6768844160781559287L;
	private static final Log log = LogFactory.getLog(CheckduiglicaijieyLastSigningAcct.class);

	@Override
	public String getId() {
		return "CheckduiglicaijieyLastSigningAcct";
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
			log.info("开始调用基础服务[CheckduiglicaijieyLastSigningAcct]......");
		}
		//当前产品代码
		String signCodeType = (String)getContext().getValue("signCodeType");
		ICompositeData Rsp2002300000402 = (ICompositeData) SessionContext.getContext().getValue("Rsp2002300000402");
		SessionContext.getContext().setValue("LicaiduigjieyLastSigningAcct", "true");
		int count = 0;
		List<ICompositeData> GRP_SYNTHESIS_INFO = CompositeDataUtils.getByPath(Rsp2002300000402, "RspAppBody/GRP_SYNTHESIS_INFO");
		if(GRP_SYNTHESIS_INFO!=null && GRP_SYNTHESIS_INFO.size()>0){
			for(ICompositeData GRP_SYNTHESIS_INFOS:GRP_SYNTHESIS_INFO){
				String SIGN_TYPE = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFOS, "SIGN_TYPE");
				if(SIGN_TYPE != null && SIGN_TYPE.equals(signCodeType)){	
					count++;
					log.info("产品["+signCodeType+"]已有账户["+CompositeDataUtils.getValue(GRP_SYNTHESIS_INFOS,"SIGN_ACC")+"]签约");
				}
			}
		}
		if(count >1 ){
			SessionContext.getContext().setValue("LicaiduigjieyLastSigningAcct", "false");
			log.info("待解约产品账户["+signCodeType+"]非最后一个签约账户");
		}else{
			log.info("待解约产品账户["+signCodeType+"]为最后一个签约账户");
		}
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[CheckduiglicaijieyLastSigningAcct]......");
		}
		return null;
	}
	
}
