package com.dcits.smartbip.runtime.impl.P2002201000104;

import java.util.ArrayList;
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
import com.dcits.smartbip.utils.XMLUtils;

/*
 * 对公解约时，检查所以待解约产品信息
 * */

public class CheckAllJieyueProductInformation extends AbstractBaseService implements IService{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8870771534268150249L;
	private static final Log log = LogFactory.getLog(CheckAllJieyueProductInformation.class);

	@Override
	public String getId() {
		return "CheckAllJieyueProductInformation";
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
			log.info("开始调用基础服务[CheckAllJieyueProductInformation]......");
		}
		
		ICompositeData Req2002201000104 = (ICompositeData) SessionContext.getContext().getValue("Req2002201000104"); 
		ICompositeData Rsp2002201000104 = (ICompositeData) SessionContext.getContext().getValue("Rsp2002201000104"); 
		
		if (null == Rsp2002201000104)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			Rsp2002201000104  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			Rsp2002201000104.setId("Rsp2002201000104");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			Rsp2002201000104.setxPath("/Rsp2002201000104");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			getContext().setValue("Rsp2002201000104",Rsp2002201000104);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		}
		
		//获取待解约产品
		@SuppressWarnings("unchecked")
		List<String> signoffCodeTypes = (List<String>) SessionContext.getContext().getValue("signoffCodeTypes");
		//对公产品列表
		Map<String,Production> productionMap = XMLUtils.getSignCodeInfo("1"); //1为对公
		
		//检查待解约产品是否都存在在库列表中
		for(String signoffCodeType : signoffCodeTypes){
		    SessionContext.getContext().setValue("AllJieyueProductLegal", "true");
			if(!productionMap.containsKey(signoffCodeType)){
				SessionContext.getContext().setValue("AllJieyueProductLegal", "false");
				log.info("该"+signoffCodeType+"解约产品不存在，请核实！");
				CompositeDataUtils.setReturn(Rsp2002201000104,ErrorCodes.CODE_BIP02E0000,"该"+signoffCodeType+"解约产品不存在，请核实！");
			    break;
		    }
		}
		//检查任一待解约产品是否允许解约
		if("true".equalsIgnoreCase((String) SessionContext.getContext().getValue("AllJieyueProductLegal"))){
			SessionContext.getContext().setValue("AllJieyueProductPermitJieyue", "true");
			for(String signoffCodeType : signoffCodeTypes){
			    String SignOffFlg = productionMap.get(signoffCodeType).getSignOffFlg();   //0代表不允许解约，1允许
			    if(null != SignOffFlg && "0".equalsIgnoreCase(SignOffFlg)){
			    	SessionContext.getContext().setValue("AllJieyueProductPermitJieyue", "false");
					log.info("该"+signoffCodeType+"解约产品不允许解约，请核实！");
					CompositeDataUtils.setReturn(Rsp2002201000104,ErrorCodes.CODE_BIP02E0005,"该"+signoffCodeType+"解约产品不允许解约，请核实！");
				    break;   	
			    }
			}
		}
		//检查任一待解约产品解约渠道是否合法
		if("true".equalsIgnoreCase((String) SessionContext.getContext().getValue("AllJieyueProductPermitJieyue"))){
			SessionContext.getContext().setValue("AllJieyueChannelLegal", "true");
			String reqchannel = CompositeDataUtils.getValue(Req2002201000104, "ReqSysHead/ChannelID");  //请求渠道
			for(String signoffCodeType : signoffCodeTypes){
				String AvlSignOffChlFlg = productionMap.get(signoffCodeType).getAvlSignOffChlFlg();
				//判断是否控制解约渠道
				if(null != AvlSignOffChlFlg && "1".equalsIgnoreCase(AvlSignOffChlFlg)){   //1代表控制渠道解约
					ArrayList<String> AvlSignOffChlList = productionMap.get(signoffCodeType).getAvlSignOffChlList();
					if(!AvlSignOffChlList.contains(reqchannel)){
						SessionContext.getContext().setValue("AllJieyueChannelLegal", "false");
						log.info("该"+signoffCodeType+"解约产品不能通过"+reqchannel+"渠道解约，请核实!");
						CompositeDataUtils.setReturn(Rsp2002201000104,ErrorCodes.CODE_BIP02E0001,"该"+signoffCodeType+"解约产品不能通过"+reqchannel+"渠道解约，请核实!");
					    break;  
					}
				}
			}
		}
		//判断是否允跨地区解约
		if("true".equalsIgnoreCase((String) SessionContext.getContext().getValue("AllJieyueChannelLegal"))){
			SessionContext.getContext().setValue("StepAreaJieyue", "true");
			for(String signoffCodeType : signoffCodeTypes){
				String CroRegSignOffFlg = productionMap.get(signoffCodeType).getCroRegSignOffFlg();  //跨地区解约
				//判断是否是否允许跨地区解约
				if(null != CroRegSignOffFlg && "0".equalsIgnoreCase(CroRegSignOffFlg)){   //0代表不允许
					SessionContext.getContext().setValue("StepAreaJieyue", "false");
					log.info("该"+signoffCodeType+"解约产品不允许跨地区解约，请核实!");
				    break;  
				}
			}
		}
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[CheckAllJieyueProductInformation]......");
		}

		return null;
	}
	
}
