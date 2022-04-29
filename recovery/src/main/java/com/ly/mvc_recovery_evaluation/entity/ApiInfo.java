package com.ly.mvc_recovery_evaluation.entity;

import com.ly.mvc_recovery_evaluation.enums.RequestType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/4/26 19:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiInfo {

    /**
     * swagger信息
     */
    private String apiOperationInfo;

    /**
     * 请求类型
     */
    private RequestType requestType;

    /**
     * 请求路径
     */
    private String requestPath;

    /**
     * 返回值
     */
    private String returnValue;

    /**
     * 参数
     */
    private List<ApiParameterInfo> apiParameterInfos;

}
