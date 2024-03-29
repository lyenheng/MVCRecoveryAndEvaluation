package com.ly.mvc_recovery_evaluation.visitor;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.ly.mvc_recovery_evaluation.entity.ClassDescription;
import com.ly.mvc_recovery_evaluation.entity.InjectionInfo;
import com.ly.mvc_recovery_evaluation.entity.MethodCalledNode;
import com.ly.mvc_recovery_evaluation.entity.MethodDescription;
import com.ly.mvc_recovery_evaluation.enums.ClassType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author liuyue
 * @date 2022/4/26 14:54
 */
@Component
public class ClassVisitor extends VoidVisitorAdapter<ClassDescription> {

    /**
     * 处理类
     * @param clz
     * @param arg
     */
    @Override
    public void visit(ClassOrInterfaceDeclaration clz, ClassDescription arg) {
        super.visit(clz, arg);
        if (clz.isInterface()) {
            arg.setDeclarationType("INTERFACE");
        }else {
            arg.setDeclarationType("CLASS");
        }

        // 注解信息
        NodeList<AnnotationExpr> annotations = clz.getAnnotations();
        if (annotations != null){
            List<String> annotationList = new ArrayList<>();
            for (AnnotationExpr annotation : annotations) {
                String annotationName = annotation.getName().getIdentifier();
                annotationList.add(annotationName);

                if (annotationName.equals("RestController") || annotationName.equals("Controller") ){
                    arg.setClassType(ClassType.CONTROLLER);
                }else if (annotationName.equals("Service")){
                    arg.setClassType(ClassType.SERVICE);
                }else if (annotationName.equals("Repository")){
                    arg.setClassType(ClassType.DAO);
                }else if (annotationName.equals("Component")){
                    arg.setClassType(ClassType.COMPONENT);
                }else if (annotationName.equals("Configuration")){
                    arg.setClassType(ClassType.CONFIGURATION);
                }
            }
            arg.setAnnotations(annotationList);
        }

        // 类名
        String className = clz.getName().toString();
        arg.setName(className);

        // 全限定类名
        String fullyQualifiedName = "";
        if (clz.getFullyQualifiedName().isPresent()) {
            fullyQualifiedName = clz.getFullyQualifiedName().get();
        }
        arg.setFullyQualifiedName(fullyQualifiedName);
    }

}
