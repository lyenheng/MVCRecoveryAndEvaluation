package com.ly.mvc_recovery_evaluation.vo;

import com.ly.mvc_recovery_evaluation.entity.ClassDescriptionPO;
import lombok.Data;

import javax.persistence.Column;


/**
 * @author liuyue
 * @date 2022/12/3 19:57
 */
@Data
public class ClassDescriptionVO extends ClassDescriptionPO {

    private Long id;
    private Long moduleNodeId;

    private String moduleName;

    private String name;

    private String filePath;

    private String classType;

    private String fullyQualifiedName;

    private String annotations;

    private String declarationType;

    /**
     * 类中包含的方法个数
     */
    private Integer methodNum;

    private String interfaceServices;

    private String extendsServices;

    private Integer hasRequestTypeError;

    private Integer hasRequestParamAnnotationLoss;

    private Integer hasRequestParamAnnotationError;

    public ClassDescriptionVO(ClassDescriptionPO classDescriptionPO, Integer methodNum) {
        this.id = classDescriptionPO.getId();
        this.moduleNodeId = classDescriptionPO.getModuleNodeId();
        this.name = classDescriptionPO.getName();
        this.filePath = classDescriptionPO.getFilePath();
        this.classType = classDescriptionPO.getClassType();
        this.fullyQualifiedName = classDescriptionPO.getFullyQualifiedName();
        this.annotations = classDescriptionPO.getAnnotations();
        this.methodNum = methodNum;
        this.declarationType = classDescriptionPO.getDeclarationType();
        this.interfaceServices = classDescriptionPO.getInterfaceServices();
        this.extendsServices = classDescriptionPO.getExtendsServices();
        this.hasRequestTypeError = classDescriptionPO.getHasRequestTypeError();
        this.hasRequestParamAnnotationLoss = classDescriptionPO.getHasRequestParamAnnotationLoss();
        this.hasRequestParamAnnotationError = classDescriptionPO.getHasRequestParamAnnotationError();
    }
}
