package com.management.news.repository;

import com.management.news.model.NewsSegment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<NewsSegment, Long> { }
