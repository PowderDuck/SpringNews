package com.management.news.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ShortNewsSegment
{
    private Long id;
    private String title;
    private LocalDate date;
    private String image_url;

    public ShortNewsSegment(NewsSegment segment)
    {
        id = segment.getId();
        title = segment.getTitle();
        image_url = segment.getImageURL();
        date = segment.getDate();
    }
}
