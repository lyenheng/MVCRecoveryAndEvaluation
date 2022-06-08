package com.ly.mvc_recovery_evaluation.entity;

import com.github.javaparser.ast.body.MethodDeclaration;
import lombok.Data;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/6/8 17:36
 */
@Data
public class MethodDescription {

    /**
     * 本方法结点信息
     */
    private MethodDeclaration methodDeclaration;

    /**
     * 调用的方法信息
     */
    private List<MethodCalledNode> methodCalledNodeList;
}
