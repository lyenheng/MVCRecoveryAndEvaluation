package com.ly.mvc_recovery_evaluation.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author liuyue
 * @date 2022/6/17 20:59
 */
public interface FileUploadService {

    void uploadMultiFile (String projectName, MultipartFile[] files);
}
