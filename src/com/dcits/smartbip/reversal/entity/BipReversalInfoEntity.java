package com.dcits.smartbip.reversal.entity;


import java.util.Date;

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
    @Column(name = "ORGBUSZZSERIALNUM", length = 255)
    private String buszzSerialNum;
    
    //冲正需要发送的业务服务ID
    @Column(name = "BUZZSERVICEID", length = 255)
    private String buzzServiceID;
    
    //冲正需要发送的业务服务ID
    @Column(name = "BUZZSERVICEMAPPER", length = 255)
    private String buzzServiceMapper;
    
    //判断冲正交易是否成功的响应域，请写全路径
    @Column(name = "REVERSALFIELD", length = 255)
    private String ReversalField; 
    
    //冲正交易成功时响应域的响应码
    @Column(name = "REVERSALSUCCCODE", length = 255)
    private String ReversalSuccCode;

    
/*    //冲正状态(0：待开始  1：进行中  2：结束)
    @Column(name = "REVERSALSTATUS")
    private int reversalStatus;*/
    
    //冲正结果(-1：还无结果  0：失败  1：成功 )
    @Column(name = "REVERSALRESULT" ,columnDefinition="int default -1")
    private int reversalResult;    
    
    //下一次冲正发起时间
    @Column(name = "NEXTREVERSALTIME")
    private Date NextReversalTime;
    
    //已发送过的冲正次数
    @Column(name = "COUNT" ,columnDefinition="int default 0")
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBuszzSerialNum() {
		return buszzSerialNum;
	}

	public void setBuszzSerialNum(String buszzSerialNum) {
		this.buszzSerialNum = buszzSerialNum;
	}

	public String getBuzzServiceID() {
		return buzzServiceID;
	}

	public void setBuzzServiceID(String buzzServiceID) {
		this.buzzServiceID = buzzServiceID;
	}

	public int getReversalResult() {
		return reversalResult;
	}

	public void setReversalResult(int reversalResult) {
		this.reversalResult = reversalResult;
	}

	public Date getNextReversalTime() {
		return NextReversalTime;
	}

	public void setNextReversalTime(Date nextReversalTime) {
		NextReversalTime = nextReversalTime;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getBuzzServiceMapper() {
		return buzzServiceMapper;
	}

	public void setBuzzServiceMapper(String buzzServiceMapper) {
		this.buzzServiceMapper = buzzServiceMapper;
	}

	public String getReversalField() {
		return ReversalField;
	}

	public void setReversalField(String reversalField) {
		ReversalField = reversalField;
	}

	public String getReversalSuccCode() {
		return ReversalSuccCode;
	}

	public void setReversalSuccCode(String reversalSuccCode) {
		ReversalSuccCode = reversalSuccCode;
	}
}
