package com.dcits.smartbip.journal.entity;

import java.util.Date;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 记录冲正报文，数据格式？？？
 * Created by vincentfxz on 16/5/6.
 */
@Entity
@Table(name = "BIP_JOURNAL_MSG")
public class BipTranMsg {
    @Id
	@Column(name = "UUID", length = 100)
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	private String uuid; //主键    
    
    @Column(name = "FLAT_DATE", length = 50)
    private Date flat_date;
    
	@Column(name = "FLOW_ID", length = 255)
    private String flowId;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "PKG_CONTENT",columnDefinition="BLOB")
    private byte[] pkgContent;


    public BipTranMsg(){
    }
    
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	

    public BipTranMsg(String flowId, byte[] pkgContent,Date flat_date) {
        this.flowId = flowId;
        this.pkgContent = pkgContent;
        this.flat_date = flat_date;
    }

    public byte[] getPkgContent() {
        return pkgContent;
    }

    public void setPkgContent(byte[] pkgContent) {
        this.pkgContent = pkgContent;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }
    
    public Date getFlat_date() {
		return flat_date;
	}

	public void setFlat_date(Date flat_date) {
		this.flat_date = flat_date;
	}
}
