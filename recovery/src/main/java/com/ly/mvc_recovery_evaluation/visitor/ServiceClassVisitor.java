package com.ly.mvc_recovery_evaluation.visitor;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.ly.mvc_recovery_evaluation.entity.ServiceClassDescription;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyue
 * @date 2022/4/27 15:28
 */
@Component
public class ServiceClassVisitor extends VoidVisitorAdapter<ServiceClassDescription> {

    /**
     * 处理类
     * @param clz
     * @param arg
     */
    @Override
    public void visit(ClassOrInterfaceDeclaration clz, ServiceClassDescription arg) {
        super.visit(clz, arg);

        // 继承类
        NodeList<ClassOrInterfaceType> extendedTypes = clz.getExtendedTypes();
        if (extendedTypes != null && extendedTypes.size() > 0){
            List<String> extendsServices = new ArrayList<>();
            for (ClassOrInterfaceType extendedType : extendedTypes) {
                extendsServices.add(extendedType.getNameAsString());
            }
            arg.setExtendsServices(extendsServices);
        }

        // 实现类
        NodeList<ClassOrInterfaceType> implementedTypes = clz.getImplementedTypes();
        if (implementedTypes != null && implementedTypes.size() > 0){
            List<String> interfaceServices = new ArrayList<>();
            for (ClassOrInterfaceType implementedType : implementedTypes) {
                interfaceServices.add(implementedType.getNameAsString());
            }
            arg.setInterfaceServices(interfaceServices);
        }
    }
}
