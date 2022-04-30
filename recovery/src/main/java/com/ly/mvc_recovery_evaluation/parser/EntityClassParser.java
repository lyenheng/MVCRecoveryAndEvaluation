package com.ly.mvc_recovery_evaluation.parser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.ly.mvc_recovery_evaluation.visitor.EntityClassVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 提取实体类的属性
 * @author liuyue
 * @date 2022/4/29 19:37
 */
@Component
public class EntityClassParser {

    @Autowired
    private EntityClassVisitor entityClassVisitor;

    public Map<String, String> parse(File entityFile){
        try {
            Map<String, ClassOrInterfaceType> arg = new HashMap<>();
            CompilationUnit compilationUnit = new JavaParser().parse(entityFile).getResult().get();
            compilationUnit.accept(entityClassVisitor, arg);

            Map<String, String> nameToType = new HashMap<>();
            arg.forEach((name , type) -> {
                nameToType.put(name, type.asString());
            });

            return nameToType;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
