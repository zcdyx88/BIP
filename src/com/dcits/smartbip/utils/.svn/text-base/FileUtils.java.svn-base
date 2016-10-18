package com.dcits.smartbip.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.net.URL;

/**
 * Created by vincentfxz on 16/5/18.
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

    private static final Log log = LogFactory.getLog(FileUtils.class);

    public static File getFileFromCL(String path) {
        File file = null;
        try {
            URL url = FileUtils.class.getClassLoader().getResource(path);
            if(null != url){
                file = new File(url.getFile());
            }
        } catch (Exception e) {
            log.error("加载文件[" + path + "]失败!", e);
        }
        return file;
    }

    public static File getFileFromInstallPath() {
        String installPath = System.getProperty("smartbip.install");
        String configPath = installPath + File.separator + "configs";
        return new File(configPath);
    }

    public static File getFromJar(String path) {
        String installPath = System.getProperty("smartbip.install");
        String jarConfigPath = installPath + File.separator + "jar";
        File jarDir = new File(jarConfigPath);
        if (jarDir.exists()) {
            jarDir.mkdirs();
        }
        return new File(jarConfigPath + File.separator + path);
    }

    public static File getFromConfigs(String path) {
        String installPath = System.getProperty("smartbip.install");
        String configPath = installPath + File.separator + "configs";
        File configDir = new File(configPath);
        if (configDir.exists()) {
            configDir.mkdirs();
        }
        return new File(configPath + File.separator + path);
    }

    public static  String getConfigPath(){
        String installPath = System.getProperty("smartbip.install");
        return installPath + File.separator + "configs";
    }

    public static String getJarPath(){
        String installPath = System.getProperty("smartbip.install");
        return installPath + File.separator + "jar";
    }
}
