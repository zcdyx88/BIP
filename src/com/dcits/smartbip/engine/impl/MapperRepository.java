package com.dcits.smartbip.engine.impl;

import com.dcits.smartbip.common.model.IConfiguration;
import com.dcits.smartbip.common.model.IParser;
import com.dcits.smartbip.common.model.IRepository;
import com.dcits.smartbip.engine.ReloadAbleRepository;
import com.dcits.smartbip.exception.ConfigurationNotFoundException;
import com.dcits.smartbip.exception.ConfigurationParseException;
import com.dcits.smartbip.exception.InstanceNotFoundException;
import com.dcits.smartbip.parser.impl.MapperParser;
import com.dcits.smartbip.parser.model.MapperDefinition;
import com.dcits.smartbip.runtime.model.IMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by vincentfxz on 16/5/18.
 */
public class MapperRepository extends ReloadAbleRepository<IMapper> implements IRepository<IMapper, String> {

    private static final Log log = LogFactory.getLog(MapperRepository.class);
    private static MapperRepository mapperRepository;
    private String id;
    private final ConcurrentHashMap<String, IMapper> mapperContainer;
    private final Map<String, MapperDefinition> mapperDefinitionMap;
    private final IParser<MapperDefinition, List<MapperDefinition>> parser;


    public static MapperRepository getRepository() {
        if (null == mapperRepository) {
            synchronized (MapperRepository.class) {
                if (null == mapperRepository) {
                    mapperRepository = new MapperRepository();
                }
            }
        }
        return mapperRepository;
    }

    private MapperRepository() {
        mapperContainer = new ConcurrentHashMap<String, IMapper>();
        mapperDefinitionMap = new ConcurrentHashMap<String, MapperDefinition>();
        parser = new MapperParser();
    }

    @Override
    public IMapper get(String id) throws InstanceNotFoundException {
        reRegister(id);
        return mapperContainer.get(id);
    }

    @Override
    public void load(List<IConfiguration> configurations) throws ConfigurationNotFoundException {
        for (IConfiguration configuration : configurations) {
            try {
                List<MapperDefinition> mapperDefinitions = parser.parse(configuration);
                for (MapperDefinition mapperDefinition : mapperDefinitions) {
                    Class mapperDefinitionClazz = mapperDefinition.getClazz();
                    try {
                        IMapper mapper = (IMapper) mapperDefinitionClazz.newInstance();
                        register(mapperDefinition.getId(), mapper);
                        registerDefinition(mapperDefinition);
                    } catch (InstantiationException e) {
                        log.error("加载数据映射配置[" + mapperDefinition.getId() + "]失败!");
                    } catch (IllegalAccessException e) {
                        log.error("加载数据映射配置[" + mapperDefinition.getId() + "]失败!");
                    }
                }
            } catch (ConfigurationParseException e) {
                log.error("解析映射配置[" + configuration + "]失败", e);
            }
        }
    }

    @Override
    public void register(String id, IMapper iMapper) {
        synchronized (getClass()) {
            mapperContainer.put(id, iMapper);
        }
    }

    @Override
    protected IParser getParser() {
        return this.parser;
    }

    @Override
    public void remove(String id) {
        synchronized (getClass()) {
            mapperContainer.remove(id);
        }
    }

    @Override
    public void persist(String id) {

    }

    @Override
    public int size() {
        return mapperContainer.size();
    }

    public String printRepositoryInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("****************************流程容器信息****************************\n");
        for (Map.Entry<String, IMapper> entry : mapperContainer.entrySet()) {
            sb.append("* Mapper ID: ").append(entry.getKey()).append("\n");
            sb.append("* Mapper hash:").append(entry.getValue().hashCode()).append("\n");
        }
        sb.append("****************************流程容器信息****************************\n");
        log.info(sb.toString());
        return sb.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
