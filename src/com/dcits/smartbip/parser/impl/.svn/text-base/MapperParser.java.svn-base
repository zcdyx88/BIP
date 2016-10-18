package com.dcits.smartbip.parser.impl;

import com.dcits.smartbip.common.model.IConfiguration;
import com.dcits.smartbip.common.model.IParser;
import com.dcits.smartbip.exception.ClazzGenerateException;
import com.dcits.smartbip.exception.ConfigurationParseException;
import com.dcits.smartbip.exception.JavaCompilerException;
import com.dcits.smartbip.parser.code.JavaClazzCodeGenerater;
import com.dcits.smartbip.parser.code.JavaMethodCodeGenerater;
import com.dcits.smartbip.parser.model.MapperConstants;
import com.dcits.smartbip.parser.model.MapperDefinition;
import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.model.impl.SoapCompositeData;
import com.dcits.smartbip.utils.CompositeDataUtils;
import com.dcits.smartbip.utils.FileUtils;
import com.dcits.smartbip.utils.XMLUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vincentfxz on 16/5/18.
 * 数据映射解析器
 * 数据对象的映射必须加锁
 */
public class MapperParser implements IParser<MapperDefinition, List<MapperDefinition>> {

    private static final Log log = LogFactory.getLog(MapperParser.class);
    private int count = 0;
    private List<Integer> uniqueIndex;

    @Override
    public List<MapperDefinition> parse(IConfiguration config) throws ConfigurationParseException {
        String mapperConfigPath = (String) config.getConfig(MapperConstants.MAPPER_CONFIG_PATH);
        List<MapperDefinition> mapperDefinitions = new ArrayList<MapperDefinition>();
//        File file = FileUtils.getFileFromCL(mapperConfigPath);
        File file = FileUtils.getFromConfigs(mapperConfigPath);
        Document document = XMLUtils.getDocFromFile(file);
        Element element = document.getRootElement();
        List<Element> mapperElements = element.elements();
        for (Element mapperElement : mapperElements) {
            List<Element> mapperAttrs = mapperElement.elements();
            MapperDefinition mapperDefinition = new MapperDefinition();
            for (Element mapperAttr : mapperAttrs) {
                if ("id".equalsIgnoreCase(mapperAttr.getName())) {
                    String id = mapperAttr.getText();
                    mapperDefinition.setId(id);
                }
                if ("file".equalsIgnoreCase(mapperAttr.getName())) {
                    String filePath = mapperAttr.getText();
                    mapperDefinition.setFile(filePath);
                }
            }
            mapperDefinitions.add(mapperDefinition);
        }
        for (MapperDefinition mapperDefinition : mapperDefinitions) {
            parseMapperDetail(mapperDefinition);
        }
        return mapperDefinitions;
    }

    @Override
    public List<MapperDefinition> parse(MapperDefinition mapperDefinition) throws ConfigurationParseException {
        List<MapperDefinition> mapperDefinitions = new ArrayList<MapperDefinition>();
        mapperDefinitions.add(parseMapperDetail(mapperDefinition));
        return mapperDefinitions;
    }

