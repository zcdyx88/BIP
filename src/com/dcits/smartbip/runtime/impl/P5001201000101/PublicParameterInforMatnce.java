package com.dcits.smartbip.runtime.impl.P5001201000101;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.utils.PropertiesUtils;

public class PublicParameterInforMatnce extends AbstractBaseService implements IService{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2139373558158529048L;
	private static final Log log = LogFactory.getLog(PublicParameterInforMatnce.class);
	@Override
	public String getId() {
		return "PublicParameterInforMatnce";
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
			log.info("开始调用基础服务[PublicParameterInforMatnce]......");
		}
		List<String> MUBIAOXTs = new ArrayList<String>();
		HashMap<String, String>  MUBIAOXTMaps = PropertiesUtils.getMUBIAOXT();
		for(String MUBIAOXTMap : MUBIAOXTMaps.keySet())
			MUBIAOXTs.add(MUBIAOXTMaps.get(MUBIAOXTMap));
		SessionContext.getContext().setValue("MUBIAOXTs", MUBIAOXTs);
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[PublicParameterInforMatnce]......");
		}
		
		return null;
	}
}
