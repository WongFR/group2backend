package com.example.group2backend.database.entity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String bio;
    private String favoriteGenres;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
