package com.example.group2backend.database.service;

import com.example.group2backend.database.entity.Comment;
import com.example.group2backend.database.mapper.CommentMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    // Add a new comment
    public void addComment(Comment comment) {
        comment.setTimestamp(LocalDateTime.now()); // Set timestamp
        commentMapper.insertComment(comment);
    }

    // Get comments by game ID
    public List<Comment> getCommentsByGameId(Long gameId) {
        return commentMapper.getCommentsByGameId(gameId);
    }

    // Get comments by game ID
    public List<Comment> getCommentsByUserId(Long userId) {
        return commentMapper.getCommentsByUserId(userId);
    }

    public void incrementLike(Long id){
        commentMapper.incrementLike(id);
    }
}

