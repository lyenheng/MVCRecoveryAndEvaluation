package com.ly.mvc_recovery_evaluation.entity;

import lombok.Data;

import java.io.File;
import java.util.List;

/**
 * @author liuyue
 * @date 2022/4/25 18:56
 */
@Data
public class EntryNode {

    private File file;

    /**
     * 注解信息
     */
    private List<String> annotations;

    /**
     * main函数内容
     */
    private String content;

}
