package com.dcits.smartbip.runtime.impl.P5001201000101;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.runtime.model.impl.SoapCompositeData;
import com.dcits.smartbip.utils.CompositeDataUtils;

public class SetCoreDataToIpp extends AbstractBaseService implements IService{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6418831597147991697L;
	private static final Log log = LogFactory.getLog(SetCoreDataToIpp.class);
	
	@Override
	public String getId() {
		return "SetCoreDataToIpp";
	}

	@Override
	public String getType() {
		return "base";
	}
	
	@Override
	public void setType(String type) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public Object execute(Object req) throws InvokeException {

		if (log.isInfoEnabled()) {
			log.info("开始调用基础服务[SetCoreDataToIpp]......");
		}
		ICompositeData Req5001200001510 = (ICompositeData) SessionContext.getContext().getValue("Req5001200001510");
		if (null == Req5001200001510)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			Req5001200001510  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			Req5001200001510.setId("Req5001200001510");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			Req5001200001510.setxPath("/Req5001200001510");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			getContext().setValue("Req5001200001510",Req5001200001510);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		}
		ICompositeData Req5001201000101 = (ICompositeData) SessionContext.getContext().getValue("Req5001201000101");
		String LTTSDATE = CompositeDataUtils.getValue(Req5001201000101, "ReqAppBody/LTTSDATE");
		String MUBIAOXT = (String) SessionContext.getContext().getValue("MUBIAOXT");
		CompositeDataUtils.setValue(Req5001200001510, "ReqAppBody/LTTSDATE", LTTSDATE);
		CompositeDataUtils.setValue(Req5001200001510, "ReqAppBody/MUBIAOXT", MUBIAOXT);
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[SetCoreDataToIpp]......");
		}
		
		return null;
	}
}
