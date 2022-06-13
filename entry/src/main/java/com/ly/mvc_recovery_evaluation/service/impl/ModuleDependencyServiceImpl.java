package com.ly.mvc_recovery_evaluation.service.impl;

import com.ly.mvc_recovery_evaluation.dao.ModuleDependencyDao;
import com.ly.mvc_recovery_evaluation.entity.ModuleDependencyPO;
import com.ly.mvc_recovery_evaluation.service.ModuleDependencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/6/13 21:48
 */
@Service
public class ModuleDependencyServiceImpl implements ModuleDependencyService {

    @Autowired
    private ModuleDependencyDao moduleDependencyDao;

    @Override
    public Long add(ModuleDependencyPO moduleDependencyPO) {
        ModuleDependencyPO save = moduleDependencyDao.save(moduleDependencyPO);
        return save.getId();
    }

    @Override
    public List<ModuleDependencyPO> findByModule(Long moduleNodeId) {
        List<ModuleDependencyPO> moduleDependencyPOS = moduleDependencyDao.findAllByModuleNodeIdEquals(moduleNodeId);
        return moduleDependencyPOS;
    }
}
