package com.ly.mvc_recovery_evaluation.service.impl;

import com.ly.mvc_recovery_evaluation.dao.MethodDescriptionDao;
import com.ly.mvc_recovery_evaluation.entity.MethodDescriptionPO;
import com.ly.mvc_recovery_evaluation.service.MethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/6/13 21:50
 */
@Service
public class MethodServiceImpl implements MethodService {

    @Autowired
    private MethodDescriptionDao methodDescriptionDao;

    @Override
    public Long add(MethodDescriptionPO methodDescriptionPO) {
        MethodDescriptionPO save = methodDescriptionDao.save(methodDescriptionPO);
        return save.getId();
    }

    @Override
    public List<MethodDescriptionPO> findByClass(Long classId) {
        List<MethodDescriptionPO> methodDescriptionPOS = methodDescriptionDao.findAllByClassDescriptionIdEquals(classId);
        return methodDescriptionPOS;
    }
}
