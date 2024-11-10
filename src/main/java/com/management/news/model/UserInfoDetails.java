package com.management.news.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserInfoDetails implements UserDetails {

    private String username;
    private String password;
    private List<GrantedAuthority> authorities;

    public UserInfoDetails(UserInfo userInfo)
    {
        username = userInfo.getUsername();
        password = userInfo.getPassword();
        String roles = userInfo.getRoles();
        authorities = roles != null ? List.of(roles.split(","))
            .stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList()) : null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
