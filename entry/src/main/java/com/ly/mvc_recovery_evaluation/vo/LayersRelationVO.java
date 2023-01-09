package com.ly.mvc_recovery_evaluation.vo;

import lombok.Data;

import java.util.List;

/**
 * @author liuyue
 * @date 2023/1/7 17:58
 * 层间关系
 */
@Data
public class LayersRelationVO {

    // 类别
    private List<Category> categories;

    // 顶点
    private List<LayersRelationNode> nodes;

    // 边
    private List<LayersRelationLink> links;


}
