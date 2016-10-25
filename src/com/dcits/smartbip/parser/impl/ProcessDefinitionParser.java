package com.dcits.smartbip.parser.impl;

import com.dcits.smartbip.common.model.IConfiguration;
import com.dcits.smartbip.common.model.IParser;
import com.dcits.smartbip.exception.ConfigurationParseException;
import com.dcits.smartbip.parser.DataDomainConstants;
import com.dcits.smartbip.parser.code.JavaClazzCodeGenerater;
import com.dcits.smartbip.parser.code.JavaMethodCodeGenerater;
import com.dcits.smartbip.parser.model.ProcessDefinition;
import com.dcits.smartbip.parser.model.ProcessDefinitionConstants;
import com.dcits.smartbip.runtime.model.IContext;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import com.dcits.smartbip.utils.FileUtils;
import com.dcits.smartbip.utils.StringUtils;
import com.dcits.smartbip.utils.XMLUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by vincentfxz on 16/4/s18.
 * 流程配置解析器
 */
public class ProcessDefinitionParser implements IParser<ProcessDefinition, List<ProcessDefinition>> {

    private static final Log log = LogFactory.getLog(ProcessDefinitionParser.class);
    private ProcessDefinition processDefinition;
    private int count = 0;

    /**
     * 解析流程配置文件,返回流程定义
     *
     * @param config
     * @return
     */
    @Override
    public List<ProcessDefinition> parse(IConfiguration config) throws ConfigurationParseException {
        List<ProcessDefinition> processDefinitions = new ArrayList<ProcessDefinition>();
        File file = getConfigFile(config);
        Document document = XMLUtils.getDocFromFile(file);
        Element rootElement = document.getRootElement();
        List<Element> processElements = rootElement.elements();
        for (Element processElement : processElements) {
            processDefinition = new ProcessDefinition();
            List<Element> processAttrs = processElement.elements();
            String id = null;
            String processFilePath = null;
            for (Element processAttr : processAttrs) {
                String tagName = processAttr.getName();
                if ("id".equalsIgnoreCase(tagName))
                    id = processAttr.getText();
                if ("file".equalsIgnoreCase(tagName))
                    processFilePath = processAttr.getText();
            }
            if (null != id && null != processFilePath) {
                processDefinition.setId(id);
                processDefinition.setFile(processFilePath);
                parse(processDefinition);
                processDefinitions.add(processDefinition);
            }
        }
        return processDefinitions;
    }

    /**
     * 获取服务的配置文件
     *
     * @param config
     * @return
     */
    private File getConfigFile(IConfiguration config) {
        String processDefPath = (String) config.getConfig(ProcessDefinitionConstants.PROCESS_DEFINITION_PATH);
        return FileUtils.getFromConfigs(processDefPath);
    }

    /**
     * 解析流程的详细配置,流程的详细设置里面可能包含了流程的字流程
     *
     * @param processDefinition
     * @return
     */
    public List<ProcessDefinition> parse(ProcessDefinition processDefinition) {
        List<ProcessDefinition> processDefinitions;
        File file = FileUtils.getFromConfigs(processDefinition.getFile());
        long lastModify = file.lastModified();
        processDefinition.setLastModify(lastModify);
        Document document = XMLUtils.getDocFromFile(file);
        Element rootElement = document.getRootElement();
        List<Element> subElements = rootElement.elements();
        //解析流程模版,返回流程类和子流程类
        processDefinitions = parseProcessNodes(subElements);
        try {
            for (ProcessDefinition targetProcessDefinition : processDefinitions) {
                JavaClazzCodeGenerater generater = targetProcessDefinition.getCodeGenerater();
                generater.addField("private Log log = LogFactory.getLog(getClass());\n");
                Class clazz = generater.install();
                processDefinition.setClazz(clazz);
            }
        } catch (Exception e) {
            log.error(e, e);
        }

        return processDefinitions;
    }

