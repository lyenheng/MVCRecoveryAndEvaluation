package com.ly.mvc_recovery_evaluation.parser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.ly.mvc_recovery_evaluation.entity.ClassDescription;
import com.ly.mvc_recovery_evaluation.entity.DaoClassDescription;
import com.ly.mvc_recovery_evaluation.entity.ModuleNode;
import com.ly.mvc_recovery_evaluation.entity.ServiceClassDescription;
import com.ly.mvc_recovery_evaluation.enums.ClassType;
import com.ly.mvc_recovery_evaluation.visitor.DaoClassVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyue
 * @date 2022/4/27 17:21
 */
@Component
public class DaoClassParser {

    @Autowired
    private DaoClassVisitor daoClassVisitor;

    public void parse(ModuleNode moduleNode){
        List<ClassDescription> classDescriptions = moduleNode.getClassDescriptions();
        if (classDescriptions == null || classDescriptions.size() < 1){
            return;
        }

        for (ClassDescription classDescription : classDescriptions) {
            if (classDescription.getClassType() != null && classDescription.getClassType().equals(ClassType.DAO)){
                File serviceFile = classDescription.getFile();

                DaoClassDescription daoClassDescription = (DaoClassDescription)classDescription ;
                try {
                    CompilationUnit compilationUnit = new JavaParser().parse(serviceFile).getResult().get();
                    compilationUnit.accept(daoClassVisitor, daoClassDescription);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
