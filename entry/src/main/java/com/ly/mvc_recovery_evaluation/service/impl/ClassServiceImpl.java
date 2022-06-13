package com.ly.mvc_recovery_evaluation.service.impl;

import com.ly.mvc_recovery_evaluation.dao.ClassDescriptionDao;
import com.ly.mvc_recovery_evaluation.entity.ClassDescriptionPO;
import com.ly.mvc_recovery_evaluation.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/6/13 21:52
 */
@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassDescriptionDao classDescriptionDao;

    @Override
    public Long add(ClassDescriptionPO classDescriptionPO) {
        ClassDescriptionPO save = classDescriptionDao.save(classDescriptionPO);
        return save.getId();
    }

    @Override
    public List<ClassDescriptionPO> findByModule(Long moduleId) {
        List<ClassDescriptionPO> classDescriptionPOS = classDescriptionDao.findAllByModuleNodeIdEquals(moduleId);
        return classDescriptionPOS;
    }
}
