package com.management.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.management.news.exception.UserNotFoundException;
import com.management.news.model.UserInfo;
import com.management.news.model.UserInfoDetails;
import com.management.news.repository.UserInfoRepository;

@Service
public class UserInfoService implements IUserInfoService, UserDetailsService {
    
    @Autowired
    private UserInfoRepository UserInfoRepository;

    private PasswordEncoder PasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException
    {
        var user = UserInfoRepository.findByUsername(username);
        if (!user.isPresent())
        {
            throw new UserNotFoundException(String.format("%s User Not Found", username));
        }

        return new UserInfoDetails(user.get());
    }

    public void AddUser(UserInfo user)
    {
        user.SetEncodedPassword(PasswordEncoder.encode(user.getPassword()));
        UserInfoRepository.save(user);
    }
}