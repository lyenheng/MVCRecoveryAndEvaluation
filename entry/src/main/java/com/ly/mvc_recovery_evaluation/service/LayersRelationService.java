package com.ly.mvc_recovery_evaluation.service;

import com.ly.mvc_recovery_evaluation.vo.LayersRelationVO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author liuyue
 * @date 2023/1/7 19:32
 */
public interface LayersRelationService {

    LayersRelationVO getLayersRelationData(Long entryModuleId);
}
