package com.ly.mvc_recovery_evaluation.util;

import org.springframework.util.StringUtils;

/**
 * 类处理工具
 * @author liuyue
 * @date 2022/4/27 18:50
 */
public class ClassHandleUtil {

    /**
     * 根据类名/全限定类名判断是否是实体
     * @param className
     * @return
     */
    public static boolean isEntityType(String className){
        if (StringUtils.isEmpty(className)){
            return false;
        }
        String[] split = className.split(".");
        String type = className;
        if ( split.length > 1){
            // 全限定类名
            type = split[split.length - 1];

        }

        if (type.equalsIgnoreCase("Integer") || type.equalsIgnoreCase("int")
                || type.equalsIgnoreCase("String") || type.equalsIgnoreCase("float")
                || type.equalsIgnoreCase("Double") || type.equalsIgnoreCase("Boolean")
                || type.equalsIgnoreCase("Date") || type.equalsIgnoreCase("Long")
                || type.equalsIgnoreCase("char") || type.equalsIgnoreCase("Character"))    {

            return false;

        }

        return true;
    }
}
