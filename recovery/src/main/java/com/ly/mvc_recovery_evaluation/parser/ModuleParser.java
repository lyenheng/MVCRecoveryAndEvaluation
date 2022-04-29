package com.ly.mvc_recovery_evaluation.parser;

import com.ly.mvc_recovery_evaluation.entity.ModuleNode;
import com.ly.mvc_recovery_evaluation.bean.ProjectNode;
import com.ly.mvc_recovery_evaluation.enums.ModuleType;
import com.ly.mvc_recovery_evaluation.extractor.ModuleCoordinateExtractor;
import com.ly.mvc_recovery_evaluation.extractor.ModuleTypeExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyue
 * @date 2022/4/24 11:27
 */
@Component
public class ModuleParser {

    @Autowired
    private ModuleCoordinateExtractor moduleCoordinateExtractor;

    @Autowired
    private ModuleTypeExtractor moduleTypeExtractor;



    public List<ModuleNode> parse(ProjectNode projectNode){

        List<ModuleNode> moduleNodes = new ArrayList<>();

        File projectFile = projectNode.getProjectFile();

        getChildFiles(projectFile, moduleNodes);

        return moduleNodes;
    }


    private void getChildFiles(File file, List<ModuleNode> moduleNodes){
        if (file.isDirectory()){
            File[] files = file.listFiles();

            if (files != null){
                boolean hasPom = false;
                ModuleNode moduleNode = new ModuleNode();
                for (File childFile : files) {

                    if (childFile.isFile() && childFile.getName().equalsIgnoreCase("pom.xml")){

                        hasPom = true;

                        moduleNode.setModuleName(file.getName());
                        moduleNode.setModuleType(moduleTypeExtractor.extract(childFile));
                        moduleNode.setModuleCoordinate(moduleCoordinateExtractor.extract(childFile));
                        moduleNode.setModuleFile(file);

                        moduleNodes.add(moduleNode);
                        break;
                    }
                }

                if (hasPom && moduleNode.getModuleType().equals(ModuleType.POM)){
                    for (File childFile : files) {
                        if (childFile.isDirectory()){
                            getChildFiles(childFile, moduleNodes);
                        }
                    }
                }
            }
        }
    }
}
