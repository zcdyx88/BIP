package com.dcits.smartbip.runtime.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.entity.ExcludeInfo;
import com.dcits.smartbip.runtime.entity.Production;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.utils.CompositeDataUtils;
import com.dcits.smartbip.utils.XMLUtils;
/**
 * 对公综合签约产品参数检查
 * @author srxhx273
 *
 */
public class CorpSignProductionParamsChk extends AbstractBaseService implements IService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5791686574002734202L;
	/**
	 * 日志打印
	 */
	private static final Log log = LogFactory.getLog(CorpSignProductionParamsChk.class);     
	
	@Override                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
	public String getId() {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
		return "CorpSignProductionParamsChk";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
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
			log.info("开始调用基础服务[CorpSignProductionParamsChk]......");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
		}
		// 获得对公综合签约产品代码列表
		@SuppressWarnings("unchecked")
		List<String> signCodeTypes = (List<String>)getContext().getValue("signCodeTypes");
		String retMsg = "交易失败";
		String retCode = "BIP00T0001";
		//业务流程是否结束标志 isEnd:0-是，1-否
		String isEnd = "0";
		try {
			if(signCodeTypes == null || signCodeTypes.size() == 0){
				log.error("待签约产品为空，业务流程结束");
				retMsg = "待签约产品为空，请核实！";
				return null;
			}
			log.info("产品参数检查：从配置文件中获取所有对公签约产品列表信息");
			//1、从本地库获取所有产品代码信息
			Map<String,Production> productionMap = XMLUtils.getSignCodeInfo("1");
			
			//2、产品参数检查
			/*
			 * 1）任一待解约产品未找到			XX产品不存在，请核实！
			 * 2）任一产品的交易渠道未在该产品可签约渠道列表内		XX产品不能通过XX渠道签约，请核实！
			 * 3）待签产品之间存在互斥	XX产品签约时，XX产品不能签约，存在互斥，请重新选择
			 * 以上情况出现则业务失败，则设置isEnd=0,返回相应提示信息
			 */
			ICompositeData Req2002201000102 = (ICompositeData) SessionContext.getContext().getValue("Req2002201000102");
			String channelId = CompositeDataUtils.getValue(Req2002201000102, "ReqSysHead/ChannelID");
			List<ExcludeInfo> excludeInfoList = null;
			List<Production> productionList = new ArrayList<Production>();//待签约产品信息
			for(String signCode : signCodeTypes){
				if(!productionMap.containsKey(signCode)){
					log.info(signCode+"产品不存在，请核实！");
					retMsg = signCode+"产品不存在，请核实";
					retCode = "BIP01E0000";
					return null;
				}
				if("1".equals(productionMap.get(signCode).getAvlSignChlFlg())
						&& !productionMap.get(signCode).getAvlSignChlList().contains(channelId)){
					log.info(signCode+"产品不能通过本渠道签约，请核实！");
					retMsg = signCode+"产品不能通过本渠道签约，请核实！";
					retCode = "BIP01E0001";
					return null;
				}
				if(signCodeTypes.size() > 1){//单一产品签约时不需进行待签产品之间存在互斥校验
					//互斥产品
					excludeInfoList = productionMap.get(signCode).getExcludeInfoList();
					if(excludeInfoList != null){
						for(ExcludeInfo excludeInfo : excludeInfoList){
							if(signCodeTypes.contains(excludeInfo.getExcludeVarCd())){
								log.info(excludeInfo.getExcludeVarCd()+"产品和"+signCode+"产品互斥，不能同时签约，请核实");
								retMsg = excludeInfo.getExcludeVarCd()+"产品和"+signCode+"产品互斥，不能同时签约，请核实";
								retCode = "BIP01E0004";
								return null;
							}
						}
						log.debug("待签产品"+signCode+"不存在互斥产品，允许签约");
					}
				}
				
				productionList.add(productionMap.get(signCode));
			}
			isEnd = "1";//执行于此表示所有产品符合解约条件
			getContext().setValue("ProductionList", productionList);
		} catch (Exception e) {
			log.error("对公综合签约产品参数检查异常：", e);
			throw new InvokeException();
		}finally{
			getContext().setValue("isEnd", isEnd);
			
			if("0".equals(isEnd)){
				CompositeDataUtils.setReturn((String)getContext().getValue("ModifySignType"), retCode, retMsg);
			}
			
			if (log.isInfoEnabled()) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
				log.info("调用基础服务结束[CorpSignProductionParamsChk]......");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		}
		return null;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               

	


