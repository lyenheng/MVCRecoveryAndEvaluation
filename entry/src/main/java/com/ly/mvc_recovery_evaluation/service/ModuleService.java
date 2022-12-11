package com.ly.mvc_recovery_evaluation.service;

import com.ly.mvc_recovery_evaluation.entity.ModuleNodePO;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/6/13 21:42
 */
public interface ModuleService {

    Long addModuleNode(ModuleNodePO moduleNodePO);

    List<ModuleNodePO> findByProject(Long projectId);

    List<ModuleNodePO> multiFindByProject(List<Long> projectIds);

    Long findProjectByModule(Long moduleId);

    ModuleNodePO findById(Long moduleId);
}
