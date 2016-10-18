package com.dcits.smartbip.runtime.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.utils.CompositeDataUtils;
/**
 * 判断当前客户是否已有账户签约当前待签产品
 * @author srxhx273
 *
 */
public class CorpChkAcctIsSigned extends AbstractBaseService implements IService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3339323344629050181L;
	/**
	 * 日志打印
	 */
	private static final Log log = LogFactory.getLog(CorpChkAcctIsSigned.class);     
	
	@Override                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
	public String getId() {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
		return "CorpChkAcctIsSigned";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
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
			log.info("开始调用基础服务[CorpChkAcctIsSigned]......");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
		}
		
		//当前客户是否已有账户已签约:0-是，1-否
		String acctIsSigned = "1";
		//当前产品代码
		String signCodeType = (String)getContext().getValue("signCodeType");
		try {
			List<ICompositeData> GRP_SYNTHESIS_INFO_ARRAY = (List<ICompositeData>)getContext().getValue("GRP_SYNTHESIS_INFO_ARRAY");
			if(null == GRP_SYNTHESIS_INFO_ARRAY){
				log.info("当前客户无产品["+signCodeType+"]签约记录");
				return null;
			}
			String SIGN_TYPE = "";//查询返回产品代码
			for(ICompositeData GRP_SYNTHESIS_INFO : GRP_SYNTHESIS_INFO_ARRAY){
				SIGN_TYPE = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFO,"SIGN_TYPE");
				if(SIGN_TYPE != null && SIGN_TYPE.equals(signCodeType)){
					acctIsSigned = "0";
					log.info("产品["+signCodeType+"]已有账户签约");
					break;
				}
			}
		} catch (Exception e) {
			log.error("判断当前客户是否已有账户签约异常：",e);
			throw new InvokeException();
		}finally{
			getContext().setValue("AcctIsSigned"+signCodeType, acctIsSigned);
			if (log.isInfoEnabled()) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
				log.info("调用基础服务结束[CorpChkAcctIsSigned]......");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			}  
		}
		
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
		return null;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               

	


