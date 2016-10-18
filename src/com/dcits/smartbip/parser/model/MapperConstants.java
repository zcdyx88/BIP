package com.dcits.smartbip.parser.model;

import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.IMapper;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.runtime.model.impl.SimpleObjectMapper;
import com.dcits.smartbip.runtime.model.impl.SoapCompositeData;
import com.dcits.smartbip.utils.CompositeDataUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vincentfxz on 16/5/18.
 */
public class MapperConstants {
    public static final String MAPPER_CONFIG_PATH = "MapperConfigPath";
    public static final String MAPPER_REQUEST_NODE = "request";
    public static final String MAPPER_RESPONSE_NODE = "response";
    public static final String RUNTIME_PROCESS_PACKAGE = "com.dcits.smartbip.runtime.impl";
    public static final Class MAPPER_EXTENDS = SimpleObjectMapper.class;
    public static final List<Class> MAPPER_IMPORTS = new ArrayList<Class>();
    public static final List<Class> MAPPER_INTERFACES = new ArrayList<Class>();
    public static final String DEFAULT_SRC = System.getProperty("user.dir") + "/jar/src";

    static {
        MAPPER_INTERFACES.add(IMapper.class);
        MAPPER_IMPORTS.addAll(MAPPER_INTERFACES);
        MAPPER_IMPORTS.add(MAPPER_EXTENDS);
//        MAPPER_IMPORTS.add(BeanUtils.class);
        MAPPER_IMPORTS.add(ICompositeData.class);
        MAPPER_IMPORTS.add(CompositeDataUtils.class);
        MAPPER_IMPORTS.add(ArrayList.class);
        MAPPER_IMPORTS.add(List.class);
        MAPPER_IMPORTS.add(Log.class);
        MAPPER_IMPORTS.add(LogFactory.class);
        MAPPER_IMPORTS.add(SoapCompositeData.class);
        MAPPER_IMPORTS.add(SessionContext.class);

    }

}
