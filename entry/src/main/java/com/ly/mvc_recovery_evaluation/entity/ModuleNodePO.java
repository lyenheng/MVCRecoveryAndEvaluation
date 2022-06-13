package com.ly.mvc_recovery_evaluation.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author liuyue
 * @date 2022/6/13 19:45
 */
@Entity
@Data
@Table(name = "module_node")
public class ModuleNodePO {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="project_node_id")
    private Long projectNodeId;

    @Column(name="module_name")
    private String moduleName;

    @Column(name="module_file_path")
    private String moduleFilePath;

    @Column(name="module_type")
    private Long moduleType;

    @Column(name="group_id")
    private String groupId;

    @Column(name="artifact_id")
    private String artifactId;
}
