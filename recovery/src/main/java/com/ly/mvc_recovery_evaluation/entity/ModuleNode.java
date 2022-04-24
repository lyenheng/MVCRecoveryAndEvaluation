package com.ly.mvc_recovery_evaluation.entity;

import com.ly.mvc_recovery_evaluation.enums.ModuleType;
import lombok.Data;

import java.io.File;
import java.util.List;

/**
 * @author liuyue
 * @date 2022/4/24 11:17
 */
@Data
public class ModuleNode {

    /**
     * 模块名
     */
    private String moduleName;

    private File moduleFile;

    /**
     *  模块类型 pom  / jar
     */
    private ModuleType moduleType;

    private ModuleCoordinate moduleCoordinate;

    /**
     * 依赖包
     */
    private List<ModuleCoordinate> moduleDependencies;
}
