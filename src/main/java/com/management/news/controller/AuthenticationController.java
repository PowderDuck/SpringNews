package com.management.news.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management.news.model.UserInfo;
import com.management.news.model.dto.ResponseDto;
import com.management.news.service.JwtService;
import com.management.news.service.UserInfoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    
    @Autowired
    private UserInfoService UserInfoService;

    @Autowired
    private JwtService JwtService;

    @Autowired
    private AuthenticationManager AuthenticationManager;

    @PostMapping("/login")
    public ResponseDto UserLogin(@RequestBody UserInfo user) throws AuthenticationException
    {
        try {
            Authentication authentication = AuthenticationManager
                .authenticate(
                    new UsernamePasswordAuthenticationToken(
                        user.getUsername(), user.getPassword()));
            
            if (!authentication.isAuthenticated())
            {
                return ResponseDto.Ok(false);
            }
        }
        catch(AuthenticationException exception) {
            System.out.println(exception);
            return ResponseDto.Ok(false);
        }

        return ResponseDto.Ok(true, GenerateTokenJson(user.getUsername()));
    }

    @PostMapping("/signup")
    public ResponseDto SignUp(@RequestBody UserInfo user)
    {
        UserInfoService.AddUser(user);
        return ResponseDto.Ok(true, GenerateTokenJson(user.getUsername()));
    }

    private Map<String, String> GenerateTokenJson(String username)
    {
        Map<String, String> token = new HashMap<String, String>();
        token.put("token", JwtService.GenerateToken(username));

        return token;
    }
}
