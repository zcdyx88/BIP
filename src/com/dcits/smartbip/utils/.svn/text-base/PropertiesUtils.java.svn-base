package com.dcits.smartbip.utils;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import com.dcits.smartbip.runtime.property.PropertiesService;

/**
 * 
 * @author srxhx273
 *
 */
public class PropertiesUtils {
	
	public static final String PROPERTY_PATH = System.getProperty("user.dir")+File.separator+"configs"+File.separator+"properties"+File.separator;
	
    
    /**
     * 获取代理业务号配置
     * @return	productMap : key-产品代码，value-代理业务号
     */
    public static HashMap<String,String> getProductionAndDLIYWH(){
		return PropertiesService.getInstance().getDliywhMap();
    }
    
	/**
	 * 获取重复签约响应码
	 * 
	 * @param seviceId :服务码
	 * @return 
	 */
    public static List<String> getReSignRetCode(String seviceId){
		HashMap<String,List<String>> retCodeMap = PropertiesService.getInstance().getReSignMap();
		return retCodeMap.get(seviceId);
	}
    
    /**
	 * 获取未签约或已解约响应码
	 * 
	 * @param seviceId :服务码
	 * @return 
	 */
    public static List<String> getSignOffRetCode(String seviceId){
		HashMap<String,List<String>> retCodeMap = PropertiesService.getInstance().getSignOffMap();
		return retCodeMap.get(seviceId);
	}
    /**
     * 获取产品代码与账户序号的配置
     * @return	productMap : key-产品代码，value-私有结构体中是否存在子账户序号：0-不存在（需要送组合账户），1-存在（需要送组合账户）
     */
    public static HashMap<String,String> getProductionAcctSrlNo(){
		return PropertiesService.getInstance().getAcctSrlNoMap();
    }
    /**
     * 获取IPP目标系统
     * */
    public static HashMap<String,String> getMUBIAOXT(){
		return PropertiesService.getInstance().getMUBIAOXTMap();
    }
}
