package com.ly.mvc_recovery_evaluation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author liuyue
 * @date 2023/3/11 20:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FunctionalityResult {

    /**
     * 循环依赖
     */
    private Boolean hasDependencyCircle;

    /**
     * 请求类型错误类
     */
    private List<String> requestTypeErrorClass;

    /**
     * 请求错误错误类
     */
    private List<String> requestParamErrorClass;
}
