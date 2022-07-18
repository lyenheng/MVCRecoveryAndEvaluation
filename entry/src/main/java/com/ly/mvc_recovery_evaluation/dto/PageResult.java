package com.ly.mvc_recovery_evaluation.dto;

import lombok.Data;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/7/18 13:31
 */
@Data
public class PageResult<T> {

    private Integer pageNo;
    private Integer pageSize;
    private Long totalPages;
    private Long totalElements;
    private List<T> data;
}
