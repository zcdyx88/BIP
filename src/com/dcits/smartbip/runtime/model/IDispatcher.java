package com.dcits.smartbip.runtime.model;

/**
 * Created by vincentfxz on 16/5/25.
 */
public interface IDispatcher<S> extends Runnable {
    public void setDispatchee(byte[] content);
    public void dispatch(S s);
}
