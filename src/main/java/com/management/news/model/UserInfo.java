package com.management.news.model;

import com.management.news.model.dto.UserInfoDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="users")
public class UserInfo 
{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @Column(unique=true)
    private String username;
    private String password;
    private String roles;

    public UserInfo() { }

    public UserInfo(UserInfoDto userInfo)
    {
        username = userInfo.getUsername();
        password = userInfo.getPassword();
        roles = userInfo.getRoles();
    }

    public void SetEncodedPassword(String encodedPassword)
    {
        password = encodedPassword;
    }
}
