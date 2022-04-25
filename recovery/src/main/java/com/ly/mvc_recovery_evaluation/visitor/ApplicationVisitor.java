package com.ly.mvc_recovery_evaluation.visitor;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.ly.mvc_recovery_evaluation.entity.EntryNode;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyue
 * @date 2022/4/25 18:48
 */
@Component
public class ApplicationVisitor extends VoidVisitorAdapter<EntryNode> {


    /**
     * 处理类
     * @param clz
     * @param arg
     */
    @Override
    public void visit(ClassOrInterfaceDeclaration clz, EntryNode arg) {
        super.visit(clz, arg);
        NodeList<AnnotationExpr> annotations = clz.getAnnotations();

        if (annotations != null){
            List<String> annotationList = new ArrayList<>();
            for (AnnotationExpr annotation : annotations) {
                String annotationName = annotation.getName().getIdentifier();
                annotationList.add(annotationName);
            }
            arg.setAnnotations(annotationList);
        }
    }

    /**
     * 处理方法
     * @param method
     * @param arg
     */
    @Override
    public void visit(MethodDeclaration method, EntryNode arg) {

        super.visit(method, arg);

        if (!StringUtils.isEmpty(arg.getContent())){
            return;
        }

        String methodName = method.getNameAsString();

        String type = method.getTypeAsString();

        NodeList<Modifier> modifiers = method.getModifiers();

        NodeList<Parameter> parameters = method.getParameters();

        if (methodName.equals("main") && type.equals("void")
                && modifiers.size() == 2 && modifiers.get(0).toString().trim().equals("public") && modifiers.get(1).toString().trim().equals("static")
                && parameters.size() == 1 &&  parameters.get(0).getType().toString().equals("String[]") ){

            arg.setContent(method.toString());

        }
    }


}
