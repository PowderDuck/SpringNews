package com.management.news.service;

import com.management.news.model.DetailedNewsSegment;
import com.management.news.model.NewsSegment;
import com.management.news.model.ShortNewsSegment;

public interface INewsService
{
    void saveSegment(NewsSegment segment);
    void saveSegments(Iterable<NewsSegment> segments);
    Iterable<NewsSegment> findAllSegments();
    Iterable<NewsSegment> findAllSegments(int pageNumber, int pageSize);
    Iterable<NewsSegment> findAllSegmentsByIds(Iterable<Long> ids);
    Iterable<ShortNewsSegment> findAllSegmentsShort();
    Iterable<DetailedNewsSegment> findAllSegmentsDetailed();
    NewsSegment findSegmentById(Long id);
    void updateSegment(NewsSegment segment);
    void deleteSegmentById(Long id);
}
