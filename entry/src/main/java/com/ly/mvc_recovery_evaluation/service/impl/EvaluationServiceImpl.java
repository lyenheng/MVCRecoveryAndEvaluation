package com.ly.mvc_recovery_evaluation.service.impl;

import com.ly.mvc_recovery_evaluation.entity.*;
import com.ly.mvc_recovery_evaluation.evaluation.EfficiencyEvaluator;
import com.ly.mvc_recovery_evaluation.evaluation.FunctionalityEvaluator;
import com.ly.mvc_recovery_evaluation.evaluation.MaintainablityEvalutor;
import com.ly.mvc_recovery_evaluation.service.ClassService;
import com.ly.mvc_recovery_evaluation.service.EvaluationService;
import com.ly.mvc_recovery_evaluation.service.MethodService;
import com.ly.mvc_recovery_evaluation.vo.ClassDescriptionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyue
 * @date 2023/3/11 20:39
 */
@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    private ClassService classService;

    @Autowired
    private MaintainablityEvalutor maintainablityEvalutor;

    @Autowired
    private FunctionalityEvaluator functionalityEvaluator;

    @Autowired
    private EfficiencyEvaluator efficiencyEvaluator;

    public static int CONTROLLER_LAYER_COMPLEXITY = 5;

    public static int CROSS_LAYER_CALL = 20;

    public static int SELF_CALL = 10;

    @Override
    public EvaluationPO getResult1(Long moduleId) {

        EvaluationPO evaluationPO = new EvaluationPO();

        MaintainabilityResult maintainabilityResult = new MaintainabilityResult();
        maintainabilityResult.setAnalyzabilityResult(new AnalyzabilityResult(1.0, 0.89, 0.86, 0.996));
        maintainabilityResult.setModuleEvaluateResult(new ModuleEvaluateResult(20, 30, 10, 0.98));
        List<ModifiabilityResult> modifiabilityResults = new ArrayList<>();
        modifiabilityResults.add(new ModifiabilityResult("TestController","fullyQualifiedName1", "Controller", 51.0));
        modifiabilityResults.add(new ModifiabilityResult("TestService", "fullyQualifiedName1", "Service", 101.0));
        modifiabilityResults.add(new ModifiabilityResult("TestDao","fullyQualifiedName3", "Dao", 21.0));
        modifiabilityResults.add(new ModifiabilityResult("TestDao","fullyQualifiedName3", "Dao", 21.0));
        modifiabilityResults.add(new ModifiabilityResult("TestDao","fullyQualifiedName3", "Dao", 21.0));
        modifiabilityResults.add(new ModifiabilityResult("TestDao","fullyQualifiedName3", "Dao", 21.0));
        modifiabilityResults.add(new ModifiabilityResult("TestDao","fullyQualifiedName3", "Dao", 21.0));
        modifiabilityResults.add(new ModifiabilityResult("TestDao","fullyQualifiedName3", "Dao", 21.0));
        modifiabilityResults.add(new ModifiabilityResult("TestDao","fullyQualifiedName3", "Dao", 21.0));
        modifiabilityResults.add(new ModifiabilityResult("TestDao","fullyQualifiedName3", "Dao", 21.0));
        modifiabilityResults.add(new ModifiabilityResult("TestDao","fullyQualifiedName3", "Dao", 21.0));
        modifiabilityResults.add(new ModifiabilityResult("TestDao","fullyQualifiedName3", "Dao", 21.0));
        modifiabilityResults.add(new ModifiabilityResult("TestDao","fullyQualifiedName3", "Dao", 21.0));
        modifiabilityResults.add(new ModifiabilityResult("TestDao","fullyQualifiedName3", "Dao", 21.0));
        modifiabilityResults.add(new ModifiabilityResult("TestDao","fullyQualifiedName3", "Dao", 21.0));
        modifiabilityResults.add(new ModifiabilityResult("TestDao","fullyQualifiedName3", "Dao", 21.0));
        modifiabilityResults.add(new ModifiabilityResult("TestDao","fullyQualifiedName3", "Dao", 21.0));
        modifiabilityResults.add(new ModifiabilityResult("TestDao","fullyQualifiedName3", "Dao", 21.0));
        modifiabilityResults.add(new ModifiabilityResult("TestDao","fullyQualifiedName3", "Dao", 21.0));
        modifiabilityResults.add(new ModifiabilityResult("TestDao","fullyQualifiedName3", "Dao", 21.0));
        maintainabilityResult.setModifiabilityResults(modifiabilityResults);

        List<String> errorClass = new ArrayList<>();
        errorClass.add("ClassC");
        errorClass.add("ClassD");
        errorClass.add("ClassD");
        errorClass.add("ClassD");
        errorClass.add("ClassD");
        errorClass.add("ClassD");
        errorClass.add("ClassD");
        errorClass.add("ClassD");
        errorClass.add("ClassD");
        errorClass.add("ClassD");
        errorClass.add("ClassD");
        errorClass.add("ClassD");
        errorClass.add("ClassD");
        errorClass.add("ClassD");
        errorClass.add("ClassD");
        errorClass.add("ClassD");
        errorClass.add("ClassD");
        errorClass.add("ClassD");
        errorClass.add("ClassD");
        errorClass.add("ClassD");
        errorClass.add("ClassD");
        errorClass.add("ClassD");
        errorClass.add("ClassD");
        errorClass.add("ClassD");
        errorClass.add("ClassD");
        FunctionalityResult functionalityResult = new FunctionalityResult();
        functionalityResult.setRequestTypeErrorClass(errorClass);
        functionalityResult.setHasDependencyCircle(false);
        functionalityResult.setRequestParamAnnotationLossClass(errorClass);
        functionalityResult.setRequestParamAnnotationErrorClass(errorClass);

        EfficiencyResult efficiencyResult = new EfficiencyResult();
        List<String> testClass = new ArrayList<>();
        testClass.add("ClassA.methodA");
        testClass.add("ClassB.methodB");
        efficiencyResult.setMiddleCyclomaticComplexity(testClass);
        efficiencyResult.setHighCyclomaticComplexity(testClass);
        efficiencyResult.setCyclomaticComplexityAverage(5.5);

        evaluationPO.setMaintainabilityResult(maintainabilityResult);
        evaluationPO.setFunctionalityResult(functionalityResult);
        evaluationPO.setEfficiencyResult(efficiencyResult);

        return evaluationPO;
    }

    @Override
    public EvaluationPO getResult(Long moduleId) {

        EvaluationPO evaluationPO = new EvaluationPO();

        // 根据入口模块查找所有类信息
        List<ClassDescriptionVO> classDescriptionVOS = classService.getAllClassByEntryModule(moduleId);

        // 可维护性评估
        MaintainabilityResult maintainabilityResult = getMaintainabilityResult(classDescriptionVOS);
        evaluationPO.setMaintainabilityResult(maintainabilityResult);

        // 功能评估
        FunctionalityResult functionalityResult = getFunctionalityResult(classDescriptionVOS);
        evaluationPO.setFunctionalityResult(functionalityResult);

        // 效率
        EfficiencyResult efficiencyResult = getEfficiencyResult(classDescriptionVOS);
        evaluationPO.setEfficiencyResult(efficiencyResult);

        return evaluationPO;
    }



    /**
     * 可维护性评估
     * @return
     */
    public MaintainabilityResult getMaintainabilityResult(List<ClassDescriptionVO> classDescriptionVOS){
        MaintainabilityResult maintainabilityResult = new MaintainabilityResult();

        // 模块化
        ModuleEvaluateResult moduleEvaluateResult = moduleEvaluate(classDescriptionVOS);
        maintainabilityResult.setModuleEvaluateResult(moduleEvaluateResult);

        // 易分析性
        AnalyzabilityResult analyzabilityResult = getAnalyzabilityResult(classDescriptionVOS);
        maintainabilityResult.setAnalyzabilityResult(analyzabilityResult);

        // 易修改性
        List<ModifiabilityResult> modifiability = getModifiability(classDescriptionVOS);
        maintainabilityResult.setModifiabilityResults(modifiability);

        return maintainabilityResult;
    }

    /**
     * 模块化评估
     * @param classDescriptionVOS
     */
    public ModuleEvaluateResult moduleEvaluate(List<ClassDescriptionVO> classDescriptionVOS) {
        return maintainablityEvalutor.evaluateModule(classDescriptionVOS);
    }

    /**
     * 易分析性评估
     * @param classDescriptionVOS
     * @return
     */
    public AnalyzabilityResult getAnalyzabilityResult(List<ClassDescriptionVO> classDescriptionVOS){

        AnalyzabilityResult analyzabilityResult = new AnalyzabilityResult();

        // Controller层复杂度
        Double controllerLayerComplexity = maintainablityEvalutor.evaluateControllerComplexity(classDescriptionVOS);
        analyzabilityResult.setControllerLayerComplexity(controllerLayerComplexity);

        // 跨层可见性评估
        Double crossLayerCallVisibility = maintainablityEvalutor.getCrossLayerCallVisibility(classDescriptionVOS);
        analyzabilityResult.setCrossLayerCallVisibility(crossLayerCallVisibility);

        // 自调用情况占比
        Double selfCallProportion = maintainablityEvalutor.getSelfCallProportion(classDescriptionVOS);
        analyzabilityResult.setSelfCallProportion(selfCallProportion);

        // 总体易分析性
        Double analyzability = CONTROLLER_LAYER_COMPLEXITY * controllerLayerComplexity + CROSS_LAYER_CALL * crossLayerCallVisibility + SELF_CALL * selfCallProportion;
        analyzabilityResult.setAnalyzability(analyzability);

        return analyzabilityResult;

    }

    /**
     * 易修改性评估
     * @param classDescriptionVOS
     * @return
     */
    public List<ModifiabilityResult> getModifiability(List<ClassDescriptionVO> classDescriptionVOS) {
        return maintainablityEvalutor.evaluateModifiable(classDescriptionVOS);
    }

    /**
     * 功能评估
     * @param classDescriptionVOS
     * @return
     */
    public FunctionalityResult getFunctionalityResult(List<ClassDescriptionVO> classDescriptionVOS){
        return functionalityEvaluator.evaluateAccuracy(classDescriptionVOS);
    }

    /**
     * 效率评估
     * @param classDescriptionVOS
     * @return
     */
    public EfficiencyResult getEfficiencyResult(List<ClassDescriptionVO> classDescriptionVOS){
        return efficiencyEvaluator.evaluateTime(classDescriptionVOS);
    }

}
