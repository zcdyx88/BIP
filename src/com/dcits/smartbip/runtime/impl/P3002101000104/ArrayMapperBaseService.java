/*
 * <p>Title: :ArrayMapperBaseService.java </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: digitalchina.Ltd</p>
 * @author 
 * Created :2016-9-22 15:41:42
 * @version 1.0
 * ModifyList:
 * <Author> <Time(yyyy/mm/dd)>  <Description>  <Version>
 */

package com.dcits.smartbip.runtime.impl.P3002101000104;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.runtime.model.impl.SoapCompositeData;
import com.dcits.smartbip.utils.CompositeDataUtils;


public class ArrayMapperBaseService extends AbstractBaseService implements IService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(ArrayMapperBaseService.class); 
	@Override
	public String getId() {
		return "ArrayMapperBaseService";
		
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
			log.info("寮�璋冪敤鍩虹鏈嶅姟[ArrayMapperBaseService]......");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
		}
		
		
		//鑾峰彇璇锋眰鍘熷瓙鏈嶅姟瀵硅薄
		ICompositeData Rsp3002101000103 = (ICompositeData) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		.getContext().getValue("Rsp3002101000103");
		//濡傛灉涓嶅瓨鍦ㄨ璇锋眰缁撴瀯锛屽垱寤虹┖缁撴灉
		if (null == Rsp3002101000103)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			Rsp3002101000103  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			Rsp3002101000103.setId("Rsp3002101000103");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			Rsp3002101000103.setxPath("/"+Rsp3002101000103);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			getContext().setValue("Rsp3002101000103",Rsp3002101000103);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		}
	   
	   ICompositeData destArrayParent = CompositeDataUtils.mkNodeNotExist(Rsp3002101000103, "Reply_Msg/Sys_Head/RET_ARRAY");
	   ICompositeData arrayiItem = new SoapCompositeData();
	   arrayiItem.setId("Row");
	   destArrayParent.setChild("Row", arrayiItem);
	   CompositeDataUtils.setValue(arrayiItem, "RET_MSG", "浜ゆ槗鎴愬姛");
	   CompositeDataUtils.setValue(arrayiItem, "RET_CODE", "ZJY0000");
	   
	   ICompositeData destArrayParent1 = CompositeDataUtils.mkNodeNotExist(Rsp3002101000103, "Reply_Msg/Sys_Head/HX_ARRAY");
	   ICompositeData arrayiItem1 = new SoapCompositeData();
	   arrayiItem1.setId("Row");
	   destArrayParent1.setChild("Row", arrayiItem1);
	   CompositeDataUtils.setValue(arrayiItem1, "RET_MSG", "Success");
	   CompositeDataUtils.setValue(arrayiItem1, "RET_CODE", "000000");
		
		if (log.isInfoEnabled()) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
			log.info("缁撴潫璋冪敤鍩虹鏈嶅姟[ArrayMapperBaseService]......");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
		}
		return null;
	}

}

