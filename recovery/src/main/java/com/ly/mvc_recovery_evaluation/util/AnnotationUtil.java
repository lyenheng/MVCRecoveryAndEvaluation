package com.ly.mvc_recovery_evaluation.util;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 获取注解的key值
 * @author liuyue
 * @date 2022/4/27 12:48
 */
public class AnnotationUtil {

    public static String getValue(AnnotationExpr annotation, String key){
        if (annotation instanceof NormalAnnotationExpr && !StringUtils.isEmpty(key)){
            List<Node> childNodes = annotation.getChildNodes();
            for (Node childNode : childNodes) {
                if (childNode instanceof MemberValuePair){
                    MemberValuePair memberValuePair = (MemberValuePair)childNode;
                    if (memberValuePair.getName().asString().equals(key)){
                        return memberValuePair.getValue().toString();
                    }
                }
            }
        }else if (annotation instanceof SingleMemberAnnotationExpr){
            if (key.equals("value")){
                return ((SingleMemberAnnotationExpr) annotation).getMemberValue().toString();
            }
        }
        return null;
    }
}
