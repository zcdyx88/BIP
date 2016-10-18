/*
 * Copyright 2010 digital china financial software Inc. All rights reserved.
 * ServiceGovernance version:---------------------------------------------------
 * author: duan.yongjian date: 2010-11-30 note:
 * --------------------------------------------------- modificator: date:
 * 2010-11-30 note:
 */
package com.dcits.smartbip.parser.code;

import com.dcits.smartbip.exception.JavaCompilerException;
import com.dcits.smartbip.parser.model.ProcessDefinitionConstants;
import com.sun.tools.javac.Main;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

/**
 * 将生成的java代码编译成class，然后将源代码和class文件打包到jar文件，并将jar文件放置到classpath下供应用程序加载使用。
 *
 * @author 段永见
 */
public class JavaCompiler {
    private static Log log = LogFactory.getLog(JavaCompiler.class);


    private static LinkedList<File> classpaths = new LinkedList<File>();

    private static List<String> jarNames = new LinkedList<String>();


    static void loadClasspath(String compilerDir) {
        String[] dirs = compilerDir.split(";");
        for (int i = 0; i < dirs.length; i++) {
            String dir = dirs[i];
            addClassPath(dir);
        }
    }

    static String getESBAppPath(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            File appDir = file.getParentFile().getParentFile();
            File libDir = new File(appDir, "lib");
            if (libDir.exists())
                return appDir.getPath();
        }
        throw new IOException("file " + file + " is not esbapp root.");
    }

    static void loadJarNames(String jarNames) {
        String[] jars = jarNames.split(";");
        for (int i = 0; i < jars.length; i++) {
            String jarName = jars[i];
            JavaCompiler.jarNames.add(jarName);
        }
    }

    /**
     * 编译单个java文件。
     *
     * @param javaFileName
     * @param fullPackage
     * @param jarParentPath
     * @throws JavaCompilerException
     */
    public static void compile(String javaFileName, String fullPackage, String jarParentPath)
            throws JavaCompilerException {
        String src = getSourcepath();
        String[] javas = new String[1];
        javas[0] = src + "/" + javaFileName + ".java";
        String packagepath = _compile(javas, fullPackage);
        try {
            _createJar(jarParentPath, javaFileName, packagepath);
        } catch (IOException e) {
            log.error("create jar failed...", e);
        } finally {
            // _deleteFile(new File(getSourcepath()));
            // _deleteFile(new File(getClassspath()));
        }
    }

    /**
     * @param fullPackage
     * @return
     * @throws JavaCompilerException
     */
    public static String _compile(String[] javaFileNames, String fullPackage)
            throws JavaCompilerException {
        String packagepath = fullPackage == null ? ProcessDefinitionConstants.RUNTIME_PROCESS_PACKAGE_DIR
                : fullPackage.replaceAll("\\.", "/");
        String src = getSourcepath();
        String cp = getClassPath();
        String[] source = new String[9 + javaFileNames.length];
        LinkedList<String> items = new LinkedList<String>();
        items.add("-cp");
        items.add(cp);
        items.add("-d");
        items.add(getDestinationDir());
        items.add("-g");
        items.add("-nowarn");
        items.add("-Xlint:unchecked");
        items.add("-sourcepath");
        items.add(src);
        for (int i = 0; i < javaFileNames.length; i++) {
            items.add(javaFileNames[i]);
        }
        source = items.toArray(source);
        if (log.isDebugEnabled()) {
            log.debug("compile args:" + Arrays.toString(source));
        }
//        javax.tools.JavaCompiler.CompilationTask task = ToolProvider.getSystemJavaCompiler().getTask(null, fileManager, diagnostics, items, null, jfiles);
//        boolean success = task.call();
        int state = Main.compile(source);
        if (state != 0) {
            StringBuilder filenames = new StringBuilder();
            for (String f : javaFileNames) {
                filenames = filenames.append(f).append(",");
            }
            String string = "compile java files " + filenames + " failed,the statecode is "
                    + state;
            log.error(string);
            throw new JavaCompilerException(string);
        }
        if (log.isInfoEnabled()) {
            StringBuilder builder = new StringBuilder();
            for (String string : javaFileNames) {
                builder.append(string).append('\n');
            }
            builder.deleteCharAt(builder.length() - 1);
            log.info("compile java file {" + builder.toString() + "} finished...");
        }
        return packagepath;
    }

    /**
     * 编译生成的所有java文件，并将生成的class文件和源码写入同一个jar包。
     *
     * @param fileNames     所有的java文件。
     * @param fullPackage   java文件的包路径
     * @param jarParentPath 生成jar包所在的路径
     * @param jarName       生成的jar包的名字。
     * @throws JavaCompilerException
     * @throws IOException
     * @throws FileNotFoundException
     */
    private static void compileAll(List fileNames, String fullPackage, String jarParentPath,
                                   String jarName) throws JavaCompilerException, FileNotFoundException, IOException {
        // 先编译所有的java文件。
        String src = getSourcepath();
        String[] javas = new String[fileNames.size()];
        for (int i = 0; i < javas.length; i++) {
            javas[i] = src + "/" + fileNames.get(i) + ".java";
        }
        String packagepath = _compile(javas, fullPackage);
        // 将生成的class和源码打包到一个jar中。
        _createJarAll(jarParentPath, fileNames, packagepath, jarName);
    }

    /**
     * 编译所有的java文件，并将编译后的class文件和源文件放到一个jar包中。
     *
     * @param fileNames     所有文件的名字集合
     * @param jarParentPath jar包所在目录。
     * @param jarName       jar包名字
     * @throws JavaCompilerException 编译错误
     * @throws FileNotFoundException 找不到文件
     * @throws IOException
     */
    public static void compileAll(List fileNames, String jarParentPath, String jarName)
            throws JavaCompilerException, FileNotFoundException, IOException {
        compileAll(fileNames, null, jarParentPath, jarName);
    }

    /**
     * 添加编译所需要的classpath。
     *
     * @param dir
     */
    public static void addClassPath(String dir) {
        addClassPath(dir, true);
    }

    public static void addClassPath(String dir, boolean filter) {
        File item = new File(dir);
        if (item.exists()) {
            if (item.isDirectory()) {
                if (dir.endsWith(File.separator)) {
                    classpaths.add(item);
                    if (log.isDebugEnabled())
                        log.debug("add [" + item.getAbsolutePath()
                                + "] to JavaCompiler classpath finish.");
                } else {
                    File[] child = item.listFiles();
                    for (int i = 0; i < child.length; i++) {
                        addClassPath(child[i].getAbsolutePath(), filter);
                    }
                }
            } else {
                String name = item.getName();
                if (name.contains(".")) {
                    name = name.substring(0, name.lastIndexOf("."));
                }
                if (filter) {
                    if (jarNames.contains(name) || name.contains("log4j")) {
                        classpaths.add(item);
                        if (log.isDebugEnabled())
                            log.debug("add [" + item.getAbsolutePath()
                                    + "] to JavaCompiler classpath finish.");
                    }
                } else {
                    classpaths.add(item);
                    if (log.isDebugEnabled())
                        log.debug("add [" + item.getAbsolutePath()
                                + "] to JavaCompiler classpath finish.");
                }
            }
        } else {
            log.info("classpath " + item + " doesnot exist!");
        }
    }

    /**
     * 返回动态编译所需要的classpath。
     *
     * @return
     */
    private static String getClassPath() {
        String split = ":";
        String os = System.getProperty("os.name");
        if (os.toLowerCase().contains("windows")) {
            split = ";";
        }
        StringBuilder classPath = new StringBuilder(".").append(split);
        URL resource = JavaCompiler.class.getClassLoader().getResource("");
        if (resource != null) {
            classPath.append(new File(resource.getFile()).getAbsolutePath());
        }

        String classPathDir = System.getProperty("smartbip.install");
        if(null == classPathDir){
            classPathDir = "/Users/vincentfxz/Code/SmartBIP/target/dependency";
        }else{
            classPathDir = classPathDir + File.separator + "lib";
        }
        File libDir = new File(classPathDir);
        if(libDir.isDirectory()){
            File[] jars = libDir.listFiles();
            for(File jar : jars){
                classPath.append(split + jar.getAbsolutePath());
            }
        }
        String string = classPath.toString();
        if (log.isDebugEnabled())
            log.debug("the classpath of JavaCompiler is :" + string);
        return string;
    }

    public static void compile(String jarParentPath, String javaFileName)
            throws JavaCompilerException {
        compile(javaFileName, null, jarParentPath);
    }

    public static String getSourcepath() {
//        String src = System.getProperty("user.dir") + "/jar/src";
    	String src = System.getProperty("smartbip.install") + "/jar/src";  //0807 修改为取保存java文件相同路径
        synchronized (JavaCompiler.class) {
            File srcFile = new File(src);
            if (!srcFile.exists())
                srcFile.mkdirs();
        }
        return src;
    }

    public static String getDestinationDir() {
//        String string = System.getProperty("user.dir") + "/jar/classes";
    	String string = System.getProperty("smartbip.install") + "/jar/classes"; //0807 修改为取保存java文件相同路径
        synchronized (JavaCompiler.class) {
            File tempFile = new File(string);
            if (!tempFile.exists())
                tempFile.mkdirs();
        }
        return string;
    }

    /**
     * 将所有的class及其源码打包到一个jar中。
     *
     * @param jarParentpath
     * @param classNames
     * @param packagePath
     * @throws IOException
     * @throws FileNotFoundException
     */
    private static void _createJarAll(String jarParentpath, List<String> classNames,
                                      String packagePath, String jarName) throws FileNotFoundException, IOException {
        List<File> files = new LinkedList<File>();
        for (int i = 0; i < classNames.size(); i++) {
            String className = classNames.get(i);
            addJavaAndClassFile(getJavaAndClassFile(className, packagePath), files);
        }
        createJar(jarParentpath, packagePath, jarName, files);
        if (log.isDebugEnabled())
            log.debug("create jar " + jarName + " in directory " + jarParentpath + " finished...");
    }

    private static void _createJar(String jarParentpath, String className, String packagePath)
            throws IOException {
        List<File> files = new LinkedList<File>();
        addJavaAndClassFile(getJavaAndClassFile(className, packagePath), files);
        createJar(jarParentpath, packagePath, className, files);
        if (log.isDebugEnabled())
            log
                    .debug("create jar " + className + " in directory " + jarParentpath
                            + " finished...");
    }

    /**
     * 将java源文件和class文件添加到集合中。
     *
     * @param fs
     * @param files
     */
    private static void addJavaAndClassFile(List<File> fs, List<File> files) {
        files.addAll(fs);
    }

    /**
     * 返回给定java名字的class文件和源文件。
     *
     * @param className
     * @param packagePath
     * @return
     */
    private static List<File> getJavaAndClassFile(String className, String packagePath) {
        List<File> targetFiles = new ArrayList<File>();
        String classpath = getDestinationDir() + "/" + packagePath;
        File classpathDir = new File(classpath);
        if(classpathDir.isDirectory()){
            for(File file : classpathDir.listFiles()){
                String fileName = file.getName();
                if(fileName.equalsIgnoreCase(className + ".class") || fileName.equalsIgnoreCase(className + ".java")){
                    targetFiles.add(file);
                }else if(fileName.startsWith(className) && fileName.indexOf("$") > 0){
                    targetFiles.add(file);
                }
            }
        }else{
            log.error("打包["+className+"]jar文件失败!");
        }
        return targetFiles;
    }

    /**
     * @param jarParentpath
     * @param packagePath
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static void createJar(String jarParentpath, String packagePath, String jarName,
                                  List<File> files) throws FileNotFoundException, IOException {
        File parentFile = new File(jarParentpath);
        if (!parentFile.exists())
            parentFile.mkdirs();
        // String jarParentpath = new File(JavaCompiler.class.getClassLoader()
        // .getResource("").getFile()).getAbsolutePath();
        String classsespath = getDestinationDir() + "/" + packagePath;
        // File classFile = new File(classsespath, className + ".class");
        // File sourceFile = new File(getSourcepath(), className + ".java");
        File jarFile = new File(jarParentpath, jarName + ".jar");
        if (log.isDebugEnabled())
            log.debug("begin create jar " + jarFile.getName() + " in directory " + jarParentpath);
        OutputStream outStream = new FileOutputStream(jarFile);
        Manifest manifest = new Manifest();
        Attributes mainAttributes = manifest.getMainAttributes();
        mainAttributes.put(Attributes.Name.MANIFEST_VERSION, "1.0");
        // mainAttributes.put(Attributes., value)
        JarOutputStream jarOut = new JarOutputStream(outStream, manifest);
        int begin = classsespath.indexOf("classes") + 8;
        String parentPath = classsespath.substring(begin);
        // 遍历所有的文件，添加到jar包。
        for (int i = 0; i < files.size(); i++) {
            File f = files.get(i);
            _addToJar(jarOut, f, parentPath);
        }
        // _addToJar(jarOut, sourceFile, parentPath);
        // _deleteFile(sourceFile);
        jarOut.finish();
//        for (int i = 0; i < files.size(); i++) {
//            File f = files.get(i);
//            String name = f.getName();
//            if (name.endsWith(".java"))
//                _deleteFile(f);
//            if (isServiceClass(name)) {
//                _deleteFile(f);
//            }
//        }
        // return jarOut;
    }

    // 如果是流化和反流化服务的拆组包类，则需要删掉class文件，仍然是运行时从jar加载，不从classes目录加载。
    private static boolean isServiceClass(String name) {
        if (name.endsWith(".class")) {
            name = name.substring(0, name.indexOf("."));
            if (name.startsWith("Parser_Channel") && name.contains("_Service_"))
                return false;
            if (name.startsWith("Packer_Service_") && name.contains("_System_")) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param classFile
     */
    public static void _deleteFile(File classFile) {
        synchronized (JavaCompiler.class) {
            if (classFile.isFile()) {
                classFile.delete();
                return;
            }
            if (classFile.isDirectory()) {
                File[] children = classFile.listFiles();
                if (children.length == 0) {
                    classFile.delete();
                    if (log.isDebugEnabled())
                        log.debug("delete " + classFile.getName() + " finished..");
                } else
                    for (File file : children) {
                        _deleteFile(file);
                    }
            }
            classFile.delete();
            if (log.isDebugEnabled())
                log.debug("delete " + classFile.getName() + " finished..");
        }
    }

    /**
     * @param jarOut
     * @param file
     * @throws IOException
     */
    private static void _addToJar(JarOutputStream jarOut, File file, String parentPath)
            throws IOException {
        if (!file.exists()) {
            log.error("the fie " + file.getAbsolutePath() + " not exists...");
            return;
        }
        String entryPath = parentPath + "/" + file.getName();
        JarEntry nextEntry = new JarEntry(entryPath);
        jarOut.putNextEntry(nextEntry);
        nextEntry.setMethod(JarEntry.DEFLATED);
        FileInputStream fileIn = new FileInputStream(file);
        byte[] buffer = new byte[2048];
        int bytesRead;
        while ((bytesRead = fileIn.read(buffer)) != -1) {
            jarOut.write(buffer, 0, bytesRead);
        }
        fileIn.close();
        jarOut.flush();
        jarOut.closeEntry();
        if (log.isDebugEnabled())
            log.debug("add " + entryPath + " finished...");
    }

    /**
     * 加载单个class文件。
     *
     * @param classFullName
     * @return
     * @throws JavaCompilerException
     */
    public static Class loadClass(String classFullName) throws JavaCompilerException {
        List<String> classFullNames = new LinkedList<String>();
        classFullNames.add(classFullName);
        Class clas = null;
        try {
            clas = Thread.currentThread().getContextClassLoader().loadClass(classFullName);
        } catch (ClassNotFoundException e) {
            String jarFileName = classFullName.substring(classFullName.lastIndexOf('.') + 1)
                    + ".jar";
            clas = _loadByURLClassLoaderFromResourcePath(classFullNames, jarFileName, true);
        }
        if (log.isInfoEnabled())
            log.info("load class " + classFullName + " finished...");
        return clas;
    }

    public static Class loadClassFromClasses(String classFullName) throws JavaCompilerException {
        List<String> classFullNames = new LinkedList<String>();
        classFullNames.add(classFullName);
        Class clas = null;
        try {
            clas = Thread.currentThread().getContextClassLoader().loadClass(classFullName);
        } catch (ClassNotFoundException e) {
            String jarFileName = classFullName.substring(classFullName.lastIndexOf('.') + 1)
                    + ".jar";
            clas = _loadByURLClassLoaderFromResourcePath(classFullNames, jarFileName, false);
        }
        if (log.isInfoEnabled())
            log.info("load class " + classFullName + " finished...");
        return clas;
    }

    // public static Class loadAllClass(String mainClassFullName) {
    //
    // }

    private static Class _loadByURLClassLoaderFromResourcePath(List<String> classFullNames,
                                                               String jarFileName, boolean jar) throws JavaCompilerException {
        Class clas = null;
        // URL[] urls = new URL[2];
        String classFullName = null;
        try {
            ClassLoader parentClassLoader = JavaCompiler.class.getClassLoader();
            // URL resource = parentClassLoader.getResource("");
            // File packerJarFile = new File(IPackerGenerator.JAR_PATH,
            // jarFileName);
            // urls[0] = packerJarFile.toURL();
            // File parserJarFile = new File(IParserGenerator.JAR_PATH,
            // jarFileName);
            // urls[1] = parserJarFile.toURL();

            URLClassLoader urlClassLoader = null;
            if (jar) {
                urlClassLoader = new URLClassLoader(geturls(), parentClassLoader);
            } else {
                urlClassLoader = new URLClassLoader(getClassesDir(), parentClassLoader);
            }
            for (int i = 0; i < classFullNames.size(); i++) {
                classFullName = classFullNames.get(i);
                clas = urlClassLoader.loadClass(classFullName);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            log.error(e1);
            throw new JavaCompilerException("class with name " + classFullName + " not be found",
                    e1);
        }
        return clas;
    }

    /**
     * @return
     * @throws MalformedURLException
     */
    private static URL[] getClassesDir() throws MalformedURLException {
        LinkedList<URL> list = new LinkedList<URL>();
        List<File> fList = new LinkedList<File>();
        File classesFile = new File(getDestinationDir());
        fList.add(classesFile);
        while (fList.size() > 0) {
            list.add(fList.remove(0).toURL());
        }
        URL[] urls = new URL[list.size()];
        URL[] array = list.toArray(urls);

        return array;
    }

    public static URL[] geturls() throws MalformedURLException {
        LinkedList<URL> list = new LinkedList<URL>();
        List<File> fList = new LinkedList<File>();
        loadFileList(new File(ProcessDefinitionConstants.DEFAULT_SRC), fList);
        while (fList.size() > 0) {
            list.add(fList.remove(0).toURL());
        }
        URL[] urls = new URL[list.size()];
        URL[] array = list.toArray(urls);
       return array;
    }

    private static void loadFileList(File file, List<File> files) {
        if (file.isDirectory()) {
            File[] fileChildren = file.listFiles();
            for (File child : fileChildren) {
                loadFileList(child, files);
            }
        } else {
            files.add(file);
        }
    }
}
