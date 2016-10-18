package com.dcits.smartbip.runtime.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

public class ZhanghujinDecideRiskGrade extends AbstractBaseService implements IService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6565436787845858717L;
	private static final Log log = LogFactory.getLog(ZhanghujinDecideRiskGrade.class);

	@Override
	public String getId() {
		return "ZhanghujinDecideRiskGrade";
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
			log.info("开始调用基础服务[ZhanghujinDecideRiskGrade]......");
		}
		
		
		//理财风险管理信息查询
		ICompositeData Rsp4001300000503 = (ICompositeData) SessionContext
				.getContext().getValue("Rsp4001300000503");
		
		if (null == Rsp4001300000503)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			Rsp4001300000503  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			Rsp4001300000503.setId("Rsp4001300000503");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			Rsp4001300000503.setxPath("/Rsp4001300000503");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
			getContext().setValue("Rsp4001300000503",Rsp4001300000503);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		}
		String RiskLevel = null;
		String ExpiryDate = null;
	
		List<ICompositeData> Acc_ListArrary =  CompositeDataUtils.getByPath(Rsp4001300000503, "RspAppBody/Acc_List");
		if (Acc_ListArrary != null && Acc_ListArrary.size()>0){
		    //客户风险级别
			RiskLevel = Acc_ListArrary.get(0).getChild("RiskLevel").get(0).getValue();
			//风险有效日期
			ExpiryDate = Acc_ListArrary.get(0).getChild("ExpiryDate").get(0).getValue();
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date newdate = new Date();
		try {
			newdate = df.parse(df.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SessionContext.getContext().setValue("RiskLevel-ExpiryDate", "false");
		
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date end = null;
		
		if(ExpiryDate!=null){
			try {
				end = format.parse(ExpiryDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//客户风险级别不是0或1或2并且在风险有效期内
			if((newdate.equals(end)||newdate.before(end)) && !RiskLevel.equalsIgnoreCase("0") && !RiskLevel.equalsIgnoreCase("1") && !RiskLevel.equalsIgnoreCase("2"))
				SessionContext.getContext().setValue("RiskLevel-ExpiryDate", "true");
		}	
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[ZhanghujinDecideRiskGrade]......");
		}
		return null;
	}
}
