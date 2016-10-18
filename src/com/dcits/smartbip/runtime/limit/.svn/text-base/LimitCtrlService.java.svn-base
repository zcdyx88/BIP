package com.dcits.smartbip.runtime.limit;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Node;

import com.dcits.smartbip.exception.ConfigurationParseException;
import com.dcits.smartbip.utils.XMLUtils;
/**
 * 限额规则，根据配置获取响应的渠道和机构的限额标志
 * @author xhx102
 *
 */
public class LimitCtrlService {
	public final static Log log = LogFactory.getLog(LimitCtrlService.class);
	// unique instance
	private static LimitCtrlService instance = new LimitCtrlService();
	public static Map<String, String> map = new HashMap<String, String>();
	public static String filename = "limitCtrl.xml";

	public LimitCtrlService(){
		
	}
	public static LimitCtrlService getInstance() {
		return instance;
	}
	/***
	 * 重新加载
	 */
	public static void reload() throws ConfigurationParseException{
		load();
	} 
	
	/***
	 * 加载限额配置
	 */
	public static void load(){
		parseConfig();
	} 
	
	/**
	 * 获取占用标志，
	 * @param channel
	 * @param orgid
	 * @return
	 */
	public static String getLimit(String channel, String orgid) {
		if (channel == null || orgid == null)
			return "3"; //default 3 , 0-不占用;1-占用但忽略应答;2-占用且允许通讯故障;3-占用且必须成功
		//search for specific config first
		String  limitFlag = map.get(channel + ":" + orgid);
		
		//if not found, search for orgid 
		if (limitFlag == null)
			limitFlag = map.get("All:" + orgid);
		
		//if not found yet, search for channel 
		if (limitFlag == null)
			limitFlag = map.get(channel + ":All");
		
		//if not found yet, search for All:All config
		if (limitFlag == null)
			limitFlag = map.get("All:All");
			
		return limitFlag == null ? "3" : limitFlag; //default 3
	}
	
	/**
	 * 解析配置
	 * @throws ConfigurationParseException
	 */
	@SuppressWarnings("unchecked")
	public static void parseConfig(){
		File fi = null;
		String fpath = System.getProperty("smartbip.install") + File.separator + "configs" + File.separator + "limitCtrlDef" + File.separator +  filename;
		try {
			fi = new File(fpath);
			Document doc = XMLUtils.getDocFromFile(fi);
			List<Node> list = doc.selectNodes("/limits/channel");
			if (list != null && list.size() > 0) {
				for (Node n : list) {
					String channelid = n.selectSingleNode("@channelid").getText().trim();
					List<Node> list2 = n.selectNodes("org");
					if (list2 != null && list2.size() > 0) {
						for (Node n2 : list2) {
							String orgName = n2.selectSingleNode("@name").getText().trim();
							String limitflag = n2.selectSingleNode("@limitflag").getText().trim();
							map.put(channelid + ":" + orgName, limitflag);
							if (log.isDebugEnabled()) {
								log.debug("加载限额配置信息 " + channelid + ":" + orgName + "=[" + limitflag + "]");
							}
						}
					}
				}
			} else {
				if (log.isInfoEnabled()) {
					log.info("没有加载任何限额配置");
				}
			}
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error("限额配置解析失败");
			}
			e.printStackTrace();
		} 
	}
	
	public static void main(String[] args) throws ConfigurationParseException {
		System.setProperty("smartbip.install", System.getProperty("user.dir"));
		LimitCtrlService.load();
		String flag = LimitCtrlService.getLimit("1131001", "9985");
		System.out.println(flag);
	}
}
