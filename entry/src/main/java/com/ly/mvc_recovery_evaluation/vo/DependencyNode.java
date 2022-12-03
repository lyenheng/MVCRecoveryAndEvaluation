package com.ly.mvc_recovery_evaluation.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/12/3 15:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DependencyNode {

    private String name;

    private List<DependencyNode> children;

}
