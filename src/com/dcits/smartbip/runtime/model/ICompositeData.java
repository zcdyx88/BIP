package com.dcits.smartbip.runtime.model;

import java.util.List;
import java.util.Map;

/**
 * Created by vincentfxz on 16/5/25.
 */
public interface ICompositeData extends IDispatchable {

    public String getValue();

    public String setMap(Map<String, String> map);

    public void setValue(String value);

    public void setChild(String index, ICompositeData compositeData);

    public List<ICompositeData> getChild(String key);

    public Map<String, List<ICompositeData>> getChildren();

    public String getPayLoad();

    public void setPayLoad(String payLoad);

    public String getxPath();

    public void setxPath(String xpath);

    public String getId();

    public void setId(String id);

}
