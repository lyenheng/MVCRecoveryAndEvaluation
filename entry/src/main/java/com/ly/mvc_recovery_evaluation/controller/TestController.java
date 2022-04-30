package com.ly.mvc_recovery_evaluation.controller;

import com.ly.mvc_recovery_evaluation.MvcRecovery;
import com.ly.mvc_recovery_evaluation.bean.ProjectNode;
import com.ly.mvc_recovery_evaluation.entity.ApiInfo;
import com.ly.mvc_recovery_evaluation.entity.ModuleNode;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author liuyue
 * @date 2022/4/21 14:35
 */
@RestController
@RequestMapping("/test")
@Api(tags = "测试")
public class TestController {

    @Autowired
    private MvcRecovery mvcRecovery;


    @GetMapping("/recovery")
    public void testRecovery(){
//        File file = new File("E:\\keda\\project\\back\\dispatch-device-bind-backend");
        File file = new File("E:\\seu\\MVC\\code\\MVCRecoveryEvaluation");
        mvcRecovery.recover(file);
    }

    @GetMapping("/recovery1")
    public List<ProjectNode> testRecovery1(@RequestBody List<ApiInfo> apiInfos, String id, Map<String, ApiInfo> keys, @RequestBody ApiInfo apiInfo){
        File file = new File("E:\\seu\\MVC\\code\\MVCRecoveryEvaluation");
        return null;
    }

    @PostMapping("/recovery2")
    public ProjectNode testRecovery2(@RequestBody List<ApiInfo> apiInfos, String id, Map<String, ApiInfo> keys, @RequestBody ApiInfo apiInfo){
        File file = new File("E:\\seu\\MVC\\code\\MVCRecoveryEvaluation");
        mvcRecovery.recover(file);
        return null;
    }

    @PostMapping("/recovery3")
    public int testRecovery3(@RequestBody List<ApiInfo> apiInfos, String id, Map<String, ApiInfo> keys, @RequestBody ApiInfo apiInfo){
        File file = new File("E:\\seu\\MVC\\code\\MVCRecoveryEvaluation");
        mvcRecovery.recover(file);
        return 1;
    }

    @PostMapping("/recovery4")
    public Integer testRecovery4(@RequestBody List<ApiInfo> apiInfos, String id, Map<String, ApiInfo> keys, @RequestBody ApiInfo apiInfo){
        File file = new File("E:\\seu\\MVC\\code\\MVCRecoveryEvaluation");
        mvcRecovery.recover(file);
        return null;
    }
}
