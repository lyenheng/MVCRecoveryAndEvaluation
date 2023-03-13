package com.ly.mvc_recovery_evaluation.evaluation;

import com.ly.mvc_recovery_evaluation.entity.ClassDescription;
import com.ly.mvc_recovery_evaluation.entity.EfficiencyResult;
import com.ly.mvc_recovery_evaluation.entity.MethodDescriptionPO;
import com.ly.mvc_recovery_evaluation.enums.ClassType;
import com.ly.mvc_recovery_evaluation.service.MethodService;
import com.ly.mvc_recovery_evaluation.vo.ClassDescriptionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyue
 * @date 2023/3/5 21:20
 */
@Service
public class EfficiencyEvaluator {

    @Autowired
    private MethodService methodService;

    /**
     * 时间特性评估
     */
    public EfficiencyResult evaluateTime (List<ClassDescriptionVO> classDescriptionVOS) {

        EfficiencyResult efficiencyResult = new EfficiencyResult();
        int methodNum = 0;
        int methodCyclomaticComplexitySum = 0;
        List<String> middleCyclomaticComplexityList = new ArrayList<>();
        List<String> highCyclomaticComplexityList = new ArrayList<>();
        for (ClassDescriptionVO classDescriptionVO : classDescriptionVOS) {
            String classType = classDescriptionVO.getClassType();
            if (classType == null)  continue;
            if (classType.equalsIgnoreCase("SERVICE")) {
                List<MethodDescriptionPO> methodDescriptionPOS = methodService.findByClass(classDescriptionVO.getId());
                for (MethodDescriptionPO methodDescriptionPO : methodDescriptionPOS) {
                    Integer cyclomaticComplexity = methodDescriptionPO.getCyclomaticComplexity();
                    methodCyclomaticComplexitySum += cyclomaticComplexity;
                    methodNum++;
                    if (cyclomaticComplexity >= 7 && cyclomaticComplexity <= 10){
                        middleCyclomaticComplexityList.add(classDescriptionVO.getName() + ":" + methodDescriptionPO.getName());
                    }else if (cyclomaticComplexity > 10){
                        highCyclomaticComplexityList.add(classDescriptionVO.getName() + ":" + methodDescriptionPO.getName());
                    }
                }
            }
        }

        Double cyclomaticComplexityAverage = methodCyclomaticComplexitySum * 1.0 / methodNum ;
        efficiencyResult.setMiddleCyclomaticComplexity(middleCyclomaticComplexityList);
        efficiencyResult.setHighCyclomaticComplexity(highCyclomaticComplexityList);
        efficiencyResult.setCyclomaticComplexityAverage((double) Math.round(cyclomaticComplexityAverage * 100) / 100);

        return efficiencyResult;
    }
}
