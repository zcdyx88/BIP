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

/*
 * 对公解约时，检查所以待解约产品信息
 * */

public class CropSMSPSignOffReqProcess extends AbstractBaseService implements IService{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8294134866752891708L;
	private static final Log log = LogFactory.getLog(CropSMSPSignOffReqProcess.class);

	@Override
	public String getId() {
		return "CropSMSPSignOffReqProcess";
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
			log.info("开始调用基础服务[CropSMSPSignOffReqProcess]......");
		}
		
		ICompositeData Rsp2002300000402 = (ICompositeData)SessionContext.getContext().getValue("Rsp2002300000402");
		if(null == Rsp2002300000402)
		{
			return null;
		}
		else
		{
			List<ICompositeData> signAccList = CompositeDataUtils.getByPath(Rsp2002300000402, "RspAppBody/GRP_SYNTHESIS_INFO");
			if(null != signAccList)
			{
				int count = 0;
				for(ICompositeData tmp : signAccList)
				{
		
					if("000003".equalsIgnoreCase((String)CompositeDataUtils.getValue(tmp,"SIGN_TYPE")))
					{
						count++;
						continue;
					}
				}
				
				if(count > 1)
				{
					SessionContext.getContext().setValue("CropSMS_LogOffFlag", "3");						
				}
				else
				{
					SessionContext.getContext().setValue("CropSMS_LogOffFlag", "4");					
				}
				
				if(log.isDebugEnabled())
				{
					log.debug("当前客户号下签约短信的账号数为"+count);
				}	
			}
			
		}		
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[CropSMSPSignOffReqProcess]......");
		}

		return null;
	}
	
}
