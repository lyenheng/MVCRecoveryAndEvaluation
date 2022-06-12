package com.ly.mvc_recovery_evaluation.extractor;

import com.ly.mvc_recovery_evaluation.entity.ApplicationConfig;
import com.ly.mvc_recovery_evaluation.util.YmlUtil;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuyue
 * @date 2022/4/24 20:02
 */
@Component
public class ConfigExtractor {

    public ApplicationConfig extract(File configFile){
        ApplicationConfig applicationConfig = new ApplicationConfig();

        Yaml yaml = new Yaml();
        try {
            Map map = yaml.loadAs(new FileInputStream(configFile), Map.class);

            String port = YmlUtil.getProperty(map, "server.port");
            String contextPath = YmlUtil.getProperty(map, "server.servlet.context-path");
            String activeFile = YmlUtil.getProperty(map, "spring.profiles.active");

            Map<String, String> payload = new HashMap<>();
            String[] keys = {
                    "spring.datasource.url",
                    "spring.data.mongodb.url",
                    "spring.data.mongodb.database",
                    "spring.data.mongodb.host",
                    "spring.data.mongodb.port",
                    "spring.redis.database",
                    "spring.redis.host",
                    "spring.redis.port"};
            for (String key : keys) {
                payload.put(key, YmlUtil.getProperty(map, key));
            }

            applicationConfig.setPort(port);
            applicationConfig.setContextPath(contextPath);
            applicationConfig.setActiveFile(activeFile);
            applicationConfig.setPayload(payload);

        }catch (Exception e){
//            e.printStackTrace();
        }
        return applicationConfig;
    }

}
