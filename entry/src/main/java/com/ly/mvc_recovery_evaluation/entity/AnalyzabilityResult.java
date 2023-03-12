package com.ly.mvc_recovery_evaluation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liuyue
 * @date 2023/3/11 20:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalyzabilityResult {

    private Double controllerLayerComplexity;

    private Double crossLayerCallVisibility;

    private Double selfCallProportion;

    private Double  analyzability;
}
