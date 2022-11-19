package com.ly.mvc_recovery_evaluation.service;

import com.ly.mvc_recovery_evaluation.entity.EntryNodePO;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/6/13 21:44
 */
public interface EntryService {

    Long add(EntryNodePO entryNodePO);

    List<EntryNodePO> findByModule(Long moduleId);

    List<EntryNodePO> findAll();
}
