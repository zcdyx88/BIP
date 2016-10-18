package com.dcits.smartbip.engine.impl;

import com.dcits.smartbip.common.model.IConfiguration;
import com.dcits.smartbip.common.model.IParser;
import com.dcits.smartbip.common.model.IRepository;
import com.dcits.smartbip.exception.ConfigurationNotFoundException;
import com.dcits.smartbip.exception.ConfigurationParseException;
import com.dcits.smartbip.exception.InstanceNotFoundException;
import com.dcits.smartbip.parser.impl.ServiceDefinitionParser;
import com.dcits.smartbip.parser.model.ServiceDefinitionConstants;
import com.dcits.smartbip.runtime.model.IService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by vincentfxz on 16/4/18.
 * 服务的基础容器单例
 */
@Service
public class ServiceRepository implements IRepository<IService, String> {

    private static final Log log = LogFactory.getLog(ServiceRepository.class);

    private final IParser<Object, List<IService>> parser;
    private final Map<String, IService> serviceMap;
    private static ServiceRepository serviceRepository = null;

    public static ServiceRepository getRepository() {
        if (null == serviceRepository) {
            synchronized (ServiceRepository.class) {
                if (null == serviceRepository) {
                    serviceRepository = new ServiceRepository();
                }
            }
        }
        return serviceRepository;
    }

    private ServiceRepository() {
        serviceMap = new ConcurrentHashMap<String, IService>();
        parser = new ServiceDefinitionParser();
    }

    @Override
    public IService get(String id) throws InstanceNotFoundException {
        IService service = serviceMap.get(id);
        if (null != service) {
            service.clearContext();
        }
        return serviceMap.get(id);
    }

    @Override
    public void load(List<IConfiguration> configurations) throws ConfigurationNotFoundException {
        for (IConfiguration configuration : configurations) {
            try {
                List<IService> services = parser.parse(configuration);
                for(IService service : services){
                    if(log.isInfoEnabled()){
                        log.info("加载服务["+service.getId()+"];");
                    }
                    register(service.getId(), service);
                }
            } catch (ConfigurationParseException e) {
                log.error("解析服务配置文件[" + configuration.getConfig(ServiceDefinitionConstants.SERVICE_DEFINE_NAME) + "]失败", e);
            }
        }
    }

    @Override
    public void register(String id, IService service) {
        if(log.isInfoEnabled()){
            log.info("注册基础服务["+id+"]");
        }
        serviceMap.put(id, service);
    }

    @Override
    public void persist(String id){

    }

    @Override
    public int size() {
        return serviceMap.size();
    }

    @Override
    public void remove(String id) {
        serviceMap.remove(id);
    }
}
