package com.dcits.smartbip.runtime.impl.P2002201000101;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.runtime.model.impl.SoapCompositeData;
import com.dcits.smartbip.utils.CompositeDataUtils;
import com.dcits.smartbip.utils.PropertiesUtils;

public class DaijiaofeiMapper extends AbstractBaseService implements IService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2196757864665830620L;
	private static final Log log = LogFactory.getLog(DaijiaofeiMapper.class);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
	@Override                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
	public String getId() {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
		return "DaijiaofeiMapper";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
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
			log.info("开始调用基础服务[DaijiaofeiMapper]......");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
		}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
		// 获得综合签约请求数据对象                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
		ICompositeData Req2002201000101 = (ICompositeData) SessionContext .getContext().getValue("Req2002201000101");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
		ICompositeData Req3006200001810 = (ICompositeData) SessionContext.getContext().getValue("Req3006200001810");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
		if (null == Req3006200001810)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{               
			Req3006200001810  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			Req3006200001810.setId("Req3006200001810");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			Req3006200001810.setxPath("/Req3006200001810");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			getContext().setValue("Req3006200001810",Req3006200001810);   
		}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		
		String productId = (String)SessionContext.getContext().getValue("signCodeType");
		
		List<ICompositeData> DirectDebitSignArray = CompositeDataUtils.getByPath(                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
				Req2002201000101, "ReqAppBody/DirectDebitSignReq/DirectDebitSignArray");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
		                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
		for (ICompositeData signComposite : DirectDebitSignArray) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
			                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
			String DLIYWH = CompositeDataUtils.getValue(signComposite,"DLIYWH");      
			
			if(null == DLIYWH)
			{
				return null;
			}
			                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
			if(log.isInfoEnabled()){
				log.info("DLIYWH=[" + DLIYWH + "],productId=[" + productId + "],配置参数[" + PropertiesUtils.getProductionAndDLIYWH().get(productId) + "]");
			}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
			if(DLIYWH.equalsIgnoreCase(PropertiesUtils.getProductionAndDLIYWH().get(productId)))
			{
				CompositeDataUtils.copy(signComposite, Req3006200001810,"SYSPINSEED", "ReqLocalHead/SYSPINSEED");
				//地区代码
				CompositeDataUtils.copy(signComposite, Req3006200001810,"PDCTNO", "ReqLocalHead/PDCTNO");
				
				CompositeDataUtils.copy(signComposite, Req3006200001810,"SHOFBZ", "ReqAppBody/SHOFBZ");
				CompositeDataUtils.copy(signComposite, Req3006200001810,"HUOBDH", "ReqAppBody/HUOBDH");                                                                                                                                                                                                                                                                                                                                                                                                                                            
				CompositeDataUtils.copy(signComposite, Req3006200001810,"CHUIBZ", "ReqAppBody/CHUIBZ");
				CompositeDataUtils.copy(signComposite, Req3006200001810,"YWZLBH", "ReqAppBody/YWZLBH");
				CompositeDataUtils.copy(signComposite, Req3006200001810,"DANWBH", "ReqAppBody/DANWBH");
				CompositeDataUtils.copy(signComposite, Req3006200001810,"DLIYWH", "ReqAppBody/DLIYWH");
				CompositeDataUtils.copy(signComposite, Req3006200001810,"SXIORQ", "ReqAppBody/SXIORQ");
				CompositeDataUtils.copy(signComposite, Req3006200001810,"ZYWXYH", "ReqAppBody/ZYWXYH");
				CompositeDataUtils.copy(signComposite, Req3006200001810,"DSYHBH", "ReqAppBody/DSYHBH");
				CompositeDataUtils.copy(signComposite, Req3006200001810,"DISFMC", "ReqAppBody/DISFMC");
				CompositeDataUtils.copy(signComposite, Req3006200001810,"DAILRM", "ReqAppBody/DAILRM");
				CompositeDataUtils.copy(signComposite, Req3006200001810,"ZHJNZL", "ReqAppBody/ZHJNZL");
				CompositeDataUtils.copy(signComposite, Req3006200001810,"ZHJHAO", "ReqAppBody/ZHJHAO");
				CompositeDataUtils.copy(signComposite, Req3006200001810,"DSYHDZ", "ReqAppBody/DSYHDZ");
				CompositeDataUtils.copy(signComposite, Req3006200001810,"DSYHYB", "ReqAppBody/DSYHYB");
				CompositeDataUtils.copy(signComposite, Req3006200001810,"DSYHDH", "ReqAppBody/DSYHDH");
				CompositeDataUtils.copy(signComposite, Req3006200001810,"GRZHLX", "ReqAppBody/GRZHLX");
				CompositeDataUtils.copy(signComposite, Req3006200001810,"GRZHAO", "ReqAppBody/GRZHAO");
				CompositeDataUtils.copy(signComposite, Req3006200001810,"JIOYMM", "ReqAppBody/JIOYMM");
				CompositeDataUtils.copy(signComposite, Req3006200001810,"QIYEDM", "ReqAppBody/QIYEDM");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
			}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
		}
		
		//设置产品代码,给代扣签约及ECIF状态更新使用
		SessionContext.getContext().setValue("SignProTp", productId);
		
		if (log.isInfoEnabled()) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
			log.info("调用基础服务结束[DaijiaofeiMapper]......");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
		}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
		return null;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
	}
	
}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               

	


