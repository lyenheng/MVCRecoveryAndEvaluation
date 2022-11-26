package com.ly.mvc_recovery_evaluation.service;

import com.ly.mvc_recovery_evaluation.entity.ModuleNodePO;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/11/19 15:51
 */
public interface MicroServiceModuleService {

    List<ModuleNodePO> findMicroServiceModuleByDetectId(Long detectId);

    /**
     * 根据入口模块id找到该子服务下的所有模块id
     * @param entryModuleId
     * @return
     */
    List<Long> findModulesByEntryModule(Long entryModuleId);
}
