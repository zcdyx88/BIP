package com.dcits.smartbip.journal;

import com.dcits.smartbip.runtime.model.IContext;

/**
 * Created by vincentfxz on 16/8/4.
 */
public interface JournalService {

    public void start();
    public void invoke(IContext context, String msg);
    public void invoke(String processFlowNo, String serviceFlowNo, String compositeServiceId, String serviceId, String msg, String step);
}
