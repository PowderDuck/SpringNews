package com.management.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.management.news.model.UserInfo;
import com.management.news.model.UserInfoDetails;
import com.management.news.repository.UserInfoRepository;
import com.management.news.service.iservice.IUserInfoService;

@Service
public class UserInfoService implements IUserInfoService, UserDetailsService {
    
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private JwtService jwtService;

    private PasswordEncoder PasswordEncoder = new BCryptPasswordEncoder();


    @Override
    public UserDetails loadUserByUsername(String username)
    {
        var user = userInfoRepository.findByUsername(username);
        if (!user.isPresent())
        {
            throw new RuntimeException(String.format("%s User Not Found", username));
        }

        return new UserInfoDetails(user.get());
    }

    public void addUser(UserInfo user)
    {
        user.SetEncodedPassword(PasswordEncoder.encode(user.getPassword()));
        userInfoRepository.save(user);
    }

    public UserInfo loadUserFromAuthorization(String authorization)
    {
        if (authorization == null || !authorization.startsWith("Bearer"))
        {
            return null;
        }

        String username = jwtService.extractUsername(authorization.substring(7));
        var user = userInfoRepository.findByUsername(username);
        if (!user.isPresent())
        {
            throw new RuntimeException(String.format("[-] User %s Not Found", username));
        }

        return user.get();
    }
}
