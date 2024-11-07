package com.management.news.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

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
}
