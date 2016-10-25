package com.dcits.smartbip.runtime.model.impl;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcits.smartbip.runtime.model.IContext;
/**
 * Created by vincentfxz on 16/5/13.
 * 会话上下文
 */
public class SessionContext implements IContext , Externalizable{

	private static final Log log = LogFactory.getLog(SessionContext.class);
    private static ThreadLocal<SessionContext> map = new ThreadLocal<SessionContext>();
    private String id;

    private Map<String, Object> contextMap;

    public static IContext getContext() {
        SessionContext instance = map.get();
        if(null == instance ){
            instance = new SessionContext();
            map.set(instance);
        }
        return instance;
    }

    public SessionContext (){
        contextMap = new ConcurrentHashMap<String, Object>();
    }

    @Override
    public <T> T getValue(String key, Class<T> clazz) {
        return null;
    }

    @Override
    public Object getValue(String key) {
        return contextMap.get(key);
    }

    @Override
    public void setValue(String key, Object value) {
    	if (null!=value)
    	{
    	  contextMap.put(key, value);
    	}
    	/*else
    	{
    	  log.error("value can't be null!");
    	}   */     
    }

    @Override
    public void clear() {
        contextMap.clear();
    }

    public String getId() {
        return id;
    }

    public void setId(String s) {
        id = s;
    }

    public Object getPersistentState() {
        return false;
    }

    public void setContext(IContext sessionContext){
        map.remove();
        map.set((SessionContext) sessionContext);
    }

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(contextMap);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		contextMap = (ConcurrentHashMap<String, Object>)in.readObject();
	}
}
