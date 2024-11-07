package com.management.news.service;

import com.management.news.exception.NewsNotFoundException;
import com.management.news.model.DetailedNewsSegment;
import com.management.news.model.NewsSegment;
import com.management.news.model.ShortNewsSegment;
import com.management.news.repository.NewsRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NewsService implements INewsService {

    @Autowired
    private NewsRepository NewsRepository;

    @Override
    public void SaveSegment(NewsSegment segment) {
        NewsRepository.save(segment);
    }

    @Override
    public void SaveSegments(Iterable<NewsSegment> segments) {
        segments.forEach((segment) -> {
            NewsRepository.save(segment);
        });
    }

    @Override
    public List<NewsSegment> FindAllSegments() {
        return NewsRepository.findAll();
    }

    @Override
    public Iterable<NewsSegment> FindAllSegments(int pageNumber, int pageSize)
    {
        if (pageNumber < 0 || pageSize <= 0)
            return FindAllSegments();

        Pageable page = PageRequest.of(pageNumber, pageSize);
        return NewsRepository.findAll(page);
    }

    @Override
    public List<ShortNewsSegment> FindAllSegmentsShort()
    {
        var allNews = FindAllSegments();

        return allNews.stream()
            .map(newsSegment -> new ShortNewsSegment(newsSegment))
            .collect(Collectors.toList());
    }

    @Override
    public List<DetailedNewsSegment> FindAllSegmentsDetailed()
    {
        var allNews = FindAllSegments();

        return allNews.stream()
            .map(newsSegment -> new DetailedNewsSegment(newsSegment))
            .collect(Collectors.toList());
    }

    @Override
    public Iterable<NewsSegment> FindAllSegmentsByIds(Iterable<Long> ids) {
        return NewsRepository.findAllById(ids);
    }

    @Override
    public NewsSegment FindSegmentById(Long id) {
        var segment = NewsRepository.findById(id);
        if (!segment.isPresent())
            throw new NewsNotFoundException("[-] Segment with id " + id + " is not Present");

        return segment.get();
    }

    @Override
    public void UpdateSegment(NewsSegment segment) {
        NewsRepository.save(segment);
    }

    @Override
    public void DeleteSegmentById(Long id) {
        // Triggering RuntimeException if id is not Present;
        FindSegmentById(id);
        NewsRepository.deleteById(id);
    }
}
