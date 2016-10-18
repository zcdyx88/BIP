package com.dcits.smartbip.engine;

import com.dcits.smartbip.common.model.*;
import com.dcits.smartbip.parser.model.ProcessDefinition;
import com.dcits.smartbip.utils.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by vincentfxz on 16/6/23.
 */
public abstract class ReloadAbleRepository<T extends IRegisterAble> {

    private final Log log = LogFactory.getLog(getClass());
    private final String productModeFlag = System.getProperty("bip.product.mode");
    private final Map<String, IReloadAbleDefinition> definitionMap = new ConcurrentHashMap<String, IReloadAbleDefinition>();

    /**
     * 与IRepository相同的接口,用来注册被注册内容
     *
     * @param id
     * @param registerAble
     */
    public abstract void register(String id, T registerAble);

    /**
     * 获取配置文件解析对象的方法
     *
     * @return
     */
    protected abstract IParser getParser();

    /**
     * 在非生产模式下,重新注册
     *
     * @param id
     */
    public void reRegister(String id) {
        if (!"true".equalsIgnoreCase(productModeFlag)) {
            IReloadAbleDefinition reloadAbleDefinition = getReloadAbleDefition(id);
            if (checkModify(reloadAbleDefinition)) {
                try {
                    getParser().parse(reloadAbleDefinition);
                    Class<T> processClazz = reloadAbleDefinition.getClazz();
                    T processInstance = processClazz.newInstance();
                    registerDefinition(reloadAbleDefinition);
                    register(reloadAbleDefinition.getId(), processInstance);
                } catch (Exception e) {
                    log.error("重新加载流程配置[" + reloadAbleDefinition.getId() + "]失败!", e);
                }
            }
        }
    }

    /**
     * 注册定义,从新加载需要保留原来的定义,以判断修改时间
     * @param definition
     */
    protected void registerDefinition( IReloadAbleDefinition definition) {
        getDefinitionMap().put(definition.getId(), definition);
    }

    /**
     * 获取定义对象的方法
     *
     * @param id
     * @return
     */
    public IReloadAbleDefinition getReloadAbleDefition(String id) {
        return getDefinitionMap().get(id);
    }

    /**
     * 检查配置文件是否被修改
     *
     * @param filePersistAble
     * @return
     */
    public boolean checkModify(IFilePersistAble filePersistAble) {
        long recordedLastModify = filePersistAble.getLastModify();
        String persistPath = filePersistAble.getFile();
        File persistFile = FileUtils.getFromConfigs(persistPath);
        long lastModify = persistFile.lastModified();
        return lastModify != recordedLastModify;
    }


    public Map<String, IReloadAbleDefinition> getDefinitionMap() {
        return definitionMap;
    }
}
