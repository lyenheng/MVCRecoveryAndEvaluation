package com.ly.mvc_recovery_evaluation.visitor;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.ly.mvc_recovery_evaluation.entity.ClassDescription;
import com.ly.mvc_recovery_evaluation.entity.InjectionInfo;
import com.ly.mvc_recovery_evaluation.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author liuyue
 * @date 2022/4/30 20:39
 */
@Component
public class InjectionVisitor extends VoidVisitorAdapter<ClassDescription> {

    @Autowired
    private CommonService commonService;

    /**
     * 获取类的属性值
     * @param n
     * @param arg
     */
    @Override
    public void visit(FieldDeclaration n, ClassDescription arg) {
        super.visit(n, arg);

        NodeList<AnnotationExpr> annotations = n.getAnnotations();
        if (annotations.size() > 0){
            String annotationExprName= annotations.get(0).getNameAsString();
            if (annotationExprName.equals("Autowired") || annotationExprName.equals("Resource")){
                NodeList<VariableDeclarator> variables = n.getVariables();
                if (variables.size() > 0){
                    InjectionInfo injectionInfo = new InjectionInfo();
                    String variableName = variables.get(0).getNameAsString();
                    injectionInfo.setVarName(variableName);
                    Type variableType = variables.get(0).getType();
                    if (variableType instanceof ClassOrInterfaceType){
                        Optional<Node> parentNode = n.getParentNode();
                        if (parentNode.isPresent()){
                            String fullyQualifiedNameByClassName = commonService.getFullyQualifiedNameByClassName((ClassOrInterfaceDeclaration) parentNode.get(), ((ClassOrInterfaceType) variableType).getNameAsString());
                            injectionInfo.setFullyQualifiedName(fullyQualifiedNameByClassName);
                        }

                    }
                }
            }
        }

        System.out.println(n);
    }
}
