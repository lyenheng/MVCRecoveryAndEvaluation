package com.ly.mvc_recovery_evaluation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * @author liuyue
 * @date 2022/4/26 19:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControllerClassDescription extends ClassDescription {

    /**
     * 描述信息
     */
    private String apiDescription;

    private String parentRequestPath;

    /**
     * 接口信息
     */
    private List<ApiInfo> apiInfos;

    public ControllerClassDescription(ClassDescription classDescription){
        this.setName(classDescription.getName());
        this.setAnnotations(classDescription.getAnnotations());
        this.setClassType(classDescription.getClassType());
        this.setFile(classDescription.getFile());
        this.setFullyQualifiedName(classDescription.getFullyQualifiedName());
        this.setInjectionInfos(classDescription.getInjectionInfos());
        this.setMethodDescriptionList(classDescription.getMethodDescriptionList());
    }
}
