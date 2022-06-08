package com.ly.mvc_recovery_evaluation.parser;

import com.ly.mvc_recovery_evaluation.entity.ApplicationConfig;
import com.ly.mvc_recovery_evaluation.entity.ModuleNode;
import com.ly.mvc_recovery_evaluation.extractor.ConfigExtractor;
import com.ly.mvc_recovery_evaluation.util.FilePathConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;

/**
 * @author liuyue
 * @date 2022/4/24 20:21
 * 解析模块配置信息
 */
@Component
public class ConfigParser {

    @Autowired
    private ConfigExtractor configExtractor;

    public ApplicationConfig parse(ModuleNode moduleNode){

        File resourceFile = FilePathConvertUtil.getResourceFile(moduleNode);

        File configFile = new File(resourceFile, "application.yml");

        if (!configFile.exists()){
           return null;
        }

        ApplicationConfig applicationConfig =  configExtractor.extract(configFile);
        if (!StringUtils.isEmpty(applicationConfig.getActiveFile())){
            File activeFile = new File(resourceFile, "application-" + applicationConfig.getActiveFile() + ".yml");
            if (activeFile.exists()){
                ApplicationConfig activeApplicationConfig = configExtractor.extract(activeFile);

                if (!StringUtils.isEmpty(activeApplicationConfig.getPort())){
                    applicationConfig.setPort(activeApplicationConfig.getPort());
                }
                if (!StringUtils.isEmpty(activeApplicationConfig.getContextPath())){
                    applicationConfig.setContextPath(activeApplicationConfig.getContextPath());
                }
                if (!StringUtils.isEmpty(activeApplicationConfig.getDatasourceUrl())){
                    applicationConfig.setDatasourceUrl(activeApplicationConfig.getDatasourceUrl());
                }
            }
        }
        return applicationConfig;
    }
}
