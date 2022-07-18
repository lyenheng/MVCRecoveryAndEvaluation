package com.ly.mvc_recovery_evaluation.dao;

import com.ly.mvc_recovery_evaluation.entity.DetectProcedure;
import com.ly.mvc_recovery_evaluation.entity.QDetectProcedure;
import com.ly.mvc_recovery_evaluation.vo.SearchProcedureVO;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;


/**
 * @author liuyue
 * @date 2022/6/14 17:20
 */
@Repository
public interface DetectProcedureDao extends JpaRepository<DetectProcedure, Long> {


}
