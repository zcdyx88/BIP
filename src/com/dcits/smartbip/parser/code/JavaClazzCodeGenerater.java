package com.dcits.smartbip.parser.code;

import com.dcits.smartbip.engine.impl.ClassPathLoader;
import com.dcits.smartbip.exception.ClazzGenerateException;
import com.dcits.smartbip.exception.JavaCompilerException;
import com.dcits.smartbip.parser.model.ProcessDefinitionConstants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vincentfxz on 16/4/24.
 * Java代码生成器,将流程配置生成为java代码
 */
public class JavaClazzCodeGenerater {

    private static final Log log = LogFactory.getLog(JavaClazzCodeGenerater.class);



    private String code;
    private String clazzName;
    private String packageName;
    private Class superClazz;
    private List<Class> importClasses;
    private List<Class> interfaces;
    private List<JavaMethodCodeGenerater> javaMethodCodeGeneraters;
    private List<String> fields = new ArrayList<String>();

    public JavaClazzCodeGenerater(String clazzName, String packageName, Class superClazz,
                                  List<Class> importClasses, List<Class> interfaces) {
        setClazzName(clazzName);
        setPackageName(packageName);
        setSuperClazz(superClazz);
        setImportClasses(importClasses);
        setInterfaces(interfaces);
    }

    public Class install() throws IOException, ClazzGenerateException, JavaCompilerException {
        String clazzContent = this.generate();
        String srcDirPath = ProcessDefinitionConstants.DEFAULT_SRC;
        File srcDir = new File(srcDirPath);
        if (!srcDir.exists()) {
            FileUtils.forceMkdir(srcDir);
        }
        //保存到java文件
        String srcName = srcDir + File.separator + this.getClazzName() + ".java";
        FileUtils.write(new File(srcName), clazzContent);
        //编译java文件并加载
        JavaCompiler.addClassPath(System.getProperty("smartbip.install") +File.separator +"lib", false);
        JavaCompiler.compile(ProcessDefinitionConstants.DEFAULT_SRC, this.getClazzName());

        File jarFile = new File(ProcessDefinitionConstants.DEFAULT_SRC + File.separator + this.getClazzName() + ".jar");
        if (!jarFile.exists()) {
            String errorMsg = "生成装载类[" + this.getClazzName() + "]失败";
            ClazzGenerateException exception = new ClazzGenerateException(errorMsg);
            log.error(errorMsg, exception);
        }
        File targetFile = new File(CodeConstants.REPOSITORY_PATH + File.separator + this.getClazzName() + ".jar");
        FileUtils.copyFile(jarFile, targetFile);
        ClassPathLoader.loadClasspath(targetFile.getAbsolutePath());
        Class clazz = JavaCompiler.loadClass(ProcessDefinitionConstants.RUNTIME_PROCESS_PACKAGE + "."
                + this.getClazzName());
        return clazz;
    }

    /**
     * 生成代码,包含:
     * 1.生成代码包信息
     * 2.生成代码Import信息
     * 3.生成类主体
     *
     * @return
     * @throws ClazzGenerateException
     */
    public String generate() throws ClazzGenerateException {
        StringBuilder sb = new StringBuilder();
        if (isValid()) {
            sb.append(generatePackage());
            sb.append(generateImports());
            sb.append(generateClazzBody());
        } else {
            throw new ClazzGenerateException("代码生成出错!");
        }
        return sb.toString();
    }

    protected String generateFields() {
        StringBuilder sb = new StringBuilder();
        for (String field : getFields()) {
            sb.append(field).append("\n");
        }
        return sb.toString();
    }

    /**
     * 生成代码主体
     *
     * @return
     */
    public String generateClazzBody() throws ClazzGenerateException {
        StringBuilder sb = new StringBuilder();
        //start of a class
        sb.append("public class ").append(getClazzName());
        Class superClazz = getSuperClazz();
        //add super class
        if (null != superClazz) {
            sb.append(" extends ").append(superClazz.getName());
        }
        //add interface
        List<Class> interfaces = getInterfaces();
        if (null != interfaces) {
            sb.append(" implements ");
            for (int i = 0; i < interfaces.size(); i++) {
                sb.append(interfaces.get(i).getName());
                if (i < interfaces.size() - 1) {
                    sb.append(",");
                }
            }
        }
        sb.append("{\n");
        sb.append(generateFields());
        //add default constructor
        sb.append(generateDefaultConstructor());
        //add all method
        List<JavaMethodCodeGenerater> methodCodeGeneraters = getJavaMethodCodeGeneraters();
        if (null != methodCodeGeneraters) {
            for (JavaMethodCodeGenerater methodCodeGenerater : methodCodeGeneraters) {
                sb.append(methodCodeGenerater.generate());
            }
        }
        //end of a class
        sb.append("}");
        return sb.toString();
    }

    /**
     * 生成默认的构造方法
     *
     * @return
     */
    public String generateDefaultConstructor() {
        StringBuilder sb = new StringBuilder();
        sb.append("public ").append(clazzName).append("(){}");
        return sb.toString();
    }

    /**
     * 生成包信息
     *
     * @return
     */
    public String generatePackage() {
        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(getPackageName()).append(";\n");
        return sb.toString();
    }

    /**
     * 生成导入的包信息
     *
     * @return
     */
    public String generateImports() {
        StringBuilder sb = new StringBuilder();
        List<Class> imports = getImportClasses();
        if (null != imports) {
            for (Class importClazz : imports) {
                sb.append("import ").append(importClazz.getName()).append(";\n");
            }
        }
        return sb.toString();
    }

    public boolean isValid() {
        return null != clazzName && null != packageName;
    }

    public void addMethod(JavaMethodCodeGenerater methodCodeGenerater) {
        if (null == getJavaMethodCodeGeneraters()) {
            setJavaMethodCodeGeneraters(new ArrayList<JavaMethodCodeGenerater>());
        }
        getJavaMethodCodeGeneraters().add(methodCodeGenerater);
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<JavaMethodCodeGenerater> getJavaMethodCodeGeneraters() {
        return javaMethodCodeGeneraters;
    }


    public void setJavaMethodCodeGeneraters(List<JavaMethodCodeGenerater> javaMethodCodeGeneraters) {
        this.javaMethodCodeGeneraters = javaMethodCodeGeneraters;
    }

    public Class getSuperClazz() {
        return superClazz;
    }

    public void setSuperClazz(Class superClazz) {
        this.superClazz = superClazz;
    }

    public List<Class> getImportClasses() {
        return importClasses;
    }

    public void addImportClazz(Class clazz) {
        getImportClasses().add(clazz);
    }

    public void setImportClasses(List<Class> importClasses) {
        this.importClasses = importClasses;
    }

    public List<Class> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(List<Class> interfaces) {
        this.interfaces = interfaces;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public void addField(String field) {
        this.fields.add(field);
    }
}
