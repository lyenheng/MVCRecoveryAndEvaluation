package com.ly.mvc_recovery_evaluation.evaluation;


import com.ly.mvc_recovery_evaluation.entity.MethodCalledNodePO;
import com.ly.mvc_recovery_evaluation.entity.MethodDescriptionPO;
import com.ly.mvc_recovery_evaluation.entity.ModifiabilityResult;
import com.ly.mvc_recovery_evaluation.entity.ModuleEvaluateResult;
import com.ly.mvc_recovery_evaluation.service.ClassService;
import com.ly.mvc_recovery_evaluation.service.MethodCalledService;
import com.ly.mvc_recovery_evaluation.service.MethodService;
import com.ly.mvc_recovery_evaluation.service.MicroServiceModuleService;
import com.ly.mvc_recovery_evaluation.vo.ClassDescriptionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyue
 * @date 2023/3/5 21:22
 */
@Service
public class MaintainablityEvalutor {

    @Autowired
    private MethodService methodService;

    @Autowired
    private MethodCalledService methodCalledService;

    @Autowired
    private MicroServiceModuleService microServiceModuleService;

    @Autowired
    private ClassService classService;

    public static double CONTROLLER_COMPLEXITY = 100.0;

    public static int CALL_FRE = 10;

    public static int CALLED_FRE = 20;

    public static int METHOD_GRANULARITY = 5;

    /**
     * 模块化评估
     * @param classDescriptionVOS
     */
    public ModuleEvaluateResult evaluateModule(List<ClassDescriptionVO> classDescriptionVOS) {

        ModuleEvaluateResult moduleEvaluateResult = new ModuleEvaluateResult();
        Integer controllerNum = 0;
        Integer serviceNum = 0;
        Integer daoNum = 0;
        Integer otherNum = 0;

        for (ClassDescriptionVO classDescription : classDescriptionVOS) {
            String classType = classDescription.getClassType();
            if (StringUtils.isEmpty(classType)){
                otherNum++;
            }else {
                if (classType.equalsIgnoreCase("CONTROLLER")) {
                    controllerNum++;
                }else if (classType.equalsIgnoreCase("SERVICE")) {
                    serviceNum++;
                }else if (classType.equalsIgnoreCase("DAO")) {
                    daoNum++;
                }else {
                    otherNum++;
                }
            }
        }
        moduleEvaluateResult.setControllerNum(controllerNum);
        moduleEvaluateResult.setServiceNum(serviceNum);
        moduleEvaluateResult.setDaoNum(daoNum);
        double totalPercent = (controllerNum + serviceNum + daoNum) * 1.0 / (controllerNum + serviceNum + daoNum + otherNum);
        moduleEvaluateResult.setTotalPercent((double) Math.round(totalPercent * 100) / 100);

        return moduleEvaluateResult;
    }

    /**
     * 易分析性评估
     */
    public void evaluateAnalytical () {



    }

    /**
     * Controller层复杂度评估
     * @param classDescriptionVOS
     * @return
     */
    public Double evaluateControllerComplexity(List<ClassDescriptionVO> classDescriptionVOS){

        Double classComplexitySum = 0.0;
        for (ClassDescriptionVO classDescriptionVO : classDescriptionVOS) {
            if (classDescriptionVO.getClassType() != null && classDescriptionVO.getClassType().equalsIgnoreCase("CONTROLLER")){
                List<MethodDescriptionPO> methodDescriptionPOS = methodService.findByClass(classDescriptionVO.getId());
                Double methodComplexitySum = 0.0;
                for (MethodDescriptionPO methodDescriptionPO : methodDescriptionPOS) {
                    methodComplexitySum += 2/(1+Math.pow(Math.E, -(methodDescriptionPO.getCodeLine()/CONTROLLER_COMPLEXITY ))) ;
                }
                classComplexitySum += methodComplexitySum / methodDescriptionPOS.size();
            }
        }
        double controllerComplexity = classComplexitySum / classDescriptionVOS.size();
        return (double) Math.round(controllerComplexity * 100) / 100;
    }

    /**
     * 跨层调用可见性
     * @return
     */
    public Double getCrossLayerCallVisibility(List<ClassDescriptionVO> classDescriptionVOS){
        for (ClassDescriptionVO classDescriptionVO : classDescriptionVOS) {
            String classType = classDescriptionVO.getClassType();
            if (classType == null || classType.equalsIgnoreCase("SERVICE")){
                continue;
            }
            Long classId = classDescriptionVO.getId();
            List<MethodDescriptionPO> methodDescriptionPOS = methodService.findByClass(classId);
            for (MethodDescriptionPO methodDescriptionPO : methodDescriptionPOS) {
                Long methodId = methodDescriptionPO.getId();
                List<MethodCalledNodePO> methodCalledNodePOS = methodCalledService.findByMethod(methodId);
                for (MethodCalledNodePO methodCalledNodePO : methodCalledNodePOS) {
                    Long methodCalledId = microServiceModuleService.getMethodCalledId(classDescriptionVOS, methodCalledNodePO, classId);
                    if (methodCalledId == null) continue;
                    Long classCalledId = methodService.findById(methodCalledId).getClassDescriptionId();
                    String classCalledType = classService.findById(classCalledId).getClassType();
                    if (classCalledType != null){
                        if ((classType.equalsIgnoreCase("CONTROLLER") && classCalledType.equalsIgnoreCase("DAO") )
                                || (classType.equalsIgnoreCase("DAO") && classCalledType.equalsIgnoreCase("CONTROLLER"))) {
                            return 1.0;
                        }
                    }
                }
            }
        }
        return 0.0;
    }

