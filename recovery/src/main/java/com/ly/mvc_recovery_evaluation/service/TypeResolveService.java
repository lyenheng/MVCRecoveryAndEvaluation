package com.ly.mvc_recovery_evaluation.service;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.ly.mvc_recovery_evaluation.parser.EntityClassParser;
import com.ly.mvc_recovery_evaluation.util.ClassHandleUtil;
import com.ly.mvc_recovery_evaluation.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author liuyue
 * @date 2022/4/30 15:38
 */
@Component
public class TypeResolveService {

    @Autowired
    private CommonService commonService;

    @Autowired
    private EntityClassParser entityClassParser;


    /**
     * 解析实体类型
     * @param clz
     * @param parameterTypeName
     * @return
     */
    public String resolveEntity(ClassOrInterfaceDeclaration clz, String parameterTypeName) {

        // 根据类名找到全限定类名
        String fullyQualifiedName = commonService.getFullyQualifiedNameByClassName(clz, parameterTypeName);
        // 根据全限定类名找到对应的文件
        File searchFile = commonService.search(fullyQualifiedName);
        if (searchFile == null || !searchFile.exists()){
            // 如果对应的文件未找到
            HashMap<String, String> map = new HashMap<>();
            map.put(parameterTypeName.substring(0,1).toLowerCase()+parameterTypeName.substring(1), parameterTypeName);
            return StrUtil.mapToJson(map);
        }else {
            Map<String, String> parse = entityClassParser.parse(searchFile);
            return StrUtil.mapToJson(parse);
        }
    }


    /**
     * 解析list类型
     * @param clz
     * @param parameterType
     * @return
     */
    public String resolveList(ClassOrInterfaceDeclaration clz, ClassOrInterfaceType parameterType){

        StringBuilder json = new StringBuilder("[");
        Optional<NodeList<Type>> typeArguments = parameterType.getTypeArguments();
        if (typeArguments.isPresent()) {
            String typeName = ((ClassOrInterfaceType) typeArguments.get().get(0)).getNameAsString();
            if (!ClassHandleUtil.isEntityType(typeName)) {
                // 基础类
                json.append(typeName);
            } else {
                // 实体类型
                json.append(resolveEntity(clz, typeName));
            }
        }

        json.append("]");
        return json.toString();
    }

}