    private MapperDefinition parseMapperDetail(MapperDefinition mapperDefinition) {
//        count = 0;
//        uniqueIndex = new ArrayList<Integer>();
        String mapperPath = mapperDefinition.getFile();
        File file = FileUtils.getFromConfigs(mapperPath);
        Document document = XMLUtils.getDocFromFile(file);
        Element rootElement = document.getRootElement();
        JavaClazzCodeGenerater javaClazzCodeGenerater = new JavaClazzCodeGenerater(mapperDefinition.getId(),
                MapperConstants.RUNTIME_PROCESS_PACKAGE,
                MapperConstants.MAPPER_EXTENDS,
                MapperConstants.MAPPER_IMPORTS,
                MapperConstants.MAPPER_INTERFACES);
        javaClazzCodeGenerater.addField("private Log log = LogFactory.getLog(getClass());\n");
        List<Element> elements = rootElement.elements();
        for (Element subElement : elements) {
            //解析生成请求的数据映射方法
            if (MapperConstants.MAPPER_REQUEST_NODE.equalsIgnoreCase(subElement.getName())) {
                count = 0;
                uniqueIndex = new ArrayList<Integer>();
                JavaMethodCodeGenerater methodCodeGenerater =
                        new JavaMethodCodeGenerater("public", "mapReq", null, null);
                methodCodeGenerater.appendBody("Object temp = null;\n");
                methodCodeGenerater.appendBody("ICompositeData destArrayParent = null;\n");
                methodCodeGenerater.appendBody("List<ICompositeData> destArray = null;\n");
                //add srcarray 重复定义 0718
                methodCodeGenerater.appendBody("List<ICompositeData> tmpArray = null;\n");
                methodCodeGenerater.appendBody("ICompositeData tmpArrayCompositeData = null;\n");
                
                
                List<Element> itemElements = subElement.elements();
                List<String> mappingObjs = new ArrayList<String>();
                List<String> destArrays = new ArrayList<String>();
                for (Element itemElement : itemElements) {
                    String itemCode = parseMapperItem(itemElement, mappingObjs,destArrays, false);

                    methodCodeGenerater.appendBody(itemCode);
                }
                mappingObjs = null;
                javaClazzCodeGenerater.addMethod(methodCodeGenerater);
            }
            //解析生成响应的数据映射方法
            else if (MapperConstants.MAPPER_RESPONSE_NODE.equalsIgnoreCase(subElement.getName())) {
                count = 0;
                uniqueIndex = new ArrayList<Integer>();
                JavaMethodCodeGenerater methodCodeGenerater =
                        new JavaMethodCodeGenerater("public", "mapRsp", null, null);
                methodCodeGenerater.appendBody("Object temp = null;\n");
                methodCodeGenerater.appendBody("ICompositeData destArrayParent = null;\n");
                methodCodeGenerater.appendBody("List<ICompositeData> destArray = null;\n");
                //add srcarray 重复定义 0718
                methodCodeGenerater.appendBody("List<ICompositeData> tmpArray = null;\n");
                methodCodeGenerater.appendBody("ICompositeData tmpArrayCompositeData = null;\n");
                
                List<Element> itemElements = subElement.elements();
                List<String> mappingObjs = new ArrayList<String>();
                List<String> destArrays = new ArrayList<String>();
                for (Element itemElement : itemElements) {
                    String itemCode = parseMapperItem(itemElement, mappingObjs,destArrays, false);
                    methodCodeGenerater.appendBody(itemCode);
                }
                mappingObjs = null;

                javaClazzCodeGenerater.addMethod(methodCodeGenerater);
            }
        }
        try {
            Class clazz = javaClazzCodeGenerater.install();
            mapperDefinition.setClazz(clazz);
        } catch (ClazzGenerateException e) {
            log.error(e, e);
        } catch (JavaCompilerException e) {
            log.error(e, e);
        } catch (IOException e) {
            log.error(e, e);
        }
        return mapperDefinition;
    }

