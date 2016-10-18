package com.dcits.smartbip.runtime.impl.P2002201000105;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;

/**
 * 设置基金类换签产品代码
 * @author srxhx273
 *
 */
public class PrivateSetJJSignCodeHandle extends AbstractBaseService implements IService{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5139230743153293123L;
	private static final Log log = LogFactory.getLog(PrivateSetJJSignCodeHandle.class);
		
	@Override
	public String getId(){
		return "PrivateSetJJSignCodeHandle";
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
			log.info("开始调用基础服务[PrivateSetJJSignCodeHandle]......");
		}
		String signCode = (String)getContext().getValue("jiJinCodeToECIF");
		log.info("本次去ECIF维护换签产品代码为["+signCode+"]");
		getContext().setValue("exchgSignProCode", signCode);
		if (log.isInfoEnabled()) {
			log.info("结束调用基础服务[PrivateSetJJSignCodeHandle]......");
		}
		return null;
	}
}
