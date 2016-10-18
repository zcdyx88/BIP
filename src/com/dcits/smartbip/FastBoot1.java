package com.dcits.smartbip;

import java.io.File;
import java.io.IOException;

import com.dcits.smartbip.utils.FileUtils;

public class FastBoot1 {

    public static void main(String[] args) {
    	System.setProperty("smartbip.install", System.getProperty("user.dir"));
    	System.setProperty("LOG_PATH", System.getProperty("user.dir") + "/logs");
    	/** 删除目录 **/
    	File ff = new File(System.getProperty("user.dir") + "/jar/");
    	
    	try {
			FileUtils.deleteDirectory(ff);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	FastBoot.start();
    }
}
