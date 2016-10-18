package com.dcits.smartbip.parser.model;

import com.dcits.smartbip.common.model.IReloadAbleDefinition;
import com.dcits.smartbip.parser.code.JavaClazzCodeGenerater;

/**
 * Created by vincentfxz on 16/5/18.
 */
public class MapperDefinition implements IReloadAbleDefinition {
    private String id;
    private String file;
    private Class mapperClazz;
    private long lastModify;


    public MapperDefinition() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Override
    public long getLastModify() {
        return this.lastModify;
    }

    @Override
    public void setLastModify(long lastModify) {
        this.lastModify = lastModify;
    }

    @Override
    public JavaClazzCodeGenerater getCodeGenerater() {
        return null;
    }

    @Override
    public void setCodeGenerater(JavaClazzCodeGenerater codeGenerater) {

    }

    @Override
    public Class getClazz() {
        return this.mapperClazz;
    }

    @Override
    public void setClazz(Class processClazz) {
        this.mapperClazz = processClazz;
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public void setType(String type) {

    }
}
