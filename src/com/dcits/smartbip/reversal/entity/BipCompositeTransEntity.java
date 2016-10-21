package com.dcits.smartbip.reversal.entity;

import javax.persistence.*;

/**
 * Created by zhangcheng on 20/10/16.
 */
@Entity
@Table(name = "BIP_COMPOSITE_TRANS")
public class BipCompositeTransEntity {
	
    //组合服务全局流水号，一般都是有调用方上送，主键
    @Id
    @Column(name = "SERIALNUM", length = 255)
    private String serialNum;    
    
    //组合服务ID
    @Column(name = "SERVIECEID", length = 255)
    private String serviceID;    
    
    //组合服务最终给调用方的响应码
    @Column(name = "RETURNCODE", length = 255)
    private String returnCode;

}
