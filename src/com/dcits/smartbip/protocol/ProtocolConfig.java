package com.dcits.smartbip.protocol;

import com.dcits.smartbip.common.model.IConfiguration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by vincentfxz on 16/5/26.
 */
public class ProtocolConfig implements IConfiguration<String> {

    private final Map<String, String> configMap = new ConcurrentHashMap<String, String>();

    @Override
    public String getConfig(String key) {
        return configMap.get(key);
    }

    @Override
    public void setConfig(String key, String value) {
        configMap.put(key, value);
    }
}
