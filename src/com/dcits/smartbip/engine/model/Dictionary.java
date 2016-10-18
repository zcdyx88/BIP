package com.dcits.smartbip.engine.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vincentfxz on 16/5/12.
 */
public class Dictionary {
    private String owner;
    private Map<String, DictionaryItem> items;

    public Dictionary(String owner){
        this.owner = owner;
        items = new HashMap<String, DictionaryItem>();
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Map<String, DictionaryItem> getItems() {
        return items;
    }

    public void setItems(Map<String, DictionaryItem> items) {
        this.items = items;
    }
}
