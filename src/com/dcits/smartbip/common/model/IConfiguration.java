package com.dcits.smartbip.common.model;

/**
 * Created by vincentfxz on 16/4/18.
 * 所有配置的接口
 */
public interface IConfiguration<T> {
    T getConfig(String key);
    void setConfig(String key, T value);
}
