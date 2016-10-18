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
 * 判断ECIF对公签约关系维护结果
 * 
 * @author srxhx273
 * 
 */

public class CorpSignChkECIFResult extends AbstractBaseService implements IService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6082721842728084643L;
	/**
	 * 
	 */
	private static final Log log = LogFactory.getLog(CorpSignChkECIFResult.class);
	/**
	 * 对公ECIF签约关系维护服务码：Rsp+服务码
	 */
	private static final String ECIF_SERVICE_ID = "Rsp"+"2002200000402";
	@Override
	public String getType() {
		return "base";
	}

	@Override
	public void setType(String type) {
		
	}
	
	@Override
	public String getId() {
		return "CorpSignChkECIFResult";
	}
	
	@Override
	public Object execute(Object req) throws InvokeException {
		
		if (log.isInfoEnabled()) {
			log.info("开始调用基础服务[CorpSignChkECIFResult]......");
		}
		
		String isNeedSignOff = "Y";//是否需要解约：Y-需要，N-不需要
		//当前产品代码
		String signCodeType = (String)getContext().getValue("signCodeType");
		try {
			ICompositeData RspECIF = (ICompositeData) SessionContext.getContext().getValue(ECIF_SERVICE_ID);
			if(null == RspECIF){
				log.info("对公ECIF签约关系维护后台返回为空");
				return null;
			}
			String isSigned = (String)SessionContext.getContext().getValue("IsSigned"+signCodeType);
			String RetCode = CompositeDataUtils.getValue(RspECIF,"RspSysHead/RetCode");
			if ("000000".equals(RetCode)) {
				log.info("ECIF对公签约关系维护成功");
				isNeedSignOff = "N";
			}else{
				if("Y".equals(isSigned)){//已签约产品（非本次签约成功的产品）不需要进行解约
					isNeedSignOff = "N";
					log.info("ECIF对公签约关系维护["+ECIF_SERVICE_ID+"]失败，不需要对本次签约产品进行解约操作");
				}else{
					log.info("ECIF对公签约关系维护["+ECIF_SERVICE_ID+"]失败，需要对本次签约产品进行解约操作");
				}
			}
		} catch (Exception e) {
			log.error("判断ECIF对公签约关系维护结果异常：",e);
			throw new InvokeException();
		}finally{
			SessionContext.getContext().setValue("isNeedSignOff"+signCodeType, isNeedSignOff);
			if (log.isInfoEnabled()) {
				log.info("调用基础服务结束[CorpSignChkECIFResult]......");
			}
		}
		
		return null;
	}

}
