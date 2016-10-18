package com.dcits.smartbip.runtime.impl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.runtime.model.impl.SoapCompositeData;
import com.dcits.smartbip.utils.CompositeDataUtils;
import com.dcits.smartbip.utils.PropertiesUtils;
/**
 * 对私综合解约请求处理
 * @author srxhx053
 *
 */
public class SignOffRequestMapper extends AbstractBaseService implements IService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1934109512687619689L;
	/**
	 * 日志打印
	 */
	private static final Log log = LogFactory.getLog(SignOffRequestMapper.class);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
	/**
	 * 代缴费原子服务码
	 */
	private static final String DJF_YZ_SERVICE_ID = "Req3006200001811";
	/**
	 * 信用卡原子服务码
	 */
	private static final String XYK_YZ_SERVICE_ID = "Req3003200001301";
	/**
	 * 组合服务码
	 */
	private static final String ZF_SERVICE_ID = "Req2002201000103"; 
	
	@Override                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
	public String getId() {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
		return "SignOffRequestMapper";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
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
			log.info("开始调用基础服务[SignOffRequestMapper]......");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
		}
		// 获得综合解约产品代码                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
		String signCodeType = (String) SessionContext.getContext().getValue("signCodeType");
		//产品代码-代理业务号
		HashMap<String, String> dliywhMap = PropertiesUtils.getProductionAndDLIYWH();
		if("000001".equalsIgnoreCase(signCodeType)){//000001-网上银行
			
		}else if("000002".equalsIgnoreCase(signCodeType)){//000002-手机银行
			
		}else if("000003".equalsIgnoreCase(signCodeType)){//000003-短信银行
			
		}else if("000004".equalsIgnoreCase(signCodeType)){//000004-电话银行
			
		}else if("000005".equalsIgnoreCase(signCodeType)){//000005-信用卡自动还款
			reqXYKInfoDeal(XYK_YZ_SERVICE_ID);
		}else if("000006".equalsIgnoreCase(signCodeType)){//000006-支付宝卡通
			
		}else if("000007".equalsIgnoreCase(signCodeType)){//000007-ATM跨行转账
			
		}else if("000009".equalsIgnoreCase(signCodeType)){//000009-鑫元宝
			
		}else if("000010".equalsIgnoreCase(signCodeType)){//000010-鑫钱宝
			
		}else if(dliywhMap.containsKey(signCodeType)){//代缴费
			reqDJFInfoDeal(DJF_YZ_SERVICE_ID,dliywhMap.get(signCodeType));
		}else if("010019".equalsIgnoreCase(signCodeType)){//010019-市民卡转账(南京)
			
		}else if("000020".equalsIgnoreCase(signCodeType)){//000020-基金
			
		}else if("000021".equalsIgnoreCase(signCodeType)){//000021-理财
			
		}else if("000022".equalsIgnoreCase(signCodeType)){//000022-账户金
			
		}else if("000023".equalsIgnoreCase(signCodeType)){//000023-黄金T+D
			
		}else if("000024".equalsIgnoreCase(signCodeType)){//000024-易得利
			
		}else if("000025".equalsIgnoreCase(signCodeType)){//000025-易得利升级版
			ydlsjbReqInfoDeal("Req3001200000402","220026,220027");
		}else if("000026".equalsIgnoreCase(signCodeType)){//000026-卡内活转定
			ydlsjbReqInfoDeal("Req3001200000402","220012");
		}
		
		
		//设置产品代码,给代扣签约及ECIF状态更新使用
		SessionContext.getContext().setValue("SignOffProTp", signCodeType);
		
		if (log.isInfoEnabled()) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
			log.info("调用基础服务结束[SignOffRequestMapper]......");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
		}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
		return null;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
	
	/**
	 * 易得利升级版处理
	 * @param reqId	：Req+原子服务码
	 * @param cpdh	：chapdaih 产品代号
	 * @return	：1-失败，0-成功
	 */
	private int ydlsjbReqInfoDeal(String reqId,String cpdh){
		// 获得综合解约请求数据对象                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
		ICompositeData Req2002201000103 = (ICompositeData) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
				.getContext().getValue(ZF_SERVICE_ID);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
		ICompositeData reqICId = (ICompositeData) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		.getContext().getValue(reqId);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
		if (null == reqICId)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{   //如果原子服务对象为空，则新建一个原子服务对象                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
			reqICId  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			reqICId.setId(reqId);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			reqICId.setxPath("/"+reqId);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			getContext().setValue(reqId,reqICId);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
		List<ICompositeData> CBSFinanceGeneralSignOffArray = CompositeDataUtils.getByPath(                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
				Req2002201000103, "ReqAppBody/CBSFinanceGeneralSignOffReq/CBSFinanceGeneralSignOffArray");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
		                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
		for (ICompositeData signOffComposite : CBSFinanceGeneralSignOffArray) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
			String chapdaih = CompositeDataUtils.getValue(signOffComposite,"chapdaih");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
			                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
			                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
			//易得利升级版                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
			if(Arrays.asList(cpdh.split(",")).contains(chapdaih))
			{
				//核心localhead中需要 加密种子
				CompositeDataUtils.copy(signOffComposite, reqICId,"shoqguiy", "ReqLocalHead/shoqguiy");
				CompositeDataUtils.copy(signOffComposite, reqICId,"shoqmima", "ReqLocalHead/shoqmima");
				CompositeDataUtils.copy(signOffComposite, reqICId,"sqqrenbz", "ReqLocalHead/sqqrenbz");
				CompositeDataUtils.copy(signOffComposite, reqICId,"syspinsd", "ReqLocalHead/syspinsd");
				
//				CompositeDataUtils.copy(signOffComposite, reqICId,"qsqqczbz", "ReqAppBody/qsqqczbz");
				CompositeDataUtils.setValue(reqICId, "ReqAppBody/qsqqczbz", "4");
				CompositeDataUtils.copy(signOffComposite, reqICId,"kehuzhao", "ReqAppBody/kehuzhao");
				CompositeDataUtils.copy(signOffComposite, reqICId,"jiaoymma", "ReqAppBody/jiaoymma");
				CompositeDataUtils.copy(signOffComposite, reqICId,"zhhuzwmc", "ReqAppBody/zhhuzwmc");
				CompositeDataUtils.copy(signOffComposite, reqICId,"chapdaih", "ReqAppBody/chapdaih");
				CompositeDataUtils.copy(signOffComposite, reqICId,"jujijine", "ReqAppBody/jujijine");
				CompositeDataUtils.copy(signOffComposite, reqICId,"znckzhlx", "ReqAppBody/znckzhlx");				
				CompositeDataUtils.copy(signOffComposite, reqICId,"znckzcgz", "ReqAppBody/znckzcgz");
				CompositeDataUtils.copy(signOffComposite, reqICId,"qyxieyih", "ReqAppBody/qyxieyih");
				CompositeDataUtils.copy(signOffComposite, reqICId,"zhhaoxuh", "ReqAppBody/zhhaoxuh");
				CompositeDataUtils.copy(signOffComposite, reqICId,"zhfutojn", "ReqAppBody/zhfutojn");
				CompositeDataUtils.copy(signOffComposite, reqICId,"kehuleix", "ReqAppBody/kehuleix");
				CompositeDataUtils.copy(signOffComposite, reqICId,"sfxkkhzh", "ReqAppBody/sfxkkhzh");
				CompositeDataUtils.copy(signOffComposite, reqICId,"qicunjie", "ReqAppBody/qicunjie");
				CompositeDataUtils.copy(signOffComposite, reqICId,"huazhyuz", "ReqAppBody/huazhyuz");
				CompositeDataUtils.copy(signOffComposite, reqICId,"yznkhzbz", "ReqAppBody/yznkhzbz");
				CompositeDataUtils.copy(signOffComposite, reqICId,"llfdonbz", "ReqAppBody/llfdonbz");
				CompositeDataUtils.copy(signOffComposite, reqICId,"lilvfdbl", "ReqAppBody/lilvfdbl");
				CompositeDataUtils.copy(signOffComposite, reqICId,"lcdqclfs", "ReqAppBody/lcdqclfs");
				CompositeDataUtils.copy(signOffComposite, reqICId,"zdhzbzhi", "ReqAppBody/zdhzbzhi");
				CompositeDataUtils.copy(signOffComposite, reqICId,"shishbbz", "ReqAppBody/shishbbz");
				CompositeDataUtils.copy(signOffComposite, reqICId,"sshzjine", "ReqAppBody/sshzjine");
				CompositeDataUtils.copy(signOffComposite, reqICId,"chufleix", "ReqAppBody/chufleix");
				CompositeDataUtils.copy(signOffComposite, reqICId,"lchuglfs", "ReqAppBody/lchuglfs");
				CompositeDataUtils.copy(signOffComposite, reqICId,"mimazlei", "ReqAppBody/mimazlei");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
				                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
				CompositeDataUtils.copy(signOffComposite, reqICId, "llistinfo", "ReqAppBody/llistinfo");                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
				List<ICompositeData> srcArray1= new ArrayList<ICompositeData>();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
				List<ICompositeData> destArray1= new ArrayList<ICompositeData>();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
				srcArray1 = CompositeDataUtils.getByPath(signOffComposite, "llistinfo");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
				destArray1 = CompositeDataUtils.getByPath(reqICId, "ReqAppBody/llistinfo");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
				for (int i1 = 0; i1 < srcArray1.size(); i1++) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
					CompositeDataUtils.copy(srcArray1.get(i1), destArray1.get(i1), "cunqiiii", "cunqiiii");                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
					CompositeDataUtils.copy(srcArray1.get(i1), destArray1.get(i1), "baifenbi", "baifenbi");                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
				}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
			}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
		}
		return 0;
	}
	
	/**
	 * 信用卡处理
	 * @param reqId ：Req+原子服务码
	 * @return 1-失败，0-成功
	 */
	private int reqXYKInfoDeal(String reqId){
		//获取组合服务请求对象
		ICompositeData Req2002201000103 = (ICompositeData) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		.getContext().getValue(ZF_SERVICE_ID);
		//获取原子服务请求对象
		ICompositeData reqICD = (ICompositeData) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		.getContext().getValue(reqId);
		if (null == reqICD)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			reqICD  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			reqICD.setId(reqId);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			reqICD.setxPath("/"+reqId);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			getContext().setValue(reqId,reqICD);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		} 
		//获取卡号数组对象
		List<ICompositeData> CrCardArray = CompositeDataUtils.getByPath(                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
				Req2002201000103, "ReqAppBody/CrCardAutoRepaySignOffReq/CrCardArray"); 
		//遍历卡号
		List<String> kahaooList = new ArrayList<String>();
		for (ICompositeData CrCards : CrCardArray) {
			String kahaoo = CompositeDataUtils.getValue(CrCards,"KAHAOO");
			if(null == kahaoo)
			{
				log.info("信用卡卡号kahaoo为空");
				break;
			}
			if(kahaoo != null && !"".equals(kahaoo.trim())){
				kahaooList.add(kahaoo);
				getContext().setValue("kahaooList", kahaooList);
			}
			log.info("KAHAOO（信用卡卡号）列表["+kahaooList.toString()+"]");
			log.info("解析KAHAOO（信用卡卡号）结束....");
		}
		
		if(kahaooList == null || kahaooList.size() == 0){
			log.info("解析KAHAOO（信用卡卡号）失败...." +
					 "卡号为空");
		}
		return 0;
	}
	
	
	
	
	/**
	 * 代缴费处理
	 * @param reqId		：Req+原子服务码
	 * @param DLIYWH	：代理业务号
	 * @return	：1-失败，0-成功
	 */
	private int reqDJFInfoDeal(String reqId,String dliywh){
		// 获得综合解约请求数据对象                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
		ICompositeData Req2002201000103 = (ICompositeData) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
				.getContext().getValue(ZF_SERVICE_ID);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
		ICompositeData reqICD = (ICompositeData) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		.getContext().getValue(reqId);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
		if (null == reqICD)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			reqICD  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			reqICD.setId(reqId);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			reqICD.setxPath("/"+reqId);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			getContext().setValue(reqId,reqICD);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		//代缴费
		List<ICompositeData> DirectDebitSignOffArray = CompositeDataUtils.getByPath(                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
				Req2002201000103, "ReqAppBody/DirectDebitSignOffReq/DirectDebitSignOffArray");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
		List<String> dsyhbhList = new ArrayList<String>();
		String DLIYWH = "";//代理业务号
		for (ICompositeData signOffComposite : DirectDebitSignOffArray) {
			DLIYWH = CompositeDataUtils.getValue(signOffComposite,"DLIYWH");   
			if(null == DLIYWH)
			{
				log.info("代理业务号字段DLIYWH为空");
				break;
			}
			//对于数组里包含数组（多条记录）的结构柜面上送不了，柜面只能上送数组里包含一条记录的数组
			if(DLIYWH.equalsIgnoreCase(dliywh))
			{        
				CompositeDataUtils.copy(signOffComposite, reqICD,"SYSPINSEED", "ReqLocalHead/SYSPINSEED");
				//用于区分不同地区
				CompositeDataUtils.copy(signOffComposite, reqICD,"PDCTNO", "ReqLocalHead/PDCTNO");
				//字段赋值
				CompositeDataUtils.copy(signOffComposite, reqICD,"DLIYWH", "ReqAppBody/DLIYWH");
				CompositeDataUtils.copy(signOffComposite, reqICD,"ZYWXYH", "ReqAppBody/ZYWXYH");                                                                                                                                                                                                                                                                                                                                                                                                                                            
				CompositeDataUtils.copy(signOffComposite, reqICD,"YWZLBH", "ReqAppBody/YWZLBH");
				CompositeDataUtils.copy(signOffComposite, reqICD,"DANWBH", "ReqAppBody/DANWBH");
				CompositeDataUtils.copy(signOffComposite, reqICD,"DISFMC", "ReqAppBody/DISFMC");
				CompositeDataUtils.copy(signOffComposite, reqICD,"DAILRM", "ReqAppBody/DAILRM");
				CompositeDataUtils.copy(signOffComposite, reqICD,"ZHJNZL", "ReqAppBody/ZHJNZL");
				CompositeDataUtils.copy(signOffComposite, reqICD,"ZHJHAO", "ReqAppBody/ZHJHAO");
				CompositeDataUtils.copy(signOffComposite, reqICD,"DSYHDZ", "ReqAppBody/DSYHDZ");
				CompositeDataUtils.copy(signOffComposite, reqICD,"DSYHYB", "ReqAppBody/DSYHYB");
				CompositeDataUtils.copy(signOffComposite, reqICD,"DSYHDH", "ReqAppBody/DSYHDH");
				CompositeDataUtils.copy(signOffComposite, reqICD,"CAOZFS", "ReqAppBody/CAOZFS");
				CompositeDataUtils.copy(signOffComposite, reqICD,"JIOYMM", "ReqAppBody/JIOYMM");
				CompositeDataUtils.copy(signOffComposite, reqICD,"CID2XX", "ReqAppBody/CID2XX");
				CompositeDataUtils.copy(signOffComposite, reqICD,"GRZHLX", "ReqAppBody/GRZHLX");
				CompositeDataUtils.copy(signOffComposite, reqICD,"GRZHAO", "ReqAppBody/GRZHAO");
				
				//解析DSYHBH	第三方用户编号
				log.info("解析DSYHBH（第三方用户编号）开始....");
				
				List<ICompositeData> dsyhbhArray = CompositeDataUtils.getByPath(signOffComposite,"ThirdUserIdArray");
				for (ICompositeData tmpComposite : dsyhbhArray) {
					String dsyhbh = CompositeDataUtils.getValue(tmpComposite,"DSYHBH");
					if(dsyhbh != null && !"".equals(dsyhbh.trim())){
						dsyhbhList.add(dsyhbh);
					}
				}
				log.info("解析DSYHBH（第三方用户编号）结束....");
			}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
			log.info("DSYHBH（第三方用户编号）列表["+dsyhbhList.toString()+"]");
			getContext().setValue("dsyhbhList", dsyhbhList);
		}
		if(dsyhbhList == null || dsyhbhList.size() == 0){
			//Rsp2002201000103结构体不存在会报NullPointerException
			ICompositeData Rsp2002201000103 = (ICompositeData) SessionContext.getContext().getValue("Rsp2002201000103");
			ICompositeData RspSysHead = CompositeDataUtils.mkNodeNotExist(Rsp2002201000103, "RspSysHead");
			CompositeDataUtils.setValue(RspSysHead, "RetCode", "FAIL00");
			CompositeDataUtils.setValue(RspSysHead, "RetMsg", "交易失败！");
			CompositeDataUtils.setValue(RspSysHead, "TransStatus", "F");
			
			ICompositeData RspAppHead = CompositeDataUtils.mkNodeNotExist(Rsp2002201000103, "RspAppHead");
			ICompositeData arrayiItem = new SoapCompositeData();
			arrayiItem.setId("PrivSignOffRsltArray");
			RspAppHead.setChild("PrivSignOffRsltArray", arrayiItem);
			CompositeDataUtils.setValue(arrayiItem, "SignOffProTp", (String) SessionContext.getContext().getValue("signCodeType"));
			CompositeDataUtils.setValue(arrayiItem, "RetCode", "FAIL01");
			CompositeDataUtils.setValue(arrayiItem, "RetMsg", dsyhbhList == null?"代理业务号["+DLIYWH+"]不合法":"第三方用户编号DSYHBH为空");
			CompositeDataUtils.setValue(arrayiItem, "TransStatus", "F");
		}
		
		return 0;
	}
}