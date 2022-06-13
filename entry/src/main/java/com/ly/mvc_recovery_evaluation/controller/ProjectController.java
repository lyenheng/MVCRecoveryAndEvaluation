package com.ly.mvc_recovery_evaluation.controller;

import com.ly.mvc_recovery_evaluation.entity.ProjectNodePO;
import com.ly.mvc_recovery_evaluation.service.ProjectService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/6/13 20:16
 */
@RestController
@RequestMapping("/info")
@Api(tags = "项目")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/project")
    public void testRecovery(){
        List<ProjectNodePO> projectNodePOS = projectService.listProject();
        System.out.println(projectNodePOS);
    }

    @PostMapping("/add")
    public void addProjectNode(@RequestBody ProjectNodePO projectNodePO){
        projectService.add(projectNodePO);
    }
}
