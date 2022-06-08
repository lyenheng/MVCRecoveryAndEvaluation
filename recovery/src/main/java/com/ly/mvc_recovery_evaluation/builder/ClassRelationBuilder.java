package com.ly.mvc_recovery_evaluation.builder;

import com.ly.mvc_recovery_evaluation.bean.ProjectNode;
import com.ly.mvc_recovery_evaluation.entity.ClassDescription;
import com.ly.mvc_recovery_evaluation.entity.DaoClassDescription;
import com.ly.mvc_recovery_evaluation.entity.ModuleNode;
import com.ly.mvc_recovery_evaluation.entity.ServiceClassDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 构建类与它的实现类或者继承类的关系
 *
 * @author liuyue
 * @date 2022/5/2 15:30
 */
@Component
public class ClassRelationBuilder {

    @Autowired
    private ProjectNode projectNode;

    public Map<String, List<String>> build() {
        HashMap<String, List<String>> classToRelation = new HashMap<>();

        List<ModuleNode> moduleNodeList = projectNode.getModuleNodeList();
        if (moduleNodeList != null && moduleNodeList.size() > 0) {
            for (ModuleNode moduleNode : moduleNodeList) {
                List<ClassDescription> classDescriptions = moduleNode.getClassDescriptions();
                if (classDescriptions != null && classDescriptions.size() > 0) {
                    for (ClassDescription classDescription : classDescriptions) {

                        List<String> interfaceServices = new ArrayList<>();
                        List<String> extendsServices = new ArrayList<>();
                        if (classDescription instanceof ServiceClassDescription) {
                            ServiceClassDescription serviceClassDescription = (ServiceClassDescription) classDescription;
                            interfaceServices = serviceClassDescription.getInterfaceServices();
                            extendsServices = serviceClassDescription.getExtendsServices();
                        } else if (classDescription instanceof DaoClassDescription) {
                            DaoClassDescription daoClassDescription = (DaoClassDescription) classDescription;
                            interfaceServices = daoClassDescription.getInterfaceServices();
                            extendsServices = daoClassDescription.getExtendsServices();
                        }

                        // 找到所有的实现类关系（接口 -> 实现类的list)
                        if (interfaceServices != null && interfaceServices.size() > 0) {
                            for (String interfaceService : interfaceServices) {
                                if (classToRelation.containsKey(interfaceService)) {
                                    classToRelation.get(interfaceService).add(classDescription.getFullyQualifiedName());
                                } else {
                                    List<String> classNames = new ArrayList<>();
                                    classNames.add(classDescription.getFullyQualifiedName());
                                    classToRelation.put(interfaceService, classNames);
                                }
                            }
                        }

                        // 找到所有的继承类关系
                        if (extendsServices != null && extendsServices.size() > 0) {
                            for (String extendsService : extendsServices) {
                                if (classToRelation.containsKey(extendsService)) {
                                    classToRelation.get(extendsService).add(classDescription.getFullyQualifiedName());
                                } else {
                                    List<String> classNames = new ArrayList<>();
                                    classNames.add(classDescription.getFullyQualifiedName());
                                    classToRelation.put(extendsService, classNames);
                                }
                            }
                        }
                    }
                }
            }
        }

        return classToRelation;
    }

}
