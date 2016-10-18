package com.dcits.smartbip.runtime.impl.p2002201000106;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.ErrorCodes;
import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.runtime.model.impl.SoapCompositeData;
import com.dcits.smartbip.utils.CompositeDataUtils;
/*
 * 换签的旧账号和新账号是否属于同一个客户
 * */
public class CheckAccountWhetherSameCustomer extends AbstractBaseService implements IService {


	
    private static final Log log = LogFactory.getLog(CheckAccountWhetherSameCustomer.class);
	
	@Override
	public String getId() {
		return "CheckAccountWhetherSameCustomer";
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
			log.info("开始调用基础服务[CheckAccountWhetherSameCustomer]......");
		}
		
		SessionContext.getContext().setValue("ExecuteSonTrade", "true");
		
		// 获得综合签约请求数据对象
		ICompositeData Req2002201000106 = (ICompositeData) SessionContext.getContext().getValue("Req2002201000106");
		ICompositeData Rsp2002201000106 = (ICompositeData) SessionContext.getContext().getValue("Rsp2002201000106");
		ICompositeData Rsp3001300000103 = (ICompositeData) SessionContext.getContext().getValue("Rsp3001300000103");
		if (null == Rsp3001300000103)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			Rsp3001300000103  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			Rsp3001300000103.setId("Rsp3001300000103");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			Rsp3001300000103.setxPath("/Rsp3001300000103");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			getContext().setValue("Rsp3001300000103",Rsp3001300000103);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		}
		List<ICompositeData> fzzhxxmc = CompositeDataUtils.getByPath(Rsp3001300000103, "RspAppBody/fzzhxxmc");
		List<String> kehuzhaos = new ArrayList<String>();
		if (null != fzzhxxmc && fzzhxxmc.size() > 0){
			String NewAcctNo = CompositeDataUtils.getValue(Req2002201000106, "ReqAppBody/NewAcctNo");
			String CstNo = CompositeDataUtils.getValue(Req2002201000106, "ReqAppBody/CstNo");
			for(ICompositeData Fzzhxxmc1:fzzhxxmc){
				String kehuzhao = CompositeDataUtils.getValue(Fzzhxxmc1, "kehuzhao");
				kehuzhaos.add(kehuzhao);
			}
			if(kehuzhaos.contains(NewAcctNo)){
				getContext().setValue("New_OldAccountSameCustomer", "true");
				log.info("旧账户："+CstNo+"新账户："+NewAcctNo+"属于同一个客户");
			}else{
				CompositeDataUtils.setReturn(Rsp2002201000106,ErrorCodes.CODE_BIP03E0002,"旧账户："+CstNo+"新账户："+NewAcctNo+"不属于同一个客户，不能换签，请核实！");
				log.error("旧账户："+CstNo+"新账户："+NewAcctNo+"不属于同一个客户，不能换签，请核实！");
		    }
		}else{
			CompositeDataUtils.setReturn(Rsp2002201000106,ErrorCodes.CODE_BIP00T0001,"客户下账户概要信息查询无客户信息/查询失败");
			log.error("客户下账户概要信息查询无客户信息/查询失败");
		}
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[CheckAccountWhetherSameCustomer]......");
		}

		return null;
	}
	
}
