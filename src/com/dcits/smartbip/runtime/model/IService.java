package com.dcits.smartbip.runtime.model;

import com.dcits.smartbip.common.model.IRegisterAble;
import com.dcits.smartbip.exception.InvokeException;

import java.io.Serializable;

/**
 * Created by vincentfxz on 16/4/18
 */
public interface IService extends IRegisterAble, Serializable {

    String getId();
    void setId(String id);

    String getType();

    void setType(String type);

    IContext getContext();

    void setContext(IContext context);

    void clearContext();

    Object execute(Object req) throws InvokeException;

    String getRspName();

    String getReqName();

    Class getReqClazz();

    void setReqClazz(Class reqClazz);

    Class getRspClazz();

    void setRspClazz(Class rspClazz);

    String getReversalServiceId();

    void setReversalServiceId(String reversalServiceId);

    boolean isReversalAble();

    void setReversalAble(boolean reversalAble);

    void setRetCodeField(String retCodeField);

    String getRetCodeField();
}
