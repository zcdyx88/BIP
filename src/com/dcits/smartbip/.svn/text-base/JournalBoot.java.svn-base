package com.dcits.smartbip;

import com.dcits.smartbip.common.model.IConfiguration;
import com.dcits.smartbip.engine.impl.ParamRepository;
import com.dcits.smartbip.exception.ConfigurationNotFoundException;
import com.dcits.smartbip.journal.impl.RedisJournalServer;
import com.dcits.smartbip.protocol.ProtocolConfig;
import com.dcits.smartbip.utils.ApplicationUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vincentfxz on 16/8/8.
 */
@SpringBootApplication
public class JournalBoot {
    private static final Log log = LogFactory.getLog(JournalBoot.class);

    public static void start() {
        //加载容器配置信息
        loadParam();
        new Thread(new Runnable(){
			@Override
			public void run() {
				RedisJournalServer.getInstance().start();				
			}        	
        }).start();

    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(JournalBoot.class, args);
        ApplicationUtils.getInstance().setContext(applicationContext);
//        System.setProperty("smartbip.install", System.getProperty("user.dir"));
        start();
		if (log.isInfoEnabled()) {
			log.info("******journal启动成功******");
		}
    }


    private static void loadParam() {
        if (log.isInfoEnabled()) {
            log.info("BIP开始加载参数信息......");
            log.info("协议配置文件的地址为[container/app.properties]");
        }
        ProtocolConfig paramConfig = new ProtocolConfig();
        paramConfig.setConfig(ParamRepository.PARAM_DEFINE_NAME, "container/app.properties");
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
}
