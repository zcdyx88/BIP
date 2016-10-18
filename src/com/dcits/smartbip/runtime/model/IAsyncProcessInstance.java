package com.dcits.smartbip.runtime.model;

import java.util.concurrent.ExecutorService;

/**
 * Created by vincentfxz on 16/4/26.
 */
public interface IAsyncProcessInstance extends IProcessInstance,Runnable{
    public ExecutorService getThreadPool();
}
