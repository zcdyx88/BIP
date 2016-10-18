package com.dcits.smartbip.parser.impl;

import com.dcits.smartbip.common.model.IConfiguration;
import com.dcits.smartbip.common.model.IParser;
import com.dcits.smartbip.engine.model.Dictionary;
import com.dcits.smartbip.engine.model.DictionaryItem;
import com.dcits.smartbip.exception.ConfigurationParseException;
import com.dcits.smartbip.parser.model.DictionaryConstants;
import com.dcits.smartbip.utils.XMLUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by vincentfxz on 16/5/12.
 */
public class DictionaryParser implements IParser<Object,Dictionary> {
    private static final Log log = LogFactory.getLog(DictionaryParser.class);

    @Override
    public Dictionary parse(IConfiguration config) throws ConfigurationParseException {
        Dictionary dictionary = null;
        String dictConfigPath = (String) config.getConfig(DictionaryConstants.DCIT_CONFIG_PATH);
        File dictConfig = new File(dictConfigPath);
        Document dictConfigDoc = XMLUtils.getDocFromFile(dictConfig);
        Element dictRoot = dictConfigDoc.getRootElement();
        Node ownerNode = dictRoot.selectSingleNode("@owner");
        if (null != ownerNode) {
            String owner = ownerNode.getText();
            dictionary = new Dictionary(owner);
            Map<String, DictionaryItem> dictionaryItemMap = dictionary.getItems();
            List<Element> itemElements = dictRoot.elements();
            for (Element itemElement : itemElements) {
                Node idNode = itemElement.selectSingleNode("@id");
                Node typeNode = itemElement.selectSingleNode("@type");
                Node valueNode = itemElement.selectSingleNode("@value");
                String desc = itemElement.getText();
                if (null != idNode && null != typeNode && null != valueNode) {
                    String id = idNode.getText();
                    String type = typeNode.getText();
                    String value = valueNode.getText();
                    DictionaryItem dictionaryItem = new DictionaryItem(id, value, type, desc, owner);
                    dictionaryItemMap.put(id,dictionaryItem);
                } else {
                    log.error("字典项解析失败,id,type,value不能为空!");
                }
            }
        } else {
            log.error("字典解析失败,字典的owner不能为空!");
        }
        return dictionary;
    }

    @Override
    public Dictionary parse(Object o) throws ConfigurationParseException {
        return null;
    }
}
