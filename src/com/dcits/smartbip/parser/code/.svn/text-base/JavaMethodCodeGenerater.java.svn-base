package com.dcits.smartbip.parser.code;

import com.dcits.smartbip.exception.ClazzGenerateException;
import com.dcits.smartbip.exception.ConfigurationParseException;
import com.dcits.smartbip.runtime.model.impl.SessionContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * Created by vincentfxz on 16/4/24.
 * 方法生成器
 */
public class JavaMethodCodeGenerater {

    private static final Log log = LogFactory.getLog(JavaMethodCodeGenerater.class);

    private String modifier;
    private String methodName;
    private List<Class> parameterTypes;
    private Class returnType;
    private String body = "";
    private String returnItem = "";

    public JavaMethodCodeGenerater(String modifier, String methodName, List<Class> parameterTypes, Class returnType) {
        this.modifier = modifier;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
    }

    /**
     * 生成方法体
     *
     * @return
     * @throws ConfigurationParseException
     */
    public String generate() throws ClazzGenerateException {
        StringBuilder sb = new StringBuilder();
        //add modifier
        String modifier = getModifier();
        if (null != modifier) {
            sb.append(modifier).append(" ");
        } else {
            throw new ClazzGenerateException("解析方法失败,modifier为[" + modifier + "]");
        }
        //add return type
        Class returnType = getReturnType();
        if (null != returnType) {
            sb.append(returnType.getName()).append(" ");
        } else {
            sb.append("void ");
        }
        //add function name
        String methodName = getMethodName();
        if (null != methodName) {
            sb.append(methodName).append(" ");
        } else {
            throw new ClazzGenerateException("解析方法失败,modifier为[" + modifier + "]");
        }
        //add paramemter
        sb.append("(");
        List<Class> parameterTypes = getParameterTypes();
        if (null != parameterTypes) {
            for (int i = 0; i < parameterTypes.size(); i++) {
                sb.append(parameterTypes.get(i).getName()).append(" arg").append(i);
                if (i != parameterTypes.size() - 1) {
                    sb.append(",");
                }
            }
        }
        sb.append(")");
        sb.append("{\n");
        //add method body
        sb.append("try{ \n");
        sb.append(getBody());
        sb.append("}catch(Exception e){ \n");
        sb.append("log.error(e,e);\n");
        sb.append(exceptionHook());
        sb.append("}finally{\n");
        sb.append("}\n");
        if (null != returnType) {
            if(null != getReturnItem()){
                sb.append(getReturnItem());
            }
        }
        //end of method
        sb.append("}\n");
        return sb.toString();
    }

    public String exceptionHook() {
        StringBuilder sb = new StringBuilder();
        sb.append("SessionContext.getContext().setValue(\"FLOW-CODE\", \"F\");\n");
        return sb.toString();
    }

    public String getBody() {
        return body;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<Class> getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(List<Class> parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Class getReturnType() {
        return returnType;
    }

    public void setReturnType(Class returnType) {
        this.returnType = returnType;
    }


    public void setBody(String body) {
        this.body = body;
    }

    public void appendBody(String body) {
        this.body = this.body + "\n" + body;
    }

    public String getReturnItem() {
        return returnItem;
    }

    public void setReturnItem(String returnItem) {
        this.returnItem = returnItem;
    }
}
