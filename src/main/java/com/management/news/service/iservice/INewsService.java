package com.management.news.service;

import com.management.news.model.DetailedNewsSegment;
import com.management.news.model.NewsSegment;
import com.management.news.model.ShortNewsSegment;

public interface INewsService
{
    void saveSegment(NewsSegment segment, String authorization);
    void acceptSegment(long id);
    void rejectSegment(long id);
    Iterable<NewsSegment> findAllSegments(String authorization);
    Iterable<NewsSegment> findAllSegments(int pageNumber, int pageSize, String authorization);
    Iterable<ShortNewsSegment> findAllSegmentsShort(String authorization);
    Iterable<DetailedNewsSegment> findAllSegmentsDetailed(String authorization);
    Iterable<NewsSegment> findAllSegmentsByIds(Iterable<Long> ids);
    NewsSegment findSegmentById(Long id);
    void updateSegment(NewsSegment segment, String authorization);
    void deleteSegmentById(Long id, String authorization);
}
