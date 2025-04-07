package com.example.group2backend.database.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

// Game comment response DTO
@Data
public class Comment {
    private Long id; // Comment ID
    private Integer gameId; // Game ID
    private Long userId; // User ID
    private String content; // Comment content
    private LocalDateTime timestamp; // Comment timestamp
}
