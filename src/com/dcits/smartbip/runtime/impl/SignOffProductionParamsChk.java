package com.dcits.smartbip.runtime.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.entity.Production;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.utils.CompositeDataUtils;
import com.dcits.smartbip.utils.XMLUtils;
/**
 * 对私综合解约产品参数检查
 * @author srxhx273
 *
 */
public class SignOffProductionParamsChk extends AbstractBaseService implements IService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9201267992838767336L;
	/**
	 * 日志打印
	 */
	private static final Log log = LogFactory.getLog(SignOffProductionParamsChk.class);     
	
	@Override                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
	public String getId() {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
		return "SignOffProductionParamsChk";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
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
			log.info("开始调用基础服务[SignOffProductionParamsChk]......");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
		}
		// 获得综合解约产品代码列表
		@SuppressWarnings("unchecked")
		List<String> signCodeTypes = (List<String>)getContext().getValue("signCodeTypes");
		//是否允许跨地区解约标志kdqbz：1-是，0-否
		String kdqbz = "1";
		//业务流程是否结束标志 isEnd:0-是，1-否
		String isEnd = "0";
		String retMsg = "";
		String retCode = "BIP00T0001";
		//组合服务码
		String ModifySignType = (String)getContext().getValue("ModifySignType");
		try {
			
			if(signCodeTypes == null || signCodeTypes.size() == 0){
				log.error("待解约产品为空，业务流程结束");
				retMsg = "待解约产品为空，请核实！";
				return null;
			}
			log.info("产品参数检查：从配置文件中获取所有对私签约产品列表信息");
			//1、从本地库获取所有产品代码信息
			Map<String,Production> productionMap = XMLUtils.getSignCodeInfo("0");
			//2、产品参数检查
			/*
			 * 1）任一待解约产品未找到			XX产品不存在，请核实
			 * 2）任一待解约产品不允许解约		XX产品不允许解约，请核实
			 * 3）任一待解约产品解约渠道不合法	XX产品不能通过本渠道解约
			 * 以上情况出现则业务失败，返回相应提示信息
			 */
			ICompositeData Req2002201000103 = (ICompositeData) SessionContext.getContext().getValue("Req"+ModifySignType);
			String channelId = CompositeDataUtils.getValue(Req2002201000103, "ReqSysHead/ChannelID");
			List<Production> productionList = new ArrayList<Production>();//待签约产品信息
			String croRegSignOffFlg = "";//是否允许跨地区解约标志
			HashMap<String,List<String>> BlngBrchBnkMap = new HashMap<String, List<String>>();//不允许跨地区解约的产品信息
			Production production = null;
			for(String signCode : signCodeTypes){
				/**
				 * 解约产品编号，查询待解约产品的参数信息，任一产品未配置，则业务失败
				 */
				if(!productionMap.containsKey(signCode)){
					log.info(signCode+"产品不存在，请核实");
					retMsg = signCode+"产品不存在，请核实";
					retCode = "BIP02E0000";
					return null;
				}
				production = productionMap.get(signCode);
				/**
				 * 任一待解约产品不允许解约，则业务失败
				 */
				if("0".equals(production.getSignOffFlg())){
					log.info(signCode+"产品不允许解约，请核实");
					retMsg = signCode+"产品不允许解约，请核实";
					retCode = "BIP02E0005";
					return null;
				}
				/**
				 * 如果任一待解约产品有解约渠道限制且解约渠道不合法，则业务失败
				 */
				if("1".equalsIgnoreCase(production.getAvlSignOffChlFlg()))
				{
					if(!production.getAvlSignOffChlList().contains(channelId)){
						log.info(signCode+"产品不能通过本渠道解约");
						retMsg = signCode+"产品不能通过本渠道解约";
						retCode = "BIP02E0001";
						return null;
					}
				}
								
				//3、设置是否允许跨地区解约标志kdqbz：1-是，0-否
				croRegSignOffFlg = production.getCroRegSignOffFlg();
				if("0".equals(croRegSignOffFlg)){
					kdqbz = croRegSignOffFlg;
					BlngBrchBnkMap.put(signCode, production.getBlngBrchBnkList());
				}
				productionList.add(productionMap.get(signCode));
			}
			isEnd = "1";//执行于此表示所有产品符合解约条件
			getContext().setValue("ProductionList", productionList);
			getContext().setValue("BlngBrchBnkMap", BlngBrchBnkMap);
			
		} catch (Exception e) {
			log.error("对私综合解约产品参数检查异常：",e);
			throw new InvokeException();
		}finally{
			getContext().setValue("kdqbz", kdqbz);
			getContext().setValue("isEnd", isEnd);
			if("0".equals(isEnd)){
				CompositeDataUtils.setReturn(ModifySignType, retCode, retMsg);
			}
			
			if (log.isInfoEnabled()) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
				log.info("调用基础服务结束[SignOffProductionParamsChk]......");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			
		}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
		return null;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               

	