    // 自调用情况占比
    public Double getSelfCallProportion(List<ClassDescriptionVO> classDescriptionVOS) {

        int controllerSelfCallFre = 0;
        int daoSelfCallFre = 0;
        int controllerSum = 0;
        int daoSum = 0;
        for (ClassDescriptionVO classDescriptionVO : classDescriptionVOS) {
            String classType = classDescriptionVO.getClassType();
            if (StringUtils.isEmpty(classType) || classType.equalsIgnoreCase("SERVICE")){
                continue;
            }else if (classType.equalsIgnoreCase("CONTROLLER")){
                controllerSum++;
            } else if (classType.equalsIgnoreCase("DAO")) {
                daoSum++;
            }
            Long classId = classDescriptionVO.getId();
            List<MethodDescriptionPO> methodDescriptionPOS = methodService.findByClass(classId);
            for (MethodDescriptionPO methodDescriptionPO : methodDescriptionPOS) {
                Long methodId = methodDescriptionPO.getId();
                List<MethodCalledNodePO> methodCalledNodePOS = methodCalledService.findByMethod(methodId);
                for (MethodCalledNodePO methodCalledNodePO : methodCalledNodePOS) {
                    Long methodCalledId = microServiceModuleService.getMethodCalledId(classDescriptionVOS, methodCalledNodePO, classId);
                    if (methodCalledId != null){
                        Long classCalledId = methodService.findById(methodCalledId).getClassDescriptionId();
                        String classCalledType = classService.findById(classCalledId).getClassType();
                        if (!StringUtils.isEmpty(classType) && !StringUtils.isEmpty(classCalledType)){
                            if ((classType.equalsIgnoreCase("CONTROLLER") && classCalledType.equalsIgnoreCase("CONTROLLER") )){
                                controllerSelfCallFre++;
                            }else if (classType.equalsIgnoreCase("DAO") && classCalledType.equalsIgnoreCase("DAO")) {
                                daoSelfCallFre++;
                            }
                        }
                    }
                }
            }
        }
        double selfCallProportion = controllerSelfCallFre * 1.0 / controllerSum + daoSelfCallFre * 1.0 / daoSum;
        return (double) Math.round(selfCallProportion * 100) / 100;
    }

    /**
     * 易修改性评估
     */
    public List<ModifiabilityResult> evaluateModifiable (List<ClassDescriptionVO> classDescriptionVOS) {

        List<ModifiabilityResult> modifiabilityResults = new ArrayList<>();
        for (ClassDescriptionVO classDescriptionVO : classDescriptionVOS) {
            Double methodModifiabilitySum = 0.0;
            ModifiabilityResult modifiabilityResult = new ModifiabilityResult();
            modifiabilityResult.setClassType(classDescriptionVO.getClassType());
            modifiabilityResult.setClassName(classDescriptionVO.getName());
            modifiabilityResult.setFullyQualifiedName(classDescriptionVO.getFullyQualifiedName());

            Long classId = classDescriptionVO.getId();
            String fullyQualifiedName = classDescriptionVO.getFullyQualifiedName();
            List<MethodDescriptionPO> methodDescriptionPOS = methodService.findByClass(classId);

            if (methodDescriptionPOS == null || methodDescriptionPOS.size() == 0){
                modifiabilityResult.setModifiability(0.0);
                modifiabilityResults.add(modifiabilityResult);
            }else {
                for (MethodDescriptionPO methodDescriptionPO : methodDescriptionPOS) {
                    int callFre = 0;
                    // 调用其他函数的频次
                    Long methodId = methodDescriptionPO.getId();
                    List<MethodCalledNodePO> byMethod = methodCalledService.findByMethod(methodId);
                    if (!byMethod.isEmpty()){
                        callFre = byMethod.size();
                    }

                    int calledFre = 0;
                    // 被其他函数调用的频次
                    for (ClassDescriptionVO classVO : classDescriptionVOS) {
                        for (MethodDescriptionPO methodVO : methodService.findByClass(classVO.getId())) {
                            for (MethodCalledNodePO methodCalledPO : methodCalledService.findByMethod(methodVO.getId())) {
                                String calledMthodName = methodCalledPO.getCalledMthodName();
                                String calledClassFullyName = methodCalledPO.getCalledClassFullyName();
                                if (StringUtils.isEmpty(calledMthodName) || StringUtils.isEmpty(calledClassFullyName)){
                                    continue;
                                }
                                if (methodCalledPO.getCalledMthodName().equals(methodDescriptionPO.getName()) && methodCalledPO.getCalledClassFullyName().equals(fullyQualifiedName)){
                                    calledFre++;
                                }
                            }
                        }
                    }

                    Double methodModifiability;
                    if (methodDescriptionPO.getCodeLine() == null){
                        methodModifiability = CALL_FRE * callFre + CALLED_FRE * calledFre + METHOD_GRANULARITY / (1 + Math.pow(Math.E, 0));
                    }else {
                        methodModifiability = CALL_FRE * callFre + CALLED_FRE * calledFre + METHOD_GRANULARITY / (1 + Math.pow(Math.E, -methodDescriptionPO.getCodeLine()));
                    }
                    methodModifiabilitySum += methodModifiability;
                }
                double classModifiability = methodModifiabilitySum / methodDescriptionPOS.size();
                modifiabilityResult.setModifiability((double) Math.round(classModifiability * 100) / 100);
                modifiabilityResults.add(modifiabilityResult);
            }
        }
        return modifiabilityResults;
    }
}
