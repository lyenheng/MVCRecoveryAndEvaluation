package com.ly.mvc_recovery_evaluation.service.impl;

import com.ly.mvc_recovery_evaluation.entity.*;
import com.ly.mvc_recovery_evaluation.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    private ModuleDependencyService moduleDependencyService;

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

    /**
     * 根据入口模块id找到同一个服务下的模块
     * @param entryModuleId
     * @return
     */
    @Override
    public List<Long> findModulesByEntryModule(Long entryModuleId) {
        // 根据当前入口模块找到对应的projectId
        Long projectId = moduleService.findProjectByModule(entryModuleId);

        // 当前模块的所有依赖信息
        List<ModuleDependencyPO> moduleDependencyPOS = moduleDependencyService.findByModule(entryModuleId);
        Set<ModuleCoordinate> moduleCoordinateSet = moduleDependencyPOS.stream().map(moduleDependencyPO -> new ModuleCoordinate(moduleDependencyPO.getGroupId(), moduleDependencyPO.getArtifactId())).collect(Collectors.toSet());

        // 当前projectId对应的所有模块
        List<ModuleNodePO> moduleNodePOs = moduleService.findByProject(projectId);

        ArrayList<Long> moduleIds = new ArrayList<>();

        for (ModuleNodePO moduleNodePO : moduleNodePOs) {
            ModuleCoordinate moduleCoordinate = new ModuleCoordinate(moduleNodePO.getGroupId(), moduleNodePO.getArtifactId());
            if (moduleCoordinateSet.contains(moduleCoordinate)){
                moduleIds.add(moduleNodePO.getId());
            }
        }

        return moduleIds;
    }
}
