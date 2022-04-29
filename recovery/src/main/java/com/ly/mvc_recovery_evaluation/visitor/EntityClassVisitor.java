package com.ly.mvc_recovery_evaluation.visitor;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author liuyue
 * @date 2022/4/29 19:31
 */
@Component
public class EntityClassVisitor extends VoidVisitorAdapter<Map<String, ClassOrInterfaceType>> {

    /**
     * 获取类的属性值
     * @param n
     * @param arg
     */
    @Override
    public void visit(FieldDeclaration n, Map<String, ClassOrInterfaceType> arg) {
        super.visit(n, arg);
        NodeList<VariableDeclarator> variables = n.getVariables();
        if (variables.size() > 0){
            String name = variables.get(0).getNameAsString();
            ClassOrInterfaceType classOrInterfaceType = (ClassOrInterfaceType)variables.get(0).getType();

            arg.put(name, classOrInterfaceType);
        }
    }

}
