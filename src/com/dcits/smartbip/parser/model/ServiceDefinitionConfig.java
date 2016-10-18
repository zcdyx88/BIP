package com.dcits.smartbip.parser.model;

import com.dcits.smartbip.common.model.IConfiguration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by vincentfxz on 16/4/23.
 */
public class ServiceDefinitionConfig implements IConfiguration<String> {
    private final Map<String, String> map = new ConcurrentHashMap<String, String>();

    @Override
    public void setConfig(String key, String value) {
        map.put(key, value);
    }

    @Override
    public String getConfig(String key) {
        return map.get(key);
    }
}
