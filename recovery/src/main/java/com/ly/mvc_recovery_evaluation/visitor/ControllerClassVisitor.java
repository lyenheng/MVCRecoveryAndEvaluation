package com.ly.mvc_recovery_evaluation.visitor;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.ly.mvc_recovery_evaluation.entity.ApiInfo;
import com.ly.mvc_recovery_evaluation.entity.ControllerClassDescription;
import com.ly.mvc_recovery_evaluation.entity.ParameterInfo;
import com.ly.mvc_recovery_evaluation.enums.RequestParameterType;
import com.ly.mvc_recovery_evaluation.enums.RequestType;
import com.ly.mvc_recovery_evaluation.util.AnnotationUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * @author liuyue
 * @date 2022/4/26 20:46
 */
@Component
public class ControllerClassVisitor extends VoidVisitorAdapter<ControllerClassDescription> {

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
            List<ParameterInfo> parameterInfos = new ArrayList<>();
            for (Parameter parameter : parameters) {

                String parameterType = parameter.getType().toString();
                String parameterName = parameter.getName().toString();

                ParameterInfo parameterInfo = new ParameterInfo();
                parameterInfo.setParameterType(parameterType);
                parameterInfo.setParameterName(parameterName);

                for (AnnotationExpr annotation : parameter.getAnnotations()) {
                    switch (annotation.getNameAsString()){
                        case "RequestBody":
                            parameterInfo.setRequestParameterType(RequestParameterType.BODY);
                            break;
                        case "PathVariable":
                            parameterInfo.setRequestParameterType(RequestParameterType.PATH);
                            break;
                        case "RequestParam":
                            parameterInfo.setRequestParameterType(RequestParameterType.PARAM);
                            break;
                    }
                }

                parameterInfos.add(parameterInfo);
            }

            apiInfo.setParameters(parameterInfos);
        }

        arg.getApiInfos().add(apiInfo);
    }
}
