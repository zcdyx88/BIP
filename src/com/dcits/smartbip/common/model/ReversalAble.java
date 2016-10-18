package com.dcits.smartbip.common.model;

import com.dcits.smartbip.exception.ReversalException;

/**
 * Created by vincentfxz on 16/5/6.
 */
public interface ReversalAble {
    public void reversal() throws ReversalException;
}
