package com.ly.mvc_recovery_evaluation.entity;

import lombok.Data;

/**
 * @author liuyue
 * @date 2023/3/11 20:36
 */
@Data
public class EvaluationPO {

    private MaintainabilityResult maintainabilityResult;

    private FunctionalityResult functionalityResult;

    private EfficiencyResult efficiencyResult;
}
