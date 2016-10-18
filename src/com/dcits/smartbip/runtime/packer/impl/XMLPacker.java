package com.dcits.smartbip.runtime.packer.impl;

import com.dcits.smartbip.runtime.model.ICompositeData;
import com.dcits.smartbip.runtime.packer.IPacker;

import java.util.List;
import java.util.Map;

/**
 * Created by vincentfxz on 16/5/25.
 */
public class XMLPacker implements IPacker {
    public String pack(ICompositeData compositeData) {
        StringBuilder builder = new StringBuilder();
        builder.append("<").append(compositeData.getId()).append(">");
        String value = compositeData.getValue();
        if (null != value) {
            builder.append(value);
        }
        for (Map.Entry<String, List<ICompositeData>> entry : compositeData.getChildren().entrySet()) {
            if (null != entry.getValue()) {
                for (ICompositeData child : entry.getValue()) {
                    builder.append(pack(child));
                }
            }
        }
        builder.append("</").append(compositeData.getId()).append(">");
        return builder.toString();
    }
}
