package com.ly.mvc_recovery_evaluation.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author liuyue
 * @date 2022/6/14 17:17
 */
@Entity
@Data
@Table(name = "detect_procedure")
public class DetectProcedure {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="begin_time")
    private Date beginTime;

    @Column(name="end_time")
    private Date endTime;

    @Column(name="project_size")
    private String projectSize;

    @Column(name="log")
    private String log;

    @Column(name="status")
    private String status;
}
