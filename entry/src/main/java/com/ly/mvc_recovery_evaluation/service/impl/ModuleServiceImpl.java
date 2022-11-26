package com.ly.mvc_recovery_evaluation.service.impl;

import com.ly.mvc_recovery_evaluation.dao.ModuleNodeDao;
import com.ly.mvc_recovery_evaluation.entity.ModuleNodePO;
import com.ly.mvc_recovery_evaluation.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author liuyue
 * @date 2022/6/13 21:47
 */
@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleNodeDao moduleNodeDao;

    @Override
    public Long addModuleNode(ModuleNodePO moduleNodePO) {

        ModuleNodePO save = moduleNodeDao.save(moduleNodePO);
        return save.getId();
    }

    @Override
    public List<ModuleNodePO> findByProject(Long projectId) {
        return moduleNodeDao.findAllByProjectNodeIdEquals(projectId);
    }

    @Override
    public List<ModuleNodePO> multiFindByProject(List<Long> projectIds) {
        return moduleNodeDao.findAllByProjectNodeIdIn(projectIds);
    }

    /**
     * 根据moduleId找到projectId
     * @param moduleId
     * @return
     */
    @Override
    public Long findProjectByModule(Long moduleId) {
        Optional<ModuleNodePO> byId = moduleNodeDao.findById(moduleId);
        return byId.map(ModuleNodePO::getProjectNodeId).orElse(null);
    }
}
