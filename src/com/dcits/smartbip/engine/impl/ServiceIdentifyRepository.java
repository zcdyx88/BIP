package com.dcits.smartbip.engine.impl;

import com.dcits.smartbip.common.model.IConfiguration;
import com.dcits.smartbip.common.model.IRepository;
import com.dcits.smartbip.exception.ConfigurationNotFoundException;
import com.dcits.smartbip.exception.InstanceNotFoundException;
import com.dcits.smartbip.parser.model.ServiceIdentifyConstants;
import com.dcits.smartbip.runtime.model.impl.IdentifyService;
import com.dcits.smartbip.utils.FileUtils;
import com.dcits.smartbip.utils.XMLUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by vincentfxz on 16/9/20.
 */
public class ServiceIdentifyRepository implements IRepository<IdentifyService, String> {

    private static final Log log = LogFactory.getLog(ServiceIdentifyRepository.class);

    private static ServiceIdentifyRepository repository;

    private Map<String, IdentifyService> identifyServiceMap = new ConcurrentHashMap<String, IdentifyService>();

    public static ServiceIdentifyRepository getRepository(){
        if(null == repository){
            synchronized (ServiceIdentifyRepository.class){
                repository = new ServiceIdentifyRepository();
                try {
                    repository.load(null);
                } catch (ConfigurationNotFoundException e) {
                    log.error(e,e);
                }
            }
        }
        return repository;
    }

    @Override
    public IdentifyService get(String id) throws InstanceNotFoundException {
        return identifyServiceMap.get(id);
    }

    @Override
    public void load(List<IConfiguration> configurations) throws ConfigurationNotFoundException {
    	File file = FileUtils.getFromConfigs("serviceDef/serviceIdentify.xml");
        Document document = XMLUtils.getDocFromFile(file);
        Element root = document.getRootElement();
        List<Element> identifyElements = root.elements();
        for (Element element : identifyElements) {
            String id = XMLUtils.getAttribute(element, "id");
            String path = XMLUtils.getAttribute(element, "path");
            List<Element> items = element.elements();
            IdentifyService identifyService = new IdentifyService();
            identifyService.setId(id);
            identifyService.setPath(path);
            for(Element item : items){
                String value = XMLUtils.getAttribute(item, "value");
                String serviceid = XMLUtils.getAttribute(item, "serviceid");
                identifyService.setIdentifyItem(value, serviceid);
            }
            this.register(id, identifyService);

        }

    }

    @Override
    public void register(String id, IdentifyService identifyService) {
        this.identifyServiceMap.put(id, identifyService);

    }

    @Override
    public void remove(String id) {
        this.identifyServiceMap.remove(id);
    }

    @Override
    public void persist(String id) {

    }

    @Override
    public int size() {
        return 0;
    }
}
