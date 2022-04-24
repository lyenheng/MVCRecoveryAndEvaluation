package com.ly.mvc_recovery_evaluation.controller;

import com.ly.mvc_recovery_evaluation.MvcRecovery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * @author liuyue
 * @date 2022/4/21 14:35
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private MvcRecovery mvcRecovery;


    @GetMapping("/recovery")
    public void testRecovery(){
        File file = new File("E:\\seu\\MVC\\code\\MVCRecoveryEvaluation");
        mvcRecovery.recover(file);
    }

}
