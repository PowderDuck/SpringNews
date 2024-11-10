package com.management.news.service.iservice;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {
    
    String save(MultipartFile file) throws FileUploadException, IOException;
    Resource load(String filename) throws MalformedURLException, FileNotFoundException;
}
