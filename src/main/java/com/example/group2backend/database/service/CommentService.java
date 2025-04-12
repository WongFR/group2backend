package com.example.group2backend.database.service;

import com.example.group2backend.controller.bodies.CommentWithVotes;
import com.example.group2backend.database.entity.Comment;
import com.example.group2backend.database.mapper.CommentMapper;
import com.example.group2backend.database.service.CommentVoteService.VoteResult;
import com.example.group2backend.database.service.CommentVoteService.VoteStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    
    @Autowired
    private CommentVoteService commentVoteService;

    /**
     * Add a new comment
     */
    public void addComment(Comment comment) {
        comment.setTimestamp(LocalDateTime.now()); // Set timestamp
        commentMapper.insertComment(comment);
    }

    /**
     * Get comments by game ID
     */
    public List<Comment> getCommentsByGameId(Integer gameId) {
        return commentMapper.getCommentsByGameId(gameId);
    }
    
    /**
     * Get comments with vote information by game ID
     */
    public List<CommentWithVotes> getCommentsWithVotesByGameId(Integer gameId, Long currentUserId) {
        List<Comment> comments = commentMapper.getCommentsByGameId(gameId);
        
        return comments.stream().map(comment -> {
            CommentWithVotes commentWithVotes = new CommentWithVotes(comment);
            
            // Get vote counts
            VoteResult voteResult = commentVoteService.getVoteCounts(comment.getId());
            commentWithVotes.setLikeCount(voteResult.getLikes());
            commentWithVotes.setDislikeCount(voteResult.getDislikes());
            
            // Get current user's vote status
            if (currentUserId != null) {
                VoteStatus userVoteStatus = commentVoteService.getUserVoteStatus(comment.getId(), currentUserId);
                commentWithVotes.setUserVoteStatus(userVoteStatus);
            }
            
            return commentWithVotes;
        }).collect(Collectors.toList());
    }
    
    /**
     * Vote on a comment
     */
    public CommentWithVotes voteOnComment(Long commentId, Long userId, Boolean isLike) {
        // Get the comment
        Comment comment = commentMapper.getCommentById(commentId);
        if (comment == null) {
            return null;
        }
        
        // Process the vote
        VoteResult voteResult = commentVoteService.voteOnComment(commentId, userId, isLike);
        
        // Create and return updated comment with vote info
        CommentWithVotes commentWithVotes = new CommentWithVotes(comment);
        commentWithVotes.setLikeCount(voteResult.getLikes());
        commentWithVotes.setDislikeCount(voteResult.getDislikes());
        
        // Get current user's vote status after voting
        VoteStatus userVoteStatus = commentVoteService.getUserVoteStatus(commentId, userId);
        commentWithVotes.setUserVoteStatus(userVoteStatus);
        
        return commentWithVotes;
    }
}