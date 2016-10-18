package com.dcits.smartbip.runtime.model.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.common.model.ErrorInterceptAble;
import com.dcits.smartbip.common.model.ReversalAble;
import com.dcits.smartbip.engine.impl.ServiceRepository;
import com.dcits.smartbip.exception.InterceptException;
import com.dcits.smartbip.exception.InvokeException;
import com.dcits.smartbip.exception.ReversalException;
import com.dcits.smartbip.runtime.model.IContext;
import com.dcits.smartbip.runtime.model.IService;

/**
 * Created by vincentfxz on 16/4/18.
 */
public abstract class AbstractBuzzService implements ReversalAble, IService, ErrorInterceptAble {

    private final Log log = LogFactory.getLog(getClass());

    private String id;
    private String reversalServiceId;
    private boolean isReversalAble = false;
    private String retCodeField;
    private IContext sessionContext = SessionContext.getContext();

    @Override
    public IContext getContext() {
        return sessionContext;
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
    public void reversal() throws ReversalException {
        try {
            if (isReversalAble() && null != getReversalServiceId()) {
                ServiceRepository serviceRepository = ServiceRepository.getRepository();
                serviceRepository.get(getReversalServiceId());
            }
            log.error("未配置冲正服务");
        } catch (Exception e) {
            String msg = "冲正失败";
            log.error(msg, e);
            throw new ReversalException(msg);
        }
    }

    @Override
    public Object execute(Object req) throws InvokeException {
        Object rsp = null;
        try {
            rsp = invokeService(req);
            getContext().setValue(getReqName(), rsp);
        } catch (Exception e) {
        	log.error("ERROR请求报文|返回报文:"+req +"\n响应报文:"+rsp);
            log.error("业务服务执行失败!"+e.toString());
            throw new InvokeException();
        } finally {
            try {
                if (!interceptErrors()) {
                    if (log.isInfoEnabled()) {
                        log.info("错误拦截完成,没有异常");
                    }
                } else {
                    log.error("拦截到错误!");
                    throw new InvokeException();
                }
            } catch (InterceptException e) {
                log.error("错误拦截失败!", e);
            }
        }
        return rsp;
    }

    protected abstract Object invokeService(Object req);

    public String getReversalServiceId() {
        return reversalServiceId;
    }

    public void setReversalServiceId(String reversalServiceId) {
        this.reversalServiceId = reversalServiceId;
    }

    public boolean isReversalAble() {
        return isReversalAble;
    }

    public void setReversalAble(boolean reversalAble) {
        isReversalAble = reversalAble;
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
    public String getRetCodeField() {
        return retCodeField;
    }

    @Override
    public void setRetCodeField(String retCodeField) {
        this.retCodeField = retCodeField;
    }
}