    /**
     * 处理流程节点们
     *
     * @param elements
     * @return
     */
    private List<ProcessDefinition> parseProcessNodes(List<Element> elements) {
        List<ProcessDefinition> processDefinitions = new ArrayList<ProcessDefinition>();
        for (Element element : elements) {
            String name = element.getName();
            if (ProcessDefinitionConstants.PROCESS_DEFINITION_TAG_NAME.equalsIgnoreCase(name)) {
                ProcessDefinition processDefinition = new ProcessDefinition();
                String id = XMLUtils.getAttribute(element, "id");
                processDefinition.setId(id);
                JavaClazzCodeGenerater javaClazzCodeGeneraters = parseProcessNode(element);
                processDefinition.setCodeGenerater(javaClazzCodeGeneraters);
                processDefinitions.add(processDefinition);
            }
        }
        return processDefinitions;
    }

    /**
     * 解析流程节点
     *
     * @param element
     * @return
     */
    private JavaClazzCodeGenerater parseProcessNode(Element element) {
        JavaClazzCodeGenerater javaClazzCodeGenerater = null;
        //流成类,一个流程包含他自身的流程类和子流程类
        String name = element.getName();
        String id = XMLUtils.getAttribute(element, "id");
        String threadpool = XMLUtils.getAttribute(element, "threadpool");
        String mapping = XMLUtils.getAttribute(element, "mapping");

        if (!ProcessDefinitionConstants.PROCESS_DEFINITION_TAG_NAME.equalsIgnoreCase(name)) {
            //如果不是流程定义节点则跳过
            if (log.isWarnEnabled()) {
                log.warn("该节点为[" + name + "]节点,此处应该出现[" +
                        ProcessDefinitionConstants.PROCESS_DEFINITION_TAG_NAME + "]节点");
            }
            return null;
        }
        //是否异步流程标志
        String isAsync = XMLUtils.getAttribute(element, "isAsync");
        //如果是异步流程或者是主流程,需要实现异步流程接口,集成异步流程的抽象类
        if (null != isAsync && ("true".equalsIgnoreCase(isAsync))) {
            javaClazzCodeGenerater = new JavaClazzCodeGenerater(id,
                    ProcessDefinitionConstants.RUNTIME_PROCESS_PACKAGE,
                    ProcessDefinitionConstants.ASYNC_PROCESS_DEFINITION_EXTENDS,
                    ProcessDefinitionConstants.ASYNC_PROCESS_IMPORTS,
                    ProcessDefinitionConstants.ASYNC_PROCESS_DEFINITION_INTERFACES);
            if (null != threadpool) {
                addThreadPool(javaClazzCodeGenerater, Integer.parseInt(threadpool));
            }
        } else {
            //非异步流程,实现非异步流程接口,继承基础流程抽象类
            javaClazzCodeGenerater = new JavaClazzCodeGenerater(id,
                    ProcessDefinitionConstants.RUNTIME_PROCESS_PACKAGE,
                    ProcessDefinitionConstants.BASE_PROCESS_DEFINITION_EXTENDS,
                    ProcessDefinitionConstants.PROCESS_IMPORTS,
                    ProcessDefinitionConstants.BASE_PROCESS_DEFINITION_INTERFACES);
        }
        //实现流程接口中的执行方法
        JavaMethodCodeGenerater methodCodeGenerater = new JavaMethodCodeGenerater("public", "execute", null, Object.class);
        methodCodeGenerater.appendBody("init();\n");
        if (null != mapping) {
            methodCodeGenerater.appendBody(" IMapper mapper = MapperRepository.getRepository().get(\"" + mapping + "\");\n");
            methodCodeGenerater.appendBody(" mapper.mapReq();\n");
        }
        List<Element> processSubElements = element.elements();
        for (Element processSubElement : processSubElements) {
            String subName = processSubElement.getName();
            //添加对流程子节点的逻辑处理
            methodCodeGenerater.appendBody(parseLogicNode(processSubElement));
        }
        methodCodeGenerater.appendBody("ICompositeData rspObj = (ICompositeData)getContext().getValue(\"Rsp"+processDefinition.getId()+"\");");
        methodCodeGenerater.appendBody("if( null == rspObj){");        
        methodCodeGenerater.appendBody("rspObj = new SoapCompositeData();");
        methodCodeGenerater.appendBody("rspObj.setId(\"Rsp" + processDefinition.getId() + "\");");
        methodCodeGenerater.appendBody("rspObj.setxPath(\"/Rsp" + processDefinition.getId() + "\");");
        methodCodeGenerater.appendBody("getContext().setValue(\"Rsp" + processDefinition.getId() + "\", rspObj);");
        methodCodeGenerater.appendBody("}\n");        
        if (null != mapping) {
            methodCodeGenerater.appendBody(" mapper.mapRsp();\n");
        }
        //添加方法返回
        methodCodeGenerater.setReturnItem("return getContext();\n");
        javaClazzCodeGenerater.addMethod(methodCodeGenerater);
        return javaClazzCodeGenerater;
    }

