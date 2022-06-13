package com.ly.mvc_recovery_evaluation.dao;

import com.ly.mvc_recovery_evaluation.entity.EntryNodePO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/6/13 20:35
 */
@Repository
public interface EntryNodeDao extends JpaRepository<EntryNodePO, Long> {

    List<EntryNodePO> findAllByModuleNodeIdEquals(Long moduleId);
}
