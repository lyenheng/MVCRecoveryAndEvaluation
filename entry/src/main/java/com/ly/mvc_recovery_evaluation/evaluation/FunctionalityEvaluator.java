package com.ly.mvc_recovery_evaluation.evaluation;

import com.ly.mvc_recovery_evaluation.entity.FunctionalityResult;
import com.ly.mvc_recovery_evaluation.entity.MethodDescriptionPO;
import com.ly.mvc_recovery_evaluation.service.ClassService;
import com.ly.mvc_recovery_evaluation.service.MethodService;
import com.ly.mvc_recovery_evaluation.vo.ClassDescriptionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyue
 * @date 2023/3/5 21:21
 */
@Service
public class FunctionalityEvaluator {

    /**
     * 功能正确性评估
     */
    public FunctionalityResult evaluateAccuracy (List<ClassDescriptionVO> classDescriptionVOS) {

        FunctionalityResult functionalityResult = new FunctionalityResult();
        functionalityResult.setHasDependencyCircle(false);

        List<String> requestTypeErrorClassList = new ArrayList<>();
        List<String> requestParamAnnotationLossClassList = new ArrayList<>();
        List<String> requestParamAnnotationErrorClassList = new ArrayList<>();

        for (ClassDescriptionVO classDescriptionVO : classDescriptionVOS) {
            if (!classDescriptionVO.getClassType().equalsIgnoreCase("CONTROLLER")){
                continue;
            }
            if (classDescriptionVO.getHasRequestTypeError() == 1){
                requestTypeErrorClassList.add(classDescriptionVO.getName());
            }

            if (classDescriptionVO.getHasRequestParamAnnotationLoss() == 1){
                requestParamAnnotationLossClassList.add(classDescriptionVO.getName());
            }

            if (classDescriptionVO.getHasRequestParamAnnotationError() == 1){
                requestParamAnnotationErrorClassList.add(classDescriptionVO.getName());
            }
        }

        functionalityResult.setRequestTypeErrorClass(requestTypeErrorClassList);
        functionalityResult.setRequestParamAnnotationLossClass(requestParamAnnotationLossClassList);
        functionalityResult.setRequestParamAnnotationErrorClass(requestParamAnnotationErrorClassList);

        return functionalityResult;
    }

}
