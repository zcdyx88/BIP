package com.dcits.smartbip.runtime.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.utils.CompositeDataUtils;

public class CheckSigningProduct extends AbstractBaseService implements IService{

	private static final Log log = LogFactory.getLog(CheckSigningProduct.class);

	@Override
	public String getId() {
		return "CheckSigningProduct";
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
			log.info("开始调用基础服务[CheckSigningProduct]......");
		}
		
		ICompositeData Req2002201000101 = (ICompositeData) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		.getContext().getValue("Req2002201000101");
		ICompositeData Rsp2002300000401 = (ICompositeData) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		.getContext().getValue("Rsp2002300000401");
		
		List<String> signCodeTypes = new ArrayList<String>();
		List<ICompositeData> signProCdArray = CompositeDataUtils.getByPath(
				Req2002201000101, "ReqAppBody/PrivSignProCdArray");
		
		for (ICompositeData signComposite : signProCdArray) {
			String signCodeType = CompositeDataUtils.getValue(signComposite,
					"SignProTp");
			signCodeTypes.add(signCodeType);
		}
		List<ICompositeData> signProCdArray1 = CompositeDataUtils.getByPath(
				Rsp2002300000401, "RspAppBody/GRP_SYNTHESIS_INFO");
		for (ICompositeData signComposite1 : signProCdArray1) {
			String signCodeType1 = CompositeDataUtils.getValue(signComposite1,
					"SIGN_TYPE");
			signCodeTypes.add(signCodeType1);
		}
		if (log.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder();
			sb.append("待签约产品和已签约产品代码为[");
			for (String signCodeType : signCodeTypes) {
				sb.append(signCodeType).append(",");
			}
			sb.append("]");
			log.debug(sb.toString());
		}
		
		String temp = "";
		for(int i=0;i<signCodeTypes.size()-1;i++){
			temp = signCodeTypes.get(i);
			 for(int j = i+1;j<signCodeTypes.size();j++){
				 if(temp.equals(signCodeTypes.get(j)))
					 SessionContext.getContext().setValue("AllProductMutual","true");
			 }
		}
		SessionContext.getContext().setValue("AllProductMutual","false");
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[CheckSigningProduct]......");
		}
		
		return null;
		
	}
}
