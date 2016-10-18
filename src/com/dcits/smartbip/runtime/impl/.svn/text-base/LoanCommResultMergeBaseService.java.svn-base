package com.dcits.smartbip.runtime.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;

/***
 * @author xhx102
 * 
 */
public class LoanCommResultMergeBaseService extends AbstractBaseService
		implements IService {
	private static final Log log = LogFactory
			.getLog(LoanCommResultMergeBaseService.class);
	private static final long serialVersionUID = 1L;

	@Override
	public String getId() {
		return "LoanCommResultMergeBaseService";
	}

	@Override
	public String getType() {
		return "base";
	}

	@Override
	public void setType(String type) {

	}

	/***
	 * 合并所有执行了的服务的响应信息，进行合并
	 */
	@Override
	public Object execute(Object req) throws InvokeException {
		if (log.isInfoEnabled()) {
			log.info("开始调用基础服务[LoanCommResultMergeBaseService]......");
		}
  
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[LoanCommResultMergeBaseService]......");
		}
		return null;

	}
}
