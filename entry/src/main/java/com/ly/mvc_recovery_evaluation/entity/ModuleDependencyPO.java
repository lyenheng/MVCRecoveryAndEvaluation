package com.ly.mvc_recovery_evaluation.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author liuyue
 * @date 2022/6/13 19:46
 */
@Entity
@Data
@Table(name = "module_dependency")
public class ModuleDependencyPO {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="module_node_id")
    private Long moduleNodeId;

    @Column(name="group_id")
    private String groupId;

    @Column(name="artifact_id")
    private String artifactId;
}
