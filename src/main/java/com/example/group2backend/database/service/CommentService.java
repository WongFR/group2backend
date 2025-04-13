package com.example.group2backend.database.service;

import com.example.group2backend.database.entity.Comment;
import com.example.group2backend.database.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        comment.setLikes(0); // Initialize likes
        comment.setDislikes(0); // Initialize dislikes
        commentMapper.insertComment(comment);
    }

    // Get comments by game ID
    public List<Comment> getCommentsByGameId(Integer gameId) {
        return commentMapper.getCommentsByGameId(gameId);
    }
    
    // Like a comment
    public void likeComment(Long commentId) {
        commentMapper.incrementLike(commentId);
    }
    
    // Dislike a comment
    public void dislikeComment(Long commentId) {
        commentMapper.incrementDislike(commentId);
    }
    
    // Unlike a comment
    public void unlikeComment(Long commentId) {
        commentMapper.decrementLike(commentId);
    }
    
    // Undislike a comment
    public void undislikeComment(Long commentId) {
        commentMapper.decrementDislike(commentId);
    }
}