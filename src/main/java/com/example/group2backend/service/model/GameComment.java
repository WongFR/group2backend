package com.example.group2backend.service.model;

import com.example.group2backend.database.entity.Comment;
import com.example.group2backend.database.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameComment {
    private Comment comment;
    private User creator;
    private boolean hasBeenLiked = false;
}
