package com.ly.mvc_recovery_evaluation.service;

import com.ly.mvc_recovery_evaluation.entity.InjectionInfoPO;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/6/13 21:44
 */
public interface InjectionService {

    Long add(InjectionInfoPO injectionInfoPO);

    List<InjectionInfoPO> findByClass(Long classId);
}
