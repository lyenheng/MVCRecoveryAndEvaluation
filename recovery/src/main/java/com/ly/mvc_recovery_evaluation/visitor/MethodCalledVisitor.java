package com.ly.mvc_recovery_evaluation.visitor;

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.ly.mvc_recovery_evaluation.entity.MethodCalledNode;
import com.ly.mvc_recovery_evaluation.entity.MethodDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


import java.util.Optional;


/**
 * @author liuyue
 * @date 2022/6/8 19:45
 * 访问方法调用节点
 */
@Component
public class MethodCalledVisitor extends VoidVisitorAdapter<MethodDescription> {

    @Override
    public void visit(MethodCallExpr n, MethodDescription arg) {
        super.visit(n, arg);

        MethodCalledNode methodCalledNode = new MethodCalledNode();

        // 被调用函数的类的变量名
        String className = null ;
        Optional<Expression> scope = n.getScope();
        if (scope.isPresent() && scope.get() instanceof NameExpr){
            NameExpr nameExpr = (NameExpr)scope.get();
            className = nameExpr.getNameAsString();
        }else if (scope.isPresent() && scope.get() instanceof MethodCallExpr){
            className = ((MethodCallExpr) scope.get()).getNameAsString();
        }
        if (!StringUtils.isEmpty(className)){
            methodCalledNode.setCalledClassVarName(className);
        }

        // 被调用的方法名
        String methodName = n.getNameAsString();
        methodCalledNode.setCalledMethodName(methodName);

        arg.getMethodCalledNodeList().add(methodCalledNode);
    }
}
