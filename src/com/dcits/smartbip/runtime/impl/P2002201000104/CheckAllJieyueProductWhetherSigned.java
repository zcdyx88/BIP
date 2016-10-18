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

/*
 * 对公解约，检查所有的待解约产品是否均已签约 
 * */

public class CheckAllJieyueProductWhetherSigned extends AbstractBaseService implements IService{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8836400071708077712L;
	private static final Log log = LogFactory.getLog(CheckAllJieyueProductWhetherSigned.class);
	
	@Override
	public String getType() {
		return "base";
	}

	@Override
	public void setType(String type) {
		
	}
	
	@Override
	public String getId() {
		return "CheckAllJieyueProductWhetherSigned";
	}
	
	@Override
	public Object execute(Object req) throws InvokeException {
		
		if (log.isInfoEnabled()) {
			log.info("开始调用基础服务[CheckAllJieyueProductWhetherSigned]......");
		}
		
		ICompositeData Req2002201000104 = (ICompositeData) SessionContext.getContext().getValue("Req2002201000104"); 
		ICompositeData Rsp2002201000104 = (ICompositeData) SessionContext.getContext().getValue("Rsp2002201000104"); 
		
		ICompositeData Rsp2002300000402 = (ICompositeData) SessionContext.getContext().getValue("Rsp2002300000402");
		
		String RetCode = CompositeDataUtils.getValue(Rsp2002300000402,
		"RspSysHead/RetCode");
		String RetMsg = CompositeDataUtils.getValue(Rsp2002300000402,
		"RspSysHead/RetMsg");
		if(null != RetCode && "000000".equalsIgnoreCase(RetCode)){
			List<ICompositeData> GRP_SYNTHESIS_INFO = CompositeDataUtils.getByPath(Rsp2002300000402, "RspAppBody/GRP_SYNTHESIS_INFO");
			String AcctNo = CompositeDataUtils.getValue(Req2002201000104, "ReqAppBody/AcctNo");  //请求账户
			List<String> signcodeTypes = new ArrayList<String>();  //此账户已签约产品代码数组
			//获取待解约产品
			@SuppressWarnings("unchecked")
			List<String> signoffCodeTypes = (List<String>) SessionContext.getContext().getValue("signoffCodeTypes");
			//对公产品列表
			Map<String,Production> productionMap = XMLUtils.getSignCodeInfo("1"); //1为对公
			
			String ReqAcctNo = CompositeDataUtils.getValue(Req2002201000104, "ReqAppBody/AcctNo");//上送签约帐号
			String AcctSrlNo = CompositeDataUtils.getValue(Req2002201000104, "ReqAppBody/AcctSrlNo");//上送账户序号
            String AcctTp = "";//账户类型
			if(null != GRP_SYNTHESIS_INFO && GRP_SYNTHESIS_INFO.size()>0){
				SessionContext.getContext().setValue("AlljieyueProductSigned", "true");
				for(ICompositeData GRP_SYNTHESIS_INFOS:GRP_SYNTHESIS_INFO){
					try
					{
						String SIGN_ACC = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFOS, "SIGN_ACC");   //已签约客户号
						String sIGN_TYPE = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFOS, "SIGN_TYPE");   //已签约产品
						String CstAcctSignFlg = productionMap.get(sIGN_TYPE).getCstAcctSignFlg();// 0客户级   1账户级
						if(CstAcctSignFlg.equalsIgnoreCase("1")){
                            AcctTp = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFOS, "SIGN_ACC_TYPE");//账户类型-签约凭证类型
							if("A".equals(AcctTp)){
								AcctNo = ReqAcctNo + AcctSrlNo;
							}else{
								AcctNo = ReqAcctNo;
							}
							if(AcctNo.equalsIgnoreCase(SIGN_ACC)){
								String SIGN_TYPE = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFOS, "SIGN_TYPE");   //账户已签约产品代码
								signcodeTypes.add(SIGN_TYPE);
							}
						}else if (CstAcctSignFlg.equalsIgnoreCase("0")){
							String SIGN_TYPE = CompositeDataUtils.getValue(GRP_SYNTHESIS_INFOS, "SIGN_TYPE");   //客户已签约产品代码
							signcodeTypes.add(SIGN_TYPE); 
						}
					}catch(Exception e)
					{
						log.error("遍历报错，忽略调该条记录", e);
					}
				}
				for(String signoffCodeType : signoffCodeTypes){
					if(!signcodeTypes.contains(signoffCodeType)){
						SessionContext.getContext().setValue("AlljieyueProductSigned", "false");
						log.info("该"+signoffCodeType+"解约产品未签约或已解约，请核实！");
						CompositeDataUtils.setReturn(Rsp2002201000104,ErrorCodes.CODE_BIP02E0002,"该"+signoffCodeType+"解约产品未签约或已解约，请核实！");
					    break;
					}
				}
			}else{
				SessionContext.getContext().setValue("AlljieyueProductSigned", "false");
				log.info("该"+AcctNo+"账户无签约产品，不能解约，请核实！");
				CompositeDataUtils.setReturn(Rsp2002201000104,ErrorCodes.CODE_BIP02E0007,"该"+AcctNo+"账户无签约产品，不能解约，请核实！");
			}
		}else{
			CompositeDataUtils.setReturn(Rsp2002201000104,ErrorCodes.CODE_BIP00T0001,"ECIF对公签约关系查询交易失败："+RetMsg);
			log.info("ECIF对公签约关系查询交易失败！");
		}
		
		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[CheckAllJieyueProductWhetherSigned]......");
		}
		return null;
	}
	
}
