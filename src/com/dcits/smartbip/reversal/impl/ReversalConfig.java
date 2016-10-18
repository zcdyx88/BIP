package com.dcits.smartbip.reversal.impl;

import com.dcits.smartbip.common.model.IConfiguration;
import com.dcits.smartbip.utils.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by vincentfxz on 16/8/27.
 */
public class ReversalConfig implements IConfiguration<String> {

    private static final Log log = LogFactory.getLog(ReversalConfig.class);
    private static final String REVERSAL_CONFIG_FILE = "reversal/reversal.properties";
    private final Properties properties;
    private static ReversalConfig reversalConfig;

    /**
     * 在启动的时候先加载冲正信息
     */
    static {
        reversalConfig = new ReversalConfig();
    }

    private ReversalConfig(){
        properties = new Properties();
        File reversalConfigFile = FileUtils.getFromConfigs(REVERSAL_CONFIG_FILE);
        try{
           properties.load(new FileInputStream(reversalConfigFile));
        }catch(Exception e){
            log.error("加载冲正配置失败!");
        }
    }

    public static ReversalConfig getInstance(){
        if(null != reversalConfig){
            reversalConfig = new ReversalConfig();
        }
        return reversalConfig;
    }

    @Override
    public String getConfig(String key) {
        return (String)properties.get(key);
    }

    @Override
    public void setConfig(String key, String value) {
        properties.setProperty(key, value);
    }
}
