package com.ly.mvc_recovery_evaluation.parser;

import com.ly.mvc_recovery_evaluation.entity.ModuleCoordinate;
import com.ly.mvc_recovery_evaluation.entity.ModuleNode;
import com.ly.mvc_recovery_evaluation.extractor.ModuleDependencyExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * @author liuyue
 * @date 2022/4/24 16:04
 * 解析模块pom声明的依赖
 */
@Component
public class DependencyParser {

    @Autowired
    private ModuleDependencyExtractor moduleDependencyExtractor;

    public List<ModuleCoordinate> parse(ModuleNode moduleNode){

        File pomFile = new File(moduleNode.getModuleFile(), "pom.xml");
        return moduleDependencyExtractor.extract(pomFile);
    }
}
