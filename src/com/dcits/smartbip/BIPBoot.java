package com.dcits.smartbip;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.dcits.smartbip.common.model.IConfiguration;
import com.dcits.smartbip.engine.impl.BaseProcessRepository;
import com.dcits.smartbip.engine.impl.ClassPathLoader;
import com.dcits.smartbip.engine.impl.MapperRepository;
import com.dcits.smartbip.engine.impl.ParamRepository;
import com.dcits.smartbip.engine.impl.ProtocolRepository;
import com.dcits.smartbip.engine.impl.ServiceIdentifyRepository;
import com.dcits.smartbip.engine.impl.ServiceRepository;
import com.dcits.smartbip.exception.ConfigurationNotFoundException;
import com.dcits.smartbip.journal.impl.RedisJournalService;
import com.dcits.smartbip.parser.code.CodeConstants;
import com.dcits.smartbip.parser.model.MapperConstants;
import com.dcits.smartbip.parser.model.ProcessDefinitionConfig;
import com.dcits.smartbip.parser.model.ProcessDefinitionConstants;
import com.dcits.smartbip.parser.model.ProtocolConstants;
import com.dcits.smartbip.parser.model.ServiceDefinitionConfig;
import com.dcits.smartbip.parser.model.ServiceDefinitionConstants;
import com.dcits.smartbip.parser.model.ServiceIdentifyConfig;
import com.dcits.smartbip.parser.model.ServiceIdentifyConstants;
import com.dcits.smartbip.protocol.ProtocolConfig;
import com.dcits.smartbip.register.RegisterJMXServer;
import com.dcits.smartbip.reversal.impl.ReversalServer;
import com.dcits.smartbip.runtime.limit.LimitCtrlService;
import com.dcits.smartbip.runtime.property.ProductionsService;
import com.dcits.smartbip.runtime.property.PropertiesService;
import com.dcits.smartbip.utils.ApplicationUtils;

/**
 * Created by vincentfxz on 16/6/2.
 */
@SpringBootApplication
public class BIPBoot {
	private static final Log log = LogFactory.getLog(BIPBoot.class);
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(
				BIPBoot.class, args);
		ApplicationUtils.getInstance().setContext(applicationContext);
		System.setProperty("-Dbip.product.mode", "true");
		// 加载容器配置信息
		loadParam();
		// 加载生成类
		loadRepsitory();
		// 加载协议配置
		loadProtocols();
		// 加载服务配置
		loadServices();
		// 加载映射器配置
		loadMappers();
		// 加载流程配置
		loadProcesses();
		// 加载properties配置文件
		loadProperty();
		// 加载产品代码配置
//		loadProduct();
		// 加载限额参数配置
		loadLimitCtrl();
		// 启动配置加载监听
		loadRegister();
		// 启动流水
		loadJournal();	
		 //加载XML服务识别配置
        loadIdentifyServices();
        /*启动冲正线程，该线程需要使用到映射关系定义，协议定义，服务定义等
         * 如果作为单独的进程启动，加载协议时会报端口重复等错误，所有选择在BIP中一起启动*/
        loadReversal();
        
