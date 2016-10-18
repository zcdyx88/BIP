package com.dcits.smartbip.common.model;

import com.dcits.smartbip.exception.InterceptException;

/**
 * Created by vincentfxz on 16/5/6.
 */
public interface ErrorInterceptAble {
    public boolean interceptErrors() throws InterceptException;
}
