package com.ly.mvc_recovery_evaluation.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author liuyue
 * @date 2022/6/13 19:50
 */
@Entity
@Data
@Table(name = "entry_node")
public class EntryNodePO {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="module_node_id")
    private Long moduleNodeId;

    @Column(name="file_path")
    private String filePath;

    @Column(name="annotations")
    private String annotations;

    @Column(name="content")
    private String content;
}
