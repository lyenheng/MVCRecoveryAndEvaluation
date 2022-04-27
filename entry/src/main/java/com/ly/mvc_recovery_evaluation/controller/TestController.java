package com.ly.mvc_recovery_evaluation.controller;

import com.ly.mvc_recovery_evaluation.MvcRecovery;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;

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
        File file = new File("E:\\keda\\project\\back\\dispatch-device-bind-backend");
//        File file = new File("E:\\seu\\MVC\\code\\MVCRecoveryEvaluation");
        mvcRecovery.recover(file);
    }



}
