package com.ly.mvc_recovery_evaluation.service.impl;

import com.ly.mvc_recovery_evaluation.dao.EntryNodeDao;
import com.ly.mvc_recovery_evaluation.entity.EntryNodePO;
import com.ly.mvc_recovery_evaluation.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/6/13 21:51
 */
@Service
public class EntryServiceImpl implements EntryService {

    @Autowired
    private EntryNodeDao entryNodeDao;

    @Override
    public Long add(EntryNodePO entryNodePO) {
        EntryNodePO save = entryNodeDao.save(entryNodePO);
        return save.getId();
    }

    @Override
    public List<EntryNodePO> findByModule(Long moduleId) {
        return entryNodeDao.findAllByModuleNodeIdEquals(moduleId);
    }

    @Override
    public List<EntryNodePO> findAll() {
        return entryNodeDao.findAll();
    }
}
