package com.example.group2backend.database.service;

import com.example.group2backend.database.entity.User;

import com.example.group2backend.database.mapper.UserMapper;
import com.example.group2backend.service.JwtUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userMapper.insertUser(user);
    }

    public void edit(User user) {
        User userFromDb = userMapper.findById(user.getId());
        if (!userFromDb.getUsername().equals(user.getUsername())
                && userMapper.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }

        user.setPassword(userFromDb.getPassword());
        user.setCreatedAt(userFromDb.getCreatedAt());
        user.setUpdatedAt(LocalDateTime.now());
        userMapper.updateUser(user);
    }

    public String login(String username, String rawPassword) {
        User user = userMapper.findByUsername(username);
        if (user != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
            return jwtUtil.generateToken(user.getUsername());
        }
        throw new RuntimeException("Invalid username or password");
    }

    public User getUserByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    public List<User> findUsersByIds(List<Long> userIds){
        return userMapper.findUsersByIds(userIds);
    }

    public User findUsersById(Long userId){
        return userMapper.findById(userId);
    }
}
