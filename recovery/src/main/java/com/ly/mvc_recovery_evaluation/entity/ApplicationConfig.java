package com.ly.mvc_recovery_evaluation.entity;

import lombok.Data;

/**
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
     * 数据库连接
     */
    private String datasourceUrl;

    /**
     * 激活的配置文件
     */
    private String activeFile;
}
