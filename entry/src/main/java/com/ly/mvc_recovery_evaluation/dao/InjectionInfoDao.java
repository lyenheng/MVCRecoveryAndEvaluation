package com.ly.mvc_recovery_evaluation.dao;

import com.ly.mvc_recovery_evaluation.entity.InjectionInfoPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/6/13 20:34
 */
@Repository
public interface InjectionInfoDao extends JpaRepository<InjectionInfoPO, Long> {

    List<InjectionInfoPO> findAllByClassDescriptionIdEquals(Long classId);
}
