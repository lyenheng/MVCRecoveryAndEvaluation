package com.ly.mvc_recovery_evaluation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author liuyue
 * @date 2023/3/11 20:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaintainabilityResult {

    /**
     * 模块化
     */
    private ModuleEvaluateResult moduleEvaluateResult;

    /**
     * 易分析性
     */
    private AnalyzabilityResult analyzabilityResult;

    /**
     * 易修改性
     */
    private List<ModifiabilityResult> modifiabilityResults;
}
