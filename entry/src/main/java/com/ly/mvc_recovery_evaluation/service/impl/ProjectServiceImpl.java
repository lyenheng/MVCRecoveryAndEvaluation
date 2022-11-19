package com.ly.mvc_recovery_evaluation.service.impl;

import com.ly.mvc_recovery_evaluation.dao.ProjectNodeDao;
import com.ly.mvc_recovery_evaluation.entity.ProjectNodePO;
import com.ly.mvc_recovery_evaluation.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/6/13 20:12
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectNodeDao projectNodeDao;

    @Override
    public List<ProjectNodePO> listProject() {
        return projectNodeDao.findAll();
    }

    @Override
    public ProjectNodePO add(ProjectNodePO projectNodePO) {
        return projectNodeDao.save(projectNodePO);
    }

    @Override
    public List<ProjectNodePO> findAllProjectByDetectId(Long detectId) {
        return projectNodeDao.findAllByProcedureId(detectId);
    }
}
