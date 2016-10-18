package com.dcits.smartbip.runtime.impl.P2002201000104;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.ErrorCodes;
import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.entity.Production;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.utils.CompositeDataUtils;
import com.dcits.smartbip.utils.XMLUtils;

public class CheckExchageHaveWhetherAuthority extends AbstractBaseService implements IService{
	    
	    /**
	 * 
	 */
	private static final long serialVersionUID = 3577040420165383967L;
		private static final Log log = LogFactory.getLog(CheckExchageHaveWhetherAuthority.class);
		
		@Override
		public String getId() {
			return "CheckExchageHaveWhetherAuthority";
		}

		@Override
		public String getType() {
			return "base";
		}
		
		@Override
		public void setType(String type) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public Object execute(Object req) throws InvokeException {

			if (log.isInfoEnabled()) {
				log.info("开始调用基础服务[CheckExchageHaveWhetherAuthority]......");
			}

			ICompositeData Rsp2002201000104 = (ICompositeData) SessionContext.getContext().getValue("Rsp2002201000104");
			ICompositeData Rsp5001300001109 = (ICompositeData) SessionContext.getContext().getValue("Rsp5001300001109");
			
			
			String RetCode = CompositeDataUtils.getValue(Rsp5001300001109,
			"RspSysHead/RetCode");
			String RetMsg = CompositeDataUtils.getValue(Rsp5001300001109,
			"RspSysHead/RetMsg");
			if(null != RetCode && "000000".equalsIgnoreCase(RetCode)){
				//获取待解约产品
				@SuppressWarnings("unchecked")
				List<String> signoffCodeTypes = (List<String>) SessionContext.getContext().getValue("signoffCodeTypes");
				//对公产品列表
				Map<String,Production> productionMap = XMLUtils.getSignCodeInfo("1"); //1为对私
				//模糊查询的分行代码
				String fenhdaim = CompositeDataUtils.getValue(Rsp5001300001109, "RspAppBody/jigxinxmc/fenhdaim");
				if(null != fenhdaim){
					SessionContext.getContext().setValue("ExchageHaveAuthority", "true");
					for(String signoffCodeType:signoffCodeTypes){
						ArrayList<String> BlngBrchBnkList = productionMap.get(signoffCodeType).getBlngBrchBnkList();
						if(!BlngBrchBnkList.contains(fenhdaim)){
							SessionContext.getContext().setValue("ExchageHaveAuthority", "false");
							log.info("该"+fenhdaim+"分行不能解约"+signoffCodeType+"产品，请核实！");
							CompositeDataUtils.setReturn(Rsp2002201000104,ErrorCodes.CODE_BIP00T0001,"该"+fenhdaim+"分行不能解约"+signoffCodeType+"产品，请核实！");
						    break;
						}
					}
				}else{
					SessionContext.getContext().setValue("ExchageHaveAuthority", "false");
					String retmsg = CompositeDataUtils.getValue(Rsp5001300001109, "RspSysHead/RetMsg");
					log.info("机构信息查询失败:"+retmsg);
					CompositeDataUtils.setReturn(Rsp2002201000104,ErrorCodes.CODE_BIP00T0001,"机构信息查询失败:"+retmsg);
				}
			}else{
				CompositeDataUtils.setReturn(Rsp2002201000104,ErrorCodes.CODE_BIP00T0001,"机构信息模糊查询交易失败："+RetMsg);
				log.info("机构信息模糊查询交易失败！");
			}	
			
			if (log.isInfoEnabled()) {
				log.info("调用基础服务结束[CheckExchageHaveWhetherAuthority]......");
			}

			return null;
		}
}
