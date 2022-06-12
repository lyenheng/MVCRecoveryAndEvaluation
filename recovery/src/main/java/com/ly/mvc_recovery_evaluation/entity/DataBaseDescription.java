package com.ly.mvc_recovery_evaluation.entity;

import com.ly.mvc_recovery_evaluation.enums.DataBaseType;
import lombok.Data;

/**
 * @author liuyue
 * @date 2022/6/12 19:39
 */
@Data
public class DataBaseDescription {

    /**
     * 数据库类型
     */
    private DataBaseType dataBaseType;

    /**
     * 地址
     */
    private String url;

    /**
     * 端口号
     */
    private String port;

    /**
     * 数据库名
     */
    private String dataBaseName;

}
