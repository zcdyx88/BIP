package com.dcits.smartbip.runtime.impl;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.utils.CompositeDataUtils;
/**
 * 对公签约卡凭证信息查询交易结果处理
 * @author srxhx273
 *
 */
public class CorpSignCardInfoQryTrRstHandle extends AbstractBaseService implements IService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1300424394487651139L;
	/**
	 * 日志打印
	 */
	private static final Log log = LogFactory.getLog(CorpSignCardInfoQryTrRstHandle.class);     
	
	@Override                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
	public String getId() {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
		return "CorpSignCardInfoQryTrRstHandle";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
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
			log.info("开始调用基础服务[CorpSignCardInfoQryTrRstHandle]......");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
		}
		//交易结果标志：0-成功，1-失败
		String trRstFlag = "1";
		String retCode = "BIP00T0001";
		String retMsg = "交易失败！";
		//获取卡凭证信息查询交易返回体 3003300000305
		ICompositeData Rsp3003300000305 = (ICompositeData) SessionContext.getContext().getValue("Rsp3003300000305");
		try {
			if(null == Rsp3003300000305){
				log.info("卡凭证信息查询交易3003300000305返回体为空");
				return null;
			}
			retCode = CompositeDataUtils.getValue(Rsp3003300000305, "RspSysHead/RetCode");
			if("000000".equals(retCode)){//交易成功
				// 判断是否附卡、单位卡
				String kadengji = CompositeDataUtils.getValue(Rsp3003300000305, "RspAppBody/kadengji");//0-主卡,1-附卡
				String kadxiang = CompositeDataUtils.getValue(Rsp3003300000305, "RspAppBody/kadxiang");//0-个人卡,-1单位卡
				if("1".equals(kadengji) || "1".equals(kadxiang)){
					log.info("交易账户为附卡或单位卡");
					// 获得综合解约产品代码列表
					@SuppressWarnings("unchecked")
					List<String> signCodeTypes = (List<String>)getContext().getValue("signCodeTypes");
					if(null == signCodeTypes){
						log.error("上送待签约产品为空，业务流程结束");
						retMsg = "上送待签约产品为空";
						return null;
					}
					//签约产品是否仅为短信和卡卡转账
					for(String signCode : signCodeTypes){
						if(!"000003".equals(signCode)){
							log.info("对公签约产品非短信【"+signCode+"】，业务流程结束");
							retMsg = "附卡/单位结算卡不允许签约"+signCode+"产品";
							retCode = "BIP01E0006";
							return null;
						}
					}
					trRstFlag = "0";
				}else{
					log.info("交易账户非附卡和单位卡");
					trRstFlag = "0";
				}
				
				
			}else{//交易失败
				retMsg = CompositeDataUtils.getValue(Rsp3003300000305, "RspSysHead/RetMsg");
			}
			
		} catch (Exception e) {
			log.error("对公签约卡凭证信息查询交易结果处理异常", e);
			throw new InvokeException();
		}finally{
			getContext().setValue("tranResultFlag", trRstFlag);
			if("1".equals(trRstFlag)){//交易失败:业务失败
				CompositeDataUtils.setReturn((String)getContext().getValue("ModifySignType"), "000000".equals(retCode)?"BIP00T0001":retCode, retMsg);
			}
			if (log.isInfoEnabled()) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
				log.info("调用基础服务结束[CorpSignCardInfoQryTrRstHandle]......");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		}
		return null;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               

	


