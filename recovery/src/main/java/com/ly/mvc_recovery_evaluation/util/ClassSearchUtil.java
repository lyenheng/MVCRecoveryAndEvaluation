package com.ly.mvc_recovery_evaluation.util;

import com.ly.mvc_recovery_evaluation.entity.ModuleNode;
import com.ly.mvc_recovery_evaluation.entity.ProjectNode;
import com.ly.mvc_recovery_evaluation.enums.ModuleType;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author liuyue
 * @date 2022/4/27 18:50
 */
public class ClassSearchUtil {

    public static File search(ProjectNode projectNode, String fullyQualifiedName){

        String[] split = fullyQualifiedName.split("\\.");
        if (split.length < 2){
            return null;
        }

        String[] path = Arrays.copyOfRange(split, 0, split.length - 1);
        String className = split[split.length - 1];

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : path) {
            stringBuilder.append(s).append(File.separator);
        }
        stringBuilder.append(className).append(".java");


        List<ModuleNode> moduleNodeList = projectNode.getModuleNodeList();
        if (moduleNodeList != null && moduleNodeList.size() > 0){
            for (ModuleNode moduleNode : moduleNodeList) {
                if (moduleNode.getModuleType().equals(ModuleType.POM)){
                    continue;
                }

                File javaFile = FilePathConvertUtil.getJavaFile(moduleNode);

                File file = new File(javaFile.getAbsolutePath() + File.separator + stringBuilder.toString());
                if (file.exists()){
                    return file;
                }
            }
        }
        return null;
    }
}
