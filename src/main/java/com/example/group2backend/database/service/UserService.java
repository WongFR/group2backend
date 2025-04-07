package com.example.group2backend.database.service;

import com.example.group2backend.database.entity.User;

import java.util.List;

import com.example.group2backend.database.mapper.UserMapper;
import com.example.group2backend.service.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public void register(User user) {
        if (userMapper.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }

        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userMapper.insertUser(user);
    }

    public String login(String username, String rawPassword) {
        User user = userMapper.findByUsername(username);
        if (user != null && passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
            return jwtUtil.generateToken(user.getUsername());
        }
        throw new RuntimeException("Invalid username or password");
    }

    public User getUserByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}
