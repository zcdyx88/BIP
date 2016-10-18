package com.dcits.smartbip.runtime.property;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * Properties配置文件加载
 * @author srxhx273
 *
 */
public class PropertiesService {

	public final static Log log = LogFactory.getLog(PropertiesService.class);
	/**
	 * 代理业务号配置文件最新修改时间
	 */
	private static long LastUptTime_dliywh = 0;
	/**
	 * 重复签约响应码配置文件最新修改时间
	 */
	private static long LastUptTime_reSign = 0;
	/**
	 * 未签约或已解约响应码配置文件最新修改时间
	 */
	private static long LastUptTime_SignOff = 0;
	/**
	 * 账户序号配置文件最新修改时间
	 */
	private static long LastUptTime_AcctSrlNo = 0;
	/**
	 * IPP后端目标系统配置文件最新修改时间
	 */
	private static long LastUptTime_MUBIAOXTs = 0;
	/**
	 * 文件存放目录
	 */
	public static final String PROPERTY_PATH = System.getProperty("user.dir")+File.separator+"configs"+File.separator+"properties"+File.separator;
	
	/**
	 * 代理业务号配置文件名
	 */
	private static final String FILE_NAME_DLIYWH = "dliywh.properties";
	/**
	 * 重复签约响应码配置文件名
	 */
	private static final String FILE_NAME_RESIGN = "reSignRspCode.properties";
	/**
	 * 未签约或已解约响应码配置文件名
	 */
	private static final String FILE_NAME_SIGNOFF = "SignOffRspCode.properties";
	/**
	 * 账户序号配置文件名
	 */
	private static final String FILE_NAME_AcctSrlNo = "AcctSrlNo.properties";
	/**
	 * IPP后端目标系统
	 */
	private static final String FILE_NAME_MUBIAOXTs = "MUBIAOXTs.properties";
	
	/**
	 * PropertiesService实例
	 */
	private static PropertiesService instance = null;
	
	private static HashMap<String,String> dliywhMap = null;
	private static HashMap<String,List<String>> reSignMap = null;
	private static HashMap<String,List<String>> signOffMap = null;
	private static HashMap<String,String> acctSrlNoMap = null;
	private static HashMap<String,String> MUBIAOXTMap = null;
	
	public HashMap<String, List<String>> getSignOffMap() {
		if(signOffMap == null){
			log.info("未签约或已解约响应码配置结果集为空，重新加载");
			loadSignOff();
		}else if(getSignOffLastUptTime() != LastUptTime_SignOff){
			log.debug("文件["+FILE_NAME_RESIGN+"]被修改过，重新加载");
			loadSignOff();
		}
		return signOffMap;
	}
	public HashMap<String,List<String>> getReSignMap() {
		if(reSignMap == null){
			log.info("重复签约响应码配置结果集为空，重新加载");
			loadReSign();
		}else if(getReSignLastUptTime() != LastUptTime_reSign){
			log.debug("文件["+FILE_NAME_RESIGN+"]被修改过，重新加载");
			loadReSign();
		}
		return reSignMap;
	}
	public HashMap<String, String> getDliywhMap() {
		if(dliywhMap == null){
			log.info("代理业务号配置结果集为空，重新加载");
			loadDLIYWH();
		}else if(getDLIYWHLastUptTime() != LastUptTime_dliywh){
			log.debug("文件["+FILE_NAME_DLIYWH+"]被修改过，重新加载");
			loadDLIYWH();
		}
		return dliywhMap;
	}
	public HashMap<String, String> getAcctSrlNoMap() {
		if(acctSrlNoMap == null){
			log.info("账户序号配置结果集为空，重新加载");
			loadAcctSrlNo();
		}else if(getAcctSrlNoLastUptTime() != LastUptTime_AcctSrlNo){
			log.debug("文件["+FILE_NAME_AcctSrlNo+"]被修改过，重新加载");
			loadAcctSrlNo();
		}
		return acctSrlNoMap;
	}
	
