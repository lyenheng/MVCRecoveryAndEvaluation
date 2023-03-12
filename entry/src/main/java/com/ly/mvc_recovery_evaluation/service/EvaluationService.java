package com.ly.mvc_recovery_evaluation.service;

import com.ly.mvc_recovery_evaluation.entity.EvaluationPO;

/**
 * @author liuyue
 * @date 2023/3/11 20:39
 */
public interface EvaluationService {

    EvaluationPO getResult(Long moduleId);

    EvaluationPO getResult1(Long moduleId);
}
