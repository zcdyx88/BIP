package com.dcits.smartbip.protocol.ws.client;

import com.dcits.smartbip.common.model.IConfiguration;
import com.dcits.smartbip.common.model.IRepository;
import com.dcits.smartbip.exception.ConfigurationNotFoundException;
import com.dcits.smartbip.exception.InstanceNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.reficio.ws.builder.SoapBuilder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by vincentfxz on 16/5/26.
 */
public class SoapBuilderFactory implements IRepository<SoapBuilder,String>{
    private static final Log log = LogFactory.getLog(SoapBuilderFactory.class);
    private static SoapBuilderFactory soapBuilderFactory = new SoapBuilderFactory();
    private final Map<String, org.reficio.ws.builder.SoapBuilder> builderMap = new ConcurrentHashMap<String, SoapBuilder>();

    private SoapBuilderFactory(){

    }

    public static SoapBuilderFactory getRepository(){
        if(null == soapBuilderFactory){
            synchronized (SoapBuilderFactory.class){
                if(null == soapBuilderFactory)
                    soapBuilderFactory = new SoapBuilderFactory();
            }
        }
        return soapBuilderFactory;
    }

    @Override
    public SoapBuilder get(String id) throws InstanceNotFoundException {
        if(log.isDebugEnabled()){
            log.debug("获取SOAP-BUILDER["+id+"]");
        }
        return builderMap.get(id);
    }

    @Override
    public void load(List<IConfiguration> configurations) throws ConfigurationNotFoundException {

    }

    @Override
    public void register(String id, SoapBuilder soapBuilder) {

    }

    @Override
    public void remove(String id) {

    }

    @Override
    public void persist(String id) {

    }

    @Override
    public int size() {
        return builderMap.size();
    }

}
