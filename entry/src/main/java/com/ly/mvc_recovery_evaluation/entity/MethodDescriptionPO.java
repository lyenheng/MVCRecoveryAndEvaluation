package com.ly.mvc_recovery_evaluation.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author liuyue
 * @date 2022/6/13 19:47
 */
@Entity
@Data
@Table(name = "method_description")
public class MethodDescriptionPO {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="class_description_id")
    private Long classDescriptionId;

    @Column(name="name")
    private String name;

    @Column(name="total_line")
    private Integer totalLine;

    @Column(name="blank_line")
    private Integer blankLine;

    @Column(name="comment_line")
    private Integer commentLine;

    @Column(name="code_line")
    private Integer codeLine;

    @Column(name="cyclomatic_complexity")
    private Integer cyclomaticComplexity;
}
