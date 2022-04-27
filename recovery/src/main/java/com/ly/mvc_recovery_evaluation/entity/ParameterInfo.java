package com.ly.mvc_recovery_evaluation.entity;

import com.ly.mvc_recovery_evaluation.enums.RequestParameterType;
import lombok.Data;

/**
 * @author liuyue
 * @date 2022/4/26 20:21
 */
@Data
public class ParameterInfo {

    /**
     * 参数类型：path / body / param
     */
    private RequestParameterType requestParameterType;

    /**
     * 参数属性类型： int / string / date等
     */
    private String parameterType;

    /**
     * 参数属性名拼接的字符串
     */
    private String parameterName;
}
