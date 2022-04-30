package com.ly.mvc_recovery_evaluation.parser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.ly.mvc_recovery_evaluation.entity.ApiInfo;
import com.ly.mvc_recovery_evaluation.entity.ClassDescription;
import com.ly.mvc_recovery_evaluation.entity.ControllerClassDescription;
import com.ly.mvc_recovery_evaluation.entity.ModuleNode;
import com.ly.mvc_recovery_evaluation.enums.ClassType;
import com.ly.mvc_recovery_evaluation.visitor.ControllerClassVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * @author liuyue
 * @date 2022/4/26 20:48
 */
@Component
public class ControllerClassParser {

    @Autowired
    private ControllerClassVisitor controllerClassVisitor;

    public void parse(ModuleNode moduleNode){
        List<ClassDescription> classDescriptions = moduleNode.getClassDescriptions();
        if (classDescriptions == null || classDescriptions.size() < 1){
            return;
        }

        for (ClassDescription classDescription : classDescriptions) {
            if (classDescription.getClassType() != null && classDescription.getClassType().equals(ClassType.CONTROLLER)){
                File controllerFile = classDescription.getFile();

                ControllerClassDescription controllerClassDescription = (ControllerClassDescription)classDescription ;
                List<ApiInfo> apiInfos = new ArrayList<>();
                controllerClassDescription.setApiInfos(apiInfos);
                try {
                    CompilationUnit compilationUnit = new JavaParser().parse(controllerFile).getResult().get();
                    compilationUnit.accept(controllerClassVisitor, controllerClassDescription);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
