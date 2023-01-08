package com.ly.mvc_recovery_evaluation.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liuyue
 * @date 2023/1/7 18:03
 * 层间关系顶点
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LayersRelationNode {

    private String id;

    // 类别
    private Integer category;

    // 名称
    private String name;

    // 大小
    private Double symbolSize;

    // 值
    private Double value;

    // 坐标
    private Double x;

    // 坐标
    private Double y;

    public LayersRelationNode(String id, Integer category, String name, Double symbolSize , Double value){
        this.id = id;
        this.category = category;
        this.name = name;
        this.symbolSize = symbolSize;
        this.value = value;
    }
}
