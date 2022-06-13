package com.ly.mvc_recovery_evaluation.service;

import com.ly.mvc_recovery_evaluation.entity.MethodCalledNodePO;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/6/13 21:43
 */
public interface MethodCalledService {

    Long add(MethodCalledNodePO methodCalledNodePO);

    List<MethodCalledNodePO> findByMethod(Long methodId);
}