    /**
     * 解析数据映射的Item
     *
     * @param itemElement
     * @param mappingObjs
     * @return
     */
    private String parseMapperItem(Element itemElement, List<String> mappingObjs,List<String> destArrays, boolean inside) {
        StringBuilder sb = new StringBuilder();
        Node srcNode = itemElement.selectSingleNode("@src");
        Node destNode = itemElement.selectSingleNode("@dest");
        String src = srcNode.getText();
        String dest = destNode.getText();

        //映射的源和目标都不能为空
        if (null != src && null != dest) {
            if (src.indexOf(".") > 0 && dest.indexOf(".") > 0) {
                String[] destItemTokens = dest.split("\\.");
                String[] srcItemTokens = src.split("\\.");
                if (2 == srcItemTokens.length && 2 == destItemTokens.length) {
                    String sourceObjName = srcItemTokens[0];
                    String targetObjName = destItemTokens[0];
                    if(!sourceObjName.equalsIgnoreCase("session") && !sourceObjName.equalsIgnoreCase("constant"))
                    {
                    	 if (!inside) {
                             if (!mappingObjs.contains(sourceObjName)) {
                                 sb.append("ICompositeData ").append(sourceObjName).append(" = ");
                                 sb.append("(ICompositeData)SessionContext.getContext().getValue(\"").append(sourceObjName).append("\"); \n");                                
                                 mappingObjs.add(sourceObjName);
                                 //当上下文中没有该对象时，新建该对象
                                 sb.append("if (null == ").append(sourceObjName).append(")\n");
                                 sb.append("{\n");
                                 sb.append(sourceObjName +"  = new SoapCompositeData();\n");
                                 sb.append(sourceObjName+".setId(\""+sourceObjName+"\");\n");
                                 sb.append(sourceObjName+".setxPath(\"/\"+"+sourceObjName+");\n");
                                 sb.append("SessionContext.getContext().setValue(\""+sourceObjName+"\","+sourceObjName+");\n");
                                 sb.append("}\n");
                             }
                         }
                    }
                    
                    if(!targetObjName.equalsIgnoreCase("session") && !targetObjName.equalsIgnoreCase("constant"))
                    {
                    	 if (!inside) {
                             if (!mappingObjs.contains(targetObjName)) {
                                 sb.append("ICompositeData ").append(targetObjName).append(" = ");
                                 sb.append("(ICompositeData)SessionContext.getContext().getValue(\"").append(targetObjName).append("\"); \n");
                                 mappingObjs.add(targetObjName);
                                 //当上下文中没有该对象时，新建该对象
                                 sb.append("if (null == ").append(targetObjName).append(")\n");
                                 sb.append("{\n");
                                 sb.append(targetObjName +"  = new SoapCompositeData();\n");
                                 sb.append(targetObjName+".setId(\""+targetObjName+"\");\n");
                                 sb.append(targetObjName+".setxPath(\"/\"+"+targetObjName+");\n");
                                 sb.append("SessionContext.getContext().setValue(\""+targetObjName+"\","+targetObjName+");\n");
                                 sb.append("}\n");
                             }
                         }
                    }       
               
                    if ("session".equalsIgnoreCase(sourceObjName)) {
                    	if("SeqNo".equalsIgnoreCase(srcItemTokens[1]))
                    	{
                    	   sb.append("CompositeDataUtils.setSeqNo("+ destItemTokens[0] + ", \"" + destItemTokens[1] + "\");\n");                    		
                    	}else if("IPPSeqNo".equalsIgnoreCase(srcItemTokens[1]))
                    	{
                     	   sb.append("CompositeDataUtils.setIPPSeqNo("+ destItemTokens[0] + ", \"" + destItemTokens[1] + "\");\n"); 
                     	}
                    	else if("DomainRef".equalsIgnoreCase(srcItemTokens[1]))
                    	{
                    	   sb.append("CompositeDataUtils.setDomainRef("+ destItemTokens[0] + ", \"" + destItemTokens[1] + "\");\n"); 
                    	}else if("Cnaps2SeqNo".equalsIgnoreCase(srcItemTokens[1])){
                    	   sb.append("CompositeDataUtils.setCnaps2SeqNo("+ destItemTokens[0] + ", \"" + destItemTokens[1] + "\");\n"); 
                    	}
                    	else if("BNK_NOTE_SEQ".equalsIgnoreCase(srcItemTokens[1]))
                    	{
                    	   sb.append("CompositeDataUtils.setEigRanNumber("+ destItemTokens[0] + ", \"" + destItemTokens[1] + "\");\n"); 
                    	}
                    	else if("constant".equalsIgnoreCase(targetObjName))
                    	{
                    		//不支持该该赋值
                    	}
                    	else
                    	{
                    	   sb.append("temp = SessionContext.getContext().getValue(\""+srcItemTokens[1]+"\");\n");	
                    	   sb.append("CompositeDataUtils.setValue(" + destItemTokens[0] + ", \"" + destItemTokens[1] + "\",(String)temp);\n");                             
                    	}
                    	return sb.toString();                    	
                    }else if("constant".equalsIgnoreCase(sourceObjName)){
                    	if("session".equalsIgnoreCase(targetObjName))
                    	{
                    		sb.append("SessionContext.getContext().setValue(\"" + destItemTokens[1] + "\",\""+srcItemTokens[1]+"\");\n");
                    	}else
                    	{
                    		sb.append("CompositeDataUtils.setValue(" + destItemTokens[0] + ", \"" + destItemTokens[1] + "\", \"" + srcItemTokens[1] + "\");\n");
                    	}
                       
                        return sb.toString();
                    }
                    else
                    {
                    	if("session".equalsIgnoreCase(targetObjName))
                    	{
                    		sb.append("temp = CompositeDataUtils.getValue(" + srcItemTokens[0] + ", \"" + srcItemTokens[1] + "\");\n");
                    		sb.append("SessionContext.getContext().setValue(\"" + destItemTokens[1] + "\",(String)temp);\n");
                    		return sb.toString();
                    	}
                    }

                    if (inside) {
                        sourceObjName = sourceObjName + count + ".get(i" + count + ")";
                        targetObjName = targetObjName + count + ".get(i" + count + ")";
                    }
                    sb.append("CompositeDataUtils.copy(" + sourceObjName + ", " + targetObjName + ", \"")
                            .append(srcItemTokens[1]).append("\", \"").append(destItemTokens[1]).append("\");\n");
                    List<Element> itemChildren = itemElement.elements();
                    if (null != itemChildren && itemChildren.size() > 0) {
                        int index = count;
                        count++;
                        if(!uniqueIndex.contains(count)){
                            sb.append("List<ICompositeData> srcArray" + count + "= new ArrayList<ICompositeData>();\n");
                            sb.append("List<ICompositeData> destArray" + count + "= new ArrayList<ICompositeData>();\n");
                            uniqueIndex.add(count);
                        }

                        sb.append("srcArray" + count + " = CompositeDataUtils.getByPath(")
                                .append(sourceObjName).append(", \"").append(srcItemTokens[1]).append("\");\n");
                        sb.append("destArray" + count + " = CompositeDataUtils.getByPath(")
                                .append(targetObjName).append(", \"").append(destItemTokens[1]).append("\");\n");
                        sb.append("for (int i" + count + " = 0; i" + count + " < srcArray" + count + ".size(); i" + count + "++) {\n");
                        for (Element itemChild : itemChildren) {
                            sb.append(parseMapperItem(itemChild, mappingObjs, destArrays,true)).append("\n");
                        }
                        sb.append("}\n");
                        count = index;
                    }
                }else if(3 == srcItemTokens.length && 2 == destItemTokens.length){//从数组内字段取值赋值给非数组外字段
                	 String sourceObjName = srcItemTokens[0];
                	 String sourceObjNameBody = srcItemTokens[1];
                	 String sourceObjNameBodyArrayName = srcItemTokens[2].split("\\/")[0];
                	 String sourceObjNameBodyArrayNodeName = srcItemTokens[2].split("\\/")[1];
                     String targetObjName = destItemTokens[0];
                     String targetObjNameNodeXpath = destItemTokens[1];
                     //modify  0718  处理数组映射到字段，重复定义srcArray
//                     sb.append("ICompositeData ").append(sourceObjName).append(" = ").append("(ICompositeData)SessionContext.getContext().getValue(\"").append(sourceObjName).append("\");\n");
//                     sb.append("List<ICompositeData> srcArray = CompositeDataUtils.getByPath(").append(sourceObjName).append(", \"").append(sourceObjNameBody).append("/").append(sourceObjNameBodyArrayName).append("\");\n");
//                     sb.append("CompositeDataUtils.copy(srcArray.get(0),").append(targetObjName).append(",\"").append(sourceObjNameBodyArrayNodeName).append("\",\"").append(targetObjNameNodeXpath).append("\");\n");
                     sb.append("tmpArrayCompositeData ").append(" = ").append("(ICompositeData)SessionContext.getContext().getValue(\"").append(sourceObjName).append("\");\n");
                     sb.append("tmpArray = CompositeDataUtils.getByPath(").append(sourceObjName).append(", \"").append(sourceObjNameBody).append("/").append(sourceObjNameBodyArrayName).append("\");\n");
                     //modify 0725 判断映射数组是否为空，空就跳过，不取第一个
                     sb.append("if(tmpArray.size()>0){\n");
                     sb.append("	CompositeDataUtils.copy(tmpArray.get(0),").append(targetObjName).append(",\"").append(sourceObjNameBodyArrayNodeName).append("\",\"").append(targetObjNameNodeXpath).append("\");\n");
                     sb.append("}\n");
                }else if(2 == srcItemTokens.length && 3 == destItemTokens.length){//从数组外取值赋值给数组内字段
             	 	String sourceObjName = srcItemTokens[0];
               	 	String sourceObjNameNodeXpath = srcItemTokens[1];
                    String targetObjName = destItemTokens[0];
                    String targetObjNameBody = destItemTokens[1];
                    String targetObjNameBodyArrayName = destItemTokens[2].split("\\/")[0];
                    String targetObjNameBodyArrayNodeName = destItemTokens[2].split("\\/")[1];
                    if(!sourceObjName.equalsIgnoreCase("session") && !sourceObjName.equalsIgnoreCase("constant"))
                    {
                    	 if (!inside) {
                             if (!mappingObjs.contains(sourceObjName)) {
                                 sb.append("ICompositeData ").append(sourceObjName).append(" = ");
                                 sb.append("(ICompositeData)SessionContext.getContext().getValue(\"").append(sourceObjName).append("\"); \n");                                
                                 mappingObjs.add(sourceObjName);
                                 //当上下文中没有该对象时，新建该对象
                                 sb.append("if (null == ").append(sourceObjName).append(")\n");
                                 sb.append("{\n");
                                 sb.append(sourceObjName +"  = new SoapCompositeData();\n");
                                 sb.append(sourceObjName+".setId(\""+sourceObjName+"\");\n");
                                 sb.append(sourceObjName+".setxPath(\"/\"+"+sourceObjName+");\n");
                                 sb.append("SessionContext.getContext().setValue(\""+sourceObjName+"\","+sourceObjName+");\n");
                                 sb.append("}\n");
                             }
                         }
                    }
                    
                    if(!targetObjName.equalsIgnoreCase("session") && !targetObjName.equalsIgnoreCase("constant"))
                    {
                    	 if (!inside) {
                             if (!mappingObjs.contains(targetObjName)) {
                                 sb.append("ICompositeData ").append(targetObjName).append(" = ");
                                 sb.append("(ICompositeData)SessionContext.getContext().getValue(\"").append(targetObjName).append("\"); \n");
                                 mappingObjs.add(targetObjName);
                                 //当上下文中没有该对象时，新建该对象
                                 sb.append("if (null == ").append(targetObjName).append(")\n");
                                 sb.append("{\n");
                                 sb.append(targetObjName +"  = new SoapCompositeData();\n");
                                 sb.append(targetObjName+".setId(\""+targetObjName+"\");\n");
                                 sb.append(targetObjName+".setxPath(\"/\"+"+targetObjName+");\n");
                                 sb.append("SessionContext.getContext().setValue(\""+targetObjName+"\","+targetObjName+");\n");
                                 sb.append("}\n");
                             }
                         }
                    }  
                    
                   // sb.append("ICompositeData ").append(targetObjName).append(" = ").append("(ICompositeData)getContext().getValue(\"").append(targetObjName).append("\");\n");                    
                    sb.append("destArrayParent = CompositeDataUtils.mkNodeNotExist(").append(targetObjName)
                    	.append(", \"").append(targetObjNameBody).append("\");\n");

                    //sb.append("CompositeDataUtils.mkNodeNotExist(").append(targetObjName).append(",").append(targetObjNameBody).append(");");	
                
                    String destArray = targetObjName+"."+targetObjNameBody+"/"+targetObjNameBodyArrayName;
                    if(!destArrays.contains(destArray))
                    {
                    	 sb.append("ICompositeData arrayiItem = new SoapCompositeData();\n");
                         sb.append("arrayiItem.setId(\""+targetObjNameBodyArrayName+"\");\n");
                         sb.append("destArrayParent.setChild(\""+targetObjNameBodyArrayName+"\", arrayiItem);\n");
                         destArrays.add(destArray);
                    }                  
                    sb.append("destArray = CompositeDataUtils.getByPath(").append(targetObjName).append(", \"").append(targetObjNameBody)
                       .append("/").append(targetObjNameBodyArrayName).append("\");\n");
                   
                    
                    if(sourceObjName.equalsIgnoreCase("session"))
                    {
                    	sb.append("temp = SessionContext.getContext().getValue(\""+srcItemTokens[1]+"\");\n");	
                    	sb.append("CompositeDataUtils.setValue(destArray.get(destArray.size()-1),\"")
                    	  .append(targetObjNameBodyArrayNodeName).append("\",(String)temp);\n");               	
                    }else if (sourceObjName.equalsIgnoreCase("constant"))                    	
                    {
                    	sb.append("CompositeDataUtils.setValue(destArray.get(destArray.size()-1),\"")
                    	  .append(targetObjNameBodyArrayNodeName).append("\",\"").append(srcItemTokens[1]).append("\");\n");  
                    }
                    else
                    {
                    	sb.append("CompositeDataUtils.copy(").append(sourceObjName).append(",destArray.get(destArray.size()-1),\"").append(sourceObjNameNodeXpath).append("\",\"").append(targetObjNameBodyArrayNodeName).append("\");\n"); 
                    }       
                  
                }else{
                	log.error("数据映射解析不支持该形式:src["+src+"],dest["+dest+"]");
                }
            }
        } else {
            log.error("数据映射必须有来源和目标");
        }
        return sb.toString();
    }


}
