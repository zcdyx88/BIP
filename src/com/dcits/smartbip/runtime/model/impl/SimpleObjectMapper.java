package com.dcits.smartbip.runtime.model.impl;

import com.dcits.smartbip.runtime.model.IContext;
import com.dcits.smartbip.runtime.model.IMapper;

/**
 * Created by vincentfxz on 16/5/18.
 */
public abstract class SimpleObjectMapper<SO, TO> implements IMapper<SO, TO> {

    private IContext sessionContext = SessionContext.getContext();
    private String id;

    @Override
    public void mapping(SO sourceObject, TO targetObject) {

    }

    @Override
    public IContext getContext() {
        return SessionContext.getContext();
    }

    @Override
    public void setContext(IContext context) {
        this.sessionContext = context;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
