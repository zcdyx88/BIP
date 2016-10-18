package com.dcits.smartbip.parser.code;

import com.dcits.smartbip.utils.FileUtils;

import java.io.File;

/**
 * Created by vincentfxz on 16/4/24.
 */
public class CodeConstants {
    private static final String PUBLIC_MODIFIER = "public";
    private static final String PROTECTED_MODIFIER = "protected";
    private static final String PRIVATE_MODIFIER = "private";
    public static final String REPOSITORY_PATH = FileUtils.getJarPath() + File.separator + "repository";
}
