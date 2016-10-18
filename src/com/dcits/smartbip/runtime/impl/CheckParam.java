package com.dcits.smartbip.runtime.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.ErrorCodes;
import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.utils.CompositeDataUtils;

public class CheckParam extends AbstractBaseService implements IService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1169149840280624174L;
	private static final Log log = LogFactory.getLog(CheckParam.class);
	
	@Override
	public String getId() {
		return "CheckParam";
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
			log.info("开始调用基础服务[CheckParam]......");
		}

		ICompositeData Rsp2002201000101 = (ICompositeData) SessionContext.getContext().getValue("Rsp2002201000101");
		// 获得借记卡凭证信息
		ICompositeData Rsp3003300000305 = (ICompositeData) SessionContext
				.getContext().getValue("Rsp3003300000305");
		String RetCode = CompositeDataUtils.getValue(Rsp3003300000305,
		"RspSysHead/RetCode");
		String RetMsg = CompositeDataUtils.getValue(Rsp3003300000305,
		"RspSysHead/RetMsg");
		if(null != RetCode && "000000".equalsIgnoreCase(RetCode)){
		    String kadxiang = CompositeDataUtils.getValue(Rsp3003300000305,
				"RspAppBody/kadxiang");
		    String kadengji = CompositeDataUtils.getValue(Rsp3003300000305,
				"RspAppBody/kadengji");
			if("1".equals(kadxiang)||"1".equals(kadengji)){
				SessionContext.getContext().setValue("kadxiang-kadengji", "true");
			    log.debug("该卡为附卡或单位卡（单位结算卡）");
			    @SuppressWarnings("unchecked")
				List<String> signCodeTypes = (List<String>) SessionContext.getContext().getValue("signCodeTypes");
			    //参数检查是否仅为短信或卡卡转账
				if((signCodeTypes.size())==1){
					String signCode0 = signCodeTypes.get(0);
					if("000003".equals(signCode0)||"000007".equals(signCode0))
						SessionContext.getContext().setValue("Duanxin-Kazhzhang", "true");
					else{
						SessionContext.getContext().setValue("Duanxin-Kazhzhang", "false");
						CompositeDataUtils.setReturn(Rsp2002201000101,ErrorCodes.CODE_BIP01E0006,"附卡/单位结算卡不允许签约非短信和卡卡转账的产品");
			 		    log.error("参数检查失败：附卡/单位结算卡不允许签约非短信和卡卡转账产品"); 
					}	
				}else if((signCodeTypes.size())==2){
					String signCode1 = signCodeTypes.get(0);
					String signCode2 = signCodeTypes.get(1);
					if(("000003".equals(signCode1)&&"000007".equals(signCode2))||("000007".equals(signCode1)&&"000003".equals(signCode2)))
						SessionContext.getContext().setValue("Duanxin-Kazhzhang", "true");
					else{
						SessionContext.getContext().setValue("Duanxin-Kazhzhang", "false");
						CompositeDataUtils.setReturn(Rsp2002201000101,ErrorCodes.CODE_BIP01E0006,"附卡/单位结算卡不允许签约非短信和卡卡转账的产品");
			 		    log.error("参数检查失败：附卡/单位结算卡不允许签约非短信和卡卡转账产品");
		 		    }
				}
			}
			else{
				SessionContext.getContext().setValue("kadxiang-kadengji", "false");
				log.debug("该卡不为附卡和单位卡（单位结算卡）");
			}
		}else{
			CompositeDataUtils.setReturn(Rsp2002201000101,ErrorCodes.CODE_BIP00T0001,"借记卡凭证信息查询交易失败："+RetMsg);
			log.info("借记卡凭证信息查询交易失败！");
		}
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[CheckParam]......");
		}

		return null;
	}
	
	

}
