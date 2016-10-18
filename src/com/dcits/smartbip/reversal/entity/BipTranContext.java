package com.dcits.smartbip.reversal.entity;

import javax.persistence.*;

/**
 * Created by vincentfxz on 16/8/16.
 */
@Entity
@Table(name = "BIP_JOURNAL_CONTEXT")
public class BipTranContext {
    //流水唯一标识
    @Id
    @Column(name = "FLOW_ID", length = 255)
    private String flowId;

    //上下文内容
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "FLOW_CONTEXT",columnDefinition="BLOB", nullable=true)
    private byte[] flowContext;


    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public byte[] getFlowContext() {
        return flowContext;
    }

    public void setFlowContext(byte[] flowContext) {
        this.flowContext = flowContext;
    }
}
