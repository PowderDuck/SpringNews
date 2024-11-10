package com.management.news.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.management.news.constant.Constants;
import com.management.news.service.iservice.IStorageService;

@Service
public class StorageService implements IStorageService {
    
    public String save(MultipartFile file) throws FileUploadException, IOException
    {
        if (file.isEmpty())
        {
            throw new FileUploadException("[-] Can't Store an Empty File");
        }

        InputStream writeStream = file.getInputStream();
        Path destinationPath = Paths.get(resourcePath(file.getOriginalFilename()));
        Files.copy(writeStream, destinationPath, StandardCopyOption.REPLACE_EXISTING);

        return Paths.get(Constants.LOCALIZED_LOCATION)
            .resolve(file.getOriginalFilename())
            .toString();
    }

    public Resource load(String filename) throws MalformedURLException, FileNotFoundException
    {
        Resource file = new UrlResource(resourcePath(filename));
        if (!file.exists())
        {
            throw new FileNotFoundException(String.format("[-] %s File Does Not Exist"));
        }

        return file;
    }

    private String resourcePath(String filename)
    {
        return Paths.get(Constants.STORAGE_ROOT_LOCATION)
                    .resolve(Paths.get(filename))
                    .toString();
    }
}
