package com.ly.mvc_recovery_evaluation.visitor;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.ly.mvc_recovery_evaluation.entity.DaoClassDescription;
import com.ly.mvc_recovery_evaluation.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


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

        arg.setInterfaceServices(new ArrayList<>());
        arg.setExtendsServices(new ArrayList<>());

        commonService.getExtendsOrImplementsFullyQualifiedName(clz, arg);
    }
}
