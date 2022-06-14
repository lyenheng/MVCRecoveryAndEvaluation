package com.ly.mvc_recovery_evaluation.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author liuyue
 * @date 2022/6/14 17:44
 */
@Data
public class DetectProcedureVO {

    private String name;

    private String description;

    private MultipartFile[] files;

}
