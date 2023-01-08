package com.ly.mvc_recovery_evaluation.controller;

import com.ly.mvc_recovery_evaluation.service.LayersRelationService;
import com.ly.mvc_recovery_evaluation.vo.LayersRelationVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author liuyue
 * @date 2023/1/7 17:48
 */
@RestController
@RequestMapping("/layerRelation")
@Api(tags = "层间关系")
public class LayerRelationController {

    @Autowired
    private LayersRelationService layersRelationService;

    @GetMapping("getLayersRelationData/{entryModuleId}")
        public LayersRelationVO getLayersRelationData(@PathVariable Long entryModuleId) {
        return layersRelationService.getLayersRelationData(entryModuleId);
    }
}
