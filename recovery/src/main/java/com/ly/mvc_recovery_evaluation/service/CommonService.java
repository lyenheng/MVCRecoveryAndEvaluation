package com.ly.mvc_recovery_evaluation.service;

import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.ly.mvc_recovery_evaluation.entity.ClassDescription;
import com.ly.mvc_recovery_evaluation.entity.DaoClassDescription;
import com.ly.mvc_recovery_evaluation.entity.ServiceClassDescription;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author liuyue
 * @date 2022/4/27 20:16
 */
@Component
public class CommonService {

    public void getExtendsOrImplementsFullyQualifiedName(ClassOrInterfaceDeclaration clz, ClassDescription arg) {


        List<String> extendsServices = new ArrayList<>();
        List<String> interfaceServices = new ArrayList<>();

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

        // 继承类
        NodeList<ClassOrInterfaceType> extendedTypes = clz.getExtendedTypes();
        getFullyQualifiedName(extendsServices, imports, packageDeclaration, extendedTypes);

        // 实现类
        NodeList<ClassOrInterfaceType> implementedTypes = clz.getImplementedTypes();
        getFullyQualifiedName(interfaceServices, imports, packageDeclaration, implementedTypes);

        if (arg instanceof ServiceClassDescription) {
            ((ServiceClassDescription) arg).setExtendsServices(extendsServices);
            ((ServiceClassDescription) arg).setInterfaceServices(interfaceServices);
        } else if (arg instanceof DaoClassDescription) {
            ((DaoClassDescription) arg).setInterfaceServices(interfaceServices);
            ((DaoClassDescription) arg).setExtendsServices(extendsServices);
        }
    }

    /**
     * 获取继承类或者接口的全限定类名
     * @param extendsServices / interfaceServices
     * @param imports
     * @param packageDeclaration
     * @param extendedTypes
     */
    private void getFullyQualifiedName(List<String> extendsServices, NodeList<ImportDeclaration> imports, String packageDeclaration, NodeList<ClassOrInterfaceType> extendedTypes) {
        if (extendedTypes != null && extendedTypes.size() > 0) {
            for (ClassOrInterfaceType extendedType : extendedTypes) {

                boolean flag = false;
                for (ImportDeclaration anImport : imports) {
                    if (anImport.getNameAsString().endsWith(extendedType.getNameAsString())) {
                        extendsServices.add(anImport.getNameAsString());
                        flag = true;
                        break;
                    }
                }
                if (!flag && !StringUtils.isEmpty(packageDeclaration)) {
                    extendsServices.add(packageDeclaration + "." + extendedType.getNameAsString());
                } else if (!flag) {
                    extendsServices.add(extendedType.getNameAsString());
                }
            }
        }
    }
}
