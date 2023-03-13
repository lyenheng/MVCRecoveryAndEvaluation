package com.ly.mvc_recovery_evaluation.controller;

import com.ly.mvc_recovery_evaluation.entity.EvaluationPO;
import com.ly.mvc_recovery_evaluation.service.EvaluationService;
import com.ly.mvc_recovery_evaluation.vo.ClassDescriptionVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author liuyue
 * @date 2023/3/11 20:35
 */
@RestController
@RequestMapping("/evaluation")
@Api(tags = "类描述信息")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @GetMapping("/{entryModuleId}")
    public EvaluationPO getEvaluationResult(@PathVariable Long entryModuleId) {
        return evaluationService.getResult(entryModuleId);
    }

    @GetMapping("/1/{entryModuleId}")
    public EvaluationPO getEvaluationResult1(@PathVariable Long entryModuleId) {
        return evaluationService.getResult1(entryModuleId);
    }
}
