package com.dcits.smartbip.runtime.impl;

import java.util.ArrayList;
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
 * 对私综合解约响应处理
 * @author srxhx053
 *
 */
public class SignOffResponseMapper extends AbstractBaseService implements IService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1934109512687619689L;
	/**
	 * 日志打印
	 */
	private static final Log log = LogFactory.getLog(SignOffResponseMapper.class);
	/**
	 * 代缴费原子服务码:Rsp + 原子服务码
	 */
	private static final String DJF_YZ_SERVICE_ID = "Rsp3006200001811";
	/**
	 * 市民卡转账(南京)原子服务码
	 */
	private static final String SMK_YZ_SERVICE_ID = "Rsp3003200000405";
	/**
	 * 信用卡自动还款 原子服务码
	 */
	private static final String XYK_YZ_SERVICE_ID = "Rsp3003200001301";
	/**
	 * ATM卡卡(跨行)转账原子服务码
	 */
	private static final String ATM_KK_SERVICE_ID = "Rsp3003200000404";
	/**
	 * 组合服务码
	 */
	private static final String ZF_SERVICE_ID = "Rsp2002201000103";
	
	@Override                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
	public String getId() {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
		return "SignOffResponseMapper";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
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
			log.info("开始调用基础服务[SignOffResponseMapper]......");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
		}
		
		// 获得综合解约产品代码                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
		String signCodeType = (String) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
				.getContext().getValue("signCodeType");
		if("000001".equalsIgnoreCase(signCodeType)){//000001-网上银行
			
		}else if("000002".equalsIgnoreCase(signCodeType)){//000002-手机银行
			
		}else if("000003".equalsIgnoreCase(signCodeType)){//000003-短信银行
			
		}else if("000004".equalsIgnoreCase(signCodeType)){//000004-电话银行
			
		}else if("000005".equalsIgnoreCase(signCodeType)){//000005-信用卡自动还款
			xykInfoDeal(XYK_YZ_SERVICE_ID);
		}else if("000006".equalsIgnoreCase(signCodeType)){//000006-支付宝卡通
			
		}else if("000007".equalsIgnoreCase(signCodeType)){//000007-ATM跨行转账
			setECIFParams(ATM_KK_SERVICE_ID,signCodeType);
		}else if("000009".equalsIgnoreCase(signCodeType)){//000009-鑫元宝
			
		}else if("000010".equalsIgnoreCase(signCodeType)){//000010-鑫钱宝
			
		}else if("010011".equalsIgnoreCase(signCodeType)){//010011-电费(南京)
			setECIFParams(DJF_YZ_SERVICE_ID,signCodeType);
			reponseInfoDeal(DJF_YZ_SERVICE_ID);
		}else if("020011".equalsIgnoreCase(signCodeType)){//020011-电费(泰州)
			setECIFParams(DJF_YZ_SERVICE_ID,signCodeType);
			reponseInfoDeal(DJF_YZ_SERVICE_ID);
		}else if("010012".equalsIgnoreCase(signCodeType)){//010012-水费(南京)
			setECIFParams(DJF_YZ_SERVICE_ID,signCodeType);
			reponseInfoDeal(DJF_YZ_SERVICE_ID);
		}else if("010013".equalsIgnoreCase(signCodeType)){//010013-联通收费(南京)
			setECIFParams(DJF_YZ_SERVICE_ID,signCodeType);
			reponseInfoDeal(DJF_YZ_SERVICE_ID);
		}else if("010014".equalsIgnoreCase(signCodeType)){//010014-燃气收费(南京)
			setECIFParams(DJF_YZ_SERVICE_ID,signCodeType);
			reponseInfoDeal(DJF_YZ_SERVICE_ID);
		}else if("020014".equalsIgnoreCase(signCodeType)){//020014-燃气收费(泰州)
			setECIFParams(DJF_YZ_SERVICE_ID,signCodeType);
			reponseInfoDeal(DJF_YZ_SERVICE_ID);
		}else if("010015".equalsIgnoreCase(signCodeType)){//010015-移动收费(南京)
			setECIFParams(DJF_YZ_SERVICE_ID,signCodeType);
			reponseInfoDeal(DJF_YZ_SERVICE_ID);
		}else if("010016".equalsIgnoreCase(signCodeType)){//010016-有线收费(南京)
			setECIFParams(DJF_YZ_SERVICE_ID,signCodeType);
			reponseInfoDeal(DJF_YZ_SERVICE_ID);
		}else if("010017".equalsIgnoreCase(signCodeType)){//010017-电信收费(南京)
			setECIFParams(DJF_YZ_SERVICE_ID,signCodeType);
			reponseInfoDeal(DJF_YZ_SERVICE_ID);
		}else if("010018".equalsIgnoreCase(signCodeType)){//010018-江宁燃气收费(南京)
			setECIFParams(DJF_YZ_SERVICE_ID,signCodeType);
			reponseInfoDeal(DJF_YZ_SERVICE_ID);
		}else if("010019".equalsIgnoreCase(signCodeType)){//010019-市民卡转账(南京)
			setECIFParams(SMK_YZ_SERVICE_ID,signCodeType);
		}else if("000020".equalsIgnoreCase(signCodeType)){//000020-基金
			
		}else if("000021".equalsIgnoreCase(signCodeType)){//000021-理财
			
		}else if("000022".equalsIgnoreCase(signCodeType)){//000022-账户金
			
		}else if("000023".equalsIgnoreCase(signCodeType)){//000023-黄金T+D
			
		}else if("000024".equalsIgnoreCase(signCodeType)){//000024-易得利
			
		}else if("000025".equalsIgnoreCase(signCodeType)){//000025-易得利升级版
			cbsFinanceGeneralResultSignOffProcess("000025","易得利升级版");
		}else if("000026".equalsIgnoreCase(signCodeType)){//000026-卡内活转定
			cbsFinanceGeneralResultSignOffProcess("000026","卡内活转定");
		}
		
		if (log.isInfoEnabled()) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
			log.info("调用基础服务结束[SignOffResponseMapper]......");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
		}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
		return null;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
	}       
	/**
	 * 信用卡响应报文处理
	 * @param rspId ：Rsp3003200001301
	 * @return
	 */
	private int xykInfoDeal(String rspId){
		ICompositeData Rsp2002201000103 = (ICompositeData) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		.getContext().getValue(ZF_SERVICE_ID); 
		ICompositeData rspICD = (ICompositeData) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		.getContext().getValue(rspId);
		if (null == rspICD)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			log.info(rspId+"为空");
			return 1;
		} 
		String retCode = CompositeDataUtils.getValue((ICompositeData) SessionContext.getContext()
				.getValue(rspId), "RspSysHead/RetCode");
		log.info("信用卡自动还款  解约结果retCode=["+retCode+"]");
		String kahaoo = (String)SessionContext.getContext().getValue("kahaoo");
		if("000000".equals(retCode)){
			log.info("信用卡解约成功...");
		}
		else {
				ICompositeData destArrayParent = CompositeDataUtils.mkNodeNotExist(Rsp2002201000103, "RspAppBody");
				ICompositeData arrayiItem = new SoapCompositeData();
				arrayiItem.setId("PrivSignOffRsltArray");
				destArrayParent.setChild("PrivSignOffRsltArray", arrayiItem);
				
				CompositeDataUtils.setValue(arrayiItem, "TransStatus", "F");
				CompositeDataUtils.copy(rspICD, arrayiItem, "RspSysHead/RetCode" , "RetCode");
				CompositeDataUtils.copy(rspICD, arrayiItem, "RspSysHead/RetMsg", "RetMsg");
				CompositeDataUtils.setValue(arrayiItem, "SignOffProTp", (String)SessionContext.getContext().getValue("signCodeType"));
				CompositeDataUtils.setValue(arrayiItem, "SignOffProNm", "信用卡自动还款");
				CompositeDataUtils.setValue(arrayiItem, "CONT_ACC", kahaoo);
				
			}
		return 1;
	}

	
	/**
	 * 代缴费响应报文处理
	 * @param rspId	:Rsp+原子服务码
	 * @return	：1-失败，0-成功
	 */
	private int reponseInfoDeal(String rspId){
		// 获得综合解约响应数据对象                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
		ICompositeData Rsp2002201000103 = (ICompositeData) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
				.getContext().getValue(ZF_SERVICE_ID);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
		ICompositeData rspICD = (ICompositeData) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		.getContext().getValue(rspId);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
		if (null == rspICD)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			log.info("返回结构体["+rspId+"]为空");
			return 1;
		}  
		
		
		//代缴费
		
		ICompositeData destArrayParent = CompositeDataUtils.mkNodeNotExist(Rsp2002201000103, "RspAppBody/DirectDebitSignOffRes");
		ICompositeData arrayiItem = new SoapCompositeData();
		arrayiItem.setId("DirectDebitSignOffArray");
		destArrayParent.setChild("DirectDebitSignOffArray", arrayiItem);
		CompositeDataUtils.copy(rspICD, arrayiItem, "RspAppBody/FOBJ1/DLIYWH", "FOBJ1/DLIYWH");
		CompositeDataUtils.copy(rspICD, arrayiItem, "RspAppBody/FOBJ1/ZYWXYH", "FOBJ1/ZYWXYH");
		CompositeDataUtils.copy(rspICD, arrayiItem, "RspAppBody/FOBJ1/DSYHBH", "FOBJ1/DSYHBH");
		CompositeDataUtils.copy(rspICD, arrayiItem, "RspAppBody/FOBJ2/ZYWXYH", "FOBJ2/ZYWXYH");
		CompositeDataUtils.copy(rspICD, arrayiItem, "RspAppBody/FOBJ2/SHMGXX", "FOBJ2/SHMGXX");
		CompositeDataUtils.copy(rspICD, arrayiItem, "RspAppBody/FOBJ2/DSYHBH", "FOBJ2/DSYHBH");
		CompositeDataUtils.copy(rspICD, arrayiItem, "RspAppBody/FOBJ2/DISFMC", "FOBJ2/DISFMC");
		CompositeDataUtils.copy(rspICD, arrayiItem, "RspAppBody/FOBJ2/DAILRM", "FOBJ2/DAILRM");
		CompositeDataUtils.copy(rspICD, arrayiItem, "RspAppBody/FOBJ2/YWZLBH", "FOBJ2/YWZLBH");
		CompositeDataUtils.copy(rspICD, arrayiItem, "RspAppBody/FOBJ2/DANWBH", "FOBJ2/DANWBH");
		CompositeDataUtils.copy(rspICD, arrayiItem, "RspAppBody/FOBJ2/SXIORQ", "FOBJ2/SXIORQ");
		CompositeDataUtils.copy(rspICD, arrayiItem, "RspAppBody/FOBJ2/KEHUXM", "FOBJ2/KEHUXM");
		CompositeDataUtils.copy(rspICD, arrayiItem, "RspAppBody/FOBJ2/ZHJNZL", "FOBJ2/ZHJNZL");
		CompositeDataUtils.copy(rspICD, arrayiItem, "RspAppBody/FOBJ2/ZHJHAO", "FOBJ2/ZHJHAO");
		CompositeDataUtils.copy(rspICD, arrayiItem, "RspAppBody/FOBJ2/DSYHDZ", "FOBJ2/DSYHDZ");
		CompositeDataUtils.copy(rspICD, arrayiItem, "RspAppBody/FOBJ2/DSYHYB", "FOBJ2/DSYHYB");
		CompositeDataUtils.copy(rspICD, arrayiItem, "RspAppBody/FOBJ2/DSYHDH", "FOBJ2/DSYHDH");
		CompositeDataUtils.copy(rspICD, arrayiItem, "RspAppBody/FOBJ2/GRZHLX", "FOBJ2/GRZHLX");
		CompositeDataUtils.copy(rspICD, arrayiItem, "RspAppBody/FOBJ2/GRZHAO", "FOBJ2/GRZHAO");  
		
		return 0;
	}
	/**
	 * 设置上送ECIF系统请求参数
	 * @param rspServiceId	:Rsp+原子服务码
	 * @param cpdm			:产品代码
	 */
	private void setECIFParams(String rspServiceId,String cpdm){
		ICompositeData Rsp2002201000103 = (ICompositeData) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		.getContext().getValue(ZF_SERVICE_ID); 
		String retCode = CompositeDataUtils.getValue((ICompositeData) SessionContext.getContext().getValue(rspServiceId), "RspSysHead/RetCode");
	
		String Third_CONT_ACC = (String)SessionContext.getContext().getValue("dsyhbh");
		
		log.info("解约产品["+cpdm+"]，解约结果retCode=["+retCode+"]");
		log.info("Third_CONT_ACC=["+Third_CONT_ACC+"]");
		String rspSid = rspServiceId!=null?rspServiceId.substring(3):"";
		List<String> retCodeList = PropertiesUtils.getSignOffRetCode(rspSid);
		log.debug("已注销或未签约响应码列表:"+retCodeList);
		if("000000".equals(retCode)){//解约成功
			//代缴费解约ECIF维护时需要上送CONT_ACC(唯一识别标志)即DSYHBH（第三方用户编号）
			log.info("原子服务["+rspSid+"]交易成功，设置上送ECIF系统请求参数成功");
			if(Third_CONT_ACC != null){
				SessionContext.getContext().setValue("Third_CONT_ACC", Third_CONT_ACC);
			}
		}else if(retCodeList != null && retCode != null && retCodeList.contains(retCode)){
			log.info("原子服务["+rspSid+"]交易成功，已签约，设置上送ECIF系统请求参数成功");
			//代缴费解约ECIF维护时需要上送CONT_ACC(唯一识别标志)即DSYHBH（第三方用户编号）
			if(Third_CONT_ACC != null){
				SessionContext.getContext().setValue("Third_CONT_ACC", Third_CONT_ACC);
			}
		}else{//解约失败，需要给结构体PrivSignOffRsltArray设置返回值
			// 获得综合解约操作响应数据对象
			log.info("原子服务["+rspSid+"]交易失败，产品["+cpdm+"]解约失败，设置返回信息");
			ICompositeData rspSId = (ICompositeData) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
					.getContext().getValue(rspServiceId);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
			if (null == rspSId)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
				log.info("返回结构体["+rspSId+"]为空");
			}else{
				ICompositeData destArrayParent = CompositeDataUtils.mkNodeNotExist(Rsp2002201000103, "RspAppBody");
				ICompositeData arrayiItem = new SoapCompositeData();
				arrayiItem.setId("PrivSignOffRsltArray");
				destArrayParent.setChild("PrivSignOffRsltArray", arrayiItem);
				CompositeDataUtils.setValue(arrayiItem, "SignOffProTp", cpdm);
				CompositeDataUtils.setValue(arrayiItem, "CONT_ACC", Third_CONT_ACC);
				CompositeDataUtils.copy(rspSId, arrayiItem, "RspSysHead/RetCode", "RetCode");
				CompositeDataUtils.copy(rspSId, arrayiItem, "RspSysHead/RetMsg", "RetMsg");
				CompositeDataUtils.copy(rspSId, arrayiItem, "RspSysHead/TransStatus", "TransStatus");
			}
		}
	}
	
	
	private void cbsFinanceGeneralResultSignOffProcess(String productId,String productName)
	{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
		// 获得综合签约请求数据对象                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
		ICompositeData Rsp2002201000103 = (ICompositeData) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
				.getContext().getValue("Rsp2002201000103"); 
		if (null == Rsp2002201000103)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			Rsp2002201000103  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			Rsp2002201000103.setId("Rsp2002201000103");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			Rsp2002201000103.setxPath("/Rsp2002201000103");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			getContext().setValue("Rsp2002201000103",Rsp2002201000103);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		}
		
		ICompositeData Rsp3001200000402 = (ICompositeData) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		.getContext().getValue("Rsp3001200000402");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
		if (null == Rsp3001200000402)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			log.error(productName+"返回结果为空!!");
		}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		String retCode = CompositeDataUtils.getValue(Rsp3001200000402, "RspSysHead/RetCode");
		List<String> retCodeList = PropertiesUtils.getSignOffRetCode("3001200000402");//易得利升级版和卡内活转定未签约错误相同
	   if("000000".equalsIgnoreCase(retCode)
			   || (retCodeList != null && retCode != null && retCodeList.contains(retCode)))
	   {
			ICompositeData destArrayParent = CompositeDataUtils.mkNodeNotExist(Rsp2002201000103, "RspAppBody/CBSFinanceGeneralSignOffRes");
			ICompositeData arrayiItem = new SoapCompositeData();
			arrayiItem.setId("CBSFinanceGeneralSignOffArray");
			destArrayParent.setChild("CBSFinanceGeneralSignOffArray", arrayiItem);
	       CompositeDataUtils.copy(Rsp3001200000402, arrayiItem, "RspAppBody/jieszhao", "jieszhao");
	       CompositeDataUtils.copy(Rsp3001200000402, arrayiItem, "RspAppBody/guiylius", "guiylius");
	       CompositeDataUtils.copy(Rsp3001200000402, arrayiItem, "RspAppBody/jioyguiy", "jioyguiy");
	       CompositeDataUtils.copy(Rsp3001200000402, arrayiItem, "RspAppBody/jiaoyirq", "jiaoyirq");
	       CompositeDataUtils.copy(Rsp3001200000402, arrayiItem, "RspAppBody/kehuzhwm", "kehuzhwm");
	       CompositeDataUtils.copy(Rsp3001200000402, arrayiItem, "RspAppBody/yingyjig", "yingyjig");
	       CompositeDataUtils.copy(Rsp3001200000402, arrayiItem, "RspAppBody/qsqqczbz", "qsqqczbz");
	       CompositeDataUtils.copy(Rsp3001200000402, arrayiItem, "RspAppBody/kehuzhao", "kehuzhao");
	       CompositeDataUtils.copy(Rsp3001200000402, arrayiItem, "RspAppBody/kehuzhlx", "kehuzhlx");
	       CompositeDataUtils.copy(Rsp3001200000402, arrayiItem, "RspAppBody/zhhuzwmc", "zhhuzwmc");
	       CompositeDataUtils.copy(Rsp3001200000402, arrayiItem, "RspAppBody/chapbhao", "chapbhao");
	       CompositeDataUtils.copy(Rsp3001200000402, arrayiItem, "RspAppBody/llfdonbz", "llfdonbz");
	       CompositeDataUtils.copy(Rsp3001200000402, arrayiItem, "RspAppBody/lilvfdbl", "lilvfdbl");
	       CompositeDataUtils.copy(Rsp3001200000402, arrayiItem, "RspAppBody/lilvfdsz", "lilvfdsz");
	       CompositeDataUtils.copy(Rsp3001200000402, arrayiItem, "RspAppBody/huazhyuz", "huazhyuz");
	       CompositeDataUtils.copy(Rsp3001200000402, arrayiItem, "RspAppBody/qicunjie", "qicunjie");
	       CompositeDataUtils.copy(Rsp3001200000402, arrayiItem, "RspAppBody/zhfutojn", "zhfutojn");
	       CompositeDataUtils.copy(Rsp3001200000402, arrayiItem, "RspAppBody/qyxieyih", "qyxieyih");
		   CompositeDataUtils.copy(Rsp3001200000402, arrayiItem, "RspAppBody/jiaoyije", "jiaoyije");
			
			CompositeDataUtils.copy(Rsp3001200000402, arrayiItem, "RspAppBody/listinfo", "listinfo");
			List<ICompositeData> srcArray1= new ArrayList<ICompositeData>();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
			List<ICompositeData> destArray1= new ArrayList<ICompositeData>();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
			destArray1 = CompositeDataUtils.getByPath(arrayiItem, "llistinfo");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
			srcArray1= CompositeDataUtils.getByPath(Rsp3001200000402, "RspAppBody/llistinfo");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
			for (int i1 = 0; i1 < srcArray1.size(); i1++) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
				CompositeDataUtils.copy(srcArray1.get(i1), destArray1.get(i1), "lckhzhao", "lckhzhao");                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
				CompositeDataUtils.copy(srcArray1.get(i1), destArray1.get(i1), "lczhhxho", "lczhhxho");
				CompositeDataUtils.copy(srcArray1.get(i1), destArray1.get(i1), "licazhao", "licazhao");
				CompositeDataUtils.copy(srcArray1.get(i1), destArray1.get(i1), "cunqiiii", "cunqiiii");
				CompositeDataUtils.copy(srcArray1.get(i1), destArray1.get(i1), "baifenbi", "baifenbi");	
			}  
	   }
	   else
	   {	
			ICompositeData destArrayParent = CompositeDataUtils.mkNodeNotExist(Rsp2002201000103, "RspAppBody");
			ICompositeData arrayiItem = new SoapCompositeData();
			arrayiItem.setId("PrivSignOffRsltArray");
			destArrayParent.setChild("PrivSignOffRsltArray", arrayiItem);			
			CompositeDataUtils.setValue(arrayiItem, "SignOffProTp", productId);
			CompositeDataUtils.setValue(arrayiItem, "SignOffProNm", productName);
			CompositeDataUtils.copy(Rsp3001200000402, arrayiItem, "RspSysHead/RetCode", "RetCode");
			CompositeDataUtils.copy(Rsp3001200000402, arrayiItem, "RspSysHead/RetMsg", "RetMsg");
			CompositeDataUtils.setValue(arrayiItem, "TransStatus", "F");		   
	   }
                                                                                                                                                                                                                                                                                                                                                                                                                                
	}
	
	public static void main(String[] args) {
		SignOffResponseMapper sm = new SignOffResponseMapper();
		sm.reponseInfoDeal("Rsp3006200001811");
	}
}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               

	


