package com.dcits.smartbip.utils;

import com.dcits.smartbip.runtime.model.IContext;

import java.util.Map;

/**
 * Created by vincentfxz on 16/4/22.
 */
public class ContextUtils {
    public void initContextWithMap(Map<String, String> map, IContext context){
        for(Map.Entry<String, String> entry : map.entrySet()){
            context.setValue(entry.getKey(),entry.getValue());
        }
    }
}