		if (log.isInfoEnabled()) {
			log.info("******BIP启动成功******");
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
		protocolConfig.setConfig(ProtocolConstants.PROTOCOL_CONFIG_PATH,
				"protocolDef/protocolDef.xml");
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

	 private static void loadServices() {
	        if (log.isInfoEnabled()) {
	            log.info("BIP开始加载服务信息......");
	            log.info("服务配置文件的地址为[serviceDef/serviceDef.xml]");
	        }
	        ServiceDefinitionConfig serviceDefinitionConfig = new ServiceDefinitionConfig();
	        serviceDefinitionConfig.setConfig(ServiceDefinitionConstants.SERVICE_DEFINE_NAME,
	                "serviceDef/serviceDef.xml");
	        List<IConfiguration> serviceConfigurations = new ArrayList<IConfiguration>();
	        serviceConfigurations.add(serviceDefinitionConfig);
	        try {
	            ServiceRepository.getRepository().load(serviceConfigurations);
	        } catch (ConfigurationNotFoundException e) {
	            log.error("加载服务配置失败!", e);
	        }
	        if (log.isInfoEnabled()) {
	            log.info("BIP加载服务信息完成!");
	        }
	    }

	private static void loadProcesses() {
		if (log.isInfoEnabled()) {
			log.info("BIP开始加载流程信息......");
			log.info("流程配置文件的地址为[processDef/processDef.xml]");
		}
		ProcessDefinitionConfig processDefinitionConfig = new ProcessDefinitionConfig();
		processDefinitionConfig.setConfig(
				ProcessDefinitionConstants.PROCESS_DEFINITION_PATH,
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
	}

	private static void loadMappers() {
		if (log.isInfoEnabled()) {
			log.info("BIP开始加载数据映射信息......");
			log.info("数据映射配置文件的地址为[mapperDef/mapperDef.xml]");
		}
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
	}

	private static void loadRegister() {
		RegisterJMXServer server = new RegisterJMXServer();
		server.register();

	}

	private static void loadLimitCtrl() {
		if (log.isInfoEnabled()) {
			log.debug("开始加载限额配置");
		}
		LimitCtrlService.load();
		if (log.isInfoEnabled()) {
			log.debug("加载限额配置完成");
		}
	}

	private static void loadProperty() {
		if (log.isInfoEnabled()) {
			log.debug("开始加载properties配置");
		}
		PropertiesService.getInstance().load();
		if (log.isInfoEnabled()) {
			log.debug("加载properties配置完成");
		}
	}

	private static void loadProduct() {
		if (log.isInfoEnabled()) {
			log.debug("开始加载产品代码配置");
		}
		ProductionsService.getInstance();
		if (log.isInfoEnabled()) {
			log.debug("加载产品代码配置完成");
		}
	}

	private static void loadJournal() {
		if (log.isInfoEnabled()) {
			log.info("开始启动流水......");
		}
		RedisJournalService journalService = RedisJournalService.getInstance();
		journalService.start();
		if (log.isInfoEnabled()) {
			log.info("流水启动结束");
		}
	}

	private static void loadParam() {
		if (log.isInfoEnabled()) {
			log.info("BIP开始加载参数信息......");
			log.info("协议配置文件的地址为[container/app.properties]");
		}
		ProtocolConfig paramConfig = new ProtocolConfig();
		paramConfig.setConfig(ParamRepository.PARAM_DEFINE_NAME,
				"container/app.properties");
		List<IConfiguration> configurations = new ArrayList<IConfiguration>();
		configurations.add(paramConfig);
		try {
			ParamRepository.getRepository().load(configurations);
		} catch (ConfigurationNotFoundException e) {
			log.error("加载参数配置失败!", e);
		}
		if (log.isInfoEnabled()) {
			log.info("BIP加载参数信息完成!");
		}
	}
	
	private static void loadIdentifyServices() {
        if (log.isInfoEnabled()) {
            log.info("开始加载服务识别信息......");
            log.info("服务配置文件的地址为[serviceDef/serviceIdentify.xml]");
        }
        ServiceIdentifyConfig serviceIdentifyConfig = new ServiceIdentifyConfig();
        serviceIdentifyConfig.setConfig(ServiceIdentifyConstants.SERVICE_IDENTIFY_NAME,
                "serviceDef/serviceIdentify.xml");
        List<IConfiguration> serviceConfigurations = new ArrayList<IConfiguration>();
        serviceConfigurations.add(serviceIdentifyConfig);
        try {
        	ServiceIdentifyRepository.getRepository().load(serviceConfigurations);
        } catch (ConfigurationNotFoundException e) {
            log.error("加载服务识别配置失败!", e);
        }
        if (log.isInfoEnabled()) {
            log.info("加载服务识别信息完成!");
        }
    }
	
	private static void loadReversal()
	{
		if (log.isInfoEnabled()) {
			log.info("开始启动冲正线程......");
		}
		ReversalServer reversalService = ReversalServer.getInstance();
		reversalService.start();
		if (log.isInfoEnabled()) {
			log.info("冲正线程启动结束");
		}
	}
}
