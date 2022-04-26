package com.ly.mvc_recovery_evaluation.entity;

import com.ly.mvc_recovery_evaluation.enums.ClassType;
import lombok.Data;

import java.io.File;
import java.util.List;

/**
 * 描述类特征
 * @author liuyue
 * @date 2022/4/26 14:01
 */
@Data
public class ClassDescription {

    /**
     * 类名
     */
    private String name;

    private File file;

    private ClassType classType;

    /**
     * 包路径
     */
    private String packagePath;

    /**
     * 注解信息
     */
    private List<String> annotations;
}
