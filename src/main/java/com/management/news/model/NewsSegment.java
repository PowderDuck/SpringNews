package com.management.news.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.management.news.constant.NewsStatus;
import com.management.news.model.dto.NewsSegmentDto;

@Data
@Entity
@Table(name="News")
public class NewsSegment
{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    private String title;
    private String description;
    private NewsStatus status = NewsStatus.PENDING;
    private final LocalDate date = LocalDate.now();
    private final Long views = 0L;
    private String author;

    @Column(name="image_url")
    @JsonProperty(value="image_url")
    private String imageURL;

    public NewsSegment() { }

    public NewsSegment(NewsSegmentDto segment)
    {
        title = segment.getTitle();
        description = segment.getDescription();
    }

    public NewsSegment(NewsSegment segment)
    {
        id = segment.id;
        title = segment.title;
        description = segment.title;
        status = segment.status;
    }
}
