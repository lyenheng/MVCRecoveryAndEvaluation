package com.ly.mvc_recovery_evaluation.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author liuyue
 * @date 2022/6/13 16:05
 */
@Entity
@Data
@Table(name = "project_node")
public class ProjectNodePO{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="project_name")
    private String projectName;

    @Column(name = "project_file_path")
    private String projectFilePath;

    @Column(name = "project_type")
    private String projectType;

    @Column(name = "port")
    private String port;

    @Column(name = "context_path")
    private String contextPath;

    @Column(name = "active_file")
    private String activeFile;
}
