

package com.example.group2backend.database.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {
    private Long id; 
    private Integer gameId; 
    private Long userId; 
    private String content; 
    private LocalDateTime timestamp; 
    private Integer likes; // Number of likes
    private Integer dislikes; // Number of dislikes
}