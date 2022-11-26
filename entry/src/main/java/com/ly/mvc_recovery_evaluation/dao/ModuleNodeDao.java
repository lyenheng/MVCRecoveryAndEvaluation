package com.ly.mvc_recovery_evaluation.dao;

import com.ly.mvc_recovery_evaluation.entity.ModuleNodePO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/6/13 20:32
 */
@Repository
public interface ModuleNodeDao extends JpaRepository<ModuleNodePO, Long> {

    List<ModuleNodePO> findAllByProjectNodeIdEquals(Long projectId);

    List<ModuleNodePO> findAllByProjectNodeIdIn(List<Long> projectId);

}