    private void addThreadPool(JavaClazzCodeGenerater javaClazzCodeGenerater, int poolsize) {
        List<String> fields = new ArrayList<String>();
        fields.add("private ExecutorService threadpool = Executors.newFixedThreadPool(10);");
        javaClazzCodeGenerater.setFields(fields);
        JavaMethodCodeGenerater methodCodeGenerater = new JavaMethodCodeGenerater("public", "getThreadPool", null, ExecutorService.class);
        methodCodeGenerater.setReturnItem("re");
        javaClazzCodeGenerater.addImportClazz(ExecutorService.class);
        javaClazzCodeGenerater.addMethod(methodCodeGenerater);
    }

    /**
     * 解析实际执行的节点,loop switch, case, processCall, serviceCall等
     *
     * @param element
     * @return
     */
    public String parseLogicNode(Element element) {
        StringBuilder sb = new StringBuilder("");
        String name = element.getName();
        if (ProcessDefinitionConstants.LOOP_TAG_NAME.equalsIgnoreCase(name)) {
            sb.append(parseLoopNode(element));
        } else if (ProcessDefinitionConstants.SWITCH_TAG_NAME.equalsIgnoreCase(name)) {
            sb.append(parseSwitchNode(element));
        } else if (ProcessDefinitionConstants.CASE_TAG_NAME.equalsIgnoreCase(name)) {
            sb.append(parseCaseNode(element));
        } else if (ProcessDefinitionConstants.PROCESS_CALL_TAG_NAME.equalsIgnoreCase(name)) {
            sb.append(parseProcessCall(element));
        } else if (ProcessDefinitionConstants.SERVICE_TAG_NAME.equalsIgnoreCase(name)) {
            sb.append(parseServiceCall(element));
        } else if (ProcessDefinitionConstants.BARRIER_TAG_NAME.equalsIgnoreCase(name)) {
            sb.append(parseBarrierNode(element));
        } else if (ProcessDefinitionConstants.RUNNABLE_TAG_NAME.equalsIgnoreCase(name)) {
            sb.append(parseRunnableNode(element));
        }else if (ProcessDefinitionConstants.REVERSAL_TAG_NAME.equalsIgnoreCase(name)) {
            sb.append(parseReversalCall(element));
        }
        return sb.toString();
    }

    public String parseRunnableNode(Element element) {
        StringBuilder sb = new StringBuilder();
        String countDown = XMLUtils.getAttribute(element, "countDown");
        String id = XMLUtils.getAttribute(element, "id");
        sb.append("final IContext ").append(id).append("Context = getContext();\n");
        sb.append("Runnable ").append(id).append("Runnable = new Runnable(){\n");
        sb.append("private SessionContext context = (SessionContext)").append(id).append("Context;\n");
        sb.append("@Override\n");
        sb.append("public void run() {\n");
        sb.append("try{\n");
        sb.append("SessionContext.getContext().setContext(context);\n");
        List<Element> subElements = element.elements();
        for (Element subElement : subElements) {
            sb.append(parseLogicNode(subElement));
        }
        sb.append("}catch(Exception e){\n");
        sb.append("log.error(e,e);\n");
        sb.append(" }finally{\n");
        if (null != countDown) {
            sb.append(countDown).append("CountDownLatch.countDown();\n");
        }
        sb.append(" }\n");
        sb.append(" }\n");
        sb.append(" };\n");
        sb.append("ProcessPool.getInstance().dispatch(").append(id).append("Runnable);\n");
        return sb.toString();
    }

