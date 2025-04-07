package com.example.group2backend.database.mapper;

import com.example.group2backend.database.entity.UserProfile;

import java.util.List;

public interface UserProfileMapper {
    UserProfile selectById(Integer id);
    List<UserProfile> selectAll();
    void insert(UserProfile userprofile);
    void update(UserProfile userprofile);
    void delete(Integer id);
}
