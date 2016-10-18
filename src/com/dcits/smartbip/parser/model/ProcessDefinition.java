package com.dcits.smartbip.parser.model;

import com.dcits.smartbip.common.model.*;
import com.dcits.smartbip.parser.code.JavaClazzCodeGenerater;

/**
 * Created by vincentfxz on 16/4/23.
 * 流程定义类,流程定义对象必须具备,可持久化,可注册的特征
 * 流程定义对象包含:
 * 流程ID: id,
 * 流程持久化状态 : persistentState
 * 流程的类型: main(作为流程入口的主流程), sync(一个同步流程,和父流程串行执行), async(一个并行流程,与主流程以及其他兄弟流程间为并行执行)
 * 流程的实现类: processClazz(实现流程的类,该类由流程解析器解析流程配置文件后生成)
 */
public class ProcessDefinition implements IReloadAbleDefinition{

    private String id;
    private String file;
    private String type;
    private Class processClazz;
    private long lastModify;
    private JavaClazzCodeGenerater codeGenerater;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JavaClazzCodeGenerater getCodeGenerater() {
        return codeGenerater;
    }

    public void setCodeGenerater(JavaClazzCodeGenerater codeGenerater) {
        this.codeGenerater = codeGenerater;
    }

    public Class getClazz() {
        return processClazz;
    }

    public void setClazz(Class processClazz) {
        this.processClazz = processClazz;
    }

    public long getLastModify() {
        return lastModify;
    }

    public void setLastModify(long lastModify) {
        this.lastModify = lastModify;
    }
}
