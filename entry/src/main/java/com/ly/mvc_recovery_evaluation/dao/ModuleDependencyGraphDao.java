package com.ly.mvc_recovery_evaluation.dao;

import com.ly.mvc_recovery_evaluation.entity.ModuleDependencyGraph;
import org.bson.Document;

/**
 * @author liuyue
 * @date 2022/10/16 14:29
 */
public interface ModuleDependencyGraphDao {

    void add(Long detectId, ModuleDependencyGraph moduleDependencyGraph);

    Document findByDetectId(Long detectId);

}
