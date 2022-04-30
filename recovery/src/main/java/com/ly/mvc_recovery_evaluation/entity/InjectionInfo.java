package com.ly.mvc_recovery_evaluation.entity;

import lombok.Data;

/**
 * 注入信息
 * @author liuyue
 * @date 2022/4/30 20:43
 */
@Data
public class InjectionInfo {

    /**
     * 注入类的变量名
     */
    private String varName;

    /**
     * 注入类的全限定类名
     */
    private String fullyQualifiedName;
}
