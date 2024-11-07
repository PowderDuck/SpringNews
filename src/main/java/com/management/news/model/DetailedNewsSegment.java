package com.management.news.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class DetailedNewsSegment {
    
    private Long id;
    private String title;
    private String description;
    private LocalDate date;
    private Long views;

    public DetailedNewsSegment(NewsSegment segment)
    {
        id = segment.getId();
        title = segment.getTitle();
        description = segment.getDescription();
        date = segment.getDate();
        views = segment.getViews();
    }
}
