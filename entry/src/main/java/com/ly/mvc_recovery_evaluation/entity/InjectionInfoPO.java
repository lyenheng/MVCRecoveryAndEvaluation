package com.ly.mvc_recovery_evaluation.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author liuyue
 * @date 2022/6/13 19:49
 */
@Entity
@Data
@Table(name = "injection_info")
public class InjectionInfoPO {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="class_description_id")
    private Long classDescriptionId;

    @Column(name="var_name")
    private String varName;

    @Column(name="fully_qualified_name")
    private String fullyQualifiedName;
}
