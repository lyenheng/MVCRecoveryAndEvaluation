package com.ly.mvc_recovery_evaluation.extractor;

import com.ly.mvc_recovery_evaluation.enums.ModuleType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * @author liuyue
 * @date 2022/4/24 13:45
 * 提取模块类型  JAR / POM
 */
@Component
public class ModuleTypeExtractor {

    public ModuleType extract(File pomFile)  {

        try{
            Document document = Jsoup.parse(pomFile, "utf-8");
            Elements packagingElements = document.select("project > packaging");
            if (packagingElements.size() >0){
                String text = packagingElements.get(0).text();
                if (text.equalsIgnoreCase("pom")){
                    return ModuleType.POM;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return ModuleType.JAR;
    }
}
