package com.dcits.smartbip.runtime.model;

/**
 * Created by vincentfxz on 16/5/9.
 * 业务服务的代理,帮助业务服务完成协议发送
 */
public interface IServiceProxy<REQT, RSPT>{
    public String pack(ICompositeData compositeData);
    public RSPT invoke(REQT req);
    public ICompositeData unpack(String msg);
}