    public String parseBarrierNode(Element element) {
        StringBuilder sb = new StringBuilder();
        String id = XMLUtils.getAttribute(element, "id");
        String countDown = XMLUtils.getAttribute(element, "countDown");
        String timeOut = XMLUtils.getAttribute(element, "timeout");
        if (null != id && null != countDown && null != timeOut
                && StringUtils.isNumeric(timeOut)) {
        	countDown = countDown.trim();
        	if(countDown.startsWith("context")){
        		if(countDown.endsWith(".size()")){
        			countDown = StringUtils.substringBetween("context.", ".size()");
        			countDown = "((List)getContext().getValue("+countDown+")).size()";
        		}else{
        			countDown = StringUtils.substringAfter(countDown, "context.");
        			countDown = "getContext().getValue("+countDown+"))";
        		}
        	}
            sb.append("final IContext mainContext = getContext();\n");
            sb.append("final CountDownLatch ").append(id).append("CountDownLatch = new CountDownLatch(").append(countDown).append(");");
            List<Element> subElements = element.elements();
            for (Element subElement : subElements) {
                sb.append(parseLogicNode(subElement));
            }
            sb.append(id).append("CountDownLatch.await(").append(timeOut).append(", TimeUnit.MILLISECONDS);\n");
        }
        return sb.toString();
    }
    
    private String parseSizeAttr(String sizeAttr){
    	
    	return sizeAttr;
    }

    public String parseLoopNode(Element element) {
        StringBuilder sb = new StringBuilder();
        String size = XMLUtils.getAttribute(element, "size");
        if (null != size && StringUtils.isNumeric(size)) {
            sb.append("for(int i=0; i < " + size + "; i++ ){\n");
            List<Element> subElements = element.elements();
            for (Element subElement : subElements) {
                sb.append(parseLogicNode(subElement));
            }
            sb.append("}\n");
        } else {
            String setAttr = XMLUtils.getAttribute(element, "set");
            String keyAttr = XMLUtils.getAttribute(element, "key");
            if (setAttr.indexOf(":") > 0) {
                String[] setAttrs = setAttr.split(":");
                if (2 == setAttrs.length) {
                    String scope = setAttrs[0].trim();
                    String setName = setAttrs[1].trim();
                    if (ProcessDefinitionConstants.CONTEXT_SCOPE.equalsIgnoreCase(scope)) {
                        sb.append("List<String> " + setName + " =  (List<String>)getContext().getValue(\"" + setName + "\");");
                    }
                    sb.append("if(null != "+setName+"){\n");
                    sb.append("for(String " + keyAttr + " : " + setName + "){\n");
                    sb.append("SessionContext.getContext().setValue(\""+keyAttr+"\","+keyAttr+");\n");
                    List<Element> subElements = element.elements();
                    for (Element subElement : subElements) {
                        sb.append(parseLogicNode(subElement));
                    }
                    sb.append("}\n");
                    sb.append("}else{\n");
                    sb.append("log.error(\""+setName+" is null,please check it!\");\n");
                    sb.append("}\n");
                }
            }
        }

        return sb.toString();

    }


    public String parseSwitchNode(Element element) {
        StringBuilder sb = new StringBuilder();
        if(count==0){
        	sb.append("for(int i =0; i < 1; i ++){\n");
        }else{
        	String i = "i"+count+"";
        	sb.append("for(int ");
        	sb.append(i);
        	sb.append(" =0; ");
        	sb.append(i);
        	sb.append(" < 1; ");
        	sb.append(i);
        	sb.append(" ++){\n");
        }
        List<Element> subElements = element.elements();
        for (Element subElement : subElements) {
        	count++;
            sb.append(parseLogicNode(subElement));
        }
        sb.append("}\n");
        return sb.toString();
    }

