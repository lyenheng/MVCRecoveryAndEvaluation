package com.ly.mvc_recovery_evaluation;

import com.ly.mvc_recovery_evaluation.builder.ModuleDependencyGraphBuilder;
import com.ly.mvc_recovery_evaluation.entity.ApplicationConfig;
import com.ly.mvc_recovery_evaluation.entity.ModuleDependencyGraph;
import com.ly.mvc_recovery_evaluation.entity.ModuleNode;
import com.ly.mvc_recovery_evaluation.entity.ProjectNode;
import com.ly.mvc_recovery_evaluation.extractor.ConfigExtractor;
import com.ly.mvc_recovery_evaluation.parser.ConfigParser;
import com.ly.mvc_recovery_evaluation.parser.DependencyParser;
import com.ly.mvc_recovery_evaluation.parser.ModuleParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
    private ConfigParser configParser;

    @Autowired
    private ModuleDependencyGraphBuilder moduleDependencyGraphBuilder;

    public void recover(File rootFile){

        // 提取模块信息
        ProjectNode projectNode = new ProjectNode();
        projectNode.setProjectFile(rootFile);
        projectNode.setProjectName(rootFile.getName());

        List<ModuleNode> moduleNodes = moduleParser.parse(projectNode);

        for (ModuleNode moduleNode : moduleNodes) {
            moduleNode.setModuleDependencies(dependencyParser.parse(moduleNode));
            ApplicationConfig applicationConfig = configParser.parse(moduleNode);
            if (applicationConfig != null){
                projectNode.setApplicationConfig(applicationConfig);
            }
        }

        projectNode.setModuleNodeList(moduleNodes);

        // 构建模块依赖图
        ModuleDependencyGraph moduleDependencyGraph = moduleDependencyGraphBuilder.build(moduleNodes);

    }
}
