package com.dcits.smartbip.engine.model;

/**
 * Created by vincentfxz on 16/5/12.
 */
public class DictionaryItemPK {
    private String owner;
    private String itemId;

    public DictionaryItemPK(String owner, String itemId) {
        this.owner = owner;
        this.itemId = itemId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
