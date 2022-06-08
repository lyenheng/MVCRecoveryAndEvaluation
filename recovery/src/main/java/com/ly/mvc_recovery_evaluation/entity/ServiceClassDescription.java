package com.ly.mvc_recovery_evaluation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/4/26 20:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceClassDescription extends ClassDescription {

    /**
     * 实现的接口
     */
    public List<String> interfaceServices;

    /**
     * 继承的接口
     */
    public List<String> extendsServices;

    public ServiceClassDescription(ClassDescription classDescription){
        this.setName(classDescription.getName());
        this.setAnnotations(classDescription.getAnnotations());
        this.setClassType(classDescription.getClassType());
        this.setFile(classDescription.getFile());
        this.setFullyQualifiedName(classDescription.getFullyQualifiedName());
        this.setInjectionInfos(classDescription.getInjectionInfos());
        this.setMethodDescriptionList(classDescription.getMethodDescriptionList());
    }

}
