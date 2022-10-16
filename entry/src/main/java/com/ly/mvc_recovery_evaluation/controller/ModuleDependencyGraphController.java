package com.ly.mvc_recovery_evaluation.controller;

import com.ly.mvc_recovery_evaluation.dto.PageResult;
import com.ly.mvc_recovery_evaluation.entity.DetectProcedure;
import com.ly.mvc_recovery_evaluation.service.ModuleDependencyGraphService;
import com.ly.mvc_recovery_evaluation.vo.SearchProcedureVO;
import io.swagger.annotations.Api;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author liuyue
 * @date 2022/10/16 15:55
 */
@RestController
@RequestMapping("/moduleDependencyGraph")
@Api(tags = "模块依赖图")
public class ModuleDependencyGraphController {

    @Autowired
    private ModuleDependencyGraphService moduleDependencyGraphService;

    @GetMapping("/{detectId}")
    public Document getModuleDependencyGraphByDetect(@PathVariable Long detectId){
        return moduleDependencyGraphService.findByDetectId(detectId);
    }

}
