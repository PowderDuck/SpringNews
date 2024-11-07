package com.management.news.controller;

import com.management.news.service.NewsService;

import jakarta.servlet.http.HttpServletRequest;
import com.management.news.exception.AuthenticationException;
import com.management.news.model.DetailedNewsSegment;
import com.management.news.model.NewsSegment;
import com.management.news.model.ShortNewsSegment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.management.news.model.dto.ResponseDto;

import java.util.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService NewsService;

    @PostMapping("/create")
    public NewsSegment CreateNewsSegment(@RequestBody NewsSegment segment)
    {
        NewsService.SaveSegment(segment);
        return segment;
    }

    @PostMapping("/createMany")
    public Map<String, String> CreateManyNewsSegments(@RequestBody Iterable<NewsSegment> segments)
    {
        NewsService.SaveSegments(segments);
        return null;
    }

    @GetMapping("/all")
    public Iterable<NewsSegment> GetAllNews(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue="10") int pageSize)
    {
        return NewsService.FindAllSegments(pageNumber, pageSize);
    }

    @GetMapping("/all/short")
    public Iterable<ShortNewsSegment> GetAllNewsShort()
    {
        return NewsService.FindAllSegmentsShort();
    }
    
    @GetMapping("/all/detailed")
    public Iterable<DetailedNewsSegment> GetAllNewsDetailed()
    {
        return NewsService.FindAllSegmentsDetailed();
    }

    @GetMapping("/get")
    public NewsSegment GetSegmentWithId(long id, HttpServletRequest request) throws AuthenticationException
    {
        return NewsService.FindSegmentById(id);
    }

    @PostMapping("/getMany")
    public Iterable<NewsSegment> GetSegmentsWithIds(@RequestBody Iterable<Long> ids)
    {
        return NewsService.FindAllSegmentsByIds(ids);
    }

    @PutMapping("update")
    public NewsSegment UpdateNews(@RequestBody NewsSegment segment)
    {
        NewsService.UpdateSegment(segment);
        return segment;
    }

    @DeleteMapping("delete")
    public ResponseDto DeleteNewsSegment(long id)
    {
        NewsService.DeleteSegmentById(id);
        return ResponseDto.Ok(true);
    }
}
