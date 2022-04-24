package com.ly.mvc_recovery_evaluation.extractor;

import com.ly.mvc_recovery_evaluation.entity.ModuleCoordinate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author liuyue
 * @date 2022/4/24 13:58
 */
@Component
public class ModuleCoordinateExtractor {

    public ModuleCoordinate extract(File pomFile){

        ModuleCoordinate moduleCoordinate = new ModuleCoordinate();

        try {
            Document document = Jsoup.parse(pomFile, "utf-8");

            // 获取groupId
            Elements groupIdElements = document.select("project > groupId");
            if (groupIdElements.size() > 0){
                moduleCoordinate.setGroupId(groupIdElements.get(0).text());
            }else {
                Elements parentGroupIdElements = document.select("project > parent > groupId");
                if (parentGroupIdElements.size() > 0){
                    moduleCoordinate.setGroupId(parentGroupIdElements.get(0).text());
                }
            }

            // 获取artifactId
            Elements artifactIdElements = document.select("project > artifactId");
            if (artifactIdElements.size() > 0){
                moduleCoordinate.setArtifactId(artifactIdElements.get(0).text());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return moduleCoordinate;
    }

}
