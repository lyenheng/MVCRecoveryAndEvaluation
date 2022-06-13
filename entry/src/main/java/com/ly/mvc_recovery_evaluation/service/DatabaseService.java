package com.ly.mvc_recovery_evaluation.service;

import com.ly.mvc_recovery_evaluation.entity.DatabaseDescriptionPO;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/6/13 21:45
 */
public interface DatabaseService {

    Long add(DatabaseDescriptionPO databaseDescriptionPO);

    List<DatabaseDescriptionPO> findByProject(Long projectId);
}
