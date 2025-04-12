package com.example.group2backend.database.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * Entity representing a user's vote (like/dislike) on a comment
 */
@Data
public class CommentVote {
    private Long id;              
    private Long commentId;       
    private Long userId;          
    private Boolean isLike;       
    private LocalDateTime timestamp;  
}