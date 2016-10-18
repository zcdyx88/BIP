package com.dcits.smartbip.engine.impl;

import com.dcits.smartbip.common.model.IConfiguration;
import com.dcits.smartbip.common.model.IRepository;
import com.dcits.smartbip.exception.ConfigurationNotFoundException;
import com.dcits.smartbip.exception.InstanceNotFoundException;
import com.dcits.smartbip.utils.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

/**
 * Created by vincentfxz on 16/8/7.
 */
public class ParamRepository implements IRepository<String, String> {

    public static final String PARAM_DEFINE_NAME = "PARAM_DEFINE";
    private static final Log log = LogFactory.getLog(ParamRepository.class);
    private final Properties param;
    private static ParamRepository paramRepository;

    public static ParamRepository getRepository() {
        if (null == paramRepository) {
            synchronized (ParamRepository.class) {
                if (null == paramRepository) {
                    paramRepository = new ParamRepository();
                }
            }
        }
        return paramRepository;
    }

    private ParamRepository() {
        param = new Properties();
    }

    @Override
    public String get(String id) throws InstanceNotFoundException {
        return (String) param.get(id);
    }

    @Override
    public void load(List<IConfiguration> configurations) throws ConfigurationNotFoundException {
        for (IConfiguration configuration : configurations) {
            File configFile = getConfigFile(configuration);
            try {
                param.load(new FileInputStream(configFile));
            } catch (Exception e) {
                log.error("解析容器配置文件[" + configFile + "]失败", e);
            }
        }
    }

    @Override
    public void register(String id, String s) {
        param.put(id, s);
    }

    @Override
    public void remove(String id) {
        param.remove(id);
    }

    @Override
    public void persist(String id) {

    }

    @Override
    public int size() {
        return 0;
    }

    private File getConfigFile(IConfiguration config) {
        String serviceDefPath = (String) config.getConfig(PARAM_DEFINE_NAME);
        return FileUtils.getFromConfigs(serviceDefPath);
    }
}
