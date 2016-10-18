package com.dcits.smartbip.register;

import com.dcits.smartbip.common.model.IConfiguration;
import com.dcits.smartbip.common.model.IReloadAbleDefinition;
import com.dcits.smartbip.engine.impl.BaseProcessRepository;
import com.dcits.smartbip.engine.impl.MapperRepository;
import com.dcits.smartbip.engine.impl.ProtocolRepository;
import com.dcits.smartbip.engine.impl.ServiceRepository;
import com.dcits.smartbip.exception.ConfigurationNotFoundException;
import com.dcits.smartbip.exception.InstanceNotFoundException;
import com.dcits.smartbip.parser.code.CodeConstants;
import com.dcits.smartbip.parser.code.JavaCompiler;
import com.dcits.smartbip.parser.model.MapperConstants;
import com.dcits.smartbip.parser.model.ProcessDefinitionConfig;
import com.dcits.smartbip.parser.model.ProcessDefinitionConstants;
import com.dcits.smartbip.protocol.IProtocol;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.runtime.model.IServiceProxy;
import com.dcits.smartbip.runtime.model.impl.BaseBuzzService;
import com.dcits.smartbip.utils.FileUtils;
import com.dcits.smartbip.utils.XMLUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PublishService implements PublishServiceMBean {
    private Log log = LogFactory.getLog(getClass());

    @Override
    public String pubBuzzService(PublishBuzzServiceDataBean bean) {
        log.info("****************** publish buzz service **************");
        log.info("id[" + bean.id + "]");
        log.info("protocolType[" + bean.protocolType + "]");
        log.info("namespace[" + bean.namespace + "]");
        log.info("reversalServiceId[" + bean.reversalServiceId + "]");
        log.info("retCode[" + bean.retCode + "]");
        log.info("wsdl[" + bean.wsdl + "]");
        log.info("serviceSchema[" + bean.serviceSchema + "]");
        log.info("metadataSchema[" + bean.metadataSchema + "]");
        log.info("****************** publish buzz service **************");
        //去掉S
        String serviceId = bean.id;
        serviceId = serviceId.startsWith("S") ? serviceId.substring(1) : serviceId;
        BaseBuzzService service = new BaseBuzzService();
        service.setId(serviceId);
        service.setType(bean.protocolType);
        service.setRetCodeField(bean.retCode);
        //TODO 协议管理页面没有完成,因为BIP直通ESB,暂时使用ProxyESB配置
        try {
            IProtocol serviceProxy = ProtocolRepository.getRepository().get("ProxyESB");
            if (serviceProxy instanceof IServiceProxy) {
                service.setServiceProxy((IServiceProxy) serviceProxy);
            }
        } catch (InstanceNotFoundException e) {
            log.error("协议[" + "ProxyESB" + "]未找到");
        }
        ServiceRepository.getRepository().register(serviceId, service);
        return "OK";
    }

    @Override
    public String pubCompositeService(PublishCompositeServiceDataBean bean) {
        log.info("****************** publish composite service **************");
        log.info("serviceId[" + bean.serviceId + "]");
        log.info("serviceName[" + bean.serviceName + "]");
        log.info("serviceDesc[" + bean.serviceDesc + "]");
        log.info(bean.processContent);

        StringBuilder sb = new StringBuilder();
        sb.append(FileUtils.getConfigPath()).append(File.separator).append("processDef").append(File.separator).append(bean.serviceId);
        String processConfigPath = sb.toString() + File.separator + "P" + bean.serviceId + ".xml";
        try {
            File file = FileUtils.getFromConfigs("processDef/processDef.xml");
            Document document = XMLUtils.getDocFromFile(file);
            Element newProcess = document.getRootElement().addElement("process");
            newProcess.addElement("id").addText(bean.serviceId);
            newProcess.addElement("file").addText(processConfigPath);
            FileUtils.write(new File(processConfigPath), processConfigPath);
        } catch (IOException e) {
            log.error(e, e);
        }
        if (log.isInfoEnabled()) {
            log.info("BIP开始加载流程信息......");
            log.info("流程配置文件的地址为[processDef/processDef.xml]");
        }
        ProcessDefinitionConfig processDefinitionConfig = new ProcessDefinitionConfig();
        processDefinitionConfig.setConfig(ProcessDefinitionConstants.PROCESS_DEFINITION_PATH,
                "processDef/processDef.xml");
        List<IConfiguration> processConfigurations = new ArrayList<IConfiguration>();
        processConfigurations.add(processDefinitionConfig);
        try {
            BaseProcessRepository.getRepository().load(processConfigurations);
        } catch (ConfigurationNotFoundException e) {
            log.error("加载流程失败!", e);
        }
        if (log.isInfoEnabled()) {
            log.info("BIP加载流程信息完成!");
        }

        log.info("****************** publish composite service **************");
        return "OK";
    }

    @Override
    public String pubBaseService(PublishBaseServiceDataBean bean) {
        String serviceId = bean.id;
        String serviceName = bean.name;
        String serviceDesc = bean.desc;
        String serviceClazzContent = bean.code;
        log.info("****************** 发布基础服务  **************");
        log.info("serviceId[" + serviceId + "]");
        log.info("serviceName[" + serviceName + "]");
        log.info("serviceDesc[" + serviceDesc + "]");
        log.info("code[" + serviceClazzContent + "]");

        String defaultSrcPath = ProcessDefinitionConstants.DEFAULT_SRC;
        String filePath = defaultSrcPath + File.separator + serviceId + ".java";
        try {
            FileUtils.write(new File(filePath), serviceClazzContent);
            JavaCompiler.addClassPath(System.getProperty("smartbip.install") + File.separator + "lib", false);
            JavaCompiler.compile(ProcessDefinitionConstants.DEFAULT_SRC, serviceId);
            File jarFile = new File(ProcessDefinitionConstants.DEFAULT_SRC + File.separator + serviceId + ".jar");
            File targetFile = new File(CodeConstants.REPOSITORY_PATH + File.separator + serviceId + ".jar");
            org.apache.commons.io.FileUtils.copyFile(jarFile, targetFile);
            Class clazz = JavaCompiler.loadClass(ProcessDefinitionConstants.RUNTIME_PROCESS_PACKAGE + "." + serviceId);
            IService serviceObject = (IService) clazz.newInstance();
            ServiceRepository.getRepository().register(serviceObject.getId(), serviceObject);

        } catch (Exception e) {
            log.error("发布基础服务失败!", e);
        } finally {
            log.info("****************** 发布基础服务 **************");
        }
        return "OK";
    }

    @Override
    public String pubParamMapFile(PublishParamMapFileDataBean bean) {
        log.info("****************** pub ParamMapFile **************");
        log.info("serviceId[" + bean.mapFiles.toString() + "]");
        log.info("serviceId[" + bean.paramFile + "]");
        int i= 0;
        String proce = null;
        for (String mapContent : bean.mapFiles) {
            Document document = XMLUtils.getDocFromBytes(mapContent.getBytes());
            String mapId = document.getRootElement().attributeValue("id");
            StringBuilder sb = new StringBuilder();
            if (i==0){
                proce = mapId;
                sb.append(FileUtils.getConfigPath()).append(File.separator).append("mapperDef").append(File.separator).append(proce).append(File.separator).append("M").append(mapId).append(".xml");
                StringBuilder mapID = new StringBuilder();
                mapId = mapID.append("M").append(proce).toString();
            }
            else{
                sb.append(FileUtils.getConfigPath()).append(File.separator).append("mapperDef").append(File.separator).append(proce).append(File.separator).append(mapId).append(".xml");
            }
            String filePath = sb.toString();
            //写入maper配置文件
            WriteFile(filePath,mapContent);
            //在mapperDef.xml加入配置信息
            StringBuilder mapprtDef = new StringBuilder();
            mapprtDef.append(FileUtils.getConfigPath()).append(File.separator).append("mapperDef").append(File.separator).append("mapperDef.xml");
            String MapprtDef = mapprtDef.toString();
            StringBuilder file = new StringBuilder();
            file.append("mapperDef/").append(proce).append("/").append(mapId).append(".xml");
            String File = file.toString();
            WriteXml(MapprtDef,mapId,File);
            if (log.isInfoEnabled()) {
                log.info("BIP开始加载数据映射信息......");
                log.info("数据映射配置文件的地址为[mapperDef/mapperDef.xml]");
            }
            Document doc = XMLUtils.getDocFromFile(FileUtils.getFromConfigs("mapperDef/mapperDef.xml"));
//            <mapper>
//            <id>M3002101000103_3002100000103</id>
//            <file>mapperDef/3002101000103/M3002101000103_3002100000103.xml</file>
//            </mapper>
            Element mapperElement = doc.getRootElement().addElement("mapper");
            mapperElement.addElement("id").addText(mapId);
            mapperElement.addElement("file").addText(filePath);
            ProcessDefinitionConfig processDefinitionConfig = new ProcessDefinitionConfig();
            processDefinitionConfig.setConfig(MapperConstants.MAPPER_CONFIG_PATH,
                    "mapperDef/mapperDef.xml");
            List<IConfiguration> mapperConfigurations = new ArrayList<IConfiguration>();
            mapperConfigurations.add(processDefinitionConfig);
            try {
                MapperRepository.getRepository().load(mapperConfigurations);
            } catch (ConfigurationNotFoundException e) {
                log.error("加载流程失败!", e);
            }
            if (log.isInfoEnabled()) {
                log.info("BIP加载映射信息完成!");
            }
            i++;
        }
        log.info("****************** pub ParamMapFile **************");
        return "OK";
    }
    
	/*
     * 写文件，覆盖之前文件
     * */
	public static void WriteFile(String filename,String msg){
		File file = new File(filename);
		FileOutputStream out;
		try {
			out = new FileOutputStream(file);
			out.write(msg.getBytes("utf-8"));
			out.flush();
			out.close();
		} catch (Exception e) {
			System.err.println("write file error !");
			e.printStackTrace();
		}
	}
	
	/*
	 * xml中加信息 
	 * */
	public static void WriteXml(String filename,String id,String file){
	    try{
            SAXReader reader = new SAXReader();
            Document doc = reader.read(new File(filename));
            List<?> list = doc.selectNodes("/mappers");
            Iterator<?> iter = list.iterator();
            if(iter.hasNext()){
            	Element owner = (Element) iter.next();
            	Element mapper = owner.addElement("mapper");
            	Element ID = mapper.addElement("id");
            	ID.setText(id);
            	Element File = mapper.addElement("file");
            	File.setText(file);
            }
            //将doc中的内容写入文件中
            try{
                FileWriter newFile = new FileWriter(new File(filename));
                XMLWriter newWriter = new XMLWriter(newFile);
                newWriter.write(doc);
                newWriter.close();
            }catch(Exception e){
                e.printStackTrace();
            }
	    }catch(Exception e){
	    	System.err.println("write xml file error !");
            e.printStackTrace();
        }
		
		
	}
}
