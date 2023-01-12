package com.ly.mvc_recovery_evaluation.service.impl;

import com.ly.mvc_recovery_evaluation.dao.ClassDescriptionDao;
import com.ly.mvc_recovery_evaluation.entity.ClassDescriptionPO;
import com.ly.mvc_recovery_evaluation.entity.MethodDescriptionPO;
import com.ly.mvc_recovery_evaluation.entity.ModuleNodePO;
import com.ly.mvc_recovery_evaluation.service.ClassService;
import com.ly.mvc_recovery_evaluation.service.MethodService;
import com.ly.mvc_recovery_evaluation.service.MicroServiceModuleService;
import com.ly.mvc_recovery_evaluation.service.ModuleService;
import com.ly.mvc_recovery_evaluation.vo.ClassDescriptionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    private MethodService methodService;

    @Autowired
    private ModuleService moduleService;

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
    public List<ClassDescriptionVO> getAllClassByEntryModule(Long entryModuleId) {
//        List<Long> modulesByEntryModule = microServiceModuleService.findModulesByEntryModule(entryModuleId);
        List<Long> modulesByEntryModule = microServiceModuleService.findModulesByEntryModule(entryModuleId);
        List<ClassDescriptionPO> classDescriptionPOList = classDescriptionDao.findAllByModuleNodeIdIn(modulesByEntryModule);

        ArrayList<ClassDescriptionVO> classDescriptionVOS = new ArrayList<>();
        for (ClassDescriptionPO classDescriptionPO : classDescriptionPOList) {
            // 获取每个类中的方法个数
            List<MethodDescriptionPO> byClass = methodService.findByClass(classDescriptionPO.getId());
            ClassDescriptionVO classDescriptionVO = new ClassDescriptionVO(classDescriptionPO, byClass.size());
            // 获取模块名
            ModuleNodePO module = moduleService.findById(classDescriptionVO.getModuleNodeId());
            classDescriptionVO.setModuleName(module.getModuleName());
            classDescriptionVOS.add(classDescriptionVO);

        }
        return classDescriptionVOS;
    }

    /**
     * 根据全限定类名获取类信息
     * @param fullyQualifiedName
     * @return
     */
    @Override
    public List<ClassDescriptionPO> findByFullyQualifiedName(String fullyQualifiedName) {
        List<ClassDescriptionPO> classDescriptionPOS = classDescriptionDao.findClassDescriptionPOSByFullyQualifiedNameEndsWithIgnoreCase(fullyQualifiedName);
        if (classDescriptionPOS.size() > 0){
            return classDescriptionPOS;
        }
        return null;
    }


}
