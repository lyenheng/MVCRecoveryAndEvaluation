package com.ly.mvc_recovery_evaluation.parser;

import com.ly.mvc_recovery_evaluation.entity.ApplicationConfig;
import com.ly.mvc_recovery_evaluation.entity.ModuleNode;
import com.ly.mvc_recovery_evaluation.extractor.ConfigExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author liuyue
 * @date 2022/4/24 20:21
 */
@Component
public class ConfigParser {

    @Autowired
    private ConfigExtractor configExtractor;

    public ApplicationConfig parse(ModuleNode moduleNode){
        File configFile = new File(moduleNode.getModuleFile(), "application.yml");
        return configExtractor.extract(configFile);
    }
}
