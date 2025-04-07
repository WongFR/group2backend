package com.example.group2backend.database.service;

import com.example.group2backend.database.entity.User;

import java.util.List;

public interface UserService {
    User getUserById(Integer id);
    List<User> getAllUsers();
    void createUser(User user);
    void updateUser(User user);
    void deleteUser(Integer id);
}