    public String parseCaseNode(Element element) {
        StringBuilder sb = new StringBuilder();
        String key = XMLUtils.getAttribute(element, "key");
//        String value = XMLUtils.getAttribute(element, "value");
        //对响应码trim处理，但是会出现没有case节点没有value属性的情况
        String value = XMLUtils.getAttribute(element, "value") == null?"":XMLUtils.getAttribute(element, "value").trim();
        String isDefault = XMLUtils.getAttribute(element, "isDefault");
        if("true".equalsIgnoreCase(isDefault)){
            List<Element> subElements = element.elements();
            for (Element subElement : subElements) {
                sb.append(parseLogicNode(subElement));
            }
            sb.append("break;");
        }else{
        	String tmpField = getField(key);
        	if(value != null && value.indexOf(",") > -1){
        		sb.append("String tmpValue"+count+" = \""+value+"\";\n");
        		sb.append("List<String> tmpArr"+count+" = java.util.Arrays.asList(tmpValue"+count+".split(\",\"));\n");
        		sb.append("if(tmpArr"+count+" != null && tmpArr"+count+".contains(" + tmpField + ")){\n");
        		List<Element> subElements = element.elements();
        		for (Element subElement : subElements) {
        			sb.append(parseLogicNode(subElement));
        		}
        		sb.append("break;");
        		sb.append("}\n");
        		count++;
        	}else{
        		sb.append("if(\"" + value + "\".equalsIgnoreCase(" + tmpField + ")) {\n");
                List<Element> subElements = element.elements();
                for (Element subElement : subElements) {
                    sb.append(parseLogicNode(subElement));
                }
                sb.append("break;");
                sb.append("}\n");
        	}
        }
        return sb.toString();
    }

    public String getField(String field) {
        StringBuilder codeStatement = new StringBuilder();
        if (null != field) {
            if (field.indexOf(".") > 0) {
                String[] fieldItems = field.split("\\.");
                if (fieldItems.length == 2) {
                    if (DataDomainConstants.CONTEXT_DOMAIN.equalsIgnoreCase(fieldItems[0])) {
                        codeStatement.append("(String)getContext().getValue(\"");
                        codeStatement.append(fieldItems[1]);
                        codeStatement.append("\")");
                    }else if(DataDomainConstants.SESSION_DOMAIN.equalsIgnoreCase(fieldItems[0])){
                        codeStatement.append("(String)getContext().getValue(\"");
                        codeStatement.append(fieldItems[1]);
                        codeStatement.append("\")");
                    } else {
                        codeStatement.append("CompositeDataUtils.getValue((ICompositeData)getContext().getValue(\"");
                        codeStatement.append(fieldItems[0]);
                        codeStatement.append("\"), \"");
                        codeStatement.append(fieldItems[1]);
                        codeStatement.append("\")");
                    }
                } else {
                    log.error("数据区定义[" + field + "]格式错误!");
                }
            } else {
                codeStatement.append(field);
            }
        } else {
            log.error("数据区标志不运行为空!");
        }
        return codeStatement.toString();
    }

    public String parseProcessCall(Element element) {
        String isAsyncStr = XMLUtils.getAttribute(element, "isAsync");
        boolean isAsync = "true".equalsIgnoreCase(isAsyncStr);
        StringBuilder sb = new StringBuilder();
        if (null != element) {
            String target = XMLUtils.getAttribute(element, "target");
            if (isAsync) {
                sb.append("IAsyncProcessInstance " + target + " = BaseProcessRepository.getRepository().getAsyncProcess(\"" + target + "\");\n");
                sb.append("getThreadPool().execute(" + target + ");");
            } else {
                sb.append("IProcessInstance " + target + " = BaseProcessRepository.getRepository().get(\"" + target + "\");\n");
                sb.append(target + ".execute();\n");
            }
        }
        return sb.toString();
    }

