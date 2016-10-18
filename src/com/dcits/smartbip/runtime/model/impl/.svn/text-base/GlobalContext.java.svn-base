package com.dcits.smartbip.runtime.model.impl;

import com.dcits.smartbip.runtime.model.IContext;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by vincentfxz on 16/5/13.
 */
public class GlobalContext implements IContext {

    private static GlobalContext instance;
    private String id;

    private final ConcurrentHashMap<String, Object> map;

    private GlobalContext() {
        map = new ConcurrentHashMap<String, Object>();
    }

    public static IContext getContext() {
        if (null == instance) {
            synchronized (GlobalContext.class){
                if(null == instance){
                    instance = new GlobalContext();
                }
            }
        }
        return instance;
    }

    @Override
    public <T> T getValue(String key, Class<T> clazz) {
        return null;
    }

    @Override
    public Object getValue(String key) {
        return map.get(key);
    }

    @Override
    public void setValue(String key, Object value) {
        map.put(key, value);
    }

    @Override
    public void setContext(IContext context) {
        instance = (GlobalContext) context;
    }

    @Override
    public void clear() {
        synchronized (GlobalContext.class){
            map.clear();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String s) {
        id = s;
    }

    public Object getPersistentState() {
        return false;
    }
}
