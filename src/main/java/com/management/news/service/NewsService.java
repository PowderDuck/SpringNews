package com.management.news.service;

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
    private NewsRepository newsRepository;

    @Override
    public void saveSegment(NewsSegment segment) {
        newsRepository.save(segment);
    }

    @Override
    public void saveSegments(Iterable<NewsSegment> segments) {
        segments.forEach((segment) -> {
            newsRepository.save(segment);
        });
    }

    @Override
    public List<NewsSegment> findAllSegments() {
        return newsRepository.findAll();
    }

    @Override
    public Iterable<NewsSegment> findAllSegments(int pageNumber, int pageSize)
    {
        if (pageNumber < 0 || pageSize <= 0)
            return findAllSegments();

        Pageable page = PageRequest.of(pageNumber, pageSize);
        return newsRepository.findAll(page);
    }

    @Override
    public List<ShortNewsSegment> findAllSegmentsShort()
    {
        var allNews = findAllSegments();

        return allNews.stream()
            .map(newsSegment -> new ShortNewsSegment(newsSegment))
            .collect(Collectors.toList());
    }

    @Override
    public List<DetailedNewsSegment> findAllSegmentsDetailed()
    {
        var allNews = findAllSegments();

        return allNews.stream()
            .map(newsSegment -> new DetailedNewsSegment(newsSegment))
            .collect(Collectors.toList());
    }

    @Override
    public Iterable<NewsSegment> findAllSegmentsByIds(Iterable<Long> ids) {
        return newsRepository.findAllById(ids);
    }

    @Override
    public NewsSegment findSegmentById(Long id) {
        var segment = newsRepository.findById(id);
        if (!segment.isPresent())
            throw new RuntimeException("[-] Segment with id " + id + " is not Present");

        return segment.get();
    }

    @Override
    public void updateSegment(NewsSegment segment) {
        newsRepository.save(segment);
    }

    @Override
    public void deleteSegmentById(Long id) {
        // Triggering RuntimeException if id is not Present;
        findSegmentById(id);
        newsRepository.deleteById(id);
    }
}
