package com.ly.mvc_recovery_evaluation.service.impl;

import com.ly.mvc_recovery_evaluation.dao.DatabaseDescriptionDao;
import com.ly.mvc_recovery_evaluation.entity.DatabaseDescriptionPO;
import com.ly.mvc_recovery_evaluation.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/6/13 21:52
 */
@Service
public class DatabaseServiceImpl implements DatabaseService {

    @Autowired
    private DatabaseDescriptionDao databaseDescriptionDao;

    @Override
    public Long add(DatabaseDescriptionPO databaseDescriptionPO) {
        DatabaseDescriptionPO save = databaseDescriptionDao.save(databaseDescriptionPO);
        return save.getId();
    }

    @Override
    public List<DatabaseDescriptionPO> findByModule(Long moduleId) {
        List<DatabaseDescriptionPO> databaseDescriptionPOS = databaseDescriptionDao.findAllByModuleNodeIdEquals(moduleId);
        return databaseDescriptionPOS;
    }
}
