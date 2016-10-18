package com.dcits.smartbip.reversal.entity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by vincentfxz on 16/8/17.
 *
 * 组合服务异步冲正的冲正策略
 *
 */
@Entity
@Table(name = "BIP_REVERSAL_POLICY")
public class BipReversalPolicy {

    //策略的唯一标识
    @Id
    @Column(name = "ID", length = 40)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    //组合服务的服务ID
    @Column(name = "COM_SERVICE_ID", length = 40)
    private String serviceId;

    //异步冲正发起间隔
    @Column(name = "BIP_INTERVAL", length = 40)
    private String interval;

    //是否自动冲正
    @Column(name = "AUTO_REVERSAL", length = 40)
    private String autoReversal;

    //冲正超时时间
    @Column(name = "TIMEOUT", length = 20)
    private String timeout;

    //重试次数
    @Column(name = "RETRY_COUNT", length = 20)
    private String retryCount;

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

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getAutoReversal() {
        return autoReversal;
    }

    public void setAutoReversal(String autoReversal) {
        this.autoReversal = autoReversal;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(String retryCount) {
        this.retryCount = retryCount;
    }
}
