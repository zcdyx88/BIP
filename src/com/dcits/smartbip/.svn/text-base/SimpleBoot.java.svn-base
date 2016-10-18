package com.dcits.smartbip;

import com.dcits.smartbip.common.model.IConfiguration;
import com.dcits.smartbip.engine.impl.*;
import com.dcits.smartbip.exception.ConfigurationNotFoundException;
import com.dcits.smartbip.parser.code.CodeConstants;
import com.dcits.smartbip.parser.model.ProtocolConstants;
import com.dcits.smartbip.protocol.ProtocolConfig;
import com.dcits.smartbip.runtime.model.IMapper;
import com.dcits.smartbip.runtime.model.IProcessInstance;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.utils.FileUtils;
import com.dcits.smartbip.utils.XMLUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vincentfxz on 16/6/2.
 * <p/>
 * 简单启动模式,直接加载 1)流程 2)映射 3)基础服务 的部署包,不解析 1)流程 2)映射 3)基础服务的配置文件
 */
public class SimpleBoot {

    private static final Log log = LogFactory.getLog(SimpleBoot.class);

    private static final String simpleBootConfigPath = FileUtils.getConfigPath()
            + File.separator + "fastBoot" + File.separator + "fastBoot.xml";

    public static void main(String[] args) {



    }

    private static void loadBootItems(){
        List<BootItem> bootItems = parseSimpleBoot();
        if (log.isInfoEnabled()) {
            log.info("使用部署模式启动!");
        }
        for (BootItem bootItem : bootItems) {
            if (log.isInfoEnabled()) {
                log.info("启动加载项ID[" + bootItem.getId() + "] 类型[" + bootItem.getType()
                        + "] 实现类[" + bootItem.getImplementation() + "]");
            }
            try {
                if ("service".equalsIgnoreCase(bootItem.getType())) {
                    Class clazz = Class.forName(bootItem.getImplementation());
                    IService service = (IService) clazz.newInstance();
                    ServiceRepository.getRepository().register(service.getId(), service);
                    if (log.isInfoEnabled()) {
                        log.info("启动加载项ID[" + bootItem.getId() + "] 成功!");
                    }
                } else if ("process".equalsIgnoreCase(bootItem.getType())) {
                    Class clazz = Class.forName(bootItem.getImplementation());
                    IProcessInstance processInstance =
                            (IProcessInstance) Class.forName(bootItem.getImplementation()).newInstance();
                    BaseProcessRepository.getRepository().register(bootItem.getId(), processInstance);
                    if (log.isInfoEnabled()) {
                        log.info("启动加载项ID[" + bootItem.getId() + "] 成功!");
                    }
                } else if ("mapper".equalsIgnoreCase(bootItem.getType())) {
                    Class clazz = Class.forName(bootItem.getImplementation());
                    IMapper mapper = (IMapper) clazz.newInstance();
                    MapperRepository.getRepository().register(bootItem.getId(), mapper);
                    if (log.isInfoEnabled()) {
                        log.info("启动加载项ID[" + bootItem.getId() + "] 成功!");
                    }
                } else if ("protocol".equalsIgnoreCase(bootItem.getType())) {

                }
            } catch (Exception e) {
                log.error("启动加载项ID[" + bootItem.getId() + "] 失败!", e);
            }

        }
    }

    private static void loadRepsitory() {
        if (log.isInfoEnabled()) {
            log.info("BIP开始加载部署包信息......");
            log.info("部署包的地址为[" + CodeConstants.REPOSITORY_PATH + "]");
        }
        ClassPathLoader.loadClasspath(CodeConstants.REPOSITORY_PATH);
        if (log.isInfoEnabled()) {
            log.info("BIP加载部署包信息完成!");
        }
    }

    private static void loadProtocols() {
        if (log.isInfoEnabled()) {
            log.info("BIP开始加载协议信息......");
            log.info("协议配置文件的地址为[protocolDef/protocolDef.xml]");
        }
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setConfig(ProtocolConstants.PROTOCOL_CONFIG_PATH, "protocolDef/protocolDef.xml");
        List<IConfiguration> configurations = new ArrayList<IConfiguration>();
        configurations.add(protocolConfig);
        try {
            ProtocolRepository.getRepository().load(configurations);
        } catch (ConfigurationNotFoundException e) {
            log.error("加载协议配置失败!", e);
        }
        if (log.isInfoEnabled()) {
            log.info("BIP加载协议信息完成!");
        }
    }

    private static List<BootItem> parseSimpleBoot() {
        List<BootItem> bootItems = new ArrayList<BootItem>();
        File simpleBootConfig = new File(simpleBootConfigPath);
        if (!simpleBootConfig.exists()) {
            log.error("SIMPLE BOOT 失败! 配置文件[" + simpleBootConfigPath + "]未能找到!");
        } else {
            Document document = XMLUtils.getDocFromFile(simpleBootConfig);
            Element documentBootRoot = document.getRootElement();
            List<Element> bootItemElements = documentBootRoot.elements();
            for (Element bootItemElement : bootItemElements) {
                String id = XMLUtils.getAttribute(bootItemElement, "id");
                String type = XMLUtils.getAttribute(bootItemElement, "type");
                String implementation = XMLUtils.getAttribute(bootItemElement, "implememtation");
                BootItem bootItem = new BootItem(id, type, implementation);
                bootItems.add(bootItem);
            }
        }
        return bootItems;
    }

    public static class BootItem {

        private String id;
        private String implementation;
        private String type;

        public BootItem(String id, String type, String implementation) {
            setId(id);
            setType(type);
            setImplementation(implementation);
        }

        public String getImplementation() {
            return implementation;
        }

        public void setImplementation(String implementation) {
            this.implementation = implementation;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
