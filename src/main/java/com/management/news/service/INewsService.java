package com.management.news.service;

import com.management.news.model.DetailedNewsSegment;
import com.management.news.model.NewsSegment;
import com.management.news.model.ShortNewsSegment;

public interface INewsService
{
    void SaveSegment(NewsSegment segment);
    void SaveSegments(Iterable<NewsSegment> segments);
    Iterable<NewsSegment> FindAllSegments();
    Iterable<NewsSegment> FindAllSegments(int pageNumber, int pageSize);
    Iterable<NewsSegment> FindAllSegmentsByIds(Iterable<Long> ids);
    Iterable<ShortNewsSegment> FindAllSegmentsShort();
    Iterable<DetailedNewsSegment> FindAllSegmentsDetailed();
    NewsSegment FindSegmentById(Long id);
    void UpdateSegment(NewsSegment segment);
    void DeleteSegmentById(Long id);
}
