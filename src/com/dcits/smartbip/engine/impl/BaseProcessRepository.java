package com.dcits.smartbip.engine.impl;

import com.dcits.smartbip.common.model.IConfiguration;
import com.dcits.smartbip.common.model.IParser;
import com.dcits.smartbip.engine.IProcessRespository;
import com.dcits.smartbip.engine.ReloadAbleRepository;
import com.dcits.smartbip.exception.ConfigurationNotFoundException;
import com.dcits.smartbip.exception.ConfigurationParseException;
import com.dcits.smartbip.exception.InstanceNotFoundException;
import com.dcits.smartbip.parser.impl.ProcessDefinitionParser;
import com.dcits.smartbip.parser.model.ProcessDefinition;
import com.dcits.smartbip.parser.model.ProcessDefinitionConstants;
import com.dcits.smartbip.runtime.model.IAsyncProcessInstance;
import com.dcits.smartbip.runtime.model.IProcessInstance;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by vincentfxz on 16/4/18.
 * 运行流程的容器类,该容器为单例.
 */
public class BaseProcessRepository extends ReloadAbleRepository<IProcessInstance> implements IProcessRespository {

    private static final Log log = LogFactory.getLog(BaseProcessRepository.class);

    private static volatile BaseProcessRepository processRepository = null;

    private final Map<String, IProcessInstance> processMap;

    private final IParser<ProcessDefinition, List<ProcessDefinition>> parser = new ProcessDefinitionParser();


    private BaseProcessRepository() {
        processMap = new ConcurrentHashMap<String, IProcessInstance>();
    }

    public static BaseProcessRepository getRepository() {
        if (null == processRepository) {
            synchronized (BaseProcessRepository.class) {
                if (null == processRepository) {
                    processRepository = new BaseProcessRepository();
                }
            }
        }
        return processRepository;
    }

    @Override
    public IProcessInstance get(String id) throws InstanceNotFoundException {
        reRegister(id);
        IProcessInstance processInstance = processMap.get(id);
        if (null == processInstance) {
            throw new InstanceNotFoundException("流程[" + id + "]未找到!");
        }
        return processInstance;
    }


    @Override
    public void load(List<IConfiguration> configurations) throws ConfigurationNotFoundException {
        for (IConfiguration configuration : configurations) {
            try {
                List<ProcessDefinition> processDefinitions = parser.parse(configuration);
                for (ProcessDefinition processDefinition : processDefinitions) {
                    Class<IProcessInstance> processClazz = processDefinition.getClazz();
                    IProcessInstance processInstance = processClazz.newInstance();
                    register(processDefinition.getId(), processInstance);
                    registerDefinition(processDefinition);
                }
            } catch (ConfigurationParseException e) {
                log.error("解析流程配置文件[" + configuration.getConfig(ProcessDefinitionConstants.PROCESS_DEFINITION_PATH) + "]失败", e);
            } catch (InstantiationException e) {
                log.error("初始化流程对象失败!", e);
            } catch (IllegalAccessException e) {
                log.error("初始化流程对象失败!", e);
            }
        }
    }


    @Override
    public void remove(String id) {
        processMap.remove(id);
    }

    @Override
    public void persist(String id) {

    }

    @Override
    public int size() {
        return this.processMap.size();
    }

    public IAsyncProcessInstance getAsyncProcess(String id) throws InstanceNotFoundException {
        IProcessInstance processInstance = processMap.get(id);
        if (processInstance instanceof IAsyncProcessInstance) {
            return (IAsyncProcessInstance) processInstance;
        } else {
            if (log.isErrorEnabled()) {
                String msg = "[" + id + "]不是异步流程实例";
                log.error(msg);
                throw new InstanceNotFoundException(msg);
            }
        }
        return null;
    }

    public String printRepositoryInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n****************************流程容器信息****************************\n");
        for (Map.Entry<String, IProcessInstance> entry : processMap.entrySet()) {
            sb.append("\n* Process ID: ").append(entry.getKey()).append("\n");
            sb.append("\n* Process hash:").append(entry.getValue().hashCode()).append("\n");
        }
        sb.append("\n****************************流程容器信息****************************\n");
        log.info(sb.toString());
        return sb.toString();
    }

    private Map<String, IProcessInstance> getProcessMap() {
        return this.processMap;
    }



    @Override
    protected IParser getParser() {
        return this.parser;
    }



    @Override
    public void register(String id, IProcessInstance processInstance) {
        processMap.put(id, processInstance);
    }
}

