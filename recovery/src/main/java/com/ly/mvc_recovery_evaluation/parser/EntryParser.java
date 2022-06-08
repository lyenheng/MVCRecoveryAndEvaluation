package com.ly.mvc_recovery_evaluation.parser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.ly.mvc_recovery_evaluation.entity.EntryNode;
import com.ly.mvc_recovery_evaluation.entity.ModuleNode;
import com.ly.mvc_recovery_evaluation.util.FilePathConvertUtil;
import com.ly.mvc_recovery_evaluation.visitor.ApplicationVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyue
 * @date 2022/4/25 18:52
 * 提取启动类信息
 */
@Component
public class EntryParser {

    @Autowired
    private ApplicationVisitor applicationVisitor;

    public List<EntryNode> parse(ModuleNode moduleNode){
        List<EntryNode> entryNodeList = new ArrayList<>();

        File javaFile = FilePathConvertUtil.getJavaFile(moduleNode);

        getChildrenFile(javaFile, entryNodeList);
        return entryNodeList;
    }

    private void getChildrenFile(File file, List<EntryNode> entryNodeList){

        if (!file.isDirectory()){
            try {
                EntryNode entryNode = new EntryNode();
                entryNode.setFile(file);
                CompilationUnit compilationUnit = new JavaParser().parse(file).getResult().get();
                compilationUnit.accept(applicationVisitor, entryNode);

                if (!StringUtils.isEmpty(entryNode.getContent())){
                    entryNodeList.add(entryNode);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            File[] files = file.listFiles();
            if (files != null){
                for (File file1 : files) {
                    getChildrenFile(file1, entryNodeList);
                }
            }
        }
    }


}
