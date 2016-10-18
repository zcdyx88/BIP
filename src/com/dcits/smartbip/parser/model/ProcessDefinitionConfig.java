package com.dcits.smartbip.parser.model;

import com.dcits.smartbip.common.model.IConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vincentfxz on 16/4/18.
 * 流程定义的配置信息类
 */
public class ProcessDefinitionConfig implements IConfiguration<String> {

    private final Map<String, String> map = new HashMap<String, String>();

    @Override
    public String getConfig(String key) {
        return map.get(key);
    }

    @Override
    public void setConfig(String key, String value) {
        map.put(key, value);
    }

}
