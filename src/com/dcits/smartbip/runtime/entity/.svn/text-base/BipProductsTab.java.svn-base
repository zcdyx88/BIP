package com.dcits.smartbip.runtime.entity;

import java.util.Date;

import javax.persistence.*;


/**
 * 签约品种参数配置
 * 
 */
@Entity
@Table(name = "BIP_PRODUCTS_TAB")
@IdClass(BipProductsTabPK.class)
public class BipProductsTab {
	@Id
	@Column(name = "SIGNVARCD", length = 6)
    private String SignVarCd;
    @Column(name = "SIGNVARNM", length = 100)
    private String SignVarNm;
    @Id
    @Column(name = "PRIVYCORPFLG", length = 1)
    private String PrivyCorpFlg;
	@Column(name = "BLNGSYSID", length = 3)
    private String BlngSysId;
	@Column(name = "BLNGLGLPRSNCD", length = 4)
    private String BlngLglPrsnCd;
	@Column(name = "CSTACCTSIGNFLG", length = 1)
    private String CstAcctSignFlg;
	@Column(name = "INOUTBANKFLG", length = 1)
    private String InOutBankFlg;
	@Column(name = "SIGNOFFFLG", length = 1)
    private String SignOffFlg;
	@Column(name = "CROREGSIGNOFFFLG", length = 1)
    private String CroRegSignOffFlg;
	@Column(name = "AVLMNTNFLG", length = 1)
    private String AvlMntnFlg;
	@Column(name = "QRYLISTVISIONFLG", length = 1)
    private String QryListVisionFlg;
	@Column(name = "CLSMSTSIGNOFFFLG", length = 1)
    private String ClsMstSignOffFlg;
	@Column(name = "AVLTRSFRFLG", length = 1)
    private String AvlTrsfrFlg;
	@Column(name = "ONECDMULSIGNFLG", length = 1)
    private String OneCdMulSignFlg;
	@Column(name = "AVLSIGNCHLFLG", length = 1)
    private String AvlSignChlFlg;
	@Column(name = "AVLSIGNOFFCHLFLG", length = 1)
    private String AvlSignOffChlFlg;
	@Column(name = "AVLSIGNEXCHGCHLFLG", length = 1)
    private String AvlSignExchgChlFlg;
	@Column(name = "BLNGBRCHBNKLIST", length = 2000)
    private String BlngBrchBnkList;
	@Column(name = "AVLSIGNCHLLIST", length = 2000)
    private String AvlSignChlList;
	@Column(name = "AVLSIGNOFFCHLLIST", length = 2000)
    private String AvlSignOffChlList;
	@Column(name = "AVLSIGNEXCHGCHLLIST", length = 2000)
    private String AvlSignExchgChlList;
	@Column(name = "EXCLUDEINFOLIST", length = 40000)
    private String ExcludeInfoList;
	@Column(name = "LAST_UPDATE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateDate;
	@Column(name = "LAST_UPDATE_PERSON", length = 40)
    private String LastUpdatePerson;
    public BipProductsTab() {
    	super();
    }
	public BipProductsTab(String signVarCd, String signVarNm,
			String privyCorpFlg, String blngSysId, String blngLglPrsnCd,
			String cstAcctSignFlg, String inOutBankFlg, String signOffFlg,
			String croRegSignOffFlg, String avlMntnFlg,
			String qryListVisionFlg, String clsMstSignOffFlg,
			String avlTrsfrFlg, String oneCdMulSignFlg, String avlSignChlFlg,
			String avlSignOffChlFlg, String avlSignExchgChlFlg,
			String blngBrchBnkList, String avlSignChlList,
			String avlSignOffChlList, String avlSignExchgChlList,
			String excludeInfoList, Date lastUpdateDate, String lastUpdatePerson) {
		super();
		this.SignVarCd = signVarCd;
		this.SignVarNm = signVarNm;
		this.PrivyCorpFlg = privyCorpFlg;
		this.BlngSysId = blngSysId;
		this.BlngLglPrsnCd = blngLglPrsnCd;
		this.CstAcctSignFlg = cstAcctSignFlg;
		this.InOutBankFlg = inOutBankFlg;
		this.SignOffFlg = signOffFlg;
		this.CroRegSignOffFlg = croRegSignOffFlg;
		this.AvlMntnFlg = avlMntnFlg;
		this.QryListVisionFlg = qryListVisionFlg;
		this.ClsMstSignOffFlg = clsMstSignOffFlg;
		this.AvlTrsfrFlg = avlTrsfrFlg;
		this.OneCdMulSignFlg = oneCdMulSignFlg;
		this.AvlSignChlFlg = avlSignChlFlg;
		this.AvlSignOffChlFlg = avlSignOffChlFlg;
		this.AvlSignExchgChlFlg = avlSignExchgChlFlg;
		this.BlngBrchBnkList = blngBrchBnkList;
		this.AvlSignChlList = avlSignChlList;
		this.AvlSignOffChlList = avlSignOffChlList;
		this.AvlSignExchgChlList = avlSignExchgChlList;
		this.ExcludeInfoList = excludeInfoList;
		this.lastUpdateDate = lastUpdateDate;
		this.LastUpdatePerson = lastUpdatePerson;
	}
	public String getSignVarCd() {
		return SignVarCd;
	}
	public void setSignVarCd(String signVarCd) {
		SignVarCd = signVarCd;
	}
	public String getSignVarNm() {
		return SignVarNm;
	}
	public void setSignVarNm(String signVarNm) {
		SignVarNm = signVarNm;
	}
	public String getPrivyCorpFlg() {
		return PrivyCorpFlg;
	}
	public void setPrivyCorpFlg(String privyCorpFlg) {
		PrivyCorpFlg = privyCorpFlg;
	}
	public String getBlngSysId() {
		return BlngSysId;
	}
	public void setBlngSysId(String blngSysId) {
		BlngSysId = blngSysId;
	}
	public String getBlngLglPrsnCd() {
		return BlngLglPrsnCd;
	}
	public void setBlngLglPrsnCd(String blngLglPrsnCd) {
		BlngLglPrsnCd = blngLglPrsnCd;
	}
	public String getCstAcctSignFlg() {
		return CstAcctSignFlg;
	}
	public void setCstAcctSignFlg(String cstAcctSignFlg) {
		CstAcctSignFlg = cstAcctSignFlg;
	}
	public String getInOutBankFlg() {
		return InOutBankFlg;
	}
	public void setInOutBankFlg(String inOutBankFlg) {
		InOutBankFlg = inOutBankFlg;
	}
	public String getSignOffFlg() {
		return SignOffFlg;
	}
	public void setSignOffFlg(String signOffFlg) {
		SignOffFlg = signOffFlg;
	}
	public String getCroRegSignOffFlg() {
		return CroRegSignOffFlg;
	}
	public void setCroRegSignOffFlg(String croRegSignOffFlg) {
		CroRegSignOffFlg = croRegSignOffFlg;
	}
	public String getAvlMntnFlg() {
		return AvlMntnFlg;
	}
	public void setAvlMntnFlg(String avlMntnFlg) {
		AvlMntnFlg = avlMntnFlg;
	}
	public String getQryListVisionFlg() {
		return QryListVisionFlg;
	}
	public void setQryListVisionFlg(String qryListVisionFlg) {
		QryListVisionFlg = qryListVisionFlg;
	}
	public String getClsMstSignOffFlg() {
		return ClsMstSignOffFlg;
	}
	public void setClsMstSignOffFlg(String clsMstSignOffFlg) {
		ClsMstSignOffFlg = clsMstSignOffFlg;
	}
	public String getAvlTrsfrFlg() {
		return AvlTrsfrFlg;
	}
	public void setAvlTrsfrFlg(String avlTrsfrFlg) {
		AvlTrsfrFlg = avlTrsfrFlg;
	}
	public String getOneCdMulSignFlg() {
		return OneCdMulSignFlg;
	}
	public void setOneCdMulSignFlg(String oneCdMulSignFlg) {
		OneCdMulSignFlg = oneCdMulSignFlg;
	}
	public String getAvlSignChlFlg() {
		return AvlSignChlFlg;
	}
	public void setAvlSignChlFlg(String avlSignChlFlg) {
		AvlSignChlFlg = avlSignChlFlg;
	}
	public String getAvlSignOffChlFlg() {
		return AvlSignOffChlFlg;
	}
	public void setAvlSignOffChlFlg(String avlSignOffChlFlg) {
		AvlSignOffChlFlg = avlSignOffChlFlg;
	}
	public String getAvlSignExchgChlFlg() {
		return AvlSignExchgChlFlg;
	}
	public void setAvlSignExchgChlFlg(String avlSignExchgChlFlg) {
		AvlSignExchgChlFlg = avlSignExchgChlFlg;
	}
	public String getBlngBrchBnkList() {
		return BlngBrchBnkList;
	}
	public void setBlngBrchBnkList(String blngBrchBnkList) {
		BlngBrchBnkList = blngBrchBnkList;
	}
	public String getAvlSignChlList() {
		return AvlSignChlList;
	}
	public void setAvlSignChlList(String avlSignChlList) {
		AvlSignChlList = avlSignChlList;
	}
	public String getAvlSignOffChlList() {
		return AvlSignOffChlList;
	}
	public void setAvlSignOffChlList(String avlSignOffChlList) {
		AvlSignOffChlList = avlSignOffChlList;
	}
	public String getAvlSignExchgChlList() {
		return AvlSignExchgChlList;
	}
	public void setAvlSignExchgChlList(String avlSignExchgChlList) {
		AvlSignExchgChlList = avlSignExchgChlList;
	}
	public String getExcludeInfoList() {
		return ExcludeInfoList;
	}
	public void setExcludeInfoList(String excludeInfoList) {
		ExcludeInfoList = excludeInfoList;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getLastUpdatePerson() {
		return LastUpdatePerson;
	}
	public void setLastUpdatePerson(String lastUpdatePerson) {
		LastUpdatePerson = lastUpdatePerson;
	}
	@Override
	public String toString() {
		return "BipProductsTab [SignVarCd=" + SignVarCd + ", SignVarNm="
				+ SignVarNm + ", PrivyCorpFlg=" + PrivyCorpFlg + ", BlngSysId="
				+ BlngSysId + ", BlngLglPrsnCd=" + BlngLglPrsnCd
				+ ", CstAcctSignFlg=" + CstAcctSignFlg + ", InOutBankFlg="
				+ InOutBankFlg + ", SignOffFlg=" + SignOffFlg
				+ ", CroRegSignOffFlg=" + CroRegSignOffFlg + ", AvlMntnFlg="
				+ AvlMntnFlg + ", QryListVisionFlg=" + QryListVisionFlg
				+ ", ClsMstSignOffFlg=" + ClsMstSignOffFlg + ", AvlTrsfrFlg="
				+ AvlTrsfrFlg + ", OneCdMulSignFlg=" + OneCdMulSignFlg
				+ ", AvlSignChlFlg=" + AvlSignChlFlg + ", AvlSignOffChlFlg="
				+ AvlSignOffChlFlg + ", AvlSignExchgChlFlg="
				+ AvlSignExchgChlFlg + ", BlngBrchBnkList=" + BlngBrchBnkList
				+ ", AvlSignChlList=" + AvlSignChlList + ", AvlSignOffChlList="
				+ AvlSignOffChlList + ", AvlSignExchgChlList="
				+ AvlSignExchgChlList + ", ExcludeInfoList=" + ExcludeInfoList
				+ ", lastUpdateDate=" + lastUpdateDate + ", LastUpdatePerson="
				+ LastUpdatePerson + "]";
	}
    
	
}
