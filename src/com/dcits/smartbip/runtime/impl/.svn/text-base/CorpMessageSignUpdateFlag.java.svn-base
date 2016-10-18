package com.dcits.smartbip.runtime.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.utils.CompositeDataUtils;
/**
 * 对公客户短信签约维护标志设置
 * @author srxhx273
 *
 */
public class CorpMessageSignUpdateFlag extends AbstractBaseService implements IService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3299388406426111974L;
	/**
	 * 日志打印
	 */
	private static final Log log = LogFactory.getLog(CorpMessageSignUpdateFlag.class);     
	
	@Override                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
	public String getId() {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
		return "CorpMessageSignUpdateFlag";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
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
			log.info("开始调用基础服务[CorpMessageSignUpdateFlag]......");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
		}
		
		//操作标志 CAOZBZ:0-新增，1-更新，2-设置支付卡，3-删除，4-销户
		String CAOZBZ = "-1";
		try {
			//当前产品代码
			String signCodeType = (String)getContext().getValue("signCodeType");
			String retCode = (String)getContext().getValue("RetCode"+signCodeType);
			//是否需要回退解约
			String isNeedSignOff = (String)getContext().getValue("isNeedSignOff"+signCodeType);
			//当前客户是否已有账户签约:0-是（只需解约当前账户），1-否（需要解约客户即销户）
			String AcctIsSigned = (String)getContext().getValue("AcctIsSigned"+signCodeType);
			log.debug("retCode=["+retCode+"],isNeedSignOff=["+isNeedSignOff+"],AcctIsSigned=["+AcctIsSigned+"]");
			if(retCode == null || "".equals(retCode.trim())){
				log.info("对公客户短信签约维护/销户交易操作标志 CAOZBZ设置为0-新增");
				CAOZBZ = "0";
			}else if("Y".equals(isNeedSignOff) && "000000".equals(retCode)){
				CAOZBZ = "1".equals(AcctIsSigned)?"4":"3";
				log.info("对公客户短信签约维护/销户交易操作标志 CAOZBZ设置为["+CAOZBZ+"]");
			}else if("1".equals(AcctIsSigned) && "ERR000".equals(retCode)){
				ICompositeData rspICD = (ICompositeData) SessionContext.getContext().getValue("Rsp1003200000205");
				if (null == rspICD)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
				{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
					log.info("Rsp1003200000205结构体为空");
					throw new InvokeException();
				} 
				String rspCode = CompositeDataUtils.getValue(rspICD, "RspSysHead/RetCode");
				if("000000".equals(rspCode)){
					CAOZBZ = "4";
					log.info("对公客户短信签约维护/销户交易操作标志 CAOZBZ设置为4-销户");
				}else{
					log.info("对公客户短信签约非本次综合签约中被签，无需进行解约操作");
				}
			}
			
		} catch (Exception e) {
			log.error("对公客户短信签约维护标志设置异常：",e);
			throw new InvokeException();
		}finally{
			getContext().setValue("CAOZBZ", CAOZBZ);
			if (log.isInfoEnabled()) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
				log.info("调用基础服务结束[CorpMessageSignUpdateFlag]......");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			}  
		}
		
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
		return null;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               

	


