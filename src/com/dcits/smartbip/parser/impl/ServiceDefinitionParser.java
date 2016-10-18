package com.dcits.smartbip.parser.impl;

import com.dcits.smartbip.common.model.IConfiguration;
import com.dcits.smartbip.common.model.IGenerater;
import com.dcits.smartbip.common.model.IParser;
import com.dcits.smartbip.engine.impl.ProtocolRepository;
import com.dcits.smartbip.exception.ConfigurationParseException;
import com.dcits.smartbip.exception.InstanceNotFoundException;
import com.dcits.smartbip.parser.model.ServiceDefinitionConstants;
import com.dcits.smartbip.protocol.IProtocol;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.IServiceProxy;
import com.dcits.smartbip.runtime.model.impl.BaseBuzzService;
import com.dcits.smartbip.runtime.model.impl.BaseCompositeService;
import com.dcits.smartbip.utils.FileUtils;
import com.dcits.smartbip.utils.XMLUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vincentfxz on 16/4/23.
 * 服务配置解析器,解析服务配置,包括业务服务/组合服务
 */
public class ServiceDefinitionParser implements IParser<Object, List<IService>> {

    private static final Log log = LogFactory.getLog(ServiceDefinitionParser.class);
    final IGenerater<Class, IService> wsEndPointGenerater;

    public ServiceDefinitionParser() {
        wsEndPointGenerater = new WSEndpointGenerater();
    }

    /**
     * 解析服务配置
     *
     * @param config
     * @return
     */
    @Override
    public List<IService> parse(IConfiguration config) {
        List<IService> services = new ArrayList<IService>();
        //获取服务配置的文件
        File serviceConfigFile = getConfigFile(config);
        Document document = XMLUtils.getDocFromFile(serviceConfigFile);
        Element serviceRoot = document.getRootElement();
        List<Element> serviceElements = serviceRoot.elements();
        for (Element serviceElement : serviceElements) {
            //获取服务的action属性,action属性有consume和provide两种,consume为bip消费的服务,provide为bip提供的服务
            Node serviceActionNode = serviceElement.selectSingleNode("@" + ServiceDefinitionConstants.SERVICE_ACTION);
            if (null != serviceActionNode) {
                String serviceAction = serviceActionNode.getText();
                //解析消费服务
                if (ServiceDefinitionConstants.SERVICE_ACTION_CONSUME.equalsIgnoreCase(serviceAction)) {
                    IService service = parseConsumeService(serviceElement);
                    services.add(service);
                }
                //解析提供服务
                else if (ServiceDefinitionConstants.SERVICE_ACTION_PROVIDE.equalsIgnoreCase(serviceAction)) {
//                    IService service = parseProvideService(serviceElement);
//                    services.add(service);
                } else if (ServiceDefinitionConstants.SERVICE_ACTION_BASE.equalsIgnoreCase(serviceAction)) {
                    IService service = parseBaseService(serviceElement);
                    services.add(service);
                }
            }
        }
        return services;
    }

    @Override
    public List<IService> parse(Object o) throws ConfigurationParseException {
        return null;
    }

    /**
     * 获取服务的配置文件
     *
     * @param config
     * @return
     */
    private File getConfigFile(IConfiguration config) {
        String serviceDefPath = (String) config.getConfig(ServiceDefinitionConstants.SERVICE_DEFINE_NAME);
        return FileUtils.getFromConfigs(serviceDefPath);
    }


    private IService parseBaseService(Element serviceElement) {
        List<Element> serviceAttrs = serviceElement.elements();
        String serviceId = "";
        IService service = null;
        try {
            for (Element serviceAttr : serviceAttrs) {
                if (ServiceDefinitionConstants.SERVICE_ID.equalsIgnoreCase(serviceAttr.getName())) {
                    serviceId = serviceAttr.getText();
                }
                if (ServiceDefinitionConstants.IMPLEMENTATION.equalsIgnoreCase(serviceAttr.getName())) {
                    String clazzName = serviceAttr.getText();
                    clazzName = clazzName == null ? "" : clazzName.trim();
                    service = (IService) Class.forName(clazzName).newInstance();
                }
            }
            service.setId(serviceId);
        } catch (Exception e) {
            log.error("加载基础服务[" + serviceId + "]失败!", e);
        }
        return service;
    }

