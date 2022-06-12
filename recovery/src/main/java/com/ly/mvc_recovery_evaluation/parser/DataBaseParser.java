package com.ly.mvc_recovery_evaluation.parser;

import com.ly.mvc_recovery_evaluation.entity.DataBaseDescription;
import com.ly.mvc_recovery_evaluation.enums.DataBaseType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author liuyue
 * @date 2022/6/12 20:33
 */
@Component
public class DataBaseParser {

    public List<DataBaseDescription> parse(Map<String, String> payload){
        List<DataBaseDescription> dataBaseDescriptions = new ArrayList<>();

        if (parseMysql(payload) != null){
            dataBaseDescriptions.add(parseMysql(payload));
        }

        if (parseRedis(payload) != null){
            dataBaseDescriptions.add(parseRedis(payload));
        }

        if (parseMongodb(payload) != null){
            dataBaseDescriptions.add(parseMongodb(payload));
        }

        return dataBaseDescriptions;
    }

    /**
     * 解析mysql类型数据库信息
     * @param payload
     * @return
     */
    DataBaseDescription parseMysql(Map<String, String> payload){
        DataBaseDescription dataBaseDescription = new DataBaseDescription();

        if (StringUtils.isEmpty(payload.get("spring.datasource.url"))){
            return null;
        }

        String url = payload.get("spring.datasource.url");
        if (!url.contains("mysql")){
            return null;
        }

        // 数据库类型为mysql
        dataBaseDescription.setDataBaseType(DataBaseType.MYSQL);

        parseUrl(dataBaseDescription, url);

        if (StringUtils.isEmpty(dataBaseDescription.getPort())){
            // 设置端口默认值
            dataBaseDescription.setPort("3306");
        }
        return dataBaseDescription;
    }


    /**
     * 解析redis类型数据库
     * @param payload
     * @return
     */
    DataBaseDescription parseRedis(Map<String, String> payload){
        DataBaseDescription dataBaseDescription = new DataBaseDescription();

        if (!StringUtils.isEmpty(payload.get("spring.redis.database")) || !StringUtils.isEmpty(payload.get("spring.redis.host")) || !StringUtils.isEmpty(payload.get("spring.redis.port"))){
            dataBaseDescription.setDataBaseType(DataBaseType.REDIS);
        }else {
            return null;
        }
        // 数据库名
        if (!StringUtils.isEmpty(payload.get("spring.redis.database"))){
            dataBaseDescription.setDataBaseName(payload.get("spring.redis.database"));
        }
        // url
        if (!StringUtils.isEmpty(payload.get("spring.redis.host"))){
            dataBaseDescription.setUrl(payload.get("spring.redis.host"));
        }
        // 端口
        if (!StringUtils.isEmpty(payload.get("spring.redis.port"))){
            dataBaseDescription.setPort(payload.get("spring.redis.port"));
        }else {
            dataBaseDescription.setPort("6379");
        }
        return dataBaseDescription;
    }

    /**
     * 解析Mongodb类型数据库
     * @param payload
     * @return
     */
    DataBaseDescription parseMongodb(Map<String, String> payload){
        DataBaseDescription dataBaseDescription = new DataBaseDescription();

        if (!StringUtils.isEmpty(payload.get("spring.data.mongodb.url"))){
            dataBaseDescription.setDataBaseType(DataBaseType.MONGODB);
            String url = payload.get("spring.data.mongodb.url");
            parseUrl(dataBaseDescription, url);
        }else if (!StringUtils.isEmpty(payload.get("spring.data.mongodb.database")) || !StringUtils.isEmpty(payload.get("spring.data.mongodb.host")) || !StringUtils.isEmpty(payload.get("spring.data.mongodb.port"))){
            dataBaseDescription.setDataBaseType(DataBaseType.MONGODB);
            // 数据库名
            if (!StringUtils.isEmpty(payload.get("spring.data.mongodb.database"))){
                dataBaseDescription.setDataBaseName(payload.get("spring.data.mongodb.database"));
            }
            // url
            if (!StringUtils.isEmpty(payload.get("spring.data.mongodb.host"))){
                dataBaseDescription.setUrl(payload.get("spring.data.mongodb.host"));
            }
            // 端口
            if (!StringUtils.isEmpty(payload.get("spring.data.mongodb.port"))){
                dataBaseDescription.setPort(payload.get("spring.data.mongodb.port"));
            }else {
                // 默认值
                dataBaseDescription.setPort("27017");
            }
        }else{
            return null;
        }
        return dataBaseDescription;
    }


    /**
     * 解析url，获取数据库连接的地址、端口、数据库名
     * @param dataBaseDescription
     * @param url
     */
    private void parseUrl(DataBaseDescription dataBaseDescription, String url) {
        String urlSub = url.substring(url.indexOf("//") + 2);
        // url
        dataBaseDescription.setUrl(urlSub.substring(0, urlSub.indexOf(":")));
        // 端口
        dataBaseDescription.setPort(urlSub.substring(urlSub.indexOf(":") + 1, urlSub.indexOf("/")));
        // 数据库名
        if (urlSub.contains("?")){
            dataBaseDescription.setDataBaseName(urlSub.substring(urlSub.indexOf("/") + 1, urlSub.indexOf("?")));
        }else {
            dataBaseDescription.setDataBaseName(urlSub.substring(urlSub.indexOf("/") + 1));
        }
    }

}
