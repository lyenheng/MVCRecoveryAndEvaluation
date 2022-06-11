package com.ly.mvc_recovery_evaluation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author liuyue
 * @date 2022/6/11 20:59
 * 方法粒度描述信息
 */
@Data
@AllArgsConstructor
public class MethodGranularityDescription {

    /**
     * 总行数
     */
    private Integer totalLine;

    /**
     * 空行行数
     */
    private Integer blankLine;

    /**
     * 注释行数
     */
    private Integer commentLine;

    /**
     * 有效代码行数
     */
    private Integer codeLine;

}
