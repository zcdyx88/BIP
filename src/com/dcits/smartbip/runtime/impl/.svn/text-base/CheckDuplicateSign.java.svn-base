package com.dcits.smartbip.runtime.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.impl.AbstractBaseService;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.utils.CompositeDataUtils;

/**
 * 检查重复签约的基础服务，
 * 
 * 对比ecif返回字段（数组）：
 * 
 * SIGN_ACC_TYPE 签约凭证类型 SIGN_ACC 签约凭证号 SIGN_TYPE 签约类型
 * 
 * 与综合签约上送：
 * 
 * AcctTp 账户类型 AcctNo 账号 SignProTp 签约产品代码(数组)
 * 
 * 最后将返回的重复状态放入session，DuplicateSignment字段中
 * 
 * @author xhx108
 * 
 */
public class CheckDuplicateSign extends AbstractBaseService implements IService {

	private static final Log log = LogFactory.getLog(CheckDuplicateSign.class);

	@Override
	public String getType() {
		return "base";
	}

	@Override
	public void setType(String type) {

	}

	@Override
	public String getId() {
		return "CheckDuplicateSign";
	}

	@Override
	public Object execute(Object req) throws InvokeException {

		if (log.isInfoEnabled()) {
			log.info("开始调用基础服务[CheckDuplicateSign]......");
		}

		// 获得综合签约请求数据对象
		ICompositeData Req2002201000101 = (ICompositeData) SessionContext
				.getContext().getValue("Req2002201000101");
		// 获取签约信息查询的返回数据对象
		ICompositeData Rsp2002300000401 = (ICompositeData) SessionContext
				.getContext().getValue("Rsp2002300000401");

		// 获取签约账户和类型
		String acctNo = CompositeDataUtils.getValue(Req2002201000101,
				"ReqAppBody/AcctNo");
		String acctType = CompositeDataUtils.getValue(Req2002201000101,
				"ReqAppBody/AcctTp");
		// 获取签约产品代码数组
		List<String> signCodeTypes = new ArrayList<String>();
		List<ICompositeData> signProCdArray = CompositeDataUtils.getByPath(
				Req2002201000101, "ReqAppBody/PrivSignProCdArray");
		for (ICompositeData signComposite : signProCdArray) {
			String signCodeType = CompositeDataUtils.getValue(signComposite,
					"SignProTp");
			signCodeTypes.add(signCodeType);
		}
		if (log.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder();
			sb.append("签约账户acctNo[" + acctNo + "], ").append(
					"签约账户类型[" + acctType + "], ");
			sb.append("签约产品代码[");
			for (String signCodeType : signCodeTypes) {
				sb.append(signCodeType).append(",");
			}
			sb.append("]");
			log.debug(sb.toString());
		}
		// 获取ECIF签约数组
		List<ICompositeData> GRP_SYNTHESIS_INFOs = CompositeDataUtils
				.getByPath(Rsp2002300000401, "RspAppBody/GRP_SYNTHESIS_INFO");
		for (String signCodeType : signCodeTypes) {
			for (ICompositeData GRP_SYNTHESIS_INFO : GRP_SYNTHESIS_INFOs) {
				String signedCodeType = CompositeDataUtils.getValue(
						GRP_SYNTHESIS_INFO, "SIGN_TYPE");
				if (signCodeType.trim().equals(signedCodeType)) {
					String SIGN_ACC_TYPE = CompositeDataUtils.getValue(
							GRP_SYNTHESIS_INFO, "SIGN_ACC_TYPE");
					String SIGN_ACC = CompositeDataUtils.getValue(
							GRP_SYNTHESIS_INFO, "SIGN_ACC");
					if (acctNo.equals(SIGN_ACC)
							&& acctType.equals(SIGN_ACC_TYPE)) {
						if (log.isInfoEnabled()) {
							StringBuilder sb = new StringBuilder();
							sb.append("签约产品代码[" + signedCodeType + "]");
							sb.append("签约账户acctNo[" + SIGN_ACC + "], ");
							sb.append("签约账户类型[" + SIGN_ACC_TYPE + "], ");
							sb.append("已经存在!");
							log.info(sb.toString());
						}
						SessionContext.getContext().setValue(
								"DuplicateSignment", "true");
					}
				}
			}
		}

		if (log.isInfoEnabled()) {
			log.info("调用基础服务结束[CheckDuplicateSign]......");
		}

		return null;
	}
}
