package com.ly.mvc_recovery_evaluation.extractor;

import com.ly.mvc_recovery_evaluation.entity.ModuleCoordinate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyue
 * @date 2022/4/24 15:04
 */
@Component
public class ModuleDependencyExtractor {

    public List<ModuleCoordinate> extract(File pomFile){

        List<ModuleCoordinate> moduleCoordinates = new ArrayList<>();

        try {
            Document document = Jsoup.parse(pomFile, "utf-8");
            Elements elements = document.select("project > dependencies > dependency");
            if (elements != null){
                for (Element element : elements) {
                    Elements groupIdElements = element.select("groupId");
                    Elements artifactIdElements = element.select("artifactId");

                    ModuleCoordinate moduleCoordinate = new ModuleCoordinate();
                    if (groupIdElements.size() > 0){
                        moduleCoordinate.setGroupId(groupIdElements.get(0).text());
                    }
                    if (artifactIdElements.size() > 0){
                        moduleCoordinate.setArtifactId(artifactIdElements.get(0).text());
                    }

                    moduleCoordinates.add(moduleCoordinate);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return moduleCoordinates;
    }
}
