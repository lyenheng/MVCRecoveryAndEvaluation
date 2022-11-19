package com.ly.mvc_recovery_evaluation.service.impl;

import com.ly.mvc_recovery_evaluation.entity.EntryNodePO;
import com.ly.mvc_recovery_evaluation.entity.ModuleNodePO;
import com.ly.mvc_recovery_evaluation.entity.ProjectNodePO;
import com.ly.mvc_recovery_evaluation.service.EntryService;
import com.ly.mvc_recovery_evaluation.service.MicroServiceModuleService;
import com.ly.mvc_recovery_evaluation.service.ModuleService;
import com.ly.mvc_recovery_evaluation.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author liuyue
 * @date 2022/11/19 15:51
 */
@Service
public class MicroServiceModuleServiceImpl implements MicroServiceModuleService {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private EntryService entryService;

    @Override
    public List<ModuleNodePO> findMicroServiceModuleByDetectId(Long detectId) {
        // 找到该检测任务对应的所有项目结点信息
        List<ProjectNodePO> projectNodePOS = projectService.findAllProjectByDetectId(detectId);

        List<Long> projectNodeIds = projectNodePOS.stream().map(ProjectNodePO::getId).collect(Collectors.toList());
        //找到该检测任务对应的所有模块结点信息
        List<ModuleNodePO> moduleNodePOS = moduleService.multiFindByProject(projectNodeIds);

        // 找到入口信息列表
        List<EntryNodePO> entryNodePOS = entryService.findAll();
        Set<Long> microServiceModuleIds = entryNodePOS.stream().map(EntryNodePO::getModuleNodeId).collect(Collectors.toSet());

        return moduleNodePOS.stream().filter(moduleNodePO -> microServiceModuleIds.contains(moduleNodePO.getId())).collect(Collectors.toList());

    }
}
