package com.ly.mvc_recovery_evaluation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liuyue
 * @date 2022/4/26 20:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DaoClassDescription extends ClassDescription {

    public String test;

    public DaoClassDescription(ClassDescription classDescription){
        this.setName(classDescription.getName());
        this.setAnnotations(classDescription.getAnnotations());
        this.setClassType(classDescription.getClassType());
        this.setFile(classDescription.getFile());
        this.setPackagePath(classDescription.getPackagePath());
    }
}
