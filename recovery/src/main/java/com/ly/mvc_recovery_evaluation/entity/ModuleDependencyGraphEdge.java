package com.ly.mvc_recovery_evaluation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 模块依赖图的边
 * @author liuyue
 * @date 2022/4/24 16:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleDependencyGraphEdge {

    private ModuleNode startNode;

    private ModuleNode endNode;

}
