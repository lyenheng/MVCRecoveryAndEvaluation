package com.ly.mvc_recovery_evaluation.service.impl;

import com.ly.mvc_recovery_evaluation.dao.ModuleNodeDao;
import com.ly.mvc_recovery_evaluation.entity.*;
import com.ly.mvc_recovery_evaluation.service.*;
import com.ly.mvc_recovery_evaluation.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author liuyue
 * @date 2022/11/19 15:51
 */
@Service
public class MicroServiceModuleServiceImpl implements MicroServiceModuleService {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private EntryService entryService;

    @Autowired
    private ModuleDependencyService moduleDependencyService;

    @Autowired
    private ModuleNodeDao moduleNodeDao;

    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private ClassService classService;

    @Autowired
    private MethodService methodService;

    @Autowired
    private MethodCalledService methodCalledService;

    @Override
    public List<ModuleNodePO> findMicroServiceModuleByDetectId(Long detectId) {
        // 找到该检测任务对应的所有项目结点信息
        List<ProjectNodePO> projectNodePOS = projectService.findAllProjectByDetectId(detectId);

        List<Long> projectNodeIds = projectNodePOS.stream().map(ProjectNodePO::getId).collect(Collectors.toList());
        //找到该检测任务对应的所有模块结点信息
        List<ModuleNodePO> moduleNodePOS = moduleService.multiFindByProject(projectNodeIds);

        // 找到入口信息列表
        List<EntryNodePO> entryNodePOS = entryService.findAll();
        Set<Long> microServiceModuleIds = entryNodePOS.stream().map(EntryNodePO::getModuleNodeId).collect(Collectors.toSet());

        return moduleNodePOS.stream().filter(moduleNodePO -> microServiceModuleIds.contains(moduleNodePO.getId())).collect(Collectors.toList());

    }

    /**
     * 根据入口模块id找到同一个服务下的模块
     * @param entryModuleId
     * @return
     */
    @Override
    public List<Long> findModulesByEntryModule(Long entryModuleId) {
        // 根据当前入口模块找到对应的projectId
        Long projectId = moduleService.findProjectByModule(entryModuleId);

        // 当前模块的所有依赖信息
        List<ModuleDependencyPO> moduleDependencyPOS = moduleDependencyService.findByModule(entryModuleId);
        Set<ModuleCoordinate> moduleCoordinateSet = moduleDependencyPOS.stream().map(moduleDependencyPO -> new ModuleCoordinate(moduleDependencyPO.getGroupId(), moduleDependencyPO.getArtifactId())).collect(Collectors.toSet());

        // 当前projectId对应的所有模块
        List<ModuleNodePO> moduleNodePOs = moduleService.findByProject(projectId);

        ArrayList<Long> moduleIds = new ArrayList<>();

        for (ModuleNodePO moduleNodePO : moduleNodePOs) {
            ModuleCoordinate moduleCoordinate = new ModuleCoordinate(moduleNodePO.getGroupId(), moduleNodePO.getArtifactId());
            if (moduleCoordinateSet.contains(moduleCoordinate)){
                moduleIds.add(moduleNodePO.getId());
            }
        }

        return moduleIds;
    }

    @Override
    public DependencyNode getModuleDependencyTree(Long moduleId) {

        DependencyNode dependencyNode = new DependencyNode();

        // 查找当前子服务的模块名
        Optional<ModuleNodePO> byId = moduleNodeDao.findById(moduleId);
        if (byId.isPresent()){
            String moduleName = byId.get().getModuleName();
            dependencyNode.setName(moduleName);
        }

        // 找到当前子服务下的所有模块
        List<Long> moduleIds = findModulesByEntryModule(moduleId);

        List<DependencyNode> children1 = new ArrayList<>();
        // 遍历每个模块获取依赖信息
        for (Long currentModuleId : moduleIds) {

            // 构造一级树节点
            Optional<ModuleNodePO> byId1 = moduleNodeDao.findById(currentModuleId);
            if (byId1.isPresent()){
                String moduleName = byId1.get().getModuleName();
                DependencyNode dependencyNode1 = new DependencyNode();
                dependencyNode1.setName(moduleName);

                ArrayList<DependencyNode> children2 = new ArrayList<>();
                // 构造二级树节点
                List<ModuleDependencyPO> moduleDependencyPOS = moduleDependencyService.findByModule(currentModuleId);
                if (moduleDependencyPOS != null && moduleDependencyPOS.size() > 0) {
                    for (ModuleDependencyPO moduleDependencyPO : moduleDependencyPOS) {
                        DependencyNode dependencyNode2 = new DependencyNode(moduleDependencyPO.getArtifactId(), null);
                        children2.add(dependencyNode2);
                    }
                }

                dependencyNode1.setChildren(children2);

                children1.add(dependencyNode1);
            }

        }
        dependencyNode.setChildren(children1);

        return dependencyNode;
    }

    @Override
    public List<DatabaseDescriptionPO> getDatabaseInfo(Long moduleId) {
        return databaseService.findByModule(moduleId);
    }

    @Override
    public LayersRelationVO getLayersRelationData(Long entryModuleId) {

        LayersRelationVO layersRelationVO = new LayersRelationVO();

        // 构造类别数据
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("A"));
        categories.add(new Category("B"));
        categories.add(new Category("C"));
        categories.add(new Category("D"));
        layersRelationVO.setCategories(categories);

        // 构造顶点数据
        ArrayList<LayersRelationNode> nodes = new ArrayList<>();
        List<ClassDescriptionVO> allClass = classService.getAllClassByEntryModule(entryModuleId);
        double controllerNum = 0.0;
        double serviceNum = 0.0;
        double daoNum = 0.0;
        double otherNum = 0.0;