    /**
     * 解析消费服务,消费服务根据服务的类型绑定对应的调用代理,代理与协议相关
     *
     * @param serviceElement
     * @return
     */
    private IService parseConsumeService(Element serviceElement) {
        BaseBuzzService service = new BaseBuzzService();
        List<Element> serviceAttrs = serviceElement.elements();
        String proxyId = null;
        for (Element serviceAttr : serviceAttrs) {
            //解析服务ID
            if (ServiceDefinitionConstants.SERVICE_ID.equalsIgnoreCase(serviceAttr.getName())) {
                String serviceId = serviceAttr.getText();
                service.setId(serviceId);
            }
            //解析服务类型
            if (ServiceDefinitionConstants.SERVICE_TYPE.equalsIgnoreCase(serviceAttr.getName())) {
                String serviceType = serviceAttr.getText();
                service.setType(serviceType);
            }
            if (ServiceDefinitionConstants.RETURN_CODE.equalsIgnoreCase(serviceAttr.getName())) {
                service.setRetCodeField(serviceAttr.getTextTrim());
            }
            if (ServiceDefinitionConstants.REVERSAL_SERVICE_ID.equalsIgnoreCase(serviceAttr.getName())) {
                service.setReversalServiceId(serviceAttr.getTextTrim());
            }
            if (ServiceDefinitionConstants.SERVICE_REQUEST.equalsIgnoreCase(serviceAttr.getName())) {
                String requestClazzName = serviceAttr.getTextTrim();
                try {
                    Class reqClazz = Class.forName(requestClazzName);
                    service.setReqClazz(reqClazz);
                } catch (ClassNotFoundException e) {
                    log.error("加载服务的请求类[" + requestClazzName + "]失败!", e);
                }
            }
            if (ServiceDefinitionConstants.SERVICE_RESPONSE.equalsIgnoreCase(serviceAttr.getName())) {
                String responseClazzName = serviceAttr.getTextTrim();
                try {
                    Class rspClazz = Class.forName(responseClazzName);
                    service.setRspClazz(rspClazz);
                } catch (ClassNotFoundException e) {
                    log.error("加载服务的请求类[" + responseClazzName + "]失败!", e);
                }
            }
            if (ServiceDefinitionConstants.PROXY.equalsIgnoreCase(serviceAttr.getName())) {
                proxyId = serviceAttr.getText();
            }
        }
        try {
            IProtocol serviceProxy = ProtocolRepository.getRepository().get(proxyId);
            if (serviceProxy instanceof IServiceProxy) {
                service.setServiceProxy((IServiceProxy) serviceProxy);
            }
        } catch (InstanceNotFoundException e) {
            log.error("服务的协议代理[" + proxyId + "]未找到!", e);
        }
        return service;
    }

    /**
     * 解析提供服务,提供服务需要绑定相应的流程
     *
     * @param serviceElement
     * @return
     */
    private IService parseProvideService(Element serviceElement) {
        BaseCompositeService service = new BaseCompositeService();
        List<Element> serviceAttrs = serviceElement.elements();
        for (Element serviceAttr : serviceAttrs) {
            if (ServiceDefinitionConstants.SERVICE_ID.equalsIgnoreCase(serviceAttr.getName())) {
                String serviceId = serviceAttr.getText();
                service.setId(serviceId);
            }
            if (ServiceDefinitionConstants.SERVICE_PROCESS_ID.equalsIgnoreCase(serviceAttr.getName())) {
                String processId = serviceAttr.getText();
                service.setProcessId(processId);
            }
        }
        Class endPointClazz = wsEndPointGenerater.generate(service);
        return service;
    }

}

