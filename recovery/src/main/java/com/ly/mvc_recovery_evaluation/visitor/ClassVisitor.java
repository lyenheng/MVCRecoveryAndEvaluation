package com.ly.mvc_recovery_evaluation.visitor;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.ly.mvc_recovery_evaluation.entity.ClassDescription;
import com.ly.mvc_recovery_evaluation.enums.ClassType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

        // 注解信息
        NodeList<AnnotationExpr> annotations = clz.getAnnotations();
        if (annotations != null){
            List<String> annotationList = new ArrayList<>();
            for (AnnotationExpr annotation : annotations) {
                String annotationName = annotation.getName().getIdentifier();
                annotationList.add(annotationName);

                if (annotationName.equals("RestController")){
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

        // 包名
        String packagePath = "";
        if (clz.getFullyQualifiedName().isPresent()) {
            packagePath = clz.getFullyQualifiedName().get();
        }
        arg.setPackagePath(packagePath);
    }
}
