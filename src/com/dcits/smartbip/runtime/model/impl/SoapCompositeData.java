package com.dcits.smartbip.runtime.model.impl;

import com.dcits.smartbip.runtime.model.ICompositeData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by vincentfxz on 16/5/25.
 */
public class SoapCompositeData implements ICompositeData,Serializable {

    private final Map<String, List<ICompositeData>> map;
    private String value;
    private String xPath;
    private String payLoad;
    private String id;
    public SoapCompositeData() {
        map = new TreeMap<String, List<ICompositeData>>();
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setChild(String index, ICompositeData compositeData) {
    	StringBuilder tmpPath = new StringBuilder();
    	tmpPath.append(this.getxPath()).append("/").append(compositeData.getId());
    	tmpPath.append("[").append(getChild(index).size()).append("]");
        compositeData.setxPath(tmpPath.toString());
        getChild(index).add(compositeData);
    }

    @Override
    public List<ICompositeData> getChild(String key) {
        List<ICompositeData> compositeDatas = map.get(key);
        if(null == compositeDatas){
            compositeDatas = new ArrayList<ICompositeData>();
            map.put(key, compositeDatas);
        }
        return compositeDatas;
    }

    @Override
    public Map<String, List<ICompositeData>> getChildren() {
        return this.map;
    }

    @Override
    public String setMap(Map<String, String> map) {
        return null;
    }

    public String getxPath() {
        return xPath;
    }

    public void setxPath(String xPath) {
        this.xPath = xPath;
    }

    public String getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(String payLoad) {
        this.payLoad = payLoad;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getxPath()).append(" = ").append(getValue()).append("\n");
        for(Map.Entry<String, List<ICompositeData>> children : getChildren().entrySet()){
            for(ICompositeData child : children.getValue()){
                sb.append(child.toString());
            }
        }
        return sb.toString();
//    	return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getDispatchId() {
        return getPayLoad();
    }

    @Override
    public void setDispatchId(String dispatchId) {
        setPayLoad(dispatchId);
    }
}
