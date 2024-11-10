package com.management.news.service.iservice;

import com.management.news.model.UserInfo;

public interface IUserInfoService {
    void addUser(UserInfo user);
    UserInfo loadUserFromAuthorization(String authorization);
}
