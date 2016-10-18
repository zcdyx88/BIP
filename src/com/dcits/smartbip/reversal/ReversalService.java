package com.dcits.smartbip.reversal;

import com.dcits.smartbip.runtime.model.IContext;

/**
 * Created by vincentfxz on 16/8/16.
 */
public interface ReversalService {
    public void start();
    public void invoke(IContext context, String msg);
}
