package com.dcits.smartbip.runtime.property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;
import com.dcits.smartbip.journal.impl.JournalConfig;
import com.dcits.smartbip.journal.impl.JournalConstants;
import com.dcits.smartbip.runtime.entity.BipProductsTab;
import com.dcits.smartbip.runtime.entity.ExcludeInfo;
import com.dcits.smartbip.runtime.entity.Production;
import com.dcits.smartbip.runtime.service.BipProductsTabService;
import com.dcits.smartbip.utils.ApplicationUtils;
/**
 * 产品代码信息加载服务
 * @author srxhx273
 *
 */
public class ProductionsService extends JedisPubSub {

	public final static Log log = LogFactory.getLog(ProductionsService.class);
	
	/**
	 * ProductionsService实例
	 */
	private static ProductionsService instance = null;
	
	
	private JedisPool pool;
	
	/**
	 * key：0-个人 1-对公 ，value：[key:产品代码，value：产品信息Production]
	 */
	private Map<String, Map<String,Production>> productionMaps ;
	/**
	 * key:签约品种代码_个人对公标志，value：产品信息Production
	 */
	
	private BipProductsTabService bipProductsTabService;
	
	private ProductionsService(){
		reload();
	}
	
	static {
		if (null == instance) {
			instance = new ProductionsService();
			instance.start();
		}
	}
	
	private void start(){
		new Thread(new Runnable(){

			@Override
			public void run() {
				JournalConfig journalConfig = JournalConfig.getInstance();
				String ip = journalConfig.getConfig(JournalConstants.QUEUE_IP);
				String port = journalConfig.getConfig(JournalConstants.QUEUE_PORT);
				JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
				jedisPoolConfig.setMaxTotal(Integer.parseInt(journalConfig.getConfig(JournalConstants.MAX_ACTIVE)));
				jedisPoolConfig.setMaxIdle(Integer.parseInt(journalConfig.getConfig(JournalConstants.MAX_IDLE)));
				jedisPoolConfig.setMaxWaitMillis(Integer.parseInt(journalConfig.getConfig(JournalConstants.MAX_WAIT)));
				pool = new JedisPool(jedisPoolConfig, ip, Integer.parseInt(port),30000,"redis");
				Jedis jedis = null;
				try {
					jedis = pool.getResource();
					jedis.subscribe(instance, "updateProduction");
				} catch (Exception e) {
					log.error(e, e);
				} finally {
					if (jedis != null) {
						pool.returnResource(jedis);
					}
				}
			}
		}).start();
		
	}
	
	public static ProductionsService getInstance() {
		if(instance == null){
			synchronized (ProductionsService.class) {
				if(instance == null){
					log.info("创建服务[ProductionsService]");
					instance = new ProductionsService();
					instance.start();
				}
			}
		}
		return instance;
	}
	/**
	 * 更新产品参数
	 * @param bipProducts
	 */
	public void saveProducts(BipProductsTab bipProducts){
		if(bipProductsTabService == null){
			bipProductsTabService = (BipProductsTabService)ApplicationUtils.getInstance().getBean("bipProductsTabService");
		}
		bipProductsTabService.save(bipProducts);
		
		Jedis jedis = null;
		try {
            jedis = pool.getResource();
            //发送一个订阅发布的通知
            jedis.publish("updateProduction", "update");
        } catch (Exception e) {
            log.error("发送流水失败!", e);
        } finally {
            if (null != pool && null != jedis)
                pool.returnResource(jedis);
        }
	}
	/**
	 * 根据主键删除产品
	 * @return
	 */
	public int deleteById(String signVarCd,String privyCorpFlg){
		if(bipProductsTabService == null){
			bipProductsTabService = (BipProductsTabService)ApplicationUtils.getInstance().getBean("bipProductsTabService");
		}
		int rt = bipProductsTabService.deleteById(signVarCd, privyCorpFlg);
		
		Jedis jedis = null;
		try {
            jedis = pool.getResource();
            //发送一个订阅发布的通知
            jedis.publish("updateProduction", "update");
        } catch (Exception e) {
            log.error("发送流水失败!", e);
        } finally {
            if (null != pool && null != jedis)
                pool.returnResource(jedis);
        }
        return rt;
	}
	
