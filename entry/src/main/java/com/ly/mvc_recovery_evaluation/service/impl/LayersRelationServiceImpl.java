package com.ly.mvc_recovery_evaluation.service.impl;

import com.ly.mvc_recovery_evaluation.entity.ClassDescriptionPO;
import com.ly.mvc_recovery_evaluation.entity.MethodCalledNodePO;
import com.ly.mvc_recovery_evaluation.entity.MethodDescriptionPO;
import com.ly.mvc_recovery_evaluation.service.ClassService;
import com.ly.mvc_recovery_evaluation.service.LayersRelationService;
import com.ly.mvc_recovery_evaluation.service.MethodCalledService;
import com.ly.mvc_recovery_evaluation.service.MethodService;
import com.ly.mvc_recovery_evaluation.vo.ClassDescriptionVO;
import com.ly.mvc_recovery_evaluation.vo.LayersRelationLink;
import com.ly.mvc_recovery_evaluation.vo.LayersRelationNode;
import com.ly.mvc_recovery_evaluation.vo.LayersRelationVO;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyue
 * @date 2023/1/7 19:33
 */
@Service
public class LayersRelationServiceImpl implements LayersRelationService {

    @Autowired
    private ClassService classService;

    @Autowired
    private MethodService methodService;

    @Autowired
    private MethodCalledService methodCalledService;

    @Override
    public LayersRelationVO getLayersRelationData(Long entryModuleId) {

        LayersRelationVO layersRelationVO = new LayersRelationVO();

        // 构造类别数据
        List<String> categories = new ArrayList<>();
        categories.add("A");
        categories.add("B");
        categories.add("C");
        categories.add("D");
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
            if (classType.equalsIgnoreCase("CONTROLLER")){
                controllerNum++;
                // 添加类结点
                nodes.add(new LayersRelationNode("class"+aClass.getId(), 0, aClass.getName(), 40.0, (double)aClass.getMethodNum()));
                // 添加方法结点
                List<MethodDescriptionPO> methods = methodService.findByClass(aClass.getId());
                for (MethodDescriptionPO method : methods) {
                    nodes.add(new LayersRelationNode("method" + method.getId(), 0, method.getName(), 20.0, (double)method.getTotalLine()));
                }
            } else if (classType.equalsIgnoreCase("SERVICE")) {
                serviceNum++;
                // 添加类结点
                nodes.add(new LayersRelationNode("class"+aClass.getId(), 1, aClass.getName(), 40.0, (double)aClass.getMethodNum()));
                // 添加方法结点
                List<MethodDescriptionPO> methods = methodService.findByClass(aClass.getId());
                for (MethodDescriptionPO method : methods) {
                    nodes.add(new LayersRelationNode("method" + method.getId(), 1, method.getName(), 20.0, (double)method.getTotalLine()));
                }
            } else if (classType.equalsIgnoreCase("DAO")) {
                daoNum++;
                // 添加类结点
                nodes.add(new LayersRelationNode("class"+aClass.getId(), 2, aClass.getName(), 40.0, (double)aClass.getMethodNum()));
                // 添加方法结点
                List<MethodDescriptionPO> methods = methodService.findByClass(aClass.getId());
                for (MethodDescriptionPO method : methods) {
                    nodes.add(new LayersRelationNode("method" + method.getId(), 2, method.getName(), 20.0, (double)method.getTotalLine()));
                }
            }else {
                otherNum++;
                // 添加类结点
                nodes.add(new LayersRelationNode("class"+aClass.getId(), 3, aClass.getName(), 40.0, (double)aClass.getMethodNum()));
                // 添加方法结点
                List<MethodDescriptionPO> methods = methodService.findByClass(aClass.getId());
                for (MethodDescriptionPO method : methods) {
                    nodes.add(new LayersRelationNode("method" + method.getId(), 3, method.getName(), 20.0, (double)method.getTotalLine()));
                }
            }

        }

        // 添加类型节点
        nodes.add(new LayersRelationNode("layer0", 0, "Controller层", 60.0, controllerNum )) ;
        nodes.add(new LayersRelationNode("layer1", 1, "Service层", 60.0, serviceNum )) ;
        nodes.add(new LayersRelationNode("layer2", 2, "Dao层", 60.0, daoNum )) ;
        nodes.add(new LayersRelationNode("layer3", 3, "other", 60.0, otherNum )) ;

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
