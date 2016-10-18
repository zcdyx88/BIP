package com.dcits.smartbip.engine.impl;

import com.dcits.smartbip.engine.IProcessEngine;
import com.dcits.smartbip.runtime.model.IProcessInstance;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by vincentfxz on 16/4/18.
 */
public class BaseProcessEngine extends ThreadPoolExecutor implements IProcessEngine<IProcessInstance>{
    public BaseProcessEngine(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                             BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }
}
