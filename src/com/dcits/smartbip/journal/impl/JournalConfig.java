package com.dcits.smartbip.journal.impl;

import com.dcits.smartbip.common.model.IConfiguration;
import com.dcits.smartbip.utils.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by vincentfxz on 16/8/8.
 */
public class JournalConfig implements IConfiguration<String> {

    private static final Log log = LogFactory.getLog(JournalConfig.class);
    private static final String JOURNAL_CONFIG_FILE = "journal/journal.properties";
    public final Properties properties;
    private static JournalConfig journalConfig;

    /**
     * 在启动的时候先加载流水信息
     */
    static{
        journalConfig = new JournalConfig();
    }


    private JournalConfig(){
        properties = new Properties();
        File journalConfigFile = FileUtils.getFromConfigs(JOURNAL_CONFIG_FILE);
        try {
            properties.load(new FileInputStream(journalConfigFile));
        } catch (IOException e) {
            log.error("流水配置加载失败!");
        }
    }

    public static JournalConfig getInstance(){
        if(null == journalConfig){
            journalConfig = new JournalConfig();
        }
        return journalConfig;
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
