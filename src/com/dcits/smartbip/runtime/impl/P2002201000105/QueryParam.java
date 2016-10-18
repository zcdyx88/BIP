package com.dcits.smartbip.runtime.impl.P2002201000105;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.entity.ExcludeInfo;
import com.dcits.smartbip.runtime.entity.Production;
import com.dcits.smartbip.runtime.impl.SignModifyGeneralFilter;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.utils.CompositeDataUtils;
import com.dcits.smartbip.utils.XMLUtils;
/**
 * 参数检查
 * 	(1)	根据待换签产品编号，查询待换签产品的参数信息，任一产品未配置，则业务失败，报错信息“XX产品不存在，请核实”
	(2)	如果任一待换签产品不允许维护，则业务失败，报错信息“XX产品不允许维护，请核实”
	(3)	如果任一待换签产品不允许签约关系转移，则业务失败，报错信息“XX产品不允许签约关系转移，请核实”
	(4)	如果任一待换签产品有换签渠道限制且换签渠道不合法，则业务失败，报错信息“XX产品不能通过本渠道换签，请核实”
	(5)	调用核心业务系统服务3001300000103（客户下账户概要信息查询），通过客户号，查询该客户所有的账户。换签的旧账户和新账户属于查回的账户列表，则继续。否则业务失败，报错信息“XX客户无XX账号”。

 * @author srxhx611
 *
 */
public class QueryParam extends AbstractBaseService implements IService {
	
	private static final Log log = LogFactory.getLog(SignModifyGeneralFilter.class);
	
	@Override
	public String getId(){
		return "QueryParam";
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
			log.info("开始调用基础服务[QueryParam]......执行参数检查");
		}
		//获取组合服务码
		String modifySignType = (String)SessionContext.getContext().getValue("ModifySignType");
		//获取组合服务对象
		ICompositeData ReqModifySignType = (ICompositeData) SessionContext
				.getContext().getValue("Req" +modifySignType);
		
		
		//参数检查
		String succ_flag = "1";
//		List<String> signTpList = new ArrayList<String>();
//		//获取待换签产品编号
//		List<ICompositeData> privExchgSignProCdArray = CompositeDataUtils.getByPath(
//				ReqModifySignType, "ReqAppBody/PrivExchgSignProCdArray");
//		for(ICompositeData PriExgSignTp : privExchgSignProCdArray){
//			//上送换签产品编号
//			String SignTp = CompositeDataUtils.getValue(PriExgSignTp,"ExchgSignProTp");
//			HashMap<String,Production> reject = XMLUtils.getSignCodeInfo("0"); //对私0 对公 1
//			List<ExcludeInfo> rejectTable = reject.get(SignTp).getExcludeInfoList();
//			for(ExcludeInfo excludeInfo : rejectTable ){
//				//表中查出的与上送产品名称有互斥关系的产品名称
//				String ExcludeTp = excludeInfo.getExcludeTpNo();
//			}
//		}
		
		
		
		
		
		
		
		
		getContext().setValue("MSIGN_SUCC_FLAG", succ_flag); //默认参数校验成功 
		
		if (log.isInfoEnabled()) {
			log.info("开始调用基础服务[QueryParam]......参数检查结束");
		}
		
		return null;
		
	}

}
