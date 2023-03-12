package com.ly.mvc_recovery_evaluation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liuyue
 * @date 2023/3/11 20:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleEvaluateResult {

    private Integer controllerNum;

    private Integer serviceNum;

    private Integer daoNum;

    private Double totalPercent;
}
