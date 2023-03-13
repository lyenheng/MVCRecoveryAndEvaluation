package com.ly.mvc_recovery_evaluation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liuyue
 * @date 2023/3/11 20:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifiabilityResult {


    private String className;

    private String fullyQualifiedName ;

    /**
     * 层次
     */
    private String classType;

    private Double modifiability;

}
