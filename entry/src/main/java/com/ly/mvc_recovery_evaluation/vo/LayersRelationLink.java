package com.ly.mvc_recovery_evaluation.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liuyue
 * @date 2023/1/7 18:57
 * 层间关系边关系
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LayersRelationLink {

    private String source;

    private String target;

}
