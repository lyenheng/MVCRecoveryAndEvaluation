package com.ly.mvc_recovery_evaluation.vo;

import lombok.Data;

/**
 * @author liuyue
 * @date 2022/6/14 17:27
 */
@Data
public class SearchProcedureVO {

    /**
     * 关键字搜索
     */
    private String keyword;

    private Integer pageSize = 10;

    private Integer pageNo = 0;

    private String orderBy;

    /**
     *  asc / desc
     */
    private String dir = "desc";
}
