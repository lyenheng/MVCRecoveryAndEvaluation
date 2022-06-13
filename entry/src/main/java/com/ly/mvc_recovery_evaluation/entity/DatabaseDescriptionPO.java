package com.ly.mvc_recovery_evaluation.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author liuyue
 * @date 2022/6/13 19:51
 */
@Entity
@Data
@Table(name = "database_description")
public class DatabaseDescriptionPO {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="project_node_id")
    private Long projectNodeId;

    @Column(name="database_type")
    private String databaseType;

    @Column(name="url")
    private String url;

    @Column(name="port")
    private String port;

    @Column(name="database_name")
    private String databaseName;
}
