package com.dcits.smartbip.runtime.model.impl;

import com.dcits.smartbip.runtime.model.IMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Field;

/**
 * Created by vincentfxz on 16/5/18.
 */
public abstract class SimpleReflectObjectMapper<SO,TO> implements IMapper<SO,TO>{

    private static final Log log = LogFactory.getLog(SimpleReflectObjectMapper.class);

    private SO sourceObject;
    private TO targetObject;


    public SimpleReflectObjectMapper(SO sourceObject, TO targetObject) {
        this.sourceObject = sourceObject;
        this.targetObject = targetObject;
    }

    public void mapping(String srcField, String targetField, String type) {
        try {
            Field field = sourceObject.getClass().getField(srcField);
            Object object = field.get(this.sourceObject);
            if("String".equalsIgnoreCase(type)){

            }

        } catch (NoSuchFieldException e) {
            log.error("在对象["+sourceObject.getClass()+"]中找不到域["+srcField+"]",e );
        } catch (IllegalAccessException e) {
            log.error("在对象["+sourceObject.getClass()+"]中域["+srcField+"]不允许访问",e );
        }
    }

    public SO getSourceObject() {
        return sourceObject;
    }

    public void setSourceObject(SO sourceObject) {
        this.sourceObject = sourceObject;
    }

    public TO getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(TO targetObject) {
        this.targetObject = targetObject;
    }
}
