package com.dcits.smartbip.exception;

import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.utils.CompositeDataUtils;


public class ErrorCodes {

	@CodeDesc(code = "BIP03E0000", description = "参数配置表中未找到待换签产品")
	public static final String CODE_BIP03E0000 = "BIP03E0000";
	
	@CodeDesc(code = "BIP03E0002", description = "换签的旧账号和新帐号不属于同一个客户")
	public static final String CODE_BIP03E0002 = "BIP03E0002";
	
	@CodeDesc(code = "BIP03E0003", description = "待换签产品在旧账户上没有签约关系")
	public static final String CODE_BIP03E0003 = "BIP03E0003";
	
	@CodeDesc(code = "BIP03E0004", description = "待换签产品在新账户上已有签约关系")
	public static final String CODE_BIP03E0004 = "BIP03E0004";
	
	@CodeDesc(code = "BIP04E0001", description = "贷款开户,必输项检查失败")
	public static final String CODE_BIP04E0001 = "BIP04E0001";

	@CodeDesc(code = "BIP04E0002", description = "数据上送不合法")
	public static final String CODE_BIP04E0002 = "BIP04E0002";
	
	@CodeDesc(code = "BIP04E0003", description = "立即支付中他行卡大于1张,请使用手工支付")
	public static final String CODE_BIP04E0003 = "BIP04E0003";
	
	@CodeDesc(code = "BIP03E0009", description = "所有基金类产品，必须同时换签！")
	public static final String CODE_BIP03E0009 = "BIP03E0009";
	
	@CodeDesc(code = "BIP01E0000", description = "产品不存在，请核实")
	public static final String CODE_BIP01E0000 = "BIP01E0000";
	
	@CodeDesc(code = "BIP01E0001", description = "产品不能通过渠道签约，请核实")
	public static final String CODE_BIP01E0001 = "BIP01E0001";
	
	@CodeDesc(code = "BIP01E0004", description = "产品和产品互斥，不能同时签约，请核实")
	public static final String CODE_BIP01E0004 = "BIP01E0004";
	
	@CodeDesc(code = "BIP01E0006", description = "附卡/单位结算卡不允许签约产品")
	public static final String CODE_BIP01E0006 = "BIP01E0006";
	
	@CodeDesc(code = "BIP01E0002", description = "产品已签约，不能重复签约")
	public static final String CODE_BIP01E0002 = "BIP01E0002";
	
	@CodeDesc(code = "BIP02E0000", description = "产品不存在")
	public static final String CODE_BIP02E0000 = "BIP02E0000";
	
	@CodeDesc(code = "BIP02E0005", description = "产品不允许解约")
	public static final String CODE_BIP02E0005 = "BIP02E0005";
	
	@CodeDesc(code = "BIP02E0001", description = "产品不能通过渠道解约")
	public static final String CODE_BIP02E0001 = "BIP02E0001";
	
	@CodeDesc(code = "BIP02E0007", description = "该账户下无任何签约关系")
	public static final String CODE_BIP02E0007 = "BIP02E0007";
	
	@CodeDesc(code = "BIP02E0002", description = "产品未签约或已解约")
	public static final String CODE_BIP02E0002 = "BIP02E0002";
	
	@CodeDesc(code = "BIP00T0001", description = "")
	public static final String CODE_BIP00T0001 = "BIP00T0001";
	/**
	 * 判断是否是成功的
	 * @param code
	 * @return
	 */
	public static boolean isSuccessful(ICompositeData compositeData ) {
		String retCode = CompositeDataUtils.getValue(compositeData, "RspSysHead/RetCode");
		return "000000".equals(retCode);
	}
}
