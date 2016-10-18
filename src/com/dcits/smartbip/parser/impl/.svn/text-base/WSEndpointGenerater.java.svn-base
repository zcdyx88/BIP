package com.dcits.smartbip.parser.impl;

import com.dcits.smartbip.common.model.IGenerater;
import com.dcits.smartbip.exception.JavaCompilerException;
import com.dcits.smartbip.parser.code.JavaCompiler;
import com.dcits.smartbip.parser.model.ProcessDefinitionConstants;
import com.dcits.smartbip.runtime.model.IService;
import com.dcits.smartbip.utils.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by vincentfxz on 16/5/22.
 */
public class WSEndpointGenerater implements IGenerater<Class, IService> {

    private static final Log log = LogFactory.getLog(WSEndpointGenerater.class);
    private static final String WS_ENDPOINT_TEMPLATE = "template/WSContainer.template";

    @Override
    public Class generate(IService service) {

        Class containerClazz = null;
        try {
            String serviceAndOperationId = service.getId();
            String serviceId = serviceAndOperationId.substring(0, 11);
            String operationId = serviceAndOperationId.substring(11);
            File file = FileUtils.getFileFromCL(WS_ENDPOINT_TEMPLATE);
            String fileTemplate = FileUtils.readFileToString(file);
            fileTemplate = fileTemplate.replaceAll("#serviceId#", serviceId);
            fileTemplate = fileTemplate.replaceAll("#operationId#", operationId);
            File targetFile = new File(ProcessDefinitionConstants.DEFAULT_SRC + File.separator + getConainerName(serviceAndOperationId) + ".java");
            FileUtils.write(targetFile, fileTemplate);
            JavaCompiler.addClassPath(System.getProperty("smartbip.install") + File.separator +"lib", false);
            JavaCompiler.compile(getConainerName(serviceAndOperationId), "com.dcits.smartbip.protocol.ws.endpoint", ProcessDefinitionConstants.DEFAULT_SRC);
            containerClazz = JavaCompiler.loadClass("com.dcits.smartbip.protocol.ws.endpoint." + getConainerName(serviceAndOperationId));
        } catch (IOException e) {
            log.error(e, e);
        } catch (JavaCompilerException e) {
            log.error(e, e);
        }
        return containerClazz;
    }

    String getConainerName(String serviceId) {
        return "S" + serviceId + "Container";
    }

}
