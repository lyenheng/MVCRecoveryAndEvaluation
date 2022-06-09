package com.ly.mvc_recovery_evaluation.entity;

import com.ly.mvc_recovery_evaluation.enums.MethodCalledType;
import lombok.Data;

/**
 * @author liuyue
 * @date 2022/6/8 17:40
 * 被调用方法描述信息
 */
@Data
public class MethodCalledNode {

    /**
     * 被调用方法的变量名
     */
    private String calledClassVarName;

    /**
     * 被调用方法的全限定类名
     */
    private String calledClassFullyName;

    /**
     * 被调用的方法名
     */
    private String calledMethodName;

    /**
     * 方法调用类型： 自调用/ 注入调用
     */
    private MethodCalledType methodCalledType;
}
