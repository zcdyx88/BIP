package com.dcits.smartbip.runtime.model.impl;

import com.dcits.smartbip.common.model.ErrorInterceptAble;
import com.dcits.smartbip.common.model.ReversalAble;
import com.dcits.smartbip.exception.InterceptException;
import com.dcits.smartbip.exception.ReversalException;
import com.dcits.smartbip.runtime.model.IContext;
import com.dcits.smartbip.runtime.model.IService;

/**
 * Created by vincentfxz on 16/5/6.
 */
public abstract class AbstractAsyncBuzzService implements ReversalAble, IService, ErrorInterceptAble {

    private IContext sessionContext = SessionContext.getContext();

    @Override
    public boolean interceptErrors() throws InterceptException {
        return true;
    }

    @Override
    public IContext getContext() {
        return SessionContext.getContext();
    }

    @Override
    public void setContext(IContext context) {
        sessionContext = context;
    }

    @Override
    public void clearContext() {
        sessionContext.clear();
    }

    @Override
    public void reversal() throws ReversalException {

    }
}
