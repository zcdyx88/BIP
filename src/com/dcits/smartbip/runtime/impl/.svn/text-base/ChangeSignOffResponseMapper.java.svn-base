package com.dcits.smartbip.runtime.impl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.runtime.model.impl.SoapCompositeData;
import com.dcits.smartbip.utils.CompositeDataUtils;
/**
 * 换签对私响应处理
 * 
 *
 */
public class ChangeSignOffResponseMapper extends AbstractBaseService implements IService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1934109512687619689L;
	/**
	 * 日志打印
	 */
	private static final Log log = LogFactory.getLog(ChangeSignOffResponseMapper.class);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
	/**
	 * 代缴费原子服务码
	 */
	private static final String DJF_YZ_SERVICE_ID = "Rsp3006200001815";
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
	 * 三方存款原子服务码
	 */
	private static final String TrdCun_SERVICE_ID = "Rsp3006200002803";
	/**
	 * 组合服务码
	 */
	private static final String ZF_SERVICE_ID = "Rsp2002201000105";
	
	@Override                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
	public String getId() {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
		return "ChangeSignOffResponseMapper";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
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
			log.info("开始调用基础服务[ChangeSignOffResponseMapper]......");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
		}
		
		                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
		String signCodeType = (String) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
				.getContext().getValue("exchgSignProCode");
		if("000001".equalsIgnoreCase(signCodeType)){//000001-网上银行
			
		}else if("000002".equalsIgnoreCase(signCodeType)){//000002-手机银行
			
		}else if("000003".equalsIgnoreCase(signCodeType)){//000003-短信银行
			
		}else if("000004".equalsIgnoreCase(signCodeType)){//000004-电话银行
			
		}else if("000005".equalsIgnoreCase(signCodeType)){//000005-信用卡自动还款
			xykInfoDeal(XYK_YZ_SERVICE_ID,signCodeType);
		}else if("000006".equalsIgnoreCase(signCodeType)){//000006-支付宝卡通
			
		}else if("000008".equalsIgnoreCase(signCodeType)){//000008-三方存管
			TrdCunInfoDeal(TrdCun_SERVICE_ID,signCodeType);
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
		}else if("000026".equalsIgnoreCase(signCodeType)){//000026-卡内活转定
		}
		
		if (log.isInfoEnabled()) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
			log.info("调用基础服务结束[ChangeSignOffResponseMapper]......");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
		}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
		return null;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
	} 
	/**
	 * 三方存管响应报文处理
	 * @param rspId ：Rsp3005200000115
	 * @return
	 */
	private int TrdCunInfoDeal(String rspId,String cpdm){
		ICompositeData Rsp2002201000105 = (ICompositeData) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		.getContext().getValue(ZF_SERVICE_ID); 
		ICompositeData rspICD = (ICompositeData) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		.getContext().getValue(rspId);
		String retCode = CompositeDataUtils.getValue((ICompositeData) SessionContext.getContext().getValue(rspId), "RspSysHead/RetCode");
		String retMsg = CompositeDataUtils.getValue((ICompositeData) SessionContext.getContext().getValue(rspId), "RspSysHead/RetMsg");
		log.info("三方存管 换签产品["+cpdm+"]，换签结果retCode=["+retCode+"],retMsg=["+retMsg+"]");
		String SOrganId_SCustAcct = (String)SessionContext.getContext().getValue("SOrganId_SCustAcct");
		if (null == rspICD)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			log.info(rspId+"为空");			
			return 0;
		} 		
		if("000000".equals(retCode)){
			log.info("三方存管换签成功...");
			//三方存款换签ECIF维护时需要上送CONT_ACC(唯一识别标志)即DSYHBH（第三方用户编号）
			if(SOrganId_SCustAcct != null){
				SessionContext.getContext().setValue("CONT_ACC", SOrganId_SCustAcct);
				log.info("设置上送ECIF系统请求参数成功   CONT_ACC="+SOrganId_SCustAcct);
			}
		}
		else {
				ICompositeData destArrayParent = CompositeDataUtils.mkNodeNotExist(Rsp2002201000105, "RspAppBody");
				ICompositeData arrayiItem = new SoapCompositeData();
				arrayiItem.setId("PrivExchgSignOffRsltArray");
				destArrayParent.setChild("PrivExchgSignOffRsltArray", arrayiItem);
				
				CompositeDataUtils.copy(rspICD, arrayiItem, "RspSysHead/TransStatus", "TransStatus");
				CompositeDataUtils.copy(rspICD, arrayiItem, "RspSysHead/RetCode" , "RetCode");
				CompositeDataUtils.copy(rspICD, arrayiItem, "RspSysHead/RetMsg", "RetMsg");
				CompositeDataUtils.setValue(arrayiItem, "ExchgSignOffProTp", cpdm);
				CompositeDataUtils.setValue(arrayiItem, "ExchgSignOffProNm", "三方存管换签");
				CompositeDataUtils.setValue(arrayiItem, "CONT_ACC", SOrganId_SCustAcct);				
			}
		return 1;
	}
	/**
	 * 信用卡响应报文处理
	 * @param rspId ：Rsp3003200001301
	 * @return
	 */
	private int xykInfoDeal(String rspId,String cpdm){
		ICompositeData Rsp2002201000105 = (ICompositeData) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
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
		String retMsg = CompositeDataUtils.getValue((ICompositeData) SessionContext.getContext()
				.getValue(rspId), "RspSysHead/RetMsg");
		log.info("信用卡自动还款 换签产品["+cpdm+"]，换签结果retCode=["+retCode+"],retMsg=["+retMsg+"]");
		String KAHAOO = (String)SessionContext.getContext().getValue("kahaoo");
		
		if("000000".equals(retCode)){
			log.info("信用卡换签成功...");
			//信用卡换签ECIF维护时需要上送CONT_ACC(唯一识别标志)即DSYHBH（第三方用户编号）
			if(KAHAOO != null){
				SessionContext.getContext().setValue("CONT_ACC", KAHAOO);
				log.info("设置上送ECIF系统请求参数成功   CONT_ACC="+KAHAOO);
			}
		}
		else {
				ICompositeData destArrayParent = CompositeDataUtils.mkNodeNotExist(Rsp2002201000105, "RspAppBody");
				ICompositeData arrayiItem = new SoapCompositeData();
				arrayiItem.setId("PrivExchgSignOffRsltArray");
				destArrayParent.setChild("PrivExchgSignOffRsltArray", arrayiItem);
				
				CompositeDataUtils.copy(rspICD, arrayiItem, "RspSysHead/TransStatus", "TransStatus");
				CompositeDataUtils.copy(rspICD, arrayiItem, "RspSysHead/RetCode" , "RetCode");
				CompositeDataUtils.copy(rspICD, arrayiItem, "RspSysHead/RetMsg", "RetMsg");
				CompositeDataUtils.setValue(arrayiItem, "ExchgSignOffProTp", cpdm);
				CompositeDataUtils.setValue(arrayiItem, "ExchgSignOffProNm", "信用卡自动还款");
				CompositeDataUtils.setValue(arrayiItem, "CONT_ACC", KAHAOO);
				
			}
		return 1;
	}

	
	/**
	 * 代缴费响应报文处理
	 * @param rspId	:Rsp+原子服务码
	 * @return	：1-失败，0-成功
	 */
	private int reponseInfoDeal(String rspId){
		// 获得综合换签响应数据对象                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
		ICompositeData Rsp2002201000105 = (ICompositeData) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
				.getContext().getValue(ZF_SERVICE_ID);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
		ICompositeData rspICD = (ICompositeData) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		.getContext().getValue(rspId);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
		if (null == rspICD)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			log.info("返回结构体["+rspId+"]为空");
			return 1;
		}  
		//代缴费
		ICompositeData destArrayParent = CompositeDataUtils.mkNodeNotExist(Rsp2002201000105, "RspAppBody/DirectDebitExchgSignReq");
		ICompositeData arrayiItem = new SoapCompositeData();
		arrayiItem.setId("DirectDebitExchgSignArray");
		destArrayParent.setChild("DirectDebitExchgSignArray", arrayiItem);
		CompositeDataUtils.copy(rspICD, arrayiItem, "RspAppBody/DLIYWMC", "DLIYWMC");
		return 0;
	}
	/**
	 * 设置上送ECIF系统请求参数
	 * @param rspServiceId	:Rsp+原子服务码
	 * @param cpdm			:产品代码
	 */
	//代缴费
	private void setECIFParams(String rspServiceId,String cpdm){
		ICompositeData Rsp2002201000105 = (ICompositeData) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		.getContext().getValue(ZF_SERVICE_ID); 
		String retCode = CompositeDataUtils.getValue((ICompositeData) SessionContext.getContext().getValue(rspServiceId), "RspSysHead/RetCode");
		log.info("换签产品["+cpdm+"]，换签结果retCode=["+retCode+"]");
		String DSYHBH = (String)SessionContext.getContext().getValue("dsyhbh");
		log.info("ECIF上送唯一标识DSYHBH=["+DSYHBH+"]");
		if("000000".equals(retCode)){//换签成功
			//代缴费换签ECIF维护时需要上送CONT_ACC(唯一识别标志)即DSYHBH（第三方用户编号）
			if(DSYHBH != null){
				SessionContext.getContext().setValue("CONT_ACC", DSYHBH);
			}
			log.info("设置上送ECIF系统请求参数成功");
		}else{//换签失败，需要给结构体PrivExchgSignOffRsltArray设置返回值
			// 获得综合换签操作响应数据对象
			log.info("产品["+cpdm+"]换签失败，设置返回信息");
			ICompositeData rspSId = (ICompositeData) SessionContext                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
					.getContext().getValue(rspServiceId);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
			if (null == rspSId)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
				log.info("返回结构体["+rspSId+"]为空");
			}else{
				ICompositeData destArrayParent = CompositeDataUtils.mkNodeNotExist(Rsp2002201000105, "RspAppBody");
				ICompositeData arrayiItem = new SoapCompositeData();
				arrayiItem.setId("PrivExchgSignOffRsltArray");
				destArrayParent.setChild("PrivExchgSignOffRsltArray", arrayiItem);
				CompositeDataUtils.setValue(arrayiItem, "ExchgSignOffProTp", cpdm);
				CompositeDataUtils.setValue(arrayiItem, "CONT_ACC", DSYHBH);
				CompositeDataUtils.copy(rspSId, arrayiItem, "RspSysHead/RetCode", "RetCode");
				CompositeDataUtils.copy(rspSId, arrayiItem, "RspSysHead/RetMsg", "RetMsg");
				CompositeDataUtils.copy(rspSId, arrayiItem, "RspSysHead/TransStatus", "TransStatus");
			}
		}
	}


}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               

	


