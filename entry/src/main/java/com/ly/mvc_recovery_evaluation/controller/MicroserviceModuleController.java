package com.ly.mvc_recovery_evaluation.controller;

import com.ly.mvc_recovery_evaluation.entity.ModuleNodePO;
import com.ly.mvc_recovery_evaluation.service.MicroServiceModuleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/11/19 15:46
 */
@RestController
@RequestMapping("/microserviceModule")
@Api(tags = "子服务模块")
public class MicroserviceModuleController {

    @Autowired
    private MicroServiceModuleService microServiceModuleService;

    @GetMapping("/{detectId}")
    public List<ModuleNodePO> getMicroServiceModule(@PathVariable Long detectId) {
        return microServiceModuleService.findMicroServiceModuleByDetectId(detectId);
    }

}
