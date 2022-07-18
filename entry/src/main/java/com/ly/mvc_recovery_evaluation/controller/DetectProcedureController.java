package com.ly.mvc_recovery_evaluation.controller;

import com.ly.mvc_recovery_evaluation.dto.PageResult;
import com.ly.mvc_recovery_evaluation.entity.DetectProcedure;
import com.ly.mvc_recovery_evaluation.service.DetectProcedureService;
import com.ly.mvc_recovery_evaluation.vo.DetectProcedureVO;
import com.ly.mvc_recovery_evaluation.vo.SearchProcedureVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author liuyue
 * @date 2022/6/13 20:16
 */
@RestController
@RequestMapping("/detectProcedure")
@Api(tags = "检测流程")
public class DetectProcedureController {

    @Autowired
    private DetectProcedureService detectProcedureService;

    @PostMapping("/startDetect")
    public void startDetect(@RequestBody DetectProcedureVO detectProcedureVO){
        detectProcedureService.startDetect(detectProcedureVO);
    }

    @PostMapping("/listProcedure")
    public PageResult<DetectProcedure> listDetect(@RequestBody SearchProcedureVO searchProcedureVO){
        return detectProcedureService.listProcedure(searchProcedureVO);
    }

}
