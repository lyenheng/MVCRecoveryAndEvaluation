package com.ly.mvc_recovery_evaluation.service;

import com.ly.mvc_recovery_evaluation.dto.PageResult;
import com.ly.mvc_recovery_evaluation.entity.DetectProcedure;
import com.ly.mvc_recovery_evaluation.vo.DetectProcedureVO;
import com.ly.mvc_recovery_evaluation.vo.SearchProcedureVO;

import java.util.List;

/**
 * @author liuyue
 * @date 2022/6/14 17:21
 */
public interface DetectProcedureService {

    /**
     * 开始检测项目
     * @param detectProcedureVO
     */
    void startDetect(DetectProcedureVO detectProcedureVO);

    /**
     * 新增一条检测流程
     * @param detectProcedure
     * @return
     */
    DetectProcedure save(DetectProcedure detectProcedure);

    /**
     * 查询所有的检测流程
     * @param searchProcedureVO
     * @return
     */
    PageResult<DetectProcedure> listProcedure(SearchProcedureVO searchProcedureVO);
}
