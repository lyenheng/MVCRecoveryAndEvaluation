package com.ly.mvc_recovery_evaluation.util;

import java.util.Map;

public class YmlUtil {
    public static String getProperty(Map yml, String property) {
        String[] properties = property.split("\\.");
        for (int i = 0; i < properties.length; i++) {
            if (null == yml) return "";
            if (i == properties.length - 1) {
                if (null == yml.get(properties[i])) return "";
                return yml.get(properties[i]).toString();
            }
            yml = (Map)yml.get(properties[i]);
        }
        return "";
    }
}
