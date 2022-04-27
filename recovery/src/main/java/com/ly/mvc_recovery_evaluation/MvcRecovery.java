package com.ly.mvc_recovery_evaluation;

import com.ly.mvc_recovery_evaluation.builder.ModuleDependencyGraphBuilder;
import com.ly.mvc_recovery_evaluation.entity.*;
import com.ly.mvc_recovery_evaluation.enums.ModuleType;
import com.ly.mvc_recovery_evaluation.parser.*;
import com.ly.mvc_recovery_evaluation.util.ClassSearchUtil;
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
    private ConfigParser configParser;

    @Autowired
    private EntryParser entryParser;

    @Autowired
    private ClassParser classParser;

    @Autowired
    private ControllerClassParser controllerClassParser;

    @Autowired
    private ServiceClassParser serviceClassParser;

    @Autowired
    private DaoClassParser daoClassParser;

    @Autowired
    private ModuleDependencyGraphBuilder moduleDependencyGraphBuilder;

    public void recover(File rootFile){

        // 提取模块信息
        ProjectNode projectNode = new ProjectNode();
        projectNode.setProjectFile(rootFile);
        projectNode.setProjectName(rootFile.getName());

        List<ModuleNode> moduleNodes = moduleParser.parse(projectNode);



        for (ModuleNode moduleNode : moduleNodes) {

            if (moduleNode.getModuleType().equals(ModuleType.POM)){
                continue;
            }

            // 解析模块依赖信息
            moduleNode.setModuleDependencies(dependencyParser.parse(moduleNode));

            // 解析模块配置信息
            ApplicationConfig applicationConfig = configParser.parse(moduleNode);
            if (applicationConfig != null){
                projectNode.setApplicationConfig(applicationConfig);
            }

            // 解析模块启动类信息
            List<EntryNode> entryNodes = entryParser.parse(moduleNode);
            if (entryNodes != null && entryNodes.size() > 0){
                moduleNode.setEntryNodes(entryNodes);
            }

            // 类基础信息（包括属于什么类型）
            List<ClassDescription> classDescriptions = classParser.parse(moduleNode);
            if (classDescriptions != null && classDescriptions.size() > 0){
                moduleNode.setClassDescriptions(classDescriptions);
            }

            controllerClassParser.parse(moduleNode);
            serviceClassParser.parse(moduleNode);
            daoClassParser.parse(moduleNode);
        }

        projectNode.setModuleNodeList(moduleNodes);


        // 构建模块依赖图
        ModuleDependencyGraph moduleDependencyGraph = moduleDependencyGraphBuilder.build(moduleNodes);
        projectNode.setModuleDependencyGraph(moduleDependencyGraph);

    }
}
