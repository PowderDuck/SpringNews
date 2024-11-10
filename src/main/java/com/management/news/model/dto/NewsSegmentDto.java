package com.management.news.model.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class NewsSegmentDto {
    
    private String title;
    private String description;
    private MultipartFile imageFile;
}
