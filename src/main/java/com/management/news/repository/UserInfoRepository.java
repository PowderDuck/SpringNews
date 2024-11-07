package com.management.news.repository;

import com.management.news.model.UserInfo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> { 
    Optional<UserInfo> findByUsername(String username);
}
