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
public class EfficiencyResult {

    private Double cyclomaticComplexityAverage;

    private List<String> middleCyclomaticComplexity;

    private List<String> highCyclomaticComplexity;

}
