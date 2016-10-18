package com.dcits.smartbip.transaction.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by vincentfxz on 16/9/23.
 */
@Entity
@Table(name = "BIP_SERVICE_TRANSATION")
public class ServiceTransaction {
    @Id
    @Column(name = "FLOW_NO", length = 255)
    private String flowNo;
    private String id;
    @Column(name = "SERVICE_ID", length = 255)
    private String serviceId;
    @Column(name = "REV_SERVICE_ID", length = 255)
    private String reversalId;
    @Column(name = "COM_SERVICE_ID", length = 255)
    private String compServiceId;
    @Column(name = "REVERSAL_TIME", length = 255)
    private String reversalTime;
    @Column(name="REVERSAL_TOTAL")
    private String reversalTotal;
    @Column(name = "STATUS", length = 255)
    private String status;
    @Column(name = "REVERSAL_PERIOD", length = 255)
    private String reversalPeriod;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getCompServiceId() {
        return compServiceId;
    }

    public void setCompServiceId(String compServiceId) {
        this.compServiceId = compServiceId;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public String getReversalTime() {
        return reversalTime;
    }

    public void setReversalTime(String reversalTime) {
        this.reversalTime = reversalTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReversalPeriod() {
        return reversalPeriod;
    }

    public void setReversalPeriod(String reversalPeriod) {
        this.reversalPeriod = reversalPeriod;
    }

    public String getReversalTotal() {
        return reversalTotal;
    }

    public void setReversalTotal(String reversalTotal) {
        this.reversalTotal = reversalTotal;
    }

    public String getReversalId() {
        return reversalId;
    }

    public void setReversalId(String reversalId) {
        this.reversalId = reversalId;
    }
}
