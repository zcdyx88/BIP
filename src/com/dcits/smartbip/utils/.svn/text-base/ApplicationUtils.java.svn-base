package com.dcits.smartbip.utils;

import org.springframework.context.ApplicationContext;

/**
 * Created by vincentfxz on 16/5/9.
 */
public class ApplicationUtils {

    private static ApplicationUtils instance;
    private ApplicationContext context;

    public static ApplicationUtils getInstance(){
       if(null == instance){
           synchronized (ApplicationUtils.class){
               if(null == instance){
                   instance = new ApplicationUtils();
               }
           }
       }
        return instance;
    }

    private ApplicationUtils(){

    }

    public ApplicationUtils(ApplicationContext context){
        this.context = context;
    }

    public ApplicationContext getContext() {
        return context;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }


    public Object getBean(String name){
        return context.getBean(name);
    }
}
