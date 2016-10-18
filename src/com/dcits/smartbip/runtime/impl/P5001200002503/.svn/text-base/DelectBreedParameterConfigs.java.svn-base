package com.dcits.smartbip.runtime.impl.P5001200002503;

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

public class DelectBreedParameterConfigs extends AbstractBaseService implements IService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1559937261182142955L;
	private static final Log log = LogFactory.getLog(DelectBreedParameterConfigs.class);
	
	@Override
	public String getId(){
		return "DelectBreedParameterConfigs";
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
			log.info("开始调用基础服务[DelectBreedParameterConfigs]......");
		}
		String ModifySignType = (String)getContext().getValue("ModifySignType");
		try {
			CompositeDataUtils.setSysHead(ModifySignType, ErrorCodes.CODE_BIP00T0001, "交易失败", "F");
			ICompositeData Req5001200002503 = (ICompositeData) SessionContext.getContext().getValue("Req5001200002503");
			if (null == Req5001200002503){                          
				log.error("前端上送5001200002503(签约品种参数配置删除)结构体为空!");
				CompositeDataUtils.setReturn(ModifySignType, ErrorCodes.CODE_BIP00T0001, "上送(签约品种参数配置删除)结构体为空，请核实！");
				return null;
			}
			String PrivyCorpFlg = CompositeDataUtils.getValue(Req5001200002503, "ReqAppBody/PrivyCorpFlg");
			String SignVarCd = CompositeDataUtils.getValue(Req5001200002503, "ReqAppBody/SignVarCd");
			if (null == SignVarCd || "".equals(SignVarCd.trim()) || null == PrivyCorpFlg || "".equals(PrivyCorpFlg.trim())){                          
				log.error("前端上送签约品种代码或对公对私标志为空！SignVarCd=["+SignVarCd+"],PrivyCorpFlg=["+PrivyCorpFlg+"]");
				CompositeDataUtils.setReturn(ModifySignType, ErrorCodes.CODE_BIP00T0001, "前端上送签约品种代码或对公对私标志为空，请核实！");
				return null;
			}
			
			if(PrivyCorpFlg.equalsIgnoreCase("0") || PrivyCorpFlg.equalsIgnoreCase("1")){
				Production product = XMLUtils.getOneProduct(SignVarCd, PrivyCorpFlg);
				if(product == null){
					log.error("要删除签约品种["+SignVarCd+"]不存在，请核实！");
					CompositeDataUtils.setReturn(ModifySignType, ErrorCodes.CODE_BIP00T0001, "要删除签约品种["+SignVarCd+"]不存在，请核实！");
					return null;
				}
				//删除操作
				int rt = XMLUtils.deleteById(SignVarCd, PrivyCorpFlg);
				if(rt == 1){
					log.info(SignVarCd+"|"+PrivyCorpFlg+"签约品种参数配置删除成功");
					CompositeDataUtils.setSucReturn(ModifySignType, "签约品种参数配置删除成功");
				}else{
					log.error(SignVarCd+"|"+PrivyCorpFlg+"签约品种参数配置删除失败");
					CompositeDataUtils.setReturn(ModifySignType, ErrorCodes.CODE_BIP00T0001, "交易失败，请核实产品是否已删除成功！");
				}
			}else{
				log.error("上送对公对私操作标志不合法！");
				CompositeDataUtils.setReturn(ModifySignType, ErrorCodes.CODE_BIP00T0001, "上送对公对私操作标志不合法！");
				return null;
			}
		} catch (Exception e) {
			log.error("签约品种参数配置删除处理异常：",e);
			CompositeDataUtils.setReturn(ModifySignType, ErrorCodes.CODE_BIP00T0001, "签约品种参数配置删除失败");
			return null;
		}finally{
			if (log.isInfoEnabled()) {
				log.info("结束调用基础服务[DelectBreedParameterConfigs]......");
			}
		}
		return null;
	}
}
