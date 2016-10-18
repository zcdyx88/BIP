package com.dcits.smartbip.runtime.impl.p2002201000106;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.impl.SignObject;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.runtime.model.impl.SoapCompositeData;
import com.dcits.smartbip.utils.CompositeDataUtils;

public class CropExchangeSignGeneralInit extends AbstractBaseService implements IService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(CropExchangeSignGeneralInit.class);

	@Override
	public String getId() {
		return "CropExchangeSignGeneralInit";
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
		
		initProductList();

		if (log.isInfoEnabled()) {
			log.info("开始调用基础服务[CropExchangeSignGeneralInit]......");
		}
		
		
		//初始化RspSysHead的返回信息
		ICompositeData Rsp2002201000106 = (ICompositeData) SessionContext.getContext().getValue("Rsp2002201000106");
		if (null == Rsp2002201000106)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			Rsp2002201000106  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			Rsp2002201000106.setId("Rsp2002201000106");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			Rsp2002201000106.setxPath("/Rsp2002201000106");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			getContext().setValue("Rsp2002201000106",Rsp2002201000106);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		}
		CompositeDataUtils.setValue(Rsp2002201000106, "RspSysHead/TransStatus", "S");
		CompositeDataUtils.setValue(Rsp2002201000106, "RspSysHead/RetCode", "000000");
		CompositeDataUtils.setValue(Rsp2002201000106, "RspSysHead/RetMsg", "通讯成功,产品签约结果详见结果数组");	
		
	
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[CropExchangeSignGeneralInit]......");
		}

		return null;
	}

	
	/*
	 * 对公换签，根据产品代码选择原子服务
	 */
	private void initProductList()
	{
		List<SignObject> productList = new ArrayList<SignObject>();
		productList.add(new SignObject("000003","1003200000301","短信银行"));
		productList.add(new SignObject("000008","3006200002803","第三方存管"));	
		productList.add(new SignObject("000020","3005200000109","基金 "));
		productList.add(new SignObject("000021","3005200000104","理财"));
		SessionContext.getContext().setValue("productList", productList);
	}
	
}
