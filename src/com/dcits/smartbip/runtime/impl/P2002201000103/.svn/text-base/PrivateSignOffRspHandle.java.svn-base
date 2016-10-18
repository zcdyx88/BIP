package com.dcits.smartbip.runtime.impl.P2002201000103;

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
 *  对私综合解约响应处理
 *  如果调用此基础服务，需要将在上一个原子交易的服务码配置到上下文的变量CurYZSrvId中
 * @author srxhx273
 *
 */
public class PrivateSignOffRspHandle extends AbstractBaseService implements IService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1849627501381316903L;
	/**
	 * 日志
	 */
	private static final Log log = LogFactory.getLog(PrivateSignOffRspHandle.class);
	

	@Override
	public String getId() {
		return "PrivateSignOffRspHandle";
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
			log.info("开始调用基础服务[PrivateSignOffRspHandle]......");
		}
		
		String sucFlag = "ERR000";//解约结果
		//当前产品代码
		String signCodeType = (String)getContext().getValue("signCodeType");
		String curYZSrvId = "";
		try {
			
			ICompositeData Rsp2002201000103 = (ICompositeData) SessionContext.getContext().getValue("Rsp2002201000103"); 
			
			if (null == Rsp2002201000103)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
				Rsp2002201000103  = new SoapCompositeData();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
				Rsp2002201000103.setId("Rsp2002201000103");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
				Rsp2002201000103.setxPath("/Rsp2002201000103");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
				getContext().setValue("Rsp2002201000103",Rsp2002201000103);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			} 
			//获取当前原子服务码
			curYZSrvId = (String)SessionContext.getContext().getValue("CurYZSrvId");
			if (null == curYZSrvId || "".equals(curYZSrvId.trim()))                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			{                          
				log.error("当前原子服务码为空!");
				return null;
			} 
			
			ICompositeData rspService = (ICompositeData) SessionContext.getContext().getValue("Rsp"+ curYZSrvId);
			
			
			if (null == rspService)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			{                          
				log.error("原子服务["+curYZSrvId+"]返回结果为空!");
				return null;
			}                             
			
			
			String retCode = CompositeDataUtils.getValue(rspService, "RspSysHead/RetCode");
			String retMsg = CompositeDataUtils.getValue(rspService, "RspSysHead/RetMsg");
			log.info("对私待解约产品代码["+signCodeType+"]，原子服务码=["+curYZSrvId+"]，返回码=["+retCode+"]，返回结果=["+retMsg+"]");
			List<String> retCodeList = PropertiesUtils.getSignOffRetCode(curYZSrvId);
			if("000000".equalsIgnoreCase(retCode)){
				log.info("原子服务["+curYZSrvId+"]交易成功");
				sucFlag = "000000";
			}else if(retCodeList != null && retCode != null && retCodeList.contains(retCode)){
				log.info("原子服务["+curYZSrvId+"]交易成功，已签约");
				sucFlag = "000000";
			}else{
				log.info("原子服务["+curYZSrvId+"]交易失败");
				ICompositeData destArrayParent = CompositeDataUtils.mkNodeNotExist(Rsp2002201000103, "RspAppBody");
				ICompositeData arrayiItem = new SoapCompositeData();
				arrayiItem.setId("PrivSignOffRsltArray");
				destArrayParent.setChild("PrivSignOffRsltArray", arrayiItem);
				
				CompositeDataUtils.setValue(arrayiItem, "TransStatus", "F");
				CompositeDataUtils.setValue(arrayiItem, "RetCode", retCode);
				CompositeDataUtils.setValue(arrayiItem, "RetMsg", retMsg);
				CompositeDataUtils.setValue(arrayiItem, "SignOffProTp", signCodeType);
			}
		} catch (Exception e) {
			log.error("对私综合解约响应处理异常:",e);
			throw new InvokeException();
		}finally{
			SessionContext.getContext().setValue(signCodeType==null?"RetCode":("RetCode"+signCodeType), sucFlag);
			if (log.isInfoEnabled()) {
				log.info("调用基础服务结束[PrivateSignOffRspHandle]......");
			}
		}
		
		
		return null;
	}
	
}






