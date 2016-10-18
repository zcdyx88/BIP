package com.dcits.smartbip.runtime.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.ErrorCodes;
import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.entity.Production;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.runtime.model.impl.SoapCompositeData;
import com.dcits.smartbip.utils.CompositeDataUtils;
import com.dcits.smartbip.utils.PropertiesUtils;
/**
 * 对私换签请求处理
 *
 */
public class ChangeSignOffRequestMapper extends AbstractBaseService implements IService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1934109512687619689L;
	/**
	 * 日志打印
	 */
	private static final Log log = LogFactory.getLog(ChangeSignOffRequestMapper.class);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
	/**
	 * 代缴费原子服务码
	 */
	private static final String DJF_YZ_SERVICE_ID = "Req3006200001815";
	/**
	 * 组合服务码
	 */
	private static final String ZF_SERVICE_ID = "Req2002201000105"; 
	
	private static final String XYK_YZ_SERVICE_ID = "Req3003200001301";
	@Override                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
	public String getId() {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
		return "ChangeSignOffRequestMapper";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
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
			log.info("开始调用基础服务[ChangeSignOffRequestMapper]......");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
		}
		
		// 获得综合解约产品代码                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
		String signCodeType = (String) SessionContext.getContext().getValue("exchgSignProCode");
		HashMap<String, String> dliywhMap = PropertiesUtils.getProductionAndDLIYWH();
		if("000001".equalsIgnoreCase(signCodeType)){//000001-网上银行
			
		}else if("000002".equalsIgnoreCase(signCodeType)){//000002-手机银行
			
		}else if("000003".equalsIgnoreCase(signCodeType)){//000003-短信银行
			
		}else if("000004".equalsIgnoreCase(signCodeType)){//000004-电话银行
			
		}else if("000005".equalsIgnoreCase(signCodeType)){//000005-信用卡自动还款
			reqXYKInfoDeal(XYK_YZ_SERVICE_ID);
		}else if("000006".equalsIgnoreCase(signCodeType)){//000006-支付宝卡通
			
		}else if("000007".equalsIgnoreCase(signCodeType)){//000007-ATM跨行转账
		
		}else if("000008".equalsIgnoreCase(signCodeType)){//000008-三方存款
			reqTrdCunInfoDeal(ZF_SERVICE_ID);
		}else if("000009".equalsIgnoreCase(signCodeType)){//000009-鑫元宝
			
		}else if("000010".equalsIgnoreCase(signCodeType)){//000010-鑫钱宝
			
		}else if(dliywhMap.containsKey(signCodeType)){//代缴费
			reqDJFInfoDeal(DJF_YZ_SERVICE_ID,dliywhMap.get(signCodeType));
		}else if("000020".equalsIgnoreCase(signCodeType)){//000020-基金
			
		}else if("000021".equalsIgnoreCase(signCodeType)){//000021-理财
			
		}else if("000022".equalsIgnoreCase(signCodeType)){//000022-账户金
			
		}else if("000023".equalsIgnoreCase(signCodeType)){//000023-黄金T+D
			
		}else if("000024".equalsIgnoreCase(signCodeType)){//000024-易得利
			setECIFParams(signCodeType);
		}else if("000025".equalsIgnoreCase(signCodeType)){//000025-易得利升级版
			setECIFParams(signCodeType);
		}else if("000026".equalsIgnoreCase(signCodeType)){//000026-卡内活转定
			setECIFParams(signCodeType);
		}
		
		
		//设置产品代码,给代扣签约及ECIF状态更新使用
		SessionContext.getContext().setValue("ExchgSignOffProTp", signCodeType);
		
		if (log.isInfoEnabled()) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
			log.info("调用基础服务结束[ChangeSignOffRequestMapper]......");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
		}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
		return null;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         

	/**
	 * 信用卡处理
	 * @param reqId ：Req+原子服务码
	 * @return 1-失败，0-成功
	 */
	private int reqXYKInfoDeal(String reqId){
		//获取组合服务请求对象
		ICompositeData Req2002201000105 = (ICompositeData) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
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
				Req2002201000105, "ReqAppBody/CrCdAutoRepayExchgSignReq/CrCardArray"); 
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
//				getContext().setValue("CONT_ACC", kahaoo);//ECIF维护使用
				kahaooList.add(kahaoo);
			}
			log.info("解析KAHAOO（信用卡卡号）结束....");
		}
		getContext().setValue("kahaooList", kahaooList);
		log.info("KAHAOO（信用卡卡号）列表["+kahaooList.toString()+"]");
		if(kahaooList == null || kahaooList.size() == 0){
			log.info("解析KAHAOO（信用卡卡号）失败...." +
					 "卡号为空");
		}
		return 0;
	}
	
	
	/**
	 *三方存款
	 * @param reqId
	 * @return
	 */
	private void reqTrdCunInfoDeal(String reqId){
		ICompositeData Req2002201000105 = (ICompositeData) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
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
		List<ICompositeData> destExchgSignArray = CompositeDataUtils.getByPath(                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
				Req2002201000105, "ReqAppBody/ThirdDepMngExchgSignReq/ThirdDepMngFlgArray");  
		String sOrganId = "";
		String sCustAcct = "";
		List<String> sOrg_sCusList = new ArrayList<String>();
		getContext().setValue("sOrg_sCusList", sOrg_sCusList);
		StringBuffer sOrganId_sCustAcct = new StringBuffer();
		for (ICompositeData signOffComposite : destExchgSignArray) {
			sOrganId = CompositeDataUtils.getValue(signOffComposite,"sOrganId");   
			sCustAcct = CompositeDataUtils.getValue(signOffComposite,"sCustAcct");  
			if(null == sOrganId || null == sCustAcct )
			{
				log.info("sOrganId["+sOrganId+"],sCustAcct[" + sCustAcct + "]为空！");
				continue;
			}else{
				sOrganId_sCustAcct.append(sOrganId).append("_").append(sCustAcct); 
				log.info("三方存款唯一标识[sOrganId_sCustAcct]:"+sOrganId_sCustAcct.toString());
				sOrg_sCusList.add(sOrganId_sCustAcct.toString());
			}
			if(log.isDebugEnabled()){
				log.debug("转换后的结果[" + sOrganId_sCustAcct.toString() + "]");
			}			
		}
	}
	
	/**
	 * 代缴费处理
	 */
	private int reqDJFInfoDeal(String reqId,String dliywh){
		
		List<String> dsyhbhList = new ArrayList<String>();

		// 获得综合解约请求数据对象                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
		ICompositeData Req2002201000105 = (ICompositeData) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
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
		List<ICompositeData> DirectDebitExchgSignArray = CompositeDataUtils.getByPath(                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
				Req2002201000105, "ReqAppBody/DirectDebitExchgSignReq/DirectDebitExchgSignArray");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
		String DLIYWH = "";
		for (ICompositeData signOffComposite : DirectDebitExchgSignArray) {
			DLIYWH = CompositeDataUtils.getValue(signOffComposite,"DLIYWH");   
			log.debug("代理业务号[" + DLIYWH + "]");
			if(null == DLIYWH)
			{
				log.info("代理业务号"+DLIYWH+"为空！");
				break;
			}
			//对于数组里包含数组（多条记录）的结构柜面上送不了，柜面只能上送数组里包含一条记录的数组
			if(DLIYWH.equalsIgnoreCase(dliywh))
			{        
				//字段赋值
				CompositeDataUtils.copy(signOffComposite, reqICD,"SYSPINSEED", "ReqLocalHead/SYSPINSEED");
				//用于区分不同地区
				CompositeDataUtils.copy(signOffComposite, reqICD,"PDCTNO", "ReqLocalHead/PDCTNO");
				
				
				CompositeDataUtils.copy(signOffComposite, reqICD,"CAOZBZ", "ReqAppBody/CAOZBZ");
				CompositeDataUtils.copy(signOffComposite, reqICD,"DLIYWH", "ReqAppBody/DLIYWH");                                                                                                                                                                                                                                                                                                                                                                                                                                            
				CompositeDataUtils.copy(signOffComposite, reqICD,"GRZHAO", "ReqAppBody/GRZHAO");
				CompositeDataUtils.copy(signOffComposite, reqICD,"DAILRM", "ReqAppBody/DAILRM");
				CompositeDataUtils.copy(signOffComposite, reqICD,"HQZHLX", "ReqAppBody/HQZHLX");
				CompositeDataUtils.copy(signOffComposite, reqICD,"HQZHAO", "ReqAppBody/HQZHAO");
				CompositeDataUtils.copy(signOffComposite, reqICD,"MMJYFS", "ReqAppBody/MMJYFS");
				CompositeDataUtils.copy(signOffComposite, reqICD,"JIOYMM", "ReqAppBody/JIOYMM");
				CompositeDataUtils.copy(signOffComposite, reqICD,"ZJYJFS", "ReqAppBody/ZJYJFS");
				CompositeDataUtils.copy(signOffComposite, reqICD,"ZHJNZL", "ReqAppBody/ZHJNZL");
				CompositeDataUtils.copy(signOffComposite, reqICD,"ZHJHAO", "ReqAppBody/ZHJHAO");
				CompositeDataUtils.copy(signOffComposite, reqICD,"MOBILE", "ReqAppBody/MOBILE");
				
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
			getContext().setValue("dsyhbhList", dsyhbhList);
		}
		log.info("DSYHBH（第三方用户编号）列表["+dsyhbhList.toString()+"]");
		if(dsyhbhList == null || dsyhbhList.size() == 0){
			//Rsp2002201000105结构体不存在会报NullPointerException
			ICompositeData Rsp2002201000105 = (ICompositeData) SessionContext.getContext().getValue("Rsp2002201000105");
			ICompositeData RspAppBody = CompositeDataUtils.mkNodeNotExist(Rsp2002201000105, "RspAppBody");
			ICompositeData arrayiItem = new SoapCompositeData();
			arrayiItem.setId("PrivExchgSignOffRsltArray");
			RspAppBody.setChild("PrivExchgSignOffRsltArray", arrayiItem);
			CompositeDataUtils.setValue(arrayiItem, "ExchgSignOffProTp", (String) SessionContext.getContext().getValue("exchgSignProCode"));
			CompositeDataUtils.setValue(arrayiItem, "RetCode", ErrorCodes.CODE_BIP00T0001);
			CompositeDataUtils.setValue(arrayiItem, "RetMsg", dsyhbhList == null?"代理业务号["+DLIYWH+"]为空":"代理业务号[" + DLIYWH + "]上送不正确,请确认");
			CompositeDataUtils.setValue(arrayiItem, "TransStatus", "F");
		}
		
		return 0;
	}
	
	private void setECIFParams(String signCode){
	    @SuppressWarnings("unchecked")
		Map<String, Production> signCodeMap = (Map<String, Production>)getContext().getValue("SignCodeMap");
		if(signCodeMap != null){
			if("0".equals(signCodeMap.get(signCode).getCstAcctSignFlg())){//客户级
				getContext().setValue("SIGN_FLAG", "1");
			}else{//账户级
				getContext().setValue("SIGN_FLAG", "2");
			}
		}else{
			log.error("对私换签产品配置信息为空，请核实");
		}
	}
}