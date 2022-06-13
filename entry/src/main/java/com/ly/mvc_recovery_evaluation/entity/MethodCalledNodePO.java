package com.ly.mvc_recovery_evaluation.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author liuyue
 * @date 2022/6/13 19:48
 */
@Entity
@Data
@Table(name = "method_called_node")
public class MethodCalledNodePO {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="method_description_id")
    private Long methodDescriptionId;

    @Column(name="called_class_var_name")
    private String calledClassVarName;

    @Column(name="called_class_fully_name")
    private String calledClassFullyName;

    @Column(name="called_method_name")
    private String calledMthodName;

    @Column(name="method_called_type")
    private String methodCalledType;
}
