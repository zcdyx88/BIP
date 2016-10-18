package com.dcits.smartbip.runtime.impl.p2002201000102;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
/**
 * 对公签约请求报文处理
 * @author srxhx273
 *
 */
public class CorpSignReqHandle extends AbstractBaseService implements IService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4367553317073975472L;
	/**
	 * 日志打印
	 */
	private static final Log log = LogFactory.getLog(CorpSignReqHandle.class);     
	
	@Override                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
	public String getId() {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
		return "CorpSignReqHandle";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
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
			log.info("开始调用基础服务[CorpSignReqHandle]......");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
		}
		
		//当前产品代码
		String signCodeType = (String)getContext().getValue("signCodeType");
		try {
			String retCode = (String)getContext().getValue("RetCode"+signCodeType);
			//是否需要回退解约
			String isNeedSignOff = (String)getContext().getValue("isNeedSignOff"+signCodeType);
			log.debug("当前待签产品=["+signCodeType+"],retCode=["+retCode+"],isNeedSignOff=["+isNeedSignOff+"]");
			if("000032".equals(signCodeType)){//协定存款
				//协定存款操作类型 xdckczlx:1--设置，2--撤销
				String xdckczlx = "-1";
				if(retCode == null || "".equals(retCode.trim())){
					log.info("对公协定存款操作类型 xdckczlx设置为1-设置");
					xdckczlx = "1";
				}else if("Y".equals(isNeedSignOff)){
					xdckczlx = "2";
					log.info("对公协定存款操作类型 xdckczlx设置为2-撤销");
				}
				getContext().setValue("xdckczlx", xdckczlx);
			}else if("000036".equals(signCodeType)){//代发代扣
				//签约维护标志:1--签约,2--解约,3--维护,4--查询
				String qywhbzhi = "-1";
				if(retCode == null || "".equals(retCode.trim())){
					log.info("对公代发代扣签约维护标志 qywhbzhi设置为1-签约");
					qywhbzhi = "1";
				}else if("Y".equals(isNeedSignOff)){
					qywhbzhi = "2";
					log.info("对公代发代扣签约维护标志 qywhbzhi设置为2-解约");
				}
				getContext().setValue("qywhbzhi", qywhbzhi);
			}else if("000039".equals(signCodeType)){//财税库行电子缴税(代缴税)
				//操作标志:1--查询 ，2--增加 ，3--修改，4--删除
				String CAOZBZ = "-1";
				if(retCode == null || "".equals(retCode.trim())){
					log.info("对公财税库行电子缴税(代缴税操作标志 CAOZBZ设置为2-增加");
					CAOZBZ = "2";
				}else if("Y".equals(isNeedSignOff)){
					CAOZBZ = "4";
					log.info("对公财税库行电子缴税(代缴税操作标志 CAOZBZ设置为4-删除");
				}
				getContext().setValue("CAOZBZ", CAOZBZ);
			}
			
		} catch (Exception e) {
			log.error("对公签约请求报文处理异常：",e);
			throw new InvokeException();
		}finally{
			if (log.isInfoEnabled()) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
				log.info("调用基础服务结束[CorpSignReqHandle]......");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			}  
		}
		
		return null;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               

	


