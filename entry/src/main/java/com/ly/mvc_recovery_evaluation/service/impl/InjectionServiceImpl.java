package com.ly.mvc_recovery_evaluation.service.impl;

import com.ly.mvc_recovery_evaluation.dao.InjectionInfoDao;
import com.ly.mvc_recovery_evaluation.entity.InjectionInfoPO;
import com.ly.mvc_recovery_evaluation.service.InjectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/6/13 21:51
 */
@Service
public class InjectionServiceImpl implements InjectionService {

    @Autowired
    private InjectionInfoDao injectionInfoDao;

    @Override
    public Long add(InjectionInfoPO injectionInfoPO) {
        InjectionInfoPO save = injectionInfoDao.save(injectionInfoPO);
        return save.getId();
    }

    @Override
    public List<InjectionInfoPO> findByClass(Long classId) {
        List<InjectionInfoPO> injectionInfoPOS = injectionInfoDao.findAllByClassDescriptionIdEquals(classId);
        return injectionInfoPOS;
    }
}
