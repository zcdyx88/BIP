package com.dcits.smartbip.runtime.model.impl;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.dcits.smartbip.engine.impl.BaseProcessRepository;
import com.dcits.smartbip.exception.InstanceNotFoundException;
import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.parser.model.ServiceDefinitionConstants;
import com.dcits.smartbip.runtime.model.IContext;
import com.dcits.smartbip.runtime.model.IService;

/**
 * Created by vincentfxz on 16/5/17.
 */
public class BaseCompositeService implements IService {

    private static final Log log = LogFactory.getLog(BaseCompositeService.class);

    private IContext sessionContext = SessionContext.getContext();
    private String id;
    private String processId;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getType() {
        return ServiceDefinitionConstants.BUZZ_SERVICE_TYPE;
    }

    @Override
    public void setType(String type) {

    }

    @Override
    public IContext getContext() {
        return sessionContext;
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
    public Object execute(Object req) throws InvokeException {
        Object result = null;
        try {
            result = BaseProcessRepository.getRepository().get(getProcessId());
        } catch (InstanceNotFoundException e) {
            log.error("组合服务[" + getId() + "]对应的流程[" + getProcessId() + "]不存在!");
        }
        return result;
    }

    @Override
    public String getRspName() {
        return null;
    }

    @Override
    public String getReqName() {
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

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public void setId(String id) {
        this.id = id;
    }
}
