package com.ly.mvc_recovery_evaluation.dao;

import com.ly.mvc_recovery_evaluation.entity.ModuleDependencyPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/6/13 20:33
 */
@Repository
public interface ModuleDependencyDao extends JpaRepository<ModuleDependencyPO, Long> {

    List<ModuleDependencyPO> findAllByModuleNodeIdEquals(Long moduleNodeId);
}

