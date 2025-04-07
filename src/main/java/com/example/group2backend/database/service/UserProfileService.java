package com.example.group2backend.database.service;

import com.example.group2backend.database.entity.UserProfile;

import java.util.List;

public interface UserProfileService {
    UserProfile getUserProfileById(Integer id);
    List<UserProfile> getAllUserProfiles();
    void createUserProfile(UserProfile userprofile);
    void updateUserProfile(UserProfile userprofile);
    void deleteUserProfile(Integer id);
}
