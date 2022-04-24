package com.ly.mvc_recovery_evaluation;

import com.ly.mvc_recovery_evaluation.builder.ModuleDependencyGraphBuilder;
import com.ly.mvc_recovery_evaluation.entity.ModuleDependencyGraph;
import com.ly.mvc_recovery_evaluation.entity.ModuleNode;
import com.ly.mvc_recovery_evaluation.entity.ProjectNode;
import com.ly.mvc_recovery_evaluation.parser.DependencyParser;
import com.ly.mvc_recovery_evaluation.parser.ModuleParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * @author liuyue
 * @date 2022/4/24 13:15
 */
@Component
public class MvcRecovery {

    @Autowired
    private ModuleParser moduleParser;

    @Autowired
    private DependencyParser dependencyParser;

    @Autowired
    private ModuleDependencyGraphBuilder moduleDependencyGraphBuilder;

    public void recover(File rootFile){

        ProjectNode projectNode = new ProjectNode();
        projectNode.setProjectFile(rootFile);
        projectNode.setProjectName(rootFile.getName());

        List<ModuleNode> moduleNodes = moduleParser.parse(projectNode);

        for (ModuleNode moduleNode : moduleNodes) {
            moduleNode.setModuleDependencies(dependencyParser.parse(moduleNode));
        }

        projectNode.setModuleNodeList(moduleNodes);

        ModuleDependencyGraph moduleDependencyGraph = moduleDependencyGraphBuilder.build(moduleNodes);

        System.out.println(moduleDependencyGraph);
    }
}
