package com.ly.mvc_recovery_evaluation.service;

import com.ly.mvc_recovery_evaluation.entity.ClassDescriptionPO;
import com.ly.mvc_recovery_evaluation.vo.ClassDescriptionVO;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/6/13 21:45
 */
public interface ClassService {

    Long add(ClassDescriptionPO classDescriptionPO);

    List<ClassDescriptionPO> findByModule(Long moduleId);

    List<ClassDescriptionPO> multiFindByModule(List<Long> moduleIds);

    /**
     * 根据入口模块id获取该子服务下的所有模块中的类信息
     * @param entryModuleId
     * @return
     */
    List<ClassDescriptionVO> getAllClassByEntryModule(Long entryModuleId);

    List<ClassDescriptionPO> findByFullyQualifiedName(String fullyQualifiedName);

    ClassDescriptionPO findById(Long classId);
}
