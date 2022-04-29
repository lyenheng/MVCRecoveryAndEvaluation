package com.ly.mvc_recovery_evaluation.bean;

import com.ly.mvc_recovery_evaluation.entity.ApplicationConfig;
import com.ly.mvc_recovery_evaluation.entity.ModuleDependencyGraph;
import com.ly.mvc_recovery_evaluation.entity.ModuleNode;
import com.ly.mvc_recovery_evaluation.enums.ProjectType;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * 项目信息
 * @author liuyue
 * @date 2022/4/24 11:03
 */
@Data
@Component
public class ProjectNode {

    /**
     * 项目名称
     */
    private String projectName;

    private File  projectFile;

    /**
     * 项目类型： mvc / others
     */
    private ProjectType projectType;

    /**
     * 模块
     */
    private List<ModuleNode> moduleNodeList;

    /**
     * 配置文件信息
     */
    private ApplicationConfig applicationConfig;

    /**
     * 模块依赖图
     */
    private ModuleDependencyGraph moduleDependencyGraph;

}
