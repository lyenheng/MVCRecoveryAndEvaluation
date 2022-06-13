package com.ly.mvc_recovery_evaluation.service.impl;

import com.ly.mvc_recovery_evaluation.dao.ModuleNodeDao;
import com.ly.mvc_recovery_evaluation.entity.ModuleNodePO;
import com.ly.mvc_recovery_evaluation.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        List<ModuleNodePO> moduleNodePOList = moduleNodeDao.findAllByProjectNodeIdEquals(projectId);
        return moduleNodePOList;
    }
}
