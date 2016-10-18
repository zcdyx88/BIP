package com.dcits.smartbip.engine.impl;

import com.dcits.smartbip.common.model.IConfiguration;
import com.dcits.smartbip.common.model.IRepository;
import com.dcits.smartbip.engine.model.Dictionary;
import com.dcits.smartbip.engine.model.DictionaryItem;
import com.dcits.smartbip.engine.model.DictionaryItemPK;
import com.dcits.smartbip.exception.ConfigurationNotFoundException;
import com.dcits.smartbip.exception.ConfigurationParseException;
import com.dcits.smartbip.exception.InstanceNotFoundException;
import com.dcits.smartbip.parser.impl.DictionaryParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vincentfxz on 16/5/12.
 * 字典容器,字典容器是运行时单例
 */
public class DictionaryRepository implements IRepository<DictionaryItem, DictionaryItemPK> {

    private static final Log log = LogFactory.getLog(DictionaryRepository.class);

    private static DictionaryRepository dictionaryRepository;

    private final DictionaryParser dictionaryParser = new DictionaryParser();
    private final Map<String,Dictionary> dictionaryMap;

    private DictionaryRepository() {
        dictionaryMap = new HashMap<String, Dictionary>();
    }

    public static DictionaryRepository getInstance() {
        if (null == dictionaryRepository) {
            synchronized (DictionaryRepository.class) {
                if (null == dictionaryRepository) {
                    dictionaryRepository = new DictionaryRepository();
                }
            }
        }
        return dictionaryRepository;
    }


    @Override
    public DictionaryItem get(DictionaryItemPK id) throws InstanceNotFoundException {
        DictionaryItem item = null;
        Dictionary dictionary = dictionaryMap.get(id.getOwner());
        if(null != dictionary){
            item = dictionary.getItems().get(id.getItemId());
        }
        return item;
    }

    @Override
    public void load(List<IConfiguration> configurations) throws ConfigurationNotFoundException {
        for (IConfiguration configuration : configurations) {
            try {
                Dictionary dictionary = dictionaryParser.parse(configuration);
                dictionaryMap.put(dictionary.getOwner(), dictionary);
            } catch (ConfigurationParseException e) {
                log.error("字典配置["+configuration+"]解析失败",e);
            }
        }
    }

    @Override
    public void register(DictionaryItemPK id, DictionaryItem dictionaryItem) {

    }

    @Override
    public void remove(DictionaryItemPK id) {

    }

    @Override
    public void persist(DictionaryItemPK id) {

    }

    @Override
    public int size() {
        return dictionaryMap.size();
    }
}
