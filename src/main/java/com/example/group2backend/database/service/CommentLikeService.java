package com.example.group2backend.database.service;

import com.example.group2backend.database.mapper.CommentLikeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentLikeService {
    @Autowired
    private CommentLikeMapper commentLikeMapper;

    public boolean existsByUserIdAndCommentId(Long userId, Long commentId){
        return commentLikeMapper.existsByUserIdAndCommentId(userId, commentId);
    }

    public void insert(Long userId, Long commentId){
        commentLikeMapper.insert(userId, commentId);
    }
}
