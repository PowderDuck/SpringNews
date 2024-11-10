package com.management.news.service;

import com.management.news.constant.NewsStatus;
import com.management.news.model.DetailedNewsSegment;
import com.management.news.model.NewsSegment;
import com.management.news.model.ShortNewsSegment;
import com.management.news.repository.NewsRepository;
import com.management.news.service.iservice.INewsService;

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

    @Autowired
    private UserInfoService userInfoService;

    public void saveSegment(NewsSegment segment, String authorization) {
        segment.setAuthor(
            userInfoService
                .loadUserFromAuthorization(authorization)
                .getUsername());
        newsRepository.save(segment);
    }

    public void acceptSegment(long id)
    {
        NewsSegment segment = findSegmentById(id);
        segment.setStatus(NewsStatus.ACCEPTED);
        updateSegment(segment, null);
    }

    public void rejectSegment(long id)
    {
        NewsSegment segment = findSegmentById(id);
        segment.setStatus(NewsStatus.REJECTED);
        updateSegment(segment, null);
    }

    public List<NewsSegment> findAllSegments(String authorization) {

        var allNews = newsRepository.findAll();
        var user = userInfoService.loadUserFromAuthorization(authorization);
        if (user != null)
        {
            if (user.getRoles().contains("ROLE_ADMIN"))
                return allNews;

            return allNews.stream()
                .filter(
                    segment -> segment.getStatus() == NewsStatus.ACCEPTED || 
                    (segment.getAuthor() != null && segment.getAuthor().equals(user.getUsername())))
                .collect(Collectors.toList());
        }

        return allNews.stream()
            .filter(segment -> segment.getStatus().equals(NewsStatus.ACCEPTED))
            .collect(Collectors.toList());
    }

    public Iterable<NewsSegment> findAllSegments(int pageNumber, int pageSize, String authorization)
    {
        if (pageNumber < 0 || pageSize <= 0)
            return findAllSegments(authorization);

        Pageable page = PageRequest.of(pageNumber, pageSize);
        return newsRepository.findAll(page);
    }

    public List<ShortNewsSegment> findAllSegmentsShort(String authorization)
    {
        var allNews = findAllSegments(authorization);

        return allNews.stream()
            .map(newsSegment -> new ShortNewsSegment(newsSegment))
            .collect(Collectors.toList());
    }

    public List<DetailedNewsSegment> findAllSegmentsDetailed(String authorization)
    {
        var allNews = findAllSegments(authorization);

        return allNews.stream()
            .map(newsSegment -> new DetailedNewsSegment(newsSegment))
            .collect(Collectors.toList());
    }

    public Iterable<NewsSegment> findAllSegmentsByIds(Iterable<Long> ids) {
        return newsRepository.findAllById(ids);
    }

    public NewsSegment findSegmentById(Long id) {
        var segment = newsRepository.findById(id);
        if (!segment.isPresent())
            throw new RuntimeException("[-] Segment with id " + id + " is not Present");
        
        return segment.get();
    }

    public void updateSegment(NewsSegment segment, String authorization) {

        var user = userInfoService.loadUserFromAuthorization(authorization);
        var targetSegment = findSegmentById(segment.getId());
        
        if (user != null)
        {
            if (isAuthor(targetSegment, user.getUsername()))
            {
                targetSegment.setTitle(segment.getTitle());
                targetSegment.setDescription(segment.getDescription());
                targetSegment.setImageURL(segment.getImageURL());

                newsRepository.save(targetSegment);
                return;
            }

            return;
        }
        
        targetSegment.setStatus(segment.getStatus());
        newsRepository.save(targetSegment);
    }

    public void deleteSegmentById(Long id, String authorization) {
        
        var user = userInfoService.loadUserFromAuthorization(authorization);
        var segment = findSegmentById(id);

        if (user.getRoles().contains("ROLE_ADMIN"))
        {
            newsRepository.deleteById(id);
            return;
        }

        if (isAuthor(segment, user.getUsername()))
        {
            newsRepository.deleteById(id);
        }
    }

    private boolean isAuthor(NewsSegment segment, String username)
    {
        return segment.getAuthor() != null &&
            segment.getAuthor().equals(username);
    }
}
