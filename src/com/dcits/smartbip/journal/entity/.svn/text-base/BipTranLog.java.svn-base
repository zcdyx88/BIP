package com.dcits.smartbip.journal.entity;

import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

/**
 * 记录流水
 * Created by vincentfxz on 16/5/6.
 */
@Entity
@Table(name = "BIP_JOURNAL_LOG")
public class BipTranLog {
    @Id
	@Column(name = "UUID", length = 100)
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	private String uuid; //主键    
    
    @Column(name = "FLOW_ID", length = 255)
    private String flowId;
    @Column(name = "FLAT_DATE", length = 50)
    private Date flat_date;
	@Column(name = "COMP_SERVICE_ID", length = 40)
    private String compositeServiceId;
    @Column(name = "SERVICE_ID", length = 40)
    private String serviceId;
    @Column(name = "REV_SERVICE_ID", length = 40)
    private String revServiceId;
    @Column(name = "COMPSITE_FLOW_NO", length = 40)
    private String tranFlowNo;
    @Column(name = "ATOM_FLOW_NO", length = 40)
    private String proxyFlowNo;
    @Column(name = "TRANSTAMP", length = 40)
    private String tranStamp;
    @Column(name = "RET_CODE", length = 80)
    private String retCode;
    @Column(name = "RET_MSG", length = 255)
    private String retMsg;
    @Column(name = "RET_STATUS", length = 255)
    private String retStatus;
    @Column(name = "FLOW_STEP", length=40)
    private String flowStep;

    public BipTranLog() {

    }

    public BipTranLog(String flowId,Date flat_date, String compositeServiceId, String serviceId, String revServiceId, String tranFlowNo, String proxyFlowNo,
                      String tranStamp, String retCode, String retMsg, String retStatus, String flowStep) {
        this.flowId = flowId;
        this.flat_date = flat_date;
        this.compositeServiceId = compositeServiceId;
        this.serviceId = serviceId;
        this.revServiceId = revServiceId;
        this.tranFlowNo = tranFlowNo;
        this.proxyFlowNo = proxyFlowNo;
        this.tranStamp = tranStamp;
        this.retCode = retCode;
        this.retMsg = retMsg;
        this.retStatus = retStatus;
        this.flowStep = flowStep;
    }

	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public Date getFlat_date() {
		return flat_date;
	}

	public void setFlat_date(Date flat_date) {
		this.flat_date = flat_date;
	}

    
    public String getRevServiceId() {
        return revServiceId;
    }

    public void setRevServiceId(String revServiceId) {
        this.revServiceId = revServiceId;
    }

    public String getRetStatus() {
        return retStatus;
    }

    public void setRetStatus(String retStatus) {
        this.retStatus = retStatus;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }


    public String getProxyFlowNo() {
        return proxyFlowNo;
    }

    public void setProxyFlowNo(String proxyFlowNo) {
        this.proxyFlowNo = proxyFlowNo;
    }

    public String getTranFlowNo() {
        return tranFlowNo;
    }

    public void setTranFlowNo(String tranFlowNo) {
        this.tranFlowNo = tranFlowNo;
    }

    public String getTranStamp() {
        return tranStamp;
    }

    public void setTranStamp(String tranStamp) {
        this.tranStamp = tranStamp;
    }


    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getCompositeServiceId() {
        return compositeServiceId;
    }

    public void setCompositeServiceId(String compositeServiceId) {
        this.compositeServiceId = compositeServiceId;
    }

    public String getFlowStep() {
        return flowStep;
    }

    public void setFlowStep(String flowStep) {
        this.flowStep = flowStep;
    }
}
