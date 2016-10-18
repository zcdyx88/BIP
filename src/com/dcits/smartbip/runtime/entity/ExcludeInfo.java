package com.dcits.smartbip.runtime.entity;

/**
 * 互斥信息列表
 * 
 * @author srxhx273
 * 
 */
public class ExcludeInfo {

	/**
	 * 互斥品种代码
	 */
	private String ExcludeVarCd;
	/**
	 * 互斥类型编号
	 */
	private String ExcludeTpNo;
	/**
	 * 互斥产品优先级
	 */
	private String ExcludePdPri;
	/**
	 * 互斥类型名称
	 */
	private String ExcludeTpNm;

	public String getExcludeVarCd() {
		return ExcludeVarCd;
	}

	public void setExcludeVarCd(String excludeVarCd) {
		ExcludeVarCd = excludeVarCd;
	}

	public String getExcludeTpNo() {
		return ExcludeTpNo;
	}

	public void setExcludeTpNo(String excludeTpNo) {
		ExcludeTpNo = excludeTpNo;
	}

	public String getExcludePdPri() {
		return ExcludePdPri;
	}

	public void setExcludePdPri(String excludePdPri) {
		ExcludePdPri = excludePdPri;
	}

	public String getExcludeTpNm() {
		return ExcludeTpNm;
	}

	public void setExcludeTpNm(String excludeTpNm) {
		ExcludeTpNm = excludeTpNm;
	}

	@Override
	public String toString() {
		
		return ExcludeVarCd+"|"+ExcludeTpNo+"|"+ExcludePdPri+"|"+ExcludeTpNm;
	}

	
}
