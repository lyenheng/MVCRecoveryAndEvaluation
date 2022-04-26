package com.ly.mvc_recovery_evaluation.visitor;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.ly.mvc_recovery_evaluation.entity.ControllerClassDescription;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


/**
 * @author liuyue
 * @date 2022/4/26 20:46
 */
@Component
public class ControllerClassVisitor extends VoidVisitorAdapter<ControllerClassDescription> {

    /**
     * 处理类
     * @param clz
     * @param arg
     */
    @Override
    public void visit(ClassOrInterfaceDeclaration clz, ControllerClassDescription arg) {
        super.visit(clz, arg);

        // 注解信息
        for (AnnotationExpr annotation : clz.getAnnotations()) {
            if (annotation.getNameAsString().equals("Api")){
                List<Node> childNodes = annotation.getChildNodes();
//                if (childNodes.size() > 1){
//                    childNodes.get(1).
//                }
                Optional<Comment> comment = annotation.getComment();
                System.out.println(comment);
            }
        }

    }
}