        for (ClassDescriptionVO aClass : allClass) {
            String classType = aClass.getClassType();
            if (!StringUtils.isEmpty(classType)) {
                if (classType.equalsIgnoreCase("CONTROLLER")) {
                    controllerNum++;
                    // 添加类结点
                    nodes.add(new LayersRelationNode("class" + aClass.getId(), 0, aClass.getName(), 20.0, (double) aClass.getMethodNum()));
                    // 添加方法结点
                    List<MethodDescriptionPO> methods = methodService.findByClass(aClass.getId());
                    for (MethodDescriptionPO method : methods) {
                        if (method.getTotalLine() != null){
                            nodes.add(new LayersRelationNode("method" + method.getId(), 0, method.getName(), 8.0, (double) method.getTotalLine()));
                        }else {
                            nodes.add(new LayersRelationNode("method" + method.getId(), 0, method.getName(), 8.0, 0.0));
                        }
                    }
                } else if (classType.equalsIgnoreCase("SERVICE")) {
                    serviceNum++;
                    // 添加类结点
                    nodes.add(new LayersRelationNode("class" + aClass.getId(), 1, aClass.getName(), 20.0, (double) aClass.getMethodNum()));
                    // 添加方法结点
                    List<MethodDescriptionPO> methods = methodService.findByClass(aClass.getId());
                    for (MethodDescriptionPO method : methods) {
                        if (method.getTotalLine() != null){
                            nodes.add(new LayersRelationNode("method" + method.getId(), 1, method.getName(), 8.0, (double) method.getTotalLine()));
                        }else {
                            nodes.add(new LayersRelationNode("method" + method.getId(), 1, method.getName(), 8.0, 0.0));
                        }
                    }
                } else if (classType.equalsIgnoreCase("DAO")) {
                    daoNum++;
                    // 添加类结点
                    nodes.add(new LayersRelationNode("class" + aClass.getId(), 2, aClass.getName(), 20.0, (double) aClass.getMethodNum()));
                    // 添加方法结点
                    List<MethodDescriptionPO> methods = methodService.findByClass(aClass.getId());
                    for (MethodDescriptionPO method : methods) {
                        if (method.getTotalLine() != null){
                            nodes.add(new LayersRelationNode("method" + method.getId(), 2, method.getName(), 8.0, (double) method.getTotalLine()));
                        }else {
                            nodes.add(new LayersRelationNode("method" + method.getId(), 2, method.getName(), 8.0, 0.0));
                        }
                    }
                } else {
                    otherNum++;
                    // 添加类结点
                    nodes.add(new LayersRelationNode("class" + aClass.getId(), 3, aClass.getName(), 20.0, (double) aClass.getMethodNum()));
                    // 添加方法结点
                    List<MethodDescriptionPO> methods = methodService.findByClass(aClass.getId());
                    for (MethodDescriptionPO method : methods) {
                        if (method.getTotalLine() != null){
                            nodes.add(new LayersRelationNode("method" + method.getId(), 3, method.getName(), 8.0, (double) method.getTotalLine()));
                        }else {
                            nodes.add(new LayersRelationNode("method" + method.getId(), 3, method.getName(), 8.0, 0.0));
                        }
                    }
                }
            }
        }

        // 添加类型节点
        nodes.add(new LayersRelationNode("layer0", 0, "Controller层", 30.0, controllerNum )) ;
        nodes.add(new LayersRelationNode("layer1", 1, "Service层", 30.0, serviceNum )) ;
        nodes.add(new LayersRelationNode("layer2", 2, "Dao层", 30.0, daoNum )) ;
        nodes.add(new LayersRelationNode("layer3", 3, "other", 30.0, otherNum )) ;

        layersRelationVO.setNodes(nodes);

        // 构造边数据
        List<LayersRelationLink> links = new ArrayList<>();

        for (ClassDescriptionVO aClass : allClass) {
            Long classId = aClass.getId();
            List<MethodDescriptionPO> methods = methodService.findByClass(classId);
            for (MethodDescriptionPO method : methods) {
                List<MethodCalledNodePO> methodCalledNodes = methodCalledService.findByMethod(method.getId());
                for (MethodCalledNodePO methodCalledNode : methodCalledNodes) {
                    Long methodCalledId = getMethodCalledId(methodCalledNode, classId);
                    links.add(new LayersRelationLink("method"+ methodCalledId , "method"+methodCalledNode.getId()));
                }
            }
        }
        layersRelationVO.setLinks(links);
        return layersRelationVO;
    }

    /**
     * 根据函数调用查找被调用的函数id
     * @param methodCalledNodePO
     * @return
     */
    Long getMethodCalledId(MethodCalledNodePO methodCalledNodePO, Long classId){
        String calledMethodName = methodCalledNodePO.getCalledMthodName();
        if (methodCalledNodePO.getCalledClassFullyName() != null){
            // 若不是自调用，则查找被调用函数的类id
            List<ClassDescriptionPO> classDescriptionPOList = classService.findByFullyQualifiedName(methodCalledNodePO.getCalledClassFullyName());
            if (classDescriptionPOList != null && classDescriptionPOList.size() == 1){
                classId = classDescriptionPOList.get(0).getId();
            }
        }

        // 查找类的方法信息
        List<MethodDescriptionPO> methods = methodService.findByClass(classId);
        for (MethodDescriptionPO method : methods) {
            if (calledMethodName.equalsIgnoreCase(method.getName())){
                return method.getId();
            }
        }
        return null;
    }
}
