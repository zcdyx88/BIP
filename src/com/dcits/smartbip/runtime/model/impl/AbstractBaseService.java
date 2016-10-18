package com.dcits.smartbip.runtime.model.impl;

import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.runtime.model.IContext;
import com.dcits.smartbip.runtime.model.IService;

/**
 * Created by vincentfxz on 16/5/16.
 */
public abstract class AbstractBaseService implements IService{

    private String id;
    private IContext sessionContext = SessionContext.getContext();

    @Override
    public IContext getContext() {
//        return sessionContext;
    	return SessionContext.getContext();
    }

    @Override
    public void setContext(IContext context) {
        this.sessionContext = context;
    }

    @Override
    public void clearContext() {
        sessionContext.clear();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getRspName() {
        return id + "Rsp";
    }

    @Override
    public String getReqName() {
        return id + "Req";
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Object execute(Object req) throws InvokeException {
        return null;
    }

    @Override
    public Class getReqClazz() {
        return null;
    }

    @Override
    public void setReqClazz(Class reqClazz) {

    }

    @Override
    public Class getRspClazz() {
        return null;
    }

    @Override
    public void setRspClazz(Class rspClazz) {

    }

    @Override
    public String getReversalServiceId() {
        return null;
    }

    @Override
    public void setReversalServiceId(String reversalServiceId) {

    }

    @Override
    public boolean isReversalAble() {
        return false;
    }

    @Override
    public void setReversalAble(boolean reversalAble) {

    }

    @Override
    public void setRetCodeField(String retCodeField) {

    }

    @Override
    public String getRetCodeField() {
        return null;
    }
}
