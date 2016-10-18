package com.dcits.smartbip.engine.impl;

import com.dcits.smartbip.common.model.IConfiguration;
import com.dcits.smartbip.common.model.IRepository;
import com.dcits.smartbip.exception.ConfigurationNotFoundException;
import com.dcits.smartbip.exception.ConfigurationParseException;
import com.dcits.smartbip.exception.InstanceNotFoundException;
import com.dcits.smartbip.parser.impl.ProtocolParser;
import com.dcits.smartbip.protocol.IProtocol;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by vincentfxz on 16/5/26.
 * 接入接出协议管理容器
 */
public class ProtocolRepository implements IRepository<IProtocol, String> {

    private static final Log log = LogFactory.getLog(ProtocolRepository.class);
    private static ProtocolRepository protocolRepository;

    private final Map<String, IProtocol> protocolMap = new ConcurrentHashMap<String, IProtocol>();
    private final ProtocolParser protocolParser;

    private ProtocolRepository() {
        protocolParser = new ProtocolParser();
    }

    public static ProtocolRepository getRepository() {
        if (null == protocolRepository) {
            synchronized (ProtocolRepository.class) {
                if (null == protocolRepository) {
                    protocolRepository = new ProtocolRepository();
                }
            }
        }
        return protocolRepository;
    }

    /**
     * 根据协议ID获取协议实例,如果协议不存在则抛出  InstanceNotFoundException
     *
     * @param id
     * @return
     * @throws InstanceNotFoundException
     */
    @Override
    public IProtocol get(String id) throws InstanceNotFoundException {
        return protocolMap.get(id);
    }

    /**
     * 加载并注册协议,如果配置文件不存在则抛出 ConfigurationNotFoundException
     *
     * @param configurations
     * @throws ConfigurationNotFoundException
     */
    @Override
    public void load(List<IConfiguration> configurations) throws ConfigurationNotFoundException {
        for (IConfiguration configuration : configurations) {
            try {
                //调用协议解析器,解析协议配置
                List<IProtocol> protocols = protocolParser.parse(configuration);
                for (IProtocol protocol : protocols) {
                    if (log.isInfoEnabled()) {
                        log.info("BIP开始注册协议[" + protocol.getId() + "]......");
                    }
                    //注册协议到容器中
                    register(protocol.getId(), protocol);
                }
            } catch (ConfigurationParseException e) {
                log.error(e, e);
            }
        }
    }

    /**
     * 注册协议并启动
     *
     * @param id
     * @param protocol
     */
    @Override
    public void register(String id, final IProtocol protocol) {
        //将协议放入Map
        this.protocolMap.put(id, protocol);
        //新线程启动协议
        new Thread(new Runnable() {
            @Override
            public void run() {
                protocol.start();
            }
        }).start();
    }

    /**
     * 根据ID从容器中删除协议
     *
     * @param id
     */
    @Override
    public void remove(String id) {
        IProtocol protocol = this.protocolMap.get(id);
        if (null != protocol) {
            protocol.stop();
            this.protocolMap.remove(id);
        }
    }


    /**
     * 将容器中的某个协议持久化
     *
     * @param id
     */
    @Override
    public void persist(String id) {
        //TODO

    }

    /**
     * 返回容器中协议的个数
     *
     * @return
     */
    @Override
    public int size() {
        return protocolMap.size();
    }
}
