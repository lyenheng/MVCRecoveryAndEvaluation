package com.ly.mvc_recovery_evaluation;

import com.ly.mvc_recovery_evaluation.entity.ModuleNode;
import com.ly.mvc_recovery_evaluation.entity.ProjectNode;
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

    public void recover(File rootFile){

        ProjectNode projectNode = new ProjectNode();
        projectNode.setProjectFile(rootFile);
        projectNode.setProjectName(rootFile.getName());

        List<ModuleNode> moduleNodes = moduleParser.parse(projectNode);

        projectNode.setModuleNodeList(moduleNodes);
    }
}
