package com.dcits.smartbip.runtime.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.utils.CompositeDataUtils;
/**
 * 交易机构是否有交易权限
 * @author srxhx273
 *
 */
public class ChkOrgIsTranPriv extends AbstractBaseService implements IService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4663928580286721872L;
	/**
	 * 日志打印
	 */
	private static final Log log = LogFactory.getLog(ChkOrgIsTranPriv.class);     
	/**
	 * 机构信息模糊查询：Rsp+原子服务码
	 */
	private static final String ORG_QRY_YZ_SERVICE_ID = "Rsp5001300001109";
	
	@Override                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
	public String getId() {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
		return "ChkOrgIsTranPriv";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
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
			log.info("开始调用基础服务[ChkOrgIsTranPriv]......");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
		}
		//组合服务码
		String ModifySignType = (String)getContext().getValue("ModifySignType");
		
		ICompositeData rspICD = (ICompositeData) SessionContext.getContext().getValue(ORG_QRY_YZ_SERVICE_ID);
		
		//交易机构是否有交易权限标志：0-是，1-否
		String orgIsTranPrivFlag = "0";
		String retCode = "BIP00T0001";//响应码
		String retMsg = "";//结果信息
		try {
			
			if (null == rspICD){
				log.info("返回结构体["+ORG_QRY_YZ_SERVICE_ID+"]为空");
				retMsg = "交易失败！";
				orgIsTranPrivFlag="1";
			}else{
				retCode = CompositeDataUtils.getValue(rspICD, "RspSysHead/RetCode");
				log.info("机构信息模糊查询结果retCode=["+retCode+"]");
				if("000000".equals(retCode)){//交易成功
					// 获得机构信息模糊查询返回数组jigxinxmc信息
					List<ICompositeData> jigxinxmcArray = CompositeDataUtils.getByPath(rspICD, "RspAppBody/jigxinxmc");
					
					//获取产品及产品的归属分行：key-产品代码，value-List
					/*
					 * hashMap需要在基础服务SignOffProductionParamsChk中生成
					 */
					HashMap<String,List<String>> hashMap = (HashMap<String,List<String>>)getContext().getValue("BlngBrchBnkMap");
					Set<Entry<String,List<String>>> sets = hashMap.entrySet();
					String cpdm = "";//产品代码:hashMap-key
					List<String> value = null;//hashMap-value
					FirstFor: for(Entry<String,List<String>> entry : sets){
						
						cpdm = entry.getKey();
						value = entry.getValue();
						if(value == null){
							log.info("["+cpdm+"]产品不允许跨地区解约，所属分行列表未配置");
							retMsg = "["+cpdm+"]产品不允许跨地区解约，所属分行列表未配置";
							orgIsTranPrivFlag="1";
							break FirstFor;
						}
						for (ICompositeData jigxinxmc : jigxinxmcArray) {
							//分行代码
							String fenhdaim = CompositeDataUtils.getValue(jigxinxmc,"fenhdaim");    
							
							if(!value.contains(fenhdaim)){//该分行无此产品权限
								log.info("["+cpdm+"]产品不允许跨地区解约，请核实");
								retMsg = "["+cpdm+"]产品不允许跨地区解约，请核实";
								orgIsTranPrivFlag="1";
								retCode = "BIP02E0004";
								break FirstFor;
							}
							
						}
					}
				}else{//交易失败
					orgIsTranPrivFlag="1";
					retMsg = CompositeDataUtils.getValue(rspICD, "RspSysHead/RetMsg");
				}
			}
		} catch (Exception e) {
			log.error("判断交易机构是否有交易权限异常", e);
			retCode = "BIP00T0001";
			retMsg = "系统异常！";
			throw new InvokeException();
		}finally{
			getContext().setValue("orgIsTranPrivFlag", orgIsTranPrivFlag);
			
			if("1".equals(orgIsTranPrivFlag)){
				CompositeDataUtils.setReturn(ModifySignType, "000000".equals(retCode)?"BIP00T0001":retCode, retMsg);
			}
			
			if (log.isInfoEnabled()) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
				log.info("调用基础服务结束[ChkOrgIsTranPriv]......");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			}  
		}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
		return null;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               

	


