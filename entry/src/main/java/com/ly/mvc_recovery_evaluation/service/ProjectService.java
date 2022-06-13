package com.ly.mvc_recovery_evaluation.service;

import com.ly.mvc_recovery_evaluation.entity.ProjectNodePO;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/6/13 20:12
 */
public interface ProjectService {

    List<ProjectNodePO> listProject();

    void add(ProjectNodePO projectNodePO);


}
