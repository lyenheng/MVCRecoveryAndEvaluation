package com.ly.mvc_recovery_evaluation.builder;

import com.ly.mvc_recovery_evaluation.entity.ModuleCoordinate;
import com.ly.mvc_recovery_evaluation.entity.ModuleDependencyGraph;
import com.ly.mvc_recovery_evaluation.entity.ModuleDependencyGraphEdge;
import com.ly.mvc_recovery_evaluation.entity.ModuleNode;
import com.ly.mvc_recovery_evaluation.util.ModuleCoordinateConvertUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuyue
 * @date 2022/4/24 16:15
 */
@Component
public class ModuleDependencyGraphBuilder {

    public ModuleDependencyGraph build(List<ModuleNode> moduleNodes){

        ModuleDependencyGraph moduleDependencyGraph = new ModuleDependencyGraph();
        moduleDependencyGraph.setNodes(moduleNodes);

        // 构建模块依赖关系图的边信息
        Map<String, ModuleNode> moduleNodeToModuleCoordinate = new HashMap<>();
        for (ModuleNode moduleNode : moduleNodes) {
            moduleNodeToModuleCoordinate.put(ModuleCoordinateConvertUtil.convert(moduleNode.getModuleCoordinate()), moduleNode);
        }

        List<ModuleDependencyGraphEdge> graphEdges = new ArrayList<>();
        for (ModuleNode moduleNode : moduleNodes) {
            List<ModuleCoordinate> moduleDependencies = moduleNode.getModuleDependencies();
            if (moduleDependencies.size() > 0){
                for (ModuleCoordinate moduleDependency : moduleDependencies) {
                    if (moduleNodeToModuleCoordinate.get(ModuleCoordinateConvertUtil.convert(moduleDependency)) != null){
                        ModuleDependencyGraphEdge graphEdge = new ModuleDependencyGraphEdge(moduleNodeToModuleCoordinate.get(ModuleCoordinateConvertUtil.convert(moduleDependency)), moduleNode);
                        graphEdges.add(graphEdge);
                    }
                }
            }
        }

        if (graphEdges.size() > 0){
            moduleDependencyGraph.setEdges(graphEdges);
        }

        return moduleDependencyGraph;
    }
}
