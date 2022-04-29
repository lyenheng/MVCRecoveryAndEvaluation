package com.ly.mvc_recovery_evaluation.entity;

import com.github.javaparser.ast.body.Parameter;
import com.ly.mvc_recovery_evaluation.enums.RequestParameterType;
import lombok.Data;

/**
 * 接口参数信息
 * @author liuyue
 * @date 2022/4/26 20:21
 */
@Data
public class ApiParameterInfo {

    /**
     * 请求参数类型：path / body / param
     */
    private RequestParameterType requestParameterType;

    /**
     * 解析后的参数
     */
    private String resolvedParameterJSON;

    /**
     * 参数
     */
    private Parameter parameter;
}
