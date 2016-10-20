package com.dcits.smartbip.reversal.entity;

import javax.persistence.*;

/**
 * Created by zhangcheng on 16/10/16.
 */
@Entity
@Table(name = "BIP_BUSZZ_TRANS")
public class BipBuszzTransEntity {
	
    //业务服务唯一流水号，BIP自动生成，主键
    @Id
    @Column(name = "SERIALNUM", length = 255)
    private String serialNum;    
    
    //业务服务ID
    @Column(name = "SERVIECEID", length = 255)
    private String serviceID;    
    
    //该业务服务后台返回的响应码
    @Column(name = "RETURNCODE", length = 255)
    private String returnCode;
    
    //业务服务所属的组合服务的流水号，作为外键关联BIP_COMPOSITE_TRANS表
    @Column(name = "COMPSERIALNUM", length = 255)
    private String CompSerialNum;   
    
}
