package com.ly.mvc_recovery_evaluation.service;

import com.ly.mvc_recovery_evaluation.entity.ClassDescriptionPO;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/6/13 21:45
 */
public interface ClassService {

    Long add(ClassDescriptionPO classDescriptionPO);

    List<ClassDescriptionPO> findByModule(Long moduleId);
}
