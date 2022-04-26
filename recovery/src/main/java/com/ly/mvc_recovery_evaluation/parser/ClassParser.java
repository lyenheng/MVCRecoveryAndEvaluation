package com.ly.mvc_recovery_evaluation.parser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.ly.mvc_recovery_evaluation.entity.*;
import com.ly.mvc_recovery_evaluation.enums.ClassType;
import com.ly.mvc_recovery_evaluation.util.FilePathConvertUtil;
import com.ly.mvc_recovery_evaluation.visitor.ClassVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author liuyue
 * @date 2022/4/26 14:59
 */
@Component
public class ClassParser {

    @Autowired
    private ClassVisitor classVisitor;

    public List<ClassDescription> parse(ModuleNode moduleNode){

        File javaFile = FilePathConvertUtil.getJavaFile(moduleNode);

        List<ClassDescription> classDescriptions = new ArrayList<>();

        getChildrenFile(javaFile, classDescriptions);

        return classDescriptions;
    }

    private void getChildrenFile(File file, List<ClassDescription> classDescriptions){

        if (file.isFile() && file.getName().trim().endsWith(".java")){
            // 如果是Java文件进行扫描
            try{
                ClassDescription classDescription = new ClassDescription();
                classDescription.setFile(file);
                CompilationUnit compilationUnit = new JavaParser().parse(file).getResult().get();
                compilationUnit.accept(classVisitor, classDescription);

                if (classDescription.getClassType() == null){
                    classDescriptions.add(classDescription);
                }else {
                    if (classDescription.getClassType().equals(ClassType.CONTROLLER)){
                        ControllerClassDescription controllerClassDescription = new ControllerClassDescription(classDescription);
                        classDescriptions.add(controllerClassDescription);
                    }else if (classDescription.getClassType().equals(ClassType.SERVICE)){
                        ServiceClassDescription serviceClassDescription = new ServiceClassDescription(classDescription);
                        classDescriptions.add(serviceClassDescription);
                    }else if (classDescription.getClassType().equals(ClassType.DAO)){
                        DaoClassDescription daoClassDescription = new DaoClassDescription(classDescription);
                        classDescriptions.add(daoClassDescription);
                    } else {
                        classDescriptions.add(classDescription);
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }else if (file.isDirectory()){
            for (File listFile : Objects.requireNonNull(file.listFiles())) {
                getChildrenFile(listFile, classDescriptions);
            }
        }
    }
}
