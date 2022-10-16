package com.ly.mvc_recovery_evaluation.service.impl;

import com.ly.mvc_recovery_evaluation.dao.ModuleDependencyGraphDao;
import com.ly.mvc_recovery_evaluation.service.ModuleDependencyGraphService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liuyue
 * @date 2022/10/16 15:57
 */
@Service
public class ModuleDependencyGraphServiceImpl implements ModuleDependencyGraphService {

    @Autowired
    private ModuleDependencyGraphDao moduleDependencyGraphDao;

    @Override
    public Document findByDetectId(Long detectId) {
        return moduleDependencyGraphDao.findByDetectId(detectId);
    }
}
