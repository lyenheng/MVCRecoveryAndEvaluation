package com.ly.mvc_recovery_evaluation.extractor;

import com.ly.mvc_recovery_evaluation.entity.ApplicationConfig;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
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
            if (map.get("server") != null){
                Map serverMap = (Map)map.get("server");
                if (serverMap.get("port") != null){
                    Integer port = (Integer)serverMap.get("port");
                    applicationConfig.setPort(port);
                }
                if (serverMap.get("servlet") != null){
                    Map servletMap = (Map)serverMap.get("servlet");
                    if (servletMap.get("context-path") != null){
                       String contextPath = (String)servletMap.get("context-path");
                       applicationConfig.setContextPath(contextPath);
                    }
                }
            }

            if (map.get("spring") != null){
                Map springMap = (Map)map.get("spring");
                if (springMap.get("datasource") != null ){
                    Map datasourceMap = (Map) springMap.get("datasource");
                    if (datasourceMap.get("url") != null){
                        String url = (String)datasourceMap.get("url");
                        applicationConfig.setDatasourceUrl(url);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return applicationConfig;
    }

}
