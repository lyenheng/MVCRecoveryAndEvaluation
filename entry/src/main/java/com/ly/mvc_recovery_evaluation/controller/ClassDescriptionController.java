package com.ly.mvc_recovery_evaluation.controller;

import com.ly.mvc_recovery_evaluation.entity.ClassDescriptionPO;
import com.ly.mvc_recovery_evaluation.entity.ModuleNodePO;
import com.ly.mvc_recovery_evaluation.service.ClassService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/11/26 18:46
 */
@RestController
@RequestMapping("/classInfo")
@Api(tags = "类描述信息")
public class ClassDescriptionController {

    @Autowired
    private ClassService classService;


    @GetMapping("getAllClassInfo/{entryModuleId}")
    public List<ClassDescriptionPO> getAllClassInfo(@PathVariable Long entryModuleId) {
        return classService.getAllClassByEntryModule(entryModuleId);
    }
}
