package com.dcits.smartbip.runtime.model.impl;

import com.dcits.smartbip.common.model.IRegisterAble;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.utils.CompositeDataUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vincentfxz on 16/9/20.
 */
public class IdentifyService implements IRegisterAble{

    public Map<String, String> identifyMap = new HashMap<String, String>();

    private String id;
    private String path;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public void identify(ICompositeData compositeData){
        String contentValue = CompositeDataUtils.getValue(compositeData, path);
        compositeData.setDispatchId(identifyMap.get(contentValue));
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setIdentifyItem(String key, String value){
        identifyMap.put(key, value);
    }
}
