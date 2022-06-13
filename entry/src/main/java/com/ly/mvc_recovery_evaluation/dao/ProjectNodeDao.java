package com.ly.mvc_recovery_evaluation.dao;

import com.ly.mvc_recovery_evaluation.entity.ProjectNodePO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/6/13 20:08
 */
@Repository
public interface ProjectNodeDao extends JpaRepository<ProjectNodePO, Long> {

    List<ProjectNodePO> findAll();
}
