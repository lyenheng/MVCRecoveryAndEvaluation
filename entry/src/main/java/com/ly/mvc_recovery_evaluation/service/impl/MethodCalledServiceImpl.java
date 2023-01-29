package com.ly.mvc_recovery_evaluation.service.impl;

import com.ly.mvc_recovery_evaluation.dao.MethodCalledNodeDao;
import com.ly.mvc_recovery_evaluation.entity.MethodCalledNodePO;
import com.ly.mvc_recovery_evaluation.service.MethodCalledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/6/13 21:50
 */
@Service
public class MethodCalledServiceImpl implements MethodCalledService {

    @Autowired
    private MethodCalledNodeDao methodCalledNodeDao;

    @Override
    public Long add(MethodCalledNodePO methodCalledNodePO) {
        MethodCalledNodePO save = methodCalledNodeDao.save(methodCalledNodePO);
        return save.getId();
    }

    @Override
    public List<MethodCalledNodePO> findByMethod(Long methodId) {
        return methodCalledNodeDao.findAllByMethodDescriptionIdEquals(methodId);
    }
}
