package com.dcits.smartbip.runtime.impl.P2002201000105;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.impl.SignModifyGeneralFilter;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
/**
 * 签约视图查询结果处理
 * @author srxhx611
 *
 */
public class NewSignViewDeal extends AbstractBaseService implements IService {
	private static final Log log = LogFactory.getLog(NewSignViewDeal.class);
	@Override
	public String getType() {
		return "base";
	}

	@Override
	public void setType(String type) {

	}
	@Override
	public String getId(){
		return "NewSignViewDeal";
	}
	
	@Override
	public Object execute(Object req) throws InvokeException {
		if (log.isInfoEnabled()) {
			log.info("开始调用基础服务[NewSignViewDeal]......");
		}
		String SignViewResult = "0";
		String newMark = SessionContext.getContext().getValue("newMark").toString();		
		if("true".equalsIgnoreCase(newMark)){
			SignViewResult = "1";
		}else{
			log.info("交易失败！");
			return null;
		}
		log.info("所有待换签的产品在新账户上都没有签约关系");
//		SessionContext.getContext().setValue("SignViewResult", SignViewResult);
		SessionContext.getContext().setValue("SignViewResult", "1");
		
		if (log.isInfoEnabled()) {
			log.info("结束调用基础服务[NewSignViewDeal]......");
		}
		return null;
	}
	

}
