package com.ly.mvc_recovery_evaluation.service.impl;

import com.ly.mvc_recovery_evaluation.dao.ClassDescriptionDao;
import com.ly.mvc_recovery_evaluation.entity.ClassDescriptionPO;
import com.ly.mvc_recovery_evaluation.service.ClassService;
import com.ly.mvc_recovery_evaluation.service.MicroServiceModuleService;
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

    @Autowired
    private MicroServiceModuleService microServiceModuleService;

    @Override
    public Long add(ClassDescriptionPO classDescriptionPO) {
        ClassDescriptionPO save = classDescriptionDao.save(classDescriptionPO);
        return save.getId();
    }

    @Override
    public List<ClassDescriptionPO> findByModule(Long moduleId) {
        return classDescriptionDao.findAllByModuleNodeIdEquals(moduleId);
    }

    @Override
    public List<ClassDescriptionPO> multiFindByModule(List<Long> moduleIds) {
        return classDescriptionDao.findAllByModuleNodeIdIn(moduleIds);
    }

    /**
     * 根据入口模块id获取该子服务下的所有模块中的类信息
     * @param entryModuleId
     * @return
     */
    @Override
    public List<ClassDescriptionPO> getAllClassByEntryModule(Long entryModuleId) {
        List<Long> modulesByEntryModule = microServiceModuleService.findModulesByEntryModule(entryModuleId);
        return classDescriptionDao.findAllByModuleNodeIdIn(modulesByEntryModule);
    }


}