    /**
     * 服务调用,分为:
     * 1.获取请求对象
     * 2.调用服务
     * 3.获取返回对象,放入上下文中的服务数据区
     * 4.服务数据区与流程公共数据区进行数据映射
     *
     * @param element
     * @return
     */
    public String parseServiceCall(Element element) {
        StringBuilder sb = new StringBuilder();
        if (null != element) {
            String reversalMapping = null;
            String retCode = null;
            String serviceId = XMLUtils.getAttribute(element, "id");
            String reversalServiceId = XMLUtils.getAttribute(element, "reversal");
            List<String> reversalCodes = new ArrayList<String>();
            Element reversalElement = element.element(ProcessDefinitionConstants.REVERSAL);
            if (null != reversalElement) {
            	  //先获取reversalMapping再赋值
//                sb.append("serviceMappings.put(\"" + reversalServiceId + "\", \"" + reversalMapping + "\");\n");
                List<Element> reveralAttrs = reversalElement.elements();
                for (Element reversalAttr : reveralAttrs) {
                    if ("reversalMapping".equalsIgnoreCase(reversalAttr.getName())) {
                        reversalMapping = reversalAttr.getText();
                    }
                    if ("retErrorValue".equalsIgnoreCase(reversalAttr.getName())) {
                        reversalCodes = parseReversalCodes(reversalAttr);
                    }
                    if ("retErrorCode".equalsIgnoreCase(reversalAttr.getName())) {
                        retCode = reversalAttr.getText();
                    }
                }
                sb.append("serviceMappings.put(\"" + reversalServiceId + "\", \"" + reversalMapping + "\");\n");
            }
            String mappingId = XMLUtils.getAttribute(element, "mapping");
            sb.append("serviceMappings.put(\"" + serviceId + "\", \"" + mappingId + "\");\n");
            
            //adc reversal
            if (null != reversalServiceId) {
            	//20160622 switch中存在多个service,service变量声明重复导致编译不过 add by zhou.kan
            /*  sb.append("IService service = ServiceRepository.getRepository().get(\"" + serviceId +"\");\n");
            	sb.append("service.setReversalServiceId(\"" + reversalServiceId +"\");\n");
            */
            	sb.append("IService service").append(serviceId).append(" = ServiceRepository.getRepository().get(\"" + serviceId +"\");\n");
            	sb.append("service").append(serviceId).append(".setReversalServiceId(\"" + reversalServiceId +"\");\n");
            }
                
            String persist = XMLUtils.getAttribute(element, "persist");
            if ("true".equalsIgnoreCase(persist)) {
                sb.append("callService(\"" + serviceId + "\", true);\n");
            } else {
                sb.append("callService(\"" + serviceId + "\", false);\n");
            }
            if (null != reversalServiceId) {
                
                sb.append("List<String> retCodeValues" + reversalServiceId + "= new ArrayList<String>();\n");
                for (String reveralCode : reversalCodes) {
                    sb.append("retCodeValues").append(reversalServiceId).append(".add(\"").append(reveralCode).append("\");\n");
                }
                //20160617 传reversalcode List
//                sb.append("if(checkReversal(\"" + reversalServiceId + "\", \"" + retCode + "\", new ArrayList<String>())){\n");
                sb.append("if(checkReversal(\"" + serviceId + "\", \"" + retCode + "\", retCodeValues" + reversalServiceId +")){\n");
                sb.append("callReversal();\n");
                sb.append("}\n");
            }
        }
        return sb.toString();
    }

    private List<String> parseReversalCodes(Element reversalCodesElement) {
        List<String> reversalCodes = new ArrayList<String>();
        if (null != reversalCodesElement) {
            List<Element> reversalCodeElements = reversalCodesElement.elements();
            for (Element reversalCodeElement : reversalCodeElements) {
                reversalCodes.add(reversalCodeElement.getText().trim()); //添加trim
            }
        }
        return reversalCodes;
    }
    /**
     *  记录冲正信息
     * @param element
     * @return
     */
    public String parseReversalCall(Element element) {
        StringBuilder sb = new StringBuilder();
        if (null != element) {
            String reversalServiceId = XMLUtils.getAttribute(element, "id");
            String mappingId = XMLUtils.getAttribute(element, "mapping");
            String returnCodeField = XMLUtils.getAttribute(element, "returncodefield");
            String returnCode = XMLUtils.getAttribute(element, "returncode");
            sb.append("ReversalService br = new ReversalService();\n");            
            sb.append("IContext context = SessionContext.getContext();\n");            
            sb.append("br.insertReversalInfo(\"\",\""+ reversalServiceId +"\",context,\""+mappingId+"\",\""+returnCodeField+"\",\""+returnCode+"\");\n");
        }
        return sb.toString();
    }
}