	public HashMap<String, String> getMUBIAOXTMap() {
		if(MUBIAOXTMap == null){
			log.info("IPP后端目标系统配置结果集为空，重新加载");
			loadMUBIAOXT();
		}else if(getMUBIAOXTLastUptTime() != LastUptTime_MUBIAOXTs){
			log.debug("文件["+FILE_NAME_MUBIAOXTs+"]被修改过，重新加载");
			loadMUBIAOXT();
		}
		return MUBIAOXTMap;
	}
	private PropertiesService(){
		
	}
	public static PropertiesService getInstance() {
		if(instance == null){
			synchronized (PropertiesService.class) {
				if(instance == null){
					log.info("创建服务[PropertiesService]");
					instance = new PropertiesService();
				}
			}
		}
		return instance;
	}
	
	public void load(){
		loadReSign();
		loadDLIYWH();
		loadSignOff();
		loadAcctSrlNo();
		loadMUBIAOXT();
	}
	/*
	 * 加载IPP后端目标系统
	 * */
	private static void loadMUBIAOXT() {
		// TODO Auto-generated method stub
		MUBIAOXTMap = new HashMap<String,String>();
		FileInputStream fis = null;
    	try {
    		String filePath = PROPERTY_PATH+FILE_NAME_MUBIAOXTs;
    		File file = new File(filePath);
    		if(file.exists()){
    			LastUptTime_MUBIAOXTs = file.lastModified();
    			fis = new FileInputStream(filePath);
    			InputStream is = new BufferedInputStream(fis);
    			ResourceBundle res = new PropertyResourceBundle(is);
    			Set<String> ent = res.keySet();
    			for(String key : ent){
    				MUBIAOXTMap.put(key, res.getString(key));
    			}
    		}else{
    			log.info("加载IPP后端目标系统配置文件["+filePath+"]不存在，请核实");
    		}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(fis != null){
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * 加载未签约或已解约响应码配置
	 */
	private static void loadSignOff(){
		signOffMap = new HashMap<String,List<String>>();
		FileInputStream fis = null;
    	try {
    		String filePath = PROPERTY_PATH+FILE_NAME_SIGNOFF;
    		File file = new File(filePath);
    		if(file.exists()){
    			LastUptTime_SignOff = file.lastModified();
    			fis = new FileInputStream(filePath);
    			InputStream is = new BufferedInputStream(fis);
    			ResourceBundle res = new PropertyResourceBundle(is);
    			Set<String> ent = res.keySet();
    			for(String key : ent){
    				signOffMap.put(key, Arrays.asList(res.getString(key).split(",")));
    			}
    		}else{
    			log.info("加载未签约或已解约响应码配置文件["+filePath+"]不存在，请核实");
    		}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(fis != null){
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 加载重复签约响应码配置
	 */
	private static void loadReSign(){
		reSignMap = new HashMap<String,List<String>>();
		FileInputStream fis = null;
    	try {
    		String filePath = PROPERTY_PATH+FILE_NAME_RESIGN;
    		File file = new File(filePath);
    		if(file.exists()){
    			LastUptTime_reSign = file.lastModified();
    			fis = new FileInputStream(file);
    			InputStream is = new BufferedInputStream(fis);
    			ResourceBundle res = new PropertyResourceBundle(is);
    			Set<String> ent = res.keySet();
    			for(String key : ent){
    				reSignMap.put(key, Arrays.asList(res.getString(key).split(",")));
    			}
    		}else{
    			log.info("重复签约响应码配置文件["+filePath+"]不存在，请核实");
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(fis != null){
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 加载代理业务号配置
	 */
	private static void loadDLIYWH(){
		dliywhMap = new HashMap<String,String>();
    	FileInputStream fis = null;
    	try {
    		String filePath = PROPERTY_PATH+FILE_NAME_DLIYWH;
    		File file = new File(filePath);
    		if(file.exists()){
    			LastUptTime_dliywh = file.lastModified();
    			fis = new FileInputStream(file);
    			InputStream is = new BufferedInputStream(fis);
    			ResourceBundle res = new PropertyResourceBundle(is);
    			Set<String> ent = res.keySet();
    			for(String key : ent){
    				dliywhMap.put(key, res.getString(key));
    			}
    		}else{
    			log.info("代理业务号配置文件["+filePath+"]不存在，请核实");
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(fis != null){
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 加载账户序号配置
	 * 配置文件中key为产品代码，value含义为私有结构体中是否存在子账户序号：0-不存在（需要送组合账户），1-存在（需要送组合账户）
	 * 	
	 */
	private static void loadAcctSrlNo(){
		acctSrlNoMap = new HashMap<String,String>();
    	FileInputStream fis = null;
    	try {
    		String filePath = PROPERTY_PATH+FILE_NAME_AcctSrlNo;
    		File file = new File(filePath);
    		if(file.exists()){
    			LastUptTime_AcctSrlNo = file.lastModified();
    			fis = new FileInputStream(file);
    			InputStream is = new BufferedInputStream(fis);
    			ResourceBundle res = new PropertyResourceBundle(is);
    			Set<String> ent = res.keySet();
    			for(String key : ent){
    				acctSrlNoMap.put(key, res.getString(key));
    			}
    		}else{
    			log.info("账户序号配置文件["+filePath+"]不存在，请核实");
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(fis != null){
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 获取账户序号配置文件最新修改时间
	 * @return
	 */
	private long getAcctSrlNoLastUptTime(){
		File file = new File(PROPERTY_PATH+FILE_NAME_AcctSrlNo);
		if(!file.exists()){
			log.debug("获取文件["+PROPERTY_PATH+FILE_NAME_AcctSrlNo+"]最新修改时间时，该文件不存在");
			return -1;
		}
		return file.lastModified();
	}
	/**
	 * IPP目标系统配置文件最新修改时间
	 * @return
	 */
	private long getMUBIAOXTLastUptTime(){
		File file = new File(PROPERTY_PATH+FILE_NAME_MUBIAOXTs);
		if(!file.exists()){
			log.debug("获取文件["+PROPERTY_PATH+FILE_NAME_MUBIAOXTs+"]最新修改时间时，该文件不存在");
			return -1;
		}
		return file.lastModified();
	}
	/**
	 * 获取代理业务号配置文件最新修改时间
	 * @return
	 */
	private long getDLIYWHLastUptTime(){
		File file = new File(PROPERTY_PATH+FILE_NAME_DLIYWH);
		if(!file.exists()){
			log.debug("获取文件["+PROPERTY_PATH+FILE_NAME_DLIYWH+"]最新修改时间时，该文件不存在");
			return -1;
		}
		return file.lastModified();
	}
	/**
	 * 获取重复签约响应码配置文件最新修改时间
	 * @return
	 */
	private long getReSignLastUptTime(){
		File file = new File(PROPERTY_PATH+FILE_NAME_RESIGN);
		if(!file.exists()){
			log.debug("获取文件["+PROPERTY_PATH+FILE_NAME_RESIGN+"]最新修改时间时，该文件不存在");
			return -1;
		}
		return file.lastModified();
	}
	/**
	 * 获取未签约或已解约响应码配置文件最新修改时间
	 * @return
	 */
	private long getSignOffLastUptTime(){
		File file = new File(PROPERTY_PATH+FILE_NAME_SIGNOFF);
		if(!file.exists()){
			log.debug("获取文件["+PROPERTY_PATH+FILE_NAME_SIGNOFF+"]最新修改时间时，该文件不存在");
			return -1;
		}
		return file.lastModified();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fp = PROPERTY_PATH + "test.properties";
		File file = new File(fp);
		System.out.println(file.exists());
		System.out.println(file.lastModified());
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddhhmmss");
		System.out.println(sf.format(new Date(file.lastModified())));
		
	}

}
