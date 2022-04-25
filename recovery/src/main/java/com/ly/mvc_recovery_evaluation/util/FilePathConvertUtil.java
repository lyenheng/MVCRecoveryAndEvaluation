package com.ly.mvc_recovery_evaluation.util;

import com.ly.mvc_recovery_evaluation.entity.ModuleNode;

import java.io.File;

/**
 * @author liuyue
 * @date 2022/4/25 14:02
 */
public class FilePathConvertUtil {

    public static File getResourceFile(ModuleNode moduleNode){
        File mainFile = getMainFile(moduleNode);
        return new File(mainFile, "resources");
    }

    public static File getMainFile(ModuleNode moduleNode){
        File moduleFile = moduleNode.getModuleFile();
        return new File(moduleFile.getAbsolutePath()+  File.separator + "src" + File.separator + "main");
    }
}
