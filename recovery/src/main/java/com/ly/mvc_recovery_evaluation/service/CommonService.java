package com.ly.mvc_recovery_evaluation.service;

import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * @author liuyue
 * @date 2022/4/27 20:16
 */
@Component
public class CommonService {

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
}
