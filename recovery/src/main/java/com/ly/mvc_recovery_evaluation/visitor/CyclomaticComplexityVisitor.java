package com.ly.mvc_recovery_evaluation.visitor;

import com.github.javaparser.ast.expr.ConditionalExpr;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.ly.mvc_recovery_evaluation.entity.MethodDescription;
import org.springframework.stereotype.Component;

/**
 * @author liuyue
 * @date 2022/6/12 16:42
 * 计算圈复杂度
 */
@Component
public class CyclomaticComplexityVisitor extends VoidVisitorAdapter<MethodDescription> {

    @Override
    public void visit(IfStmt n, MethodDescription arg) {
        super.visit(n, arg);
        arg.setCyclomaticComplexity(arg.getCyclomaticComplexity() + 1);
    }

    @Override
    public void visit(ForEachStmt n, MethodDescription arg) {
        super.visit(n, arg);
        arg.setCyclomaticComplexity(arg.getCyclomaticComplexity() + 1);
    }

    @Override
    public void visit(ForStmt n, MethodDescription arg) {
        super.visit(n, arg);
        arg.setCyclomaticComplexity(arg.getCyclomaticComplexity() + 1);
    }

    @Override
    public void visit(CatchClause n, MethodDescription arg) {
        super.visit(n, arg);
        arg.setCyclomaticComplexity(arg.getCyclomaticComplexity() + 1);
    }

    @Override
    public void visit(WhileStmt n, MethodDescription arg) {
        super.visit(n, arg);
        arg.setCyclomaticComplexity(arg.getCyclomaticComplexity() + 1);
    }

    @Override
    public void visit(ConditionalExpr n, MethodDescription arg) {
        super.visit(n, arg);
        arg.setCyclomaticComplexity(arg.getCyclomaticComplexity() + 1);
    }

    @Override
    public void visit(SwitchStmt n, MethodDescription arg) {
        super.visit(n, arg);
        arg.setCyclomaticComplexity(arg.getCyclomaticComplexity() + n.getEntries().size() - 1);
    }
}
