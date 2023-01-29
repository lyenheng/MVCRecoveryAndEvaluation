package com.ly.mvc_recovery_evaluation.service;

import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.ly.mvc_recovery_evaluation.bean.ProjectNode;
import com.ly.mvc_recovery_evaluation.entity.ClassDescription;
import com.ly.mvc_recovery_evaluation.entity.ModuleNode;
import com.ly.mvc_recovery_evaluation.enums.ModuleType;
import com.ly.mvc_recovery_evaluation.util.ClassHandleUtil;
import com.ly.mvc_recovery_evaluation.util.FilePathConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author liuyue
 * @date 2022/4/27 20:16
 */
@Component
public class CommonService {

    @Autowired
    private ProjectNode projectNode;

    /**
     * 根据类名获取全限定类名
     * @param clz
     * @param className
     * @return
     */
    public String getFullyQualifiedNameByClassName(ClassOrInterfaceDeclaration clz, String className){

        if (StringUtils.isEmpty(className)){
            return null;
        }

        // 如果是基础类型
        if (!ClassHandleUtil.isEntityType(className)){
            return className;
        }

        // imports
        NodeList<ImportDeclaration> imports = new NodeList<>();
        // packageDeclaration
        String packageDeclaration = null;
        Optional<Node> parentNode = clz.getParentNode();
        if (parentNode.isPresent()) {
            imports = ((CompilationUnit) parentNode.get()).getImports();
            Optional<PackageDeclaration> packageDeclaration1 = ((CompilationUnit) parentNode.get()).getPackageDeclaration();
            if (packageDeclaration1.isPresent()) {
                packageDeclaration = packageDeclaration1.get().getNameAsString();
            }
        }

        for (ImportDeclaration anImport : imports) {
            if (anImport.getNameAsString().endsWith(className)){
                return anImport.getNameAsString();
            }
        }

        if (StringUtils.isEmpty(packageDeclaration)){
            return className;
        }
        return packageDeclaration + "." + className;

    }

    /**
     * 根据全限定类名找到对应的文件
     * @param fullyQualifiedName
     * @return
     */
    public File search(String fullyQualifiedName){

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
