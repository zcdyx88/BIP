package com.dcits.smartbip.runtime.impl.P2002201000105;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.ErrorCodes;
import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.entity.ExcludeInfo;
import com.dcits.smartbip.runtime.entity.Production;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.utils.CompositeDataUtils;
import com.dcits.smartbip.utils.XMLUtils;
/**
 * 判断任一换签产品，是否和新账户已签约的换签产品存在互斥关系
 * 如果存在互斥关系，则业务失败，报错信息“XX账号已签约XX产品，换签产品XX和该产品互斥，不能换签，请核实”
 * @author srxhx611
 *
 */
public class MutualReject extends AbstractBaseService implements IService {
	private static final Log log = LogFactory.getLog(MutualReject.class);
	
	@Override
	public String getId(){
		return "MutualReject";
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
			log.info("开始调用基础服务[MutualReject]......");
		}
		//获取组合服务码
		String modifySignType = (String)SessionContext.getContext().getValue("ModifySignType");
		//获取组合服务对象
		ICompositeData ReqModifySignType = (ICompositeData) SessionContext
				.getContext().getValue("Req" +modifySignType);
		//获取待换签产品名称
		List<ICompositeData> privExchgSignProCdArray = CompositeDataUtils.getByPath(
				ReqModifySignType, "ReqAppBody/PrivExchgSignProCdArray");
		String Exclude = "false";
		for(ICompositeData PriExgSignTp : privExchgSignProCdArray){
			//上送换签产品名称
			String SignTp = CompositeDataUtils.getValue(PriExgSignTp,"ExchgSignProTp");
			Map<String,Production> reject = XMLUtils.getSignCodeInfo("0"); //对私0 对公 1
			List<ExcludeInfo> rejectTable = reject.get(SignTp).getExcludeInfoList();
			for(ExcludeInfo excludeInfo : rejectTable ){
				//表中查出的与上送产品名称有互斥关系的产品名称
				String ExcludeTp = excludeInfo.getExcludeTpNo();
				if(SignTp.equalsIgnoreCase(ExcludeTp)){
					Exclude = "false";
					log.info("该账号已签约"+ExcludeTp+"产品，换签产品"+SignTp+"和该产品互斥");
					CompositeDataUtils.setReturn(modifySignType, ErrorCodes.CODE_BIP03E0002, "该账号已签约"+ExcludeTp+"产品，换签产品"+SignTp+"和该产品互斥，不能换签，请核实");
					return null;
				}else{
					Exclude = "true";
				}
			}
		}
		if("true".equalsIgnoreCase(Exclude)){
			log.info("参数检查完成！");
			SessionContext.getContext().setValue("Exclude", Exclude);
		}
		
		if (log.isInfoEnabled()) {
			log.info("开始调用基础服务[MutualReject]......");
		}
		
		return null;
	}
}
