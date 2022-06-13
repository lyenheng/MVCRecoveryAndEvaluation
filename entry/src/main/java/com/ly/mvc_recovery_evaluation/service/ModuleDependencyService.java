package com.ly.mvc_recovery_evaluation.service;

import com.ly.mvc_recovery_evaluation.entity.ModuleDependencyPO;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/6/13 21:43
 */
public interface ModuleDependencyService {

    Long add(ModuleDependencyPO moduleDependencyPO);

    List<ModuleDependencyPO> findByModule(Long moduleNodeId);
}
