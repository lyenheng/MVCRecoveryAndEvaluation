package com.ly.mvc_recovery_evaluation.entity;

import lombok.Data;

import javax.persistence.*;

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
}
