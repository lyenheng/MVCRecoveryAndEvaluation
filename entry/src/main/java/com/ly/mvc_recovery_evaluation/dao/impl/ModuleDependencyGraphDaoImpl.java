package com.ly.mvc_recovery_evaluation.dao.impl;

import com.ly.mvc_recovery_evaluation.dao.ModuleDependencyGraphDao;
import com.ly.mvc_recovery_evaluation.entity.ModuleDependencyGraph;
import com.ly.mvc_recovery_evaluation.entity.ModuleDependencyGraphEdge;
import com.ly.mvc_recovery_evaluation.entity.ModuleNode;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuyue
 * @date 2022/10/16 14:32
 */
@Repository
public class ModuleDependencyGraphDaoImpl implements ModuleDependencyGraphDao {

    @Resource
    private MongoTemplate mongoTemplate;


    @Override
    public void add(Long detectId, ModuleDependencyGraph moduleDependencyGraph) {

        Map<String, Object> map = new HashMap<>();
        map.put("detectId", detectId);
        map.put("moduleDependencyGraph", getDocumentForModuleDependency(moduleDependencyGraph));

        mongoTemplate.insert(new Document(map), "module_dependency_graph");

    }

    @Override
    public Document findByDetectId(Long detectId) {
        return mongoTemplate.findOne(new Query(Criteria.where("detectId").is(detectId)), Document.class, "module_dependency_graph");
    }

    private Document getDocumentForModuleDependency(ModuleDependencyGraph moduleDependencyGraph){
        Document doc = new Document();
        List<Document> nodeDocs = new ArrayList<>();
        List<Document> edgesDocs = new ArrayList<>();
        List<ModuleNode> nodes = moduleDependencyGraph.getNodes();
        List<ModuleDependencyGraphEdge> edges = moduleDependencyGraph.getEdges();

        for (ModuleNode node: nodes) {
            nodeDocs.add(getDocumentForModuleNode(node));
        }

        if (edges != null && edges.size() >0){
            for (ModuleDependencyGraphEdge edge: edges) {
                Document document = new Document();
                document.put("begin", edge.getStartNode().getModuleName());
                document.put("end", edge.getEndNode().getModuleName());
                edgesDocs.add(document);
            }
        }

        doc.put("nodes", nodeDocs);
        doc.put("edges", edgesDocs);
        return doc;
    }

    private Document getDocumentForModuleNode(ModuleNode moduleNode){
        Document document = new Document();

        document.put("moduleName", moduleNode.getModuleName());
        document.put("moduleType", moduleNode.getModuleType().toString());
        document.put("groupId", moduleNode.getModuleCoordinate().getGroupId());
        document.put("artifactId", moduleNode.getModuleCoordinate().getArtifactId());

        return document;
    }



}
