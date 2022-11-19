package com.ly.mvc_recovery_evaluation.service;

import com.ly.mvc_recovery_evaluation.entity.ModuleNodePO;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/11/19 15:51
 */
public interface MicroServiceModuleService {

    List<ModuleNodePO> findMicroServiceModuleByDetectId(Long detectId);
}
