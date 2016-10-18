package com.dcits.smartbip.common.model;

/**
 * Created by vincentfxz on 16/6/23.
 */
public interface IFilePersistAble {
    String getFile();

    long getLastModify();

    void setLastModify(long lastModify);
}
