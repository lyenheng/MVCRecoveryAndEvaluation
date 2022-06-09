package com.ly.mvc_recovery_evaluation.visitor;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.ly.mvc_recovery_evaluation.entity.ClassDescription;
import com.ly.mvc_recovery_evaluation.entity.InjectionInfo;
import com.ly.mvc_recovery_evaluation.entity.MethodCalledNode;
import com.ly.mvc_recovery_evaluation.entity.MethodDescription;
import com.ly.mvc_recovery_evaluation.enums.MethodCalledType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author liuyue
 * @date 2022/6/8 21:47
 */
@Component
public class MethodVisitor extends VoidVisitorAdapter<ClassDescription> {

    @Autowired
    private MethodCalledVisitor methodCalledVisitor;

    /**
     * 保存方法信息
     * @param n
     * @param arg
     */
    @Override
    public void visit(MethodDeclaration n, ClassDescription arg) {
        super.visit(n, arg);
        MethodDescription methodDescription = new MethodDescription();
        methodDescription.setMethodDeclaration(n);
        List<MethodCalledNode> methodCalledNodes = new ArrayList<>();
        methodDescription.setMethodCalledNodeList(methodCalledNodes);

        arg.getMethodDescriptionList().add(methodDescription);

        n.accept(methodCalledVisitor, methodDescription );

        filterCalledMethod(arg, methodDescription);

    }

    /**
     * 过滤掉非注入的函数调用
     * @param classDescription
     * @param methodDescription
     */
    void filterCalledMethod(ClassDescription classDescription, MethodDescription methodDescription){

        Map<String, String> injectionMap = new HashMap<>();
        List<InjectionInfo> injectionInfos = classDescription.getInjectionInfos();
        if (injectionInfos != null && injectionInfos.size() > 0){
            for (InjectionInfo injectionInfo : injectionInfos) {
                injectionMap.put(injectionInfo.getVarName(), injectionInfo.getFullyQualifiedName());
            }
        }

        List<MethodCalledNode> collect = methodDescription.getMethodCalledNodeList().stream().filter(methodCalledNode -> {
            String calledClassVarName = methodCalledNode.getCalledClassVarName();
            if (injectionMap.get(calledClassVarName) != null) {
                methodCalledNode.setCalledClassFullyName(injectionMap.get(calledClassVarName));
                methodCalledNode.setMethodCalledType(MethodCalledType.INJECT_CALLED);
                return true;
            } else if (StringUtils.isEmpty(calledClassVarName)){
                methodCalledNode.setMethodCalledType(MethodCalledType.SELF_CALLED);
                return true;
            }else {
                return false;
            }
        }).collect(Collectors.toList());

        methodDescription.setMethodCalledNodeList(collect);
    }
}
