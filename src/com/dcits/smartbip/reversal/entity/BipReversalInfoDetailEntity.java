package com.dcits.smartbip.reversal.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Created by zhangcheng on 20/10/16.
 */
@Entity
@Table(name = "BIP_REVERSAL_INFO_DETAIL")
public class BipReversalInfoDetailEntity {	
    //流水唯一标识,BIP自动生成
    @Id
	@GeneratedValue(generator="system-id")
	@GenericGenerator(name="system-id",strategy="increment")
    @Column(name = "ID", length = 255)
    private int id;
    
    //被冲正的业务服务流水号，作为外键关联BIP_REVERSAL_INFO表
    @Column(name = "REVERSALINFOID", length = 100)
    private String reveralInfoId;
    
    //本次冲正流水号
    @Column(name = "SERIALNUM")
    private Date serialNum;
    
    //本次冲正响应码
    @Column(name = "RETURNCODE", length = 100)
    private String returnCode;
    
    //当前冲正次数
    @Column(name = "COUNT")
    private int count = 0;
    
    //本次冲正时间
    @Column(name = "DATE")
    private Date reversalTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReveralInfoId() {
		return reveralInfoId;
	}

	public void setReveralInfoId(String reveralInfoId) {
		this.reveralInfoId = reveralInfoId;
	}

	public Date getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(Date serialNum) {
		this.serialNum = serialNum;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Date getReversalTime() {
		return reversalTime;
	}

	public void setReversalTime(Date reversalTime) {
		this.reversalTime = reversalTime;
	}
}
