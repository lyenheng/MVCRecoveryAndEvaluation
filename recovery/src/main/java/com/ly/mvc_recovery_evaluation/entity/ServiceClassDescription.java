package com.ly.mvc_recovery_evaluation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liuyue
 * @date 2022/4/26 20:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceClassDescription extends ClassDescription {

    public String test;

    public ServiceClassDescription(ClassDescription classDescription){
        this.setName(classDescription.getName());
        this.setAnnotations(classDescription.getAnnotations());
        this.setClassType(classDescription.getClassType());
        this.setFile(classDescription.getFile());
        this.setPackagePath(classDescription.getPackagePath());
    }

}
