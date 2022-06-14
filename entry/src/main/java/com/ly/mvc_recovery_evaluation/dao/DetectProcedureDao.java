package com.ly.mvc_recovery_evaluation.dao;

import com.ly.mvc_recovery_evaluation.entity.DetectProcedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author liuyue
 * @date 2022/6/14 17:20
 */
@Repository
public interface DetectProcedureDao extends JpaRepository<DetectProcedure, Long> {

}
