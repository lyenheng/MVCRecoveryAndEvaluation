package com.ly.mvc_recovery_evaluation.service.impl;

import com.ly.mvc_recovery_evaluation.service.FileUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author liuyue
 * @date 2022/6/17 20:59
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${project.repository.path}")
    private String repositoryPath;

    public void uploadMultiFile (String projectName, MultipartFile[] files){

        if (files == null || files.length == 0) {
            return ;
        }

        for (MultipartFile file : files) {
            String filePath = repositoryPath + File.separator + projectName + File.separator+ file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('/'));
            makeDir(filePath);
            File dest = new File(filePath);
            try {
                //上传文件
                file.transferTo(dest);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 确保目录存在，不存在则创建
     * @param filePath
     */
    private void makeDir(String filePath) {
        if (filePath.lastIndexOf('/') > 0) {
            String dirPath = filePath.substring(0, filePath.lastIndexOf('/'));
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
    }
}
