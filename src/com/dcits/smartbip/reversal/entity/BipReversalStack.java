package com.dcits.smartbip.reversal.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by vincentfxz on 16/8/17.
 *
 * 组合服务冲正栈,保存组合服务中需要冲正的原子服务和冲正状态
 */
@Entity
@Table(name = "BIP_REVERSAL_STACK")
public class BipReversalStack {

    //唯一标识,流水号
    @Id
    @Column(name = "ID", length = 40)
    private String id;

    @Column(name = "COM_SERVICE_ID", length = 40)
    private String compositeServiceId;

    @Column(name = "ATOM_SERVICE_ID", length = 40)
    private String atomServiceId;

    @Column(name = "REVERSAL_STATUS", length = 40)
    private String reversalStatus;

    public String getCompositeServiceId() {
        return compositeServiceId;
    }

    public void setCompositeServiceId(String compositeServiceId) {
        this.compositeServiceId = compositeServiceId;
    }

    public String getAtomServiceId() {
        return atomServiceId;
    }

    public void setAtomServiceId(String atomServiceId) {
        this.atomServiceId = atomServiceId;
    }

    public String getReversalStatus() {
        return reversalStatus;
    }

    public void setReversalStatus(String reversalStatus) {
        this.reversalStatus = reversalStatus;
    }
}
