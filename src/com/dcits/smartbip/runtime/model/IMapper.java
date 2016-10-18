package com.dcits.smartbip.runtime.model;

import com.dcits.smartbip.common.model.IRegisterAble;

/**
 * Created by vincentfxz on 16/5/18.
 */
public interface IMapper <SO,TO> extends IRegisterAble{
    void mapping(SO sourceObject, TO targetObject);
    /**
     * 映射请求
     */
    void mapReq();
    /**
     * 映射返回
     */
    void mapRsp();
    /**
     * 获取流程的上下文信息
     * @return
     */
    IContext getContext();

    /**
     * 设置流程的上下文信息
     * @param context
     */
    void setContext(IContext context);

}
