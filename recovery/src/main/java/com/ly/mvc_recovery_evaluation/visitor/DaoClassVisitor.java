package com.ly.mvc_recovery_evaluation.visitor;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.ly.mvc_recovery_evaluation.entity.DaoClassDescription;
import com.ly.mvc_recovery_evaluation.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * @author liuyue
 * @date 2022/4/27 17:22
 */
@Component
public class DaoClassVisitor extends VoidVisitorAdapter<DaoClassDescription> {

    @Autowired
    private CommonService commonService;

    /**
     * 处理类
     * @param clz
     * @param arg
     */
    @Override
    public void visit(ClassOrInterfaceDeclaration clz, DaoClassDescription arg) {
        super.visit(clz, arg);

        List<String> interfaceServices = new ArrayList<>();
        List<String> extendsServices = new ArrayList<>();

        // 继承类
        NodeList<ClassOrInterfaceType> extendedTypes = clz.getExtendedTypes();
        if (extendedTypes != null && extendedTypes.size() > 0){
            for (ClassOrInterfaceType extendedType : extendedTypes) {
                interfaceServices.add(commonService.getFullyQualifiedNameByClassName(clz, extendedType.getNameAsString())) ;
            }
        }

        // 实现类
        NodeList<ClassOrInterfaceType> implementedTypes = clz.getImplementedTypes();
        if (implementedTypes != null && implementedTypes.size() > 0){
            for (ClassOrInterfaceType implementedType : implementedTypes) {
                extendsServices.add(commonService.getFullyQualifiedNameByClassName(clz, implementedType.getNameAsString()));
            }
        }

        arg.setInterfaceServices(interfaceServices);
        arg.setExtendsServices(extendsServices);

    }
}
