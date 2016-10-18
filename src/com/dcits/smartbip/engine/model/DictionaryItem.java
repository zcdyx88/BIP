package com.dcits.smartbip.engine.model;

/**
 * Created by vincentfxz on 16/5/12.
 * 字典项类
 */
public class DictionaryItem {
    private String id;
    private String value;
    private String type;
    private String desc;
    private String owner;

    public DictionaryItem(){

    }

    public DictionaryItem(String id, String value, String type, String desc, String owner) {
        this.id = id;
        this.value = value;
        this.type = type;
        this.desc = desc;
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
