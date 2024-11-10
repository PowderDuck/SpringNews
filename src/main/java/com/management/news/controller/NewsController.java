package com.management.news.controller;

import com.management.news.service.NewsService;
import com.management.news.service.StorageService;

import com.management.news.model.DetailedNewsSegment;
import com.management.news.model.NewsSegment;
import com.management.news.model.ShortNewsSegment;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.management.news.model.dto.NewsSegmentDto;
import com.management.news.model.dto.ResponseDto;

import java.io.IOException;

import javax.naming.AuthenticationException;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private StorageService storageService;

    @PostMapping("/create")
    public NewsSegment createNewsSegment(@ModelAttribute NewsSegmentDto segmentDto, 
        @RequestHeader String authorization)
        throws FileUploadException, IOException, AuthenticationException
    {
        String imageURL = storageService.save(segmentDto.getImageFile());
        NewsSegment newsSegment = new NewsSegment(segmentDto);
        newsSegment.setImageURL(imageURL);

        newsService.saveSegment(newsSegment, authorization);
        return newsSegment;
    }

    @PostMapping("/accept")
    public ResponseDto acceptNewsSegment(long id)
    {
        newsService.acceptSegment(id);
        return ResponseDto.ok(true);
    }

    @PostMapping("/reject")
    public ResponseDto rejectNewsSegment(long id)
    {
        newsService.rejectSegment(id);
        return ResponseDto.ok(true);
    }

    @GetMapping("/all")
    public Iterable<NewsSegment> getAllNews(@RequestHeader(required=false) String authorization)
    {

        return newsService.findAllSegments(authorization);
    }

    @GetMapping("/all/short")
    public Iterable<ShortNewsSegment> getAllNewsShort(@RequestHeader(required=false) String authorization)
    {
        return newsService.findAllSegmentsShort(authorization);
    }
    
    @GetMapping("/all/detailed")
    public Iterable<DetailedNewsSegment> getAllNewsDetailed(@RequestHeader(required=false) String authorization)
    {
        return newsService.findAllSegmentsDetailed(authorization);
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
    public ResponseDto updateNews(@RequestBody NewsSegment segment, @RequestHeader String authorization)
    {
        newsService.updateSegment(segment, authorization);
        return ResponseDto.ok(true);
    }

    @DeleteMapping("delete")
    public ResponseDto deleteNewsSegment(long id, @RequestHeader(required=false) String authorization)
    {
        newsService.deleteSegmentById(id, authorization);
        return ResponseDto.ok(true);
    }
}
