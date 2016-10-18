package com.dcits.smartbip.parser.model;

import com.dcits.smartbip.engine.impl.BaseProcessRepository;
import com.dcits.smartbip.engine.impl.MapperRepository;
import com.dcits.smartbip.engine.impl.ServiceRepository;
import com.dcits.smartbip.exception.InstanceNotFoundException;
import com.dcits.smartbip.runtime.model.*;
import com.dcits.smartbip.runtime.model.impl.*;
import com.dcits.smartbip.utils.CompositeDataUtils;
import com.dcits.smartbip.utils.FileUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by vincentfxz on 16/4/23.
 */
public class ProcessDefinitionConstants {
    public static final String PROCESS_DEFINITION_PATH = "PROCESS_DEFINITION_PATH";
    public static final String PROCESS_DEFINITION = "processDefinition";
    public static final String PROCESS_MAIN_TYPE = "type";
    public static final String SERVICE_TAG_NAME = "service";
    public static final String PROCESS_CALL_TAG_NAME = "processCall";
    public static final String LOOP_TAG_NAME = "loop";
    public static final String SWITCH_TAG_NAME = "switch";
    public static final String CASE_TAG_NAME = "case";
    public static final String IF_TAG_NAME = "if";
    public static final String BARRIER_TAG_NAME = "barrier";
    public static final String RUNNABLE_TAG_NAME = "runnable";
    public static final String PROCESS_DEFINITION_TAG_NAME = "processDefinition";
    public static final String START_TAG_NAME = "start";
    public static final String END_TAG_NAME = "end";
    public static final List<Class> PROCESS_IMPORTS = new ArrayList<Class>();
    public static final List<Class> ASYNC_PROCESS_IMPORTS = new ArrayList<Class>();
    public static final List<Class> BASE_PROCESS_DEFINITION_INTERFACES = new ArrayList<Class>();
    public static final List<Class> ASYNC_PROCESS_DEFINITION_INTERFACES = new ArrayList<Class>();
    public static final Class BASE_PROCESS_DEFINITION_EXTENDS = AbstractBaseProcessInstance.class;
    public static final Class ASYNC_PROCESS_DEFINITION_EXTENDS = AbstractAsyncProcessInstance.class;
    public static final String RUNTIME_PROCESS_PACKAGE = "com.dcits.smartbip.runtime.impl";
    public static final String RUNTIME_PROCESS_PACKAGE_DIR = "com/dcits/smartbip/runtime/impl";
    public static final String DEFAULT_SRC = FileUtils.getJarPath() + File.separator + "src";

    public static final String REVERSAL = "reversal";

    public static final String CONTEXT_SCOPE = "context";
    public static final String LOCAL_SCOPE = "local";
    public static final String SERVICE_STACK = "serviceStack";

    static{
        BASE_PROCESS_DEFINITION_INTERFACES .add(IProcessInstance.class);
        ASYNC_PROCESS_DEFINITION_INTERFACES.add(IAsyncProcessInstance.class);
        PROCESS_IMPORTS.add(BaseProcessRepository.class);
        PROCESS_IMPORTS.add(ServiceRepository.class);
        PROCESS_IMPORTS.add(IProcessInstance.class);
        PROCESS_IMPORTS.add(IService.class);
        PROCESS_IMPORTS.add(List.class);
        PROCESS_IMPORTS.add(Log.class);
        PROCESS_IMPORTS.add(LogFactory.class);
        PROCESS_IMPORTS.add(IMapper.class);
        PROCESS_IMPORTS.add(MapperRepository.class);
        PROCESS_IMPORTS.add(InstanceNotFoundException.class);
        PROCESS_IMPORTS.add(Stack.class);
        PROCESS_IMPORTS.add(ArrayList.class);
        PROCESS_IMPORTS.add(BeanUtils.class);
        PROCESS_IMPORTS.add(IContext.class);
        PROCESS_IMPORTS.add(SessionContext.class);
        PROCESS_IMPORTS.add(CountDownLatch.class);
        PROCESS_IMPORTS.add(ProcessPool.class);
        PROCESS_IMPORTS.add(TimeUnit.class);
        PROCESS_IMPORTS.add(SoapCompositeData.class);
        PROCESS_IMPORTS.add(ICompositeData.class);
        PROCESS_IMPORTS.add(CompositeDataUtils.class);
        ASYNC_PROCESS_IMPORTS.addAll(PROCESS_IMPORTS);
        ASYNC_PROCESS_IMPORTS.add(IAsyncProcessInstance.class);
    }
}
