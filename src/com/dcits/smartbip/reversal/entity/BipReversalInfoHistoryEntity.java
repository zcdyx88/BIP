package com.dcits.smartbip.reversal.entity;


import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * Created by zhangcheng on 20/10/16.
 */
@Entity
@Table(name = "BIP_REVERSAL_INFO_HISTORY")
public class BipReversalInfoHistoryEntity {	
    //流水唯一标识,BIP自动生成
    @Id
	/*@GeneratedValue(generator="system-id")
	@GenericGenerator(name="system-id",strategy="increment")*/
    @Column(name = "ID", length = 100)
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
    @Column(name = "REVERSALRESULT")
    private int reversalResult = -1;    
    
/*    //下一次冲正发起时间
    @Column(name = "NEXTREVERSALTIME")
    private Date NextReversalTime = new Date();*/
    
    //已发送过的冲正次数
    @Column(name = "COUNT")
    private int count = 0;  

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

/*	public Date getNextReversalTime() {
		return NextReversalTime;
	}

	public void setNextReversalTime(Date nextReversalTime) {
		NextReversalTime = nextReversalTime;
	}*/

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("原交易流水号=").append(this.buszzSerialNum).append("\n");
		sb.append("冲正服务ID=").append(this.buzzServiceID).append("\n");
		sb.append("当前冲正次数=").append(this.count).append("\n");
		sb.append("冲正结果=").append(this.reversalResult).append("\n");
		return sb.toString();
	}
}
