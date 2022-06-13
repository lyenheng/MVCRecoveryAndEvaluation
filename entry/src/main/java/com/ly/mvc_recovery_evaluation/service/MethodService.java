package com.ly.mvc_recovery_evaluation.service;

import com.ly.mvc_recovery_evaluation.entity.MethodDescriptionPO;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/6/13 21:43
 */
public interface MethodService {

    Long add(MethodDescriptionPO methodDescriptionPO);

    List<MethodDescriptionPO> findByClass(Long classId);

}
