package com.dcits.smartbip.runtime.model;

import java.io.Serializable;

/**
 * Created by vincentfxz on 16/4/18.
 * 容器上下文框架
 */
public interface IContext extends  Serializable {

    /**
     * 获取上下文内容
     * @param key
     * @return
     */
    <T> T getValue(String key, Class<T> clazz);
    Object getValue(String key);

    /**
     * 设置上下文内容
     * @param key
     * @param value
     */
    void setValue(String key, Object value);
    void setContext(IContext context);

    void clear();

}
