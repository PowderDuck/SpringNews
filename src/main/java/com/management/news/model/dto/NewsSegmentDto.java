package com.management.news.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class NewsSegmentDto {
    
    private String title;
    private String description;

    @JsonProperty("image_url")
    private String imageURL;
}
