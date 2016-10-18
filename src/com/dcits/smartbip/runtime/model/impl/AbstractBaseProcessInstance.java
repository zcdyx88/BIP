package com.dcits.smartbip.runtime.model.impl;

import com.dcits.smartbip.runtime.model.IContext;
import com.dcits.smartbip.runtime.model.IProcessInstance;

/**
 * Created by vincentfxz on 16/4/18.
 * 流程实例
 */
public abstract class AbstractBaseProcessInstance implements IProcessInstance {

    private IContext context = SessionContext.getContext();
    private String id;

    @Override
    public void init() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getPersistentState() {
        return null;
    }

    @Override
    public IContext getContext() {
        return SessionContext.getContext();
    }

    @Override
    public void setContext(IContext context) {
        this.context = context;
    }

}
