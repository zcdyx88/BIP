package com.dcits.smartbip.runtime.impl.P5001200002501;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.dcits.smartbip.exception.ErrorCodes;
import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.entity.BipProductsTab;
import com.dcits.smartbip.runtime.entity.ExcludeInfo;
import com.dcits.smartbip.runtime.entity.Production;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.utils.CompositeDataUtils;
import com.dcits.smartbip.utils.XMLUtils;

/**
 * 签约品种参数配置处理
 * @author srxhx273
 *
 */
public class SignCodeParamsHandle extends AbstractBaseService implements IService{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1964719933478479483L;
	private static final Log log = LogFactory.getLog(SignCodeParamsHandle.class);
	private static final String SPLIT1 = "|";
	private static final String SPLIT2 = "^";
	@Override
	public String getId(){
		return "SignCodeParamsHandle";
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
			log.info("开始调用基础服务[SignCodeParamsHandle]......");
		}
		String ModifySignType = (String)getContext().getValue("ModifySignType");
		try {
			CompositeDataUtils.setSysHead(ModifySignType, ErrorCodes.CODE_BIP00T0001, "交易失败", "F");
			ICompositeData Req5001200002501 = (ICompositeData) SessionContext.getContext().getValue("Req5001200002501");
			if (null == Req5001200002501){                          
				log.error("前端上送5001200002501(签约品种参数配置)结构体为空!");
				CompositeDataUtils.setReturn(ModifySignType, ErrorCodes.CODE_BIP00T0001, "签约品种参数配置失败");
				return null;
			}
			String SignVarCd = CompositeDataUtils.getValue(Req5001200002501, "ReqAppBody/SignVarCd");
			if (null == SignVarCd || "".equals(SignVarCd.trim())){                          
				log.error("前端上送签约品种代码为空!");
				CompositeDataUtils.setReturn(ModifySignType, ErrorCodes.CODE_BIP00T0001, "前端上送签约品种代码为空，请核实");
				return null;
			}
			
			String PrivyCorpFlg = CompositeDataUtils.getValueNoNull(Req5001200002501, "ReqAppBody/PrivyCorpFlg");
			Production product = XMLUtils.getOneProduct(SignVarCd, PrivyCorpFlg);
			String OperationTp = CompositeDataUtils.getValue(Req5001200002501, "ReqAppBody/OperationTp");//操作类型:0-修改 1-新增
			String excludeInfoStr = "";
			if("0".equals(OperationTp)){//0-修改
				if(product == null){
					log.error("签约品种代码["+SignVarCd+"]不存在，不能修改，请核实");
					CompositeDataUtils.setReturn(ModifySignType, ErrorCodes.CODE_BIP00T0001, "签约品种代码["+SignVarCd+"]不存在，不能修改，请核实");
					return null;
				}
				ArrayList<ExcludeInfo> excludeInfoList = product.getExcludeInfoList();
				if(excludeInfoList != null){
					StringBuffer excludeInfoSB = new StringBuffer();
					for(ExcludeInfo exc : excludeInfoList){
						excludeInfoSB.append(exc.getExcludeVarCd()).append(SPLIT1).append(exc.getExcludeTpNo()).append(SPLIT1)
						.append(exc.getExcludePdPri()).append(SPLIT1).append(exc.getExcludeTpNm()).append(SPLIT1).append(SPLIT2);
					}
					excludeInfoStr = excludeInfoSB.toString();
				}
			}else if("1".equals(OperationTp)){//1-新增
				if(product != null){
					log.error("签约品种代码["+SignVarCd+"]已存在，不能添加，请核实");
					CompositeDataUtils.setReturn(ModifySignType, ErrorCodes.CODE_BIP00T0001, "签约品种代码["+SignVarCd+"]已存在，不能添加，请核实");
					return null;
				}
			}else{
				log.error("操作类型["+OperationTp+"]不合法，请核实");
				CompositeDataUtils.setReturn(ModifySignType, ErrorCodes.CODE_BIP00T0001, "操作类型["+OperationTp+"]不合法，请核实");
				return null;
			}
			
			String TlrNo = CompositeDataUtils.getValue(Req5001200002501, "ReqAppHead/TlrNo");//操作员
			String SignVarNm = CompositeDataUtils.getValueNoNull(Req5001200002501, "ReqAppBody/SignVarNm");
			String BlngSysId = CompositeDataUtils.getValueNoNull(Req5001200002501, "ReqAppBody/BlngSysId");
			String BlngLglPrsnCd = CompositeDataUtils.getValueNoNull(Req5001200002501, "ReqAppBody/BlngLglPrsnCd");
			String CstAcctSignFlg = CompositeDataUtils.getValueNoNull(Req5001200002501, "ReqAppBody/CstAcctSignFlg");
			String InOutBankFlg = CompositeDataUtils.getValueNoNull(Req5001200002501, "ReqAppBody/InOutBankFlg");
			String SignOffFlg = CompositeDataUtils.getValueNoNull(Req5001200002501, "ReqAppBody/SignOffFlg");
			String CroRegSignOffFlg = CompositeDataUtils.getValueNoNull(Req5001200002501, "ReqAppBody/CroRegSignOffFlg");
			String AvlMntnFlg = CompositeDataUtils.getValueNoNull(Req5001200002501, "ReqAppBody/AvlMntnFlg");
			String QryListVisionFlg = CompositeDataUtils.getValueNoNull(Req5001200002501, "ReqAppBody/QryListVisionFlg");
			String ClsMstSignOffFlg = CompositeDataUtils.getValueNoNull(Req5001200002501, "ReqAppBody/ClsMstSignOffFlg");
			String AvlTrsfrFlg = CompositeDataUtils.getValueNoNull(Req5001200002501, "ReqAppBody/AvlTrsfrFlg");
			String OneCdMulSignFlg = CompositeDataUtils.getValueNoNull(Req5001200002501, "ReqAppBody/OneCdMulSignFlg");
			String AvlSignChlFlg = CompositeDataUtils.getValueNoNull(Req5001200002501, "ReqAppBody/AvlSignChlFlg");
			String AvlSignOffChlFlg = CompositeDataUtils.getValueNoNull(Req5001200002501, "ReqAppBody/AvlSignOffChlFlg");
			String AvlSignExchgChlFlg = CompositeDataUtils.getValueNoNull(Req5001200002501, "ReqAppBody/AvlSignExchgChlFlg");
			List<ICompositeData> BlngBrchBnkList = CompositeDataUtils.getByPath(Req5001200002501, "ReqAppBody/BlngBrchBnkList");
			List<ICompositeData> AvlSignChlList = CompositeDataUtils.getByPath(Req5001200002501, "ReqAppBody/AvlSignChlList");
			List<ICompositeData> AvlSignOffChlList = CompositeDataUtils.getByPath(Req5001200002501, "ReqAppBody/AvlSignOffChlList");
			List<ICompositeData> AvlSignExchgChlList = CompositeDataUtils.getByPath(Req5001200002501, "ReqAppBody/AvlSignExchgChlList");
			
			BipProductsTab bipProductsTab = new BipProductsTab(SignVarCd, SignVarNm, PrivyCorpFlg, BlngSysId, BlngLglPrsnCd,
					CstAcctSignFlg, InOutBankFlg, SignOffFlg, CroRegSignOffFlg, AvlMntnFlg, QryListVisionFlg, ClsMstSignOffFlg, AvlTrsfrFlg, 
					OneCdMulSignFlg, AvlSignChlFlg, AvlSignOffChlFlg, AvlSignExchgChlFlg, "", "", "", "", excludeInfoStr, new Date(), TlrNo);
			StringBuffer tmpSB = null;
			if(BlngBrchBnkList != null){
				tmpSB = new StringBuffer();
				for(ICompositeData tmpICD : BlngBrchBnkList){
					tmpSB.append(CompositeDataUtils.getValueNoNull(tmpICD, "BlngBrchBnkNo")).append(SPLIT1);
				}
				bipProductsTab.setBlngBrchBnkList(tmpSB.toString());
			}
			if(AvlSignChlList != null){
				tmpSB = new StringBuffer();
				for(ICompositeData tmpICD : AvlSignChlList){
					tmpSB.append(CompositeDataUtils.getValueNoNull(tmpICD, "ChlId")).append(SPLIT1);
				}
				bipProductsTab.setAvlSignChlList(tmpSB.toString());
			}
			if(AvlSignOffChlList != null){
				tmpSB = new StringBuffer();
				for(ICompositeData tmpICD : AvlSignOffChlList){
					tmpSB.append(CompositeDataUtils.getValueNoNull(tmpICD, "ChlId")).append(SPLIT1);
				}
				bipProductsTab.setAvlSignOffChlList(tmpSB.toString());
			}
			if(AvlSignExchgChlList != null){
				tmpSB = new StringBuffer();
				for(ICompositeData tmpICD : AvlSignExchgChlList){
					tmpSB.append(CompositeDataUtils.getValueNoNull(tmpICD, "ChlId")).append(SPLIT1);
				}
				bipProductsTab.setAvlSignExchgChlList(tmpSB.toString());
			}
			
			XMLUtils.saveProducts(bipProductsTab);
			CompositeDataUtils.setSucReturn(ModifySignType, "签约品种参数配置成功");
			log.info("签约品种参数配置成功！");
		} catch (Exception e) {
			log.error("签约品种参数配置处理异常：",e);
			CompositeDataUtils.setReturn(ModifySignType, ErrorCodes.CODE_BIP00T0001, "签约品种参数配置失败");
			return null;
		}catch (Error e) {
			log.error("签约品种参数配置处理异常111：",e);
		}
		finally{
			if (log.isInfoEnabled()) {
				log.info("结束调用基础服务[SignCodeParamsHandle]......");
			}
		}
		return null;
	}
	
}
