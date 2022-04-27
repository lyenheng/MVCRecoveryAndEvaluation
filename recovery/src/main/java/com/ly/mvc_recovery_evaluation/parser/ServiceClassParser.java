package com.ly.mvc_recovery_evaluation.parser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.ly.mvc_recovery_evaluation.entity.*;
import com.ly.mvc_recovery_evaluation.enums.ClassType;
import com.ly.mvc_recovery_evaluation.visitor.ServiceClassVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyue
 * @date 2022/4/27 15:26
 */
@Component
public class ServiceClassParser {

    @Autowired
    private ServiceClassVisitor serviceClassVisitor;

    public void parse(ModuleNode moduleNode){
        List<ClassDescription> classDescriptions = moduleNode.getClassDescriptions();
        if (classDescriptions == null || classDescriptions.size() < 1){
            return;
        }

        for (ClassDescription classDescription : classDescriptions) {
            if (classDescription.getClassType() != null && classDescription.getClassType().equals(ClassType.SERVICE)){
                File serviceFile = classDescription.getFile();

                ServiceClassDescription serviceClassDescription = (ServiceClassDescription)classDescription ;
                try {
                    CompilationUnit compilationUnit = new JavaParser().parse(serviceFile).getResult().get();
                    compilationUnit.accept(serviceClassVisitor, serviceClassDescription);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
