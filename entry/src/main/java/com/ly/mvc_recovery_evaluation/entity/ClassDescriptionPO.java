package com.ly.mvc_recovery_evaluation.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author liuyue
 * @date 2022/6/13 19:52
 */
@Entity
@Data
@Table(name = "class_description")
public class ClassDescriptionPO {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="module_node_id")
    private Long moduleNodeId;

    @Column(name="name")
    private String name;

    @Column(name="file_path")
    private String filePath;

    @Column(name="class_type")
    private String classType;

    @Column(name="fully_qualified_name")
    private String fullyQualifiedName;

    @Column(name="annotations")
    private String annotations;

    @Column(name="declaration_type")
    private String declarationType;

    @Column(name="interface_services")
    private String interfaceServices;

    @Column(name="extends_services")
    private String extendsServices;

    @Column(name="has_request_type_error")
    private int hasRequestTypeError;

    @Column(name="has_request_param_annotation_loss")
    private int hasRequestParamAnnotationLoss;

    @Column(name="has_request_param_annotation_error")
    private int hasRequestParamAnnotationError;
}
