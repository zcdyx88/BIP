package com.dcits.smartbip.buss.accountopen.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 保存出账号 域、与外部流水号，核心响应交易流水号的映射关系
 * @author xhx102
 *
 */
@Entity
@Table(name = "BIP_ACCOUNT_OPEN")
public class BipTransAccountOpen {
    @Id
	@Column(name = "UUID", length = 100)
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	private String uuid; //主键
	@Column(name = "WAIBLIUS", length = 100)
	private String waiblius; //外部流水,上送
	@Column(name = "DKCZHZHH", length = 100)
	private String dkczhzhh; //出账号
	@Column(name = "JIAOYILS", length = 100)
	private String jiaoyils; //交易流水，核心返回
	@Column(name = "TRANDATE", length = 100)
	private String trandate; //交易日期
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getTrandate() {
		return trandate;
	}
	public void setTrandate(String trandate) {
		this.trandate = trandate;
	}
	public String getWaiblius() {
		return waiblius;
	}
	public void setWaiblius(String waiblius) {
		this.waiblius = waiblius;
	}
	public String getDkczhzhh() {
		return dkczhzhh;
	}
	public void setDkczhzhh(String dkczhzhh) {
		this.dkczhzhh = dkczhzhh;
	}
	public String getJiaoyils() {
		return jiaoyils;
	}
	public void setJiaoyils(String jiaoyils) {
		this.jiaoyils = jiaoyils;
	}

	public BipTransAccountOpen(){
		
	}

	public BipTransAccountOpen(String waiblius,String dkczhzhh, String jiaoyils,String trandate){
		this.waiblius = waiblius;
		this.dkczhzhh = dkczhzhh;
		this.jiaoyils = jiaoyils;
		this.trandate = trandate;
		
	}
	
}
