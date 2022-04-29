package com.ly.mvc_recovery_evaluation.util;

import java.util.Map;

/**
 * 字符串处理工具类
 * @author liuyue
 * @date 2022/4/29 20:51
 */
public class StrUtil {

    /**
     * 将map转化为json字符串
     * @return
     */
    public static String mapToJson(Map<String, String> map){

        StringBuilder json = new StringBuilder();

        json.append("{\n");

        map.forEach((key, value) -> {
            json.append("\t").append("\"").append(key).append("\"").append(": ").append(value).append(",\n");
        });

        json.append("}");
        return json.toString();
    }
}
