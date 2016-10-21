package com.dcits.smartbip.reversal.entity;

import java.sql.Date;

import javax.persistence.*;

/**
 * Created by zhangcheng on 20/10/16.
 */
@Entity
@Table(name = "BIP_REVERSAL_INFO")
public class BipReversalInfoEntity {	
    //流水唯一标识,BIP自动生成
    @Id
    @Column(name = "ID", length = 255)
    private String id;
    
    //被冲正的业务服务流水号，作为外键关联BIP_BUSZZ_TRANS
    @Column(name = "BUSZZSERIALNUM", length = 255)
    private String buszzSerialNum;
    
    //冲正需要发送的业务服务ID
    @Column(name = "BUZZSERVICEID", length = 255)
    private String buzzServiceID;
    
    //冲正状态(0：待开始  1：进行中  2：结束)
    @Column(name = "REVERSALSTATUS")
    private int reversalStatus;
    
    //冲正状态(0：失败  1：成功 )
    @Column(name = "REVERSALRESULT")
    private int reversalResult;    
    
    //下一次冲正发起时间
    @Column(name = "NEXTREVERSALTIME")
    private Date NextReversalTime;
    
    //已发送过的冲正次数
    @Column(name = "COUNT")
    private int count;    

    //上下文内容
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "FLOW_CONTEXT",columnDefinition="BLOB", nullable=true)
    private byte[] flowContext;

    public byte[] getFlowContext() {
        return flowContext;
    }

    public void setFlowContext(byte[] flowContext) {
        this.flowContext = flowContext;
    }
}
