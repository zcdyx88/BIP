package com.dcits.smartbip.runtime.model;

import com.dcits.smartbip.common.model.IRegisterAble;

import java.io.Serializable;

/**
 * Created by vincentfxz on 16/4/18.
 */
public interface IProcessInstance<T> extends Serializable, IRegisterAble {

    /**
     * 初始化流程
     */
    void init();

    /**
     * 获取流程的上下文信息
     *
     * @return
     */
    IContext getContext();

    /**
     * 设置流程的上下文信息
     *
     * @param context
     */
    void setContext(IContext context);

    /**
     * 运行流程
     *
     * @return
     */
    public T execute();


}