	public void reload(){
		Map<String, Map<String, Production>> tempProductionMaps = new ConcurrentHashMap<String,  Map<String, Production>>();
		try{
			if(bipProductsTabService == null){
				bipProductsTabService = (BipProductsTabService)ApplicationUtils.getInstance().getBean("bipProductsTabService");
			}
			List<BipProductsTab> products =  bipProductsTabService.getAllProduicts();
			if(products == null){				
				log.info("数据库中未配置产品代码配置信息");
				return;
			}
		    
			Production production = null;
			ArrayList<ExcludeInfo> excludeInfoLst = null;
			ExcludeInfo excludeInfo = null;
			ArrayList<String> tmpLst = null;
			String PrivyCorpFlg = "";
			String excludeInfoStr = "";
			String blngBrchBnkStr = "";
			String avlSignChlStr = "";
			String avlSignOffChlStr = "";
			String avlSignExchgChlStr = "";
			String[] tmpArray = null;
			for(BipProductsTab productTab : products){
				
				PrivyCorpFlg = productTab.getPrivyCorpFlg();
				Map<String, Production> productionMap = tempProductionMaps.get(PrivyCorpFlg);
				if(null == productionMap){
					productionMap = new ConcurrentHashMap<String, Production>();
				}
				production = new Production();
				production.setSignVarCd(productTab.getSignVarCd());//签约品种代码
				production.setSignVarNm(productTab.getSignVarNm());//签约品种名称
				production.setPrivyCorpFlg(PrivyCorpFlg);//个人对公标志
				production.setBlngSysId(productTab.getBlngSysId());//所属系统
				production.setBlngLglPrsnCd(productTab.getBlngLglPrsnCd());//法人代码
				production.setCstAcctSignFlg(productTab.getCstAcctSignFlg());//客户账户签约标志
				production.setInOutBankFlg(productTab.getInOutBankFlg());//行内行外标志
				production.setSignOffFlg(productTab.getSignOffFlg());//是否允许解约
				production.setCroRegSignOffFlg(productTab.getCroRegSignOffFlg());//是否允许跨地区解约
				production.setAvlMntnFlg(productTab.getAvlMntnFlg());//是否允许维护
				production.setQryListVisionFlg(productTab.getQryListVisionFlg());//查询清单是否展现
				production.setClsMstSignOffFlg(productTab.getClsMstSignOffFlg());//销户是否必解
				production.setAvlTrsfrFlg(productTab.getAvlTrsfrFlg());//可转移标志
				production.setOneCdMulSignFlg(productTab.getOneCdMulSignFlg());//是否允许一卡多签
				production.setAvlSignChlFlg(productTab.getAvlSignChlFlg());//是否控制签约渠道
				production.setAvlSignOffChlFlg(productTab.getAvlSignOffChlFlg());//是否控制解约渠道
				production.setAvlSignExchgChlFlg(productTab.getAvlSignExchgChlFlg());//是否控制换签渠道
				
				excludeInfoStr = productTab.getExcludeInfoList();//互斥信息列表
				excludeInfoLst = new ArrayList<ExcludeInfo>();
				if(excludeInfoStr != null){
					List<String> excludeInfoList = Arrays.asList(excludeInfoStr.split("\\^"));
					for(String tmp : excludeInfoList){
						String[] tmpArr = tmp.split("\\|",-1);
						excludeInfo = new ExcludeInfo();
						excludeInfo.setExcludeVarCd(tmpArr[0]);//互斥品种代码
						excludeInfo.setExcludeTpNo(tmpArr[1]);//互斥类型编号
						excludeInfo.setExcludePdPri(tmpArr[2]);//互斥产品优先级
						excludeInfo.setExcludeTpNm(tmpArr[3]);//互斥类型名称
						excludeInfoLst.add(excludeInfo);
					}
				}
				production.setExcludeInfoList(excludeInfoLst);
				
				blngBrchBnkStr = productTab.getBlngBrchBnkList();//所属分行列表
				tmpLst = new ArrayList<String>();
				if(blngBrchBnkStr != null){
					tmpArray = blngBrchBnkStr.split("\\|");
					for(int i = 0 ;tmpArray != null && i < tmpArray.length;i++){
						tmpLst.add(tmpArray[i]);
					}
				}
				production.setBlngBrchBnkList(tmpLst);
				
				avlSignChlStr = productTab.getAvlSignChlList();//可签约渠道列表
				tmpLst = new ArrayList<String>();
				if(avlSignChlStr != null){
					tmpArray = avlSignChlStr.split("\\|");
					for(int i = 0 ;tmpArray != null && i < tmpArray.length;i++){
						tmpLst.add(tmpArray[i]);
					}
				}
				production.setAvlSignChlList(tmpLst);
				
				avlSignOffChlStr = productTab.getAvlSignOffChlList();//可解约渠道列表
				tmpLst = new ArrayList<String>();
				if(avlSignOffChlStr != null){
					tmpArray = avlSignOffChlStr.split("\\|");
					for(int i = 0 ;tmpArray != null && i < tmpArray.length;i++){
						tmpLst.add(tmpArray[i]);
					}
				}
				production.setAvlSignOffChlList(tmpLst);
				
				avlSignExchgChlStr = productTab.getAvlSignExchgChlList();//可解约渠道列表
				tmpLst = new ArrayList<String>();
				if(avlSignExchgChlStr != null){
					tmpArray = avlSignExchgChlStr.split("\\|");
					for(int i = 0 ;tmpArray != null && i < tmpArray.length;i++){
						tmpLst.add(tmpArray[i]);
					}
				}
				production.setAvlSignExchgChlList(tmpLst);
				productionMap.put(production.getSignVarCd(),production);
				tempProductionMaps.put(PrivyCorpFlg, productionMap);
			}
			productionMaps = tempProductionMaps;
		}catch(Exception e){
			log.error("加载签约品种参数",e);
		}
	}
	
	
	/**
	 * 获取产品代码配置信息
	 * @return	对私/对公签约产品信息
	 */
	public Map<String,Production> getProductMap(String privyCorpFlg){
		return productionMaps.get(privyCorpFlg);
	}
	
	public Production getProduct(String productCode, String privyCorpFlg){
		Map<String,Production> map = productionMaps.get(privyCorpFlg);
		return map==null?null:map.get(productCode);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProductionsService.getInstance();
	}

	@Override
	public void onMessage(String arg0, String arg1) {
		if(log.isInfoEnabled()){
			log.info(">>>>>>>>>>接收到产品代码更新的请求，开始进行产品更新");
		}
		reload();
		if(log.isInfoEnabled()){
			log.info(">>>>>>>>>>产品代码更新完成");
		}
	}

	@Override
	public void onPMessage(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPSubscribe(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPUnsubscribe(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSubscribe(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnsubscribe(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

}
