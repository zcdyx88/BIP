package com.dcits.smartbip.utils;

/**
 * Created by vincentfxz on 16/5/11.
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils{
    public static String capture(String str){
        char[] cs = str.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }
}