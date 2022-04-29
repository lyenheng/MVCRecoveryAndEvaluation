package com.ly.mvc_recovery_evaluation.util;


import com.ly.mvc_recovery_evaluation.entity.ModuleCoordinate;

/**
 * 将模块的groupId和artifactId与字符串的转换
 * @author liuyue
 * @date 2022/4/24 16:35
 */
public class ModuleCoordinateConvertUtil {

    public static String convert(ModuleCoordinate moduleCoordinate){
        return moduleCoordinate.getGroupId() + ":" + moduleCoordinate.getArtifactId();
    }

    public static ModuleCoordinate convert(String packageName){
        String[] split = packageName.split(":");
        if (split.length > 1){
            return new ModuleCoordinate(split[0], split[1]);
        }
        return null;
    }
}
