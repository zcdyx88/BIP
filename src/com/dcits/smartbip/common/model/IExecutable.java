package com.dcits.smartbip.common.model;

/**
 * Created by vincentfxz on 16/4/18.
 * 所有可执行单元的接口
 */
public interface IExecutable<RT> {
    RT execute();
}
