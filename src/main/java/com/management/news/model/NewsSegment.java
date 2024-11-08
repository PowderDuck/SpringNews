package com.management.news.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private final LocalDate date = LocalDate.now();
    private final Long views = 0L;

    @Column(name="image_url")
    @JsonProperty(value="image_url")
    private String imageURL;

    public NewsSegment() { }

    public NewsSegment(NewsSegmentDto segment)
    {
        title = segment.getTitle();
        description = segment.getDescription();
        imageURL = segment.getImageURL();
    }
}
