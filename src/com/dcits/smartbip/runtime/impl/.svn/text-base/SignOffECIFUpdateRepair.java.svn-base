package com.dcits.smartbip.runtime.impl;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.utils.CompositeDataUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ECIF更新失败补偿
 * 当ECIF签约关系维护失败时，记录需补偿的解约产品代码信息
 * @author srxhx273
 * 
 */

public class SignOffECIFUpdateRepair extends AbstractBaseService implements IService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6309856187440321704L;
	/**
	 * 
	 */
	private static final Log log = LogFactory.getLog(SignOffECIFUpdateRepair.class);
	
	@Override
	public String getType() {
		return "base";
	}

	@Override
	public void setType(String type) {
		
	}
	
	@Override
	public String getId() {
		return "SignOffECIFUpdateRepair";
	}
	
	@Override
	public Object execute(Object req) throws InvokeException {
		
		if (log.isInfoEnabled()) {
			log.info("开始调用基础服务[SignOffECIFUpdateRepair]......");
		}
		
		// 获得当前综合解约产品代码                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
//		String signCodeType = (String) SessionContext.getContext().getValue("signCodeType");
		
		// 获得综合ECIF签约关系维护请求数据对象并记录入库
		ICompositeData Req2002200000401 = (ICompositeData) SessionContext.getContext().getValue("Req2002200000401");
		//获得综合ECIF签约关系维护响应数据对象
		ICompositeData Rsp2002200000401 = (ICompositeData) SessionContext.getContext().getValue("Rsp2002200000401");
		String RetCode = CompositeDataUtils.getValue(Rsp2002200000401,"RspSysHead/RetCode");
		//
		if (!"000000".equals(RetCode)) {
			log.info("ECIF对私签约关系维护[2002200000401]失败，需要将相关数据进行记录入库以备补偿");
		}else{
			log.info("ECIF对私签约关系维护[2002200000401]成功");
		}
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[SignOffECIFUpdateRepair]......");
		}
		return null;
	}

}
