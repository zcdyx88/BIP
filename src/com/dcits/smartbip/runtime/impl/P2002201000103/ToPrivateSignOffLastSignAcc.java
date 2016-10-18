package com.dcits.smartbip.runtime.impl.P2002201000103;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.utils.CompositeDataUtils;
/**
 * 对私解约判断是否最后一个签约账户
 * @author srxhx273
 *
 */
public class ToPrivateSignOffLastSignAcc extends AbstractBaseService implements IService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5217724462029452312L;
	/**
	 * 日志打印
	 */
	private static final Log log = LogFactory.getLog(ToPrivateSignOffLastSignAcc.class);     
	
	@Override                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
	public String getId() {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
		return "ToPrivateSignOffLastSignAcc";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
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
			log.info("开始调用基础服务[ToPrivateSignOffLastSignAcc]......");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
		}
		
		//是否最后一个签约账户：0-是，1-否
		String isLastSignedAccFlag = "1";
		//当前产品代码
		String signCodeType = (String)getContext().getValue("signCodeType");
		int count = 0;
		try {
			List<ICompositeData> GRP_SYNTHESIS_INFO_ARRAY = (List<ICompositeData>)getContext().getValue("GRP_SYNTHESIS_INFO_ARRAY");
			
			String SIGN_TYPE = "";//查询返回产品代码
			for(ICompositeData GRP_SYNTHESIS_INFO : GRP_SYNTHESIS_INFO_ARRAY){
				SIGN_TYPE = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFO,"SIGN_TYPE");
				if(SIGN_TYPE != null && SIGN_TYPE.equals(signCodeType)){
					count++;
					log.info("产品["+signCodeType+"]已有账户["+CompositeDataUtils.getValue(GRP_SYNTHESIS_INFO,"SIGN_ACC")+"]签约");
				}
			}
			if(count == 1){
				log.info("待解约产品["+signCodeType+"]为最后一个签约账户");
				isLastSignedAccFlag = "0";
			}
		} catch (Exception e) {
			log.error("对私解约判断是否最后一个签约账户异常：",e);
			throw new InvokeException();
		}finally{
			getContext().setValue("isLastSignedAccFlag"+signCodeType, isLastSignedAccFlag);
			if (log.isInfoEnabled()) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
				log.info("调用基础服务结束[ToPrivateSignOffLastSignAcc]......");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			}  
		}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
		return null;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               

	


