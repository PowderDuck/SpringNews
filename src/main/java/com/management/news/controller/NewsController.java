package com.management.news.controller;

import com.management.news.service.NewsService;

import com.management.news.model.DetailedNewsSegment;
import com.management.news.model.NewsSegment;
import com.management.news.model.ShortNewsSegment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.management.news.model.dto.NewsSegmentDto;
import com.management.news.model.dto.ResponseDto;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @PostMapping("/create")
    public NewsSegment createNewsSegment(@RequestBody NewsSegmentDto segmentDto)
    {
        NewsSegment newsSegment = new NewsSegment(segmentDto);
        newsService.saveSegment(newsSegment);
        return newsSegment;
    }

    @PostMapping("/create-many")
    public ResponseDto createManyNewsSegments(@RequestBody List<NewsSegmentDto> segmentsDto)
    {
        var newsSegments = segmentsDto.stream()
            .map(segmentDto -> new NewsSegment(segmentDto))
            .collect(Collectors.toList());
        newsService.saveSegments(newsSegments);
        return ResponseDto.ok(true);
    }

    @GetMapping("/all")
    public Iterable<NewsSegment> getAllNews(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue="10") int pageSize)
    {
        return newsService.findAllSegments(pageNumber, pageSize);
    }

    @GetMapping("/all/short")
    public Iterable<ShortNewsSegment> getAllNewsShort()
    {
        return newsService.findAllSegmentsShort();
    }
    
    @GetMapping("/all/detailed")
    public Iterable<DetailedNewsSegment> getAllNewsDetailed()
    {
        return newsService.findAllSegmentsDetailed();
    }

    @GetMapping("/get")
    public NewsSegment getSegmentWithId(long id)
    {
        return newsService.findSegmentById(id);
    }

    @GetMapping("/get/short")
    public ShortNewsSegment getSegmentWithIdShort(long id)
    {
        NewsSegment fullSegment = getSegmentWithId(id);
        return new ShortNewsSegment(fullSegment);
    }

    @GetMapping("/get/detailed")
    public DetailedNewsSegment getSegmentWithIdDetailed(long id)
    {
        NewsSegment fullSegment = getSegmentWithId(id);
        return new DetailedNewsSegment(fullSegment);
    }

    @PostMapping("/get-many")
    public Iterable<NewsSegment> getSegmentsWithIds(@RequestBody Iterable<Long> ids)
    {
        return newsService.findAllSegmentsByIds(ids);
    }

    @PutMapping("update")
    public NewsSegment updateNews(@RequestBody NewsSegment segment)
    {
        newsService.updateSegment(segment);
        return segment;
    }

    @DeleteMapping("delete")
    public ResponseDto deleteNewsSegment(long id)
    {
        newsService.deleteSegmentById(id);
        return ResponseDto.ok(true);
    }
}
