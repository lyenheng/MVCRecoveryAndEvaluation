package com.ly.mvc_recovery_evaluation.service;

import org.bson.Document;

/**
 * @author liuyue
 * @date 2022/10/16 15:57
 */
public interface ModuleDependencyGraphService {

    Document findByDetectId(Long detectId);
}
