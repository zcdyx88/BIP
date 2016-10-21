package com.dcits.smartbip.reversal.entity;

import javax.persistence.*;

/**
 * Created by zhangcheng on 20/10/16.
 */
@Entity
@Table(name = "BIP_REVERSAL_POLICY")
public class BipReversalPolicyEntity {
	
    //冲正策略ID
    @Id
    @Column(name = "ID", length = 255)
    private String id;

    //时间段起始时间(24小时制，支持只在某个时间段冲正，不设置则默认为何时都可以)
    @Column(name = "STARTTIME", length = 255)
    private String startTime;

    //时间段结束时间(24小时制，支持只在某个时间段冲正，不设置则默认为何时都可以)
    @Column(name = "ENDTIME", length = 255)
    private String endTime;
    
    //最大冲正次数
    @Column(name = "MAXCOUNT")
    private int maxCount;
    
    //俩次冲正交易间的时间间隔,单位为秒
    @Column(name = "INTERVAL")
    private int INTERVAL;
    
}
