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
import com.management.news.model.dto.AuthenticationDto;
import com.management.news.model.dto.ResponseDto;
import com.management.news.model.dto.UserInfoDto;
import com.management.news.service.JwtService;
import com.management.news.service.UserInfoService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseDto userLogin(@RequestBody AuthenticationDto user) throws AuthenticationException
    {
        try {
            Authentication authentication = authenticationManager
                .authenticate(
                    new UsernamePasswordAuthenticationToken(
                        user.getUsername(), user.getPassword()));
            
            if (!authentication.isAuthenticated())
            {
                return ResponseDto.ok(false);
            }
        }
        catch(AuthenticationException exception) {
            System.out.println(exception);
            return ResponseDto.ok(false);
        }

        return ResponseDto.ok(true, generateTokenJson(user.getUsername()));
    }

    @PostMapping("/signup")
    public ResponseDto signUp(@RequestBody UserInfoDto userDto)
    {
        UserInfo user = new UserInfo(userDto);
        user.setRoles("ROLE_USER");
        
        userInfoService.addUser(user);
        return ResponseDto.ok(true, generateTokenJson(user.getUsername()));
    }

    private Map<String, String> generateTokenJson(String username)
    {
        Map<String, String> token = new HashMap<String, String>();
        token.put("token", jwtService.generateToken(username));

        return token;
    }
}
