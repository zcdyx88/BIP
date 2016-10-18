package com.dcits.smartbip.parser.impl;

import com.dcits.smartbip.common.model.IConfiguration;
import com.dcits.smartbip.common.model.IParser;
import com.dcits.smartbip.exception.ConfigurationParseException;
import com.dcits.smartbip.parser.model.ProtocolConstants;
import com.dcits.smartbip.protocol.IProtocol;
import com.dcits.smartbip.protocol.ProtocolConfig;
import com.dcits.smartbip.utils.FileUtils;
import com.dcits.smartbip.utils.XMLUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.dcits.smartbip.parser.model.ProtocolConstants.*;

/**
 * Created by vincentfxz on 16/5/26.
 */
public class ProtocolParser implements IParser<Object,List<IProtocol>> {

    private static final Log log = LogFactory.getLog(ProtocolParser.class);


    /**
     * ½âÎöÐ­Òé
     * @param config
     * @return
     * @throws ConfigurationParseException
     */
    @Override
    public List<IProtocol> parse(IConfiguration config) throws ConfigurationParseException {
        List<IProtocol> protocols = new ArrayList<IProtocol>();
        String path = (String) config.getConfig(PROTOCOL_CONFIG_PATH);
        File file = FileUtils.getFromConfigs(path);
        Document document = XMLUtils.getDocFromFile(file);
         Element root = document.getRootElement();
        List<Element> protocolElements = root.elements();
        for (Element protocolElement : protocolElements) {
            try {
                String id = XMLUtils.getChildElementText(protocolElement, "id");
                log.debug("准备启动协议["  + id + "]");
                String implementation = XMLUtils.getChildElementText(protocolElement, "implement");
                String type = XMLUtils.getChildElementText(protocolElement, "type");
                Class protocolClazz = Class.forName(implementation);
                IProtocol protocol = (IProtocol) protocolClazz.newInstance();
                protocol.setId(id);
                String paramPath = XMLUtils.getChildElementText(protocolElement, "param");
//                File paramFile = FileUtils.getFileFromCL(paramPath);
                File paramFile = FileUtils.getFromConfigs(paramPath);
                Document paramDoc = XMLUtils.getDocFromFile(paramFile);
                Element rootParamElement = paramDoc.getRootElement();
                Map<String, String> paramMap = XMLUtils.elementToMap(rootParamElement);
                ProtocolConfig protocolConfig = new ProtocolConfig();
                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                    protocolConfig.setConfig(entry.getKey(), entry.getValue());
                }
                protocol.setConfig(protocolConfig);
                protocols.add(protocol);
            } catch (ClassNotFoundException e) {
                log.error(e, e);
            } catch (InstantiationException e) {
                log.error(e, e);
            } catch (IllegalAccessException e) {
                log.error(e, e);
            }
        }
        return protocols;
    }

    @Override
    public List<IProtocol> parse(Object o) throws ConfigurationParseException {
        return null;
    }
}
