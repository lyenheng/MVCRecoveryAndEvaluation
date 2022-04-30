package com.ly.mvc_recovery_evaluation.visitor;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.ly.mvc_recovery_evaluation.entity.ApiInfo;
import com.ly.mvc_recovery_evaluation.entity.ControllerClassDescription;
import com.ly.mvc_recovery_evaluation.entity.ApiParameterInfo;
import com.ly.mvc_recovery_evaluation.enums.RequestParameterType;
import com.ly.mvc_recovery_evaluation.enums.RequestType;
import com.ly.mvc_recovery_evaluation.parser.EntityClassParser;
import com.ly.mvc_recovery_evaluation.service.TypeResolveService;
import com.ly.mvc_recovery_evaluation.util.AnnotationUtil;
import com.ly.mvc_recovery_evaluation.util.ClassHandleUtil;
import com.ly.mvc_recovery_evaluation.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.*;


/**
 * @author liuyue
 * @date 2022/4/26 20:46
 */
@Component
public class ControllerClassVisitor extends VoidVisitorAdapter<ControllerClassDescription> {

    @Autowired
    private TypeResolveService typeResolveService;

    /**
     * 处理类
     * @param clz
     * @param arg
     */
    @Override
    public void visit(ClassOrInterfaceDeclaration clz, ControllerClassDescription arg) {
        super.visit(clz, arg);

        String parentPath = null;

        // 获取类的swagger信息和请求路径
        for (AnnotationExpr annotation : clz.getAnnotations()) {
            // swagger注解信息
            if (annotation.getNameAsString().equals("Api")){
                String tags = AnnotationUtil.getValue(annotation, "tags");
                arg.setApiDescription(tags);
            }

            // 请求父路径
            if (annotation.getNameAsString().equals("RequestMapping")){
                parentPath = AnnotationUtil.getValue(annotation, "value");
                arg.setParentRequestPath(parentPath);
            }
        }
    }

    /**
     * 处理方法
     * @param method
     * @param arg
     */
    @Override
    public void visit(MethodDeclaration method, ControllerClassDescription arg) {

        super.visit(method, arg);

        ClassOrInterfaceDeclaration clz = null;

        Optional<Node> parentNode = method.getParentNode();
        if (parentNode.isPresent()){
            clz = (ClassOrInterfaceDeclaration)parentNode.get();
        }else {
            return ;
        }

        ApiInfo apiInfo = new ApiInfo();
        NodeList<AnnotationExpr> annotations = method.getAnnotations();
        if (annotations != null && annotations.size() > 0){
            for (AnnotationExpr annotation : annotations) {
                if (annotation.getNameAsString().equals("ApiOperation")){
                    // swagger注解信息
                    String apiOperationInfo = AnnotationUtil.getValue(annotation, "value");
                    apiInfo.setApiOperationInfo(apiOperationInfo);
                }else {
                    // 请求类型和请求路径信息
                    String requestPath = AnnotationUtil.getValue(annotation, "value");
                    boolean flag = true;
                    switch (annotation.getNameAsString()){
                        case "PostMapping" :
                            apiInfo.setRequestType(RequestType.POST);
                            break;
                        case "GetMapping":
                            apiInfo.setRequestType(RequestType.GET);
                            break;
                        case "DeleteMapping":
                            apiInfo.setRequestType(RequestType.DELETE);
                            break;
                        case "PutMapping":
                            apiInfo.setRequestType(RequestType.PUT);
                            break;
                        case "PatchMapping":
                            apiInfo.setRequestType(RequestType.PATCH);
                            break;
                        case "RequestMapping":
                            String methodType = AnnotationUtil.getValue(annotation, "method");
                            if (StringUtils.isEmpty(methodType)){
                                apiInfo.setRequestType(RequestType.ALL);
                                break;
                            }

                            if (methodType.equals("RequestMethod.GET")){
                                apiInfo.setRequestType(RequestType.GET);
                            }else if (methodType.equals("RequestMethod.POST")){
                                apiInfo.setRequestType(RequestType.POST);
                            }else if (methodType.equals("RequestMethod.PUT")){
                                apiInfo.setRequestType(RequestType.PUT);
                            }else if (methodType.equals("RequestMethod.PATCH")){
                                apiInfo.setRequestType(RequestType.PATCH);
                            }else if (methodType.equals("RequestMethod.DELETE")){
                                apiInfo.setRequestType(RequestType.DELETE);
                            }
                            break;
                        default:
                            flag = false;

                    }
                    if (flag){
                        apiInfo.setRequestPath(requestPath);
                    }
                }
            }
        }

        // 返回值
        String returnValue = method.getType().toString();
        apiInfo.setReturnValue(returnValue);
        // 参数
        NodeList<Parameter> parameters = method.getParameters();
        if (parameters != null && parameters.size() > 0){
            List<ApiParameterInfo> apiParameterInfos = new ArrayList<>();
            for (Parameter parameter : parameters) {

                ApiParameterInfo apiParameterInfo = new ApiParameterInfo();
                apiParameterInfo.setParameter(parameter);

                if (parameter.getAnnotations().size() > 0){
                    for (AnnotationExpr annotation : parameter.getAnnotations()) {
                        switch (annotation.getNameAsString()){
                            case "RequestBody":
                                apiParameterInfo.setRequestParameterType(RequestParameterType.BODY);
                                break;
                            case "PathVariable":
                                apiParameterInfo.setRequestParameterType(RequestParameterType.PATH);
                                break;
                            case "RequestParam":
                                apiParameterInfo.setRequestParameterType(RequestParameterType.PARAM);
                                break;
                            default:
                                apiParameterInfo.setRequestParameterType(RequestParameterType.OTHERS);
                        }
                    }
                }else {
                    apiParameterInfo.setRequestParameterType(RequestParameterType.NULL);
                }

                if (!apiParameterInfo.getRequestParameterType().equals(RequestParameterType.NULL)
                        && !apiParameterInfo.getRequestParameterType().equals(RequestParameterType.OTHERS)
                ){

                    apiParameterInfo.setResolvedParameterJSON(resolveParameter(clz, apiParameterInfo));
                }
                apiParameterInfos.add(apiParameterInfo);
            }

            apiInfo.setApiParameterInfos(apiParameterInfos);
        }

        arg.getApiInfos().add(apiInfo);
    }

    /**
     * 解析参数信息，根据类名获取详细属性信息
     * @param clz
     * @param apiParameterInfo
     * @return
     */
    private String resolveParameter(ClassOrInterfaceDeclaration clz, ApiParameterInfo apiParameterInfo){

        Parameter parameter = apiParameterInfo.getParameter();

        String parameterName = parameter.getNameAsString();
        ClassOrInterfaceType parameterType = (ClassOrInterfaceType)parameter.getType();
        String parameterTypeName = parameterType.getNameAsString();
        if (!ClassHandleUtil.isEntityType(parameterTypeName)){
            // 基本数据类型
            return parameterName +  ": " +parameterTypeName ;
        }

        if (parameterTypeName.equals("List")){
            // List类型
            return typeResolveService.resolveList(clz, parameterType);

        }else if (parameterName.equals("Map")){
            // Map类型
            HashMap<String, String> map = new HashMap<>();
            map.put(parameterName, parameterTypeName);
            return StrUtil.mapToJson(map);
        }else {
            // 实体对象
            return typeResolveService.resolveEntity(clz,parameterTypeName);
        }
    }
}
