package com.ly.mvc_recovery_evaluation.entity;

import lombok.Data;

import java.util.Map;

/**
 * 配置文件信息
 * @author liuyue
 * @date 2022/4/24 20:04
 */
@Data
public class ApplicationConfig {

    /**
     * 端口号
     */
    private String port;

    /**
     * 路径
     */
    private String contextPath;

    /**
     * 激活的配置文件
     */
    private String activeFile;

    /**
     * 其他配置信息
     */
    private Map<String, String> payload;
}
