package com.shopping.service;

import org.springframework.web.multipart.MultipartFile;

public interface CommonService {

    String uploadImage(MultipartFile file);
}
