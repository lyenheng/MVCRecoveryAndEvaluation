package com.ly.mvc_recovery_evaluation.entity;

import lombok.Data;

import java.util.List;

/**
 * 模块依赖图
 * @author liuyue
 * @date 2022/4/24 16:15
 */

@Data
public class ModuleDependencyGraph {

    /**
     * 节点信息
     */
    private List<ModuleNode> nodes;

    /**
     * 边信息
     */
    private List<ModuleDependencyGraphEdge> edges;
}
