package com.shopping.service.impl;

import com.shopping.common.util.ConditionUtil;
import com.shopping.common.util.IdGenerateUtil;
import com.shopping.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
public class CommonServiceImpl implements CommonService {

    @Value("${resource}")
    private String resource;

    @Value("${nresource}")
    private String nresource;

    @Override
    public String uploadImage(MultipartFile file) {
        ConditionUtil.checkArgument(file != null, "上传文件为空");

        String imgName = IdGenerateUtil.randomUUID();
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String fileName = imgName + "." + extension;
        String finalPath = resource + fileName;
        File finalFile = new File(finalPath);
        if(!finalFile.exists()) {
            boolean b = finalFile.mkdirs();
        }
        try {
            file.transferTo(finalFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nresource + fileName;
    }
}
