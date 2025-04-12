package com.example.group2backend.controller.bodies;

import com.example.group2backend.database.entity.Comment;
import com.example.group2backend.database.service.CommentVoteService.VoteStatus;
import lombok.Data;

/**
 * DTO that extends Comment with vote information
 */
@Data
public class CommentWithVotes extends Comment {
    private int likeCount;
    private int dislikeCount;
    private VoteStatus userVoteStatus; // Current user's vote on this comment
    
    public CommentWithVotes(Comment comment) {
        // Copy all properties from the Comment to this object
        this.setId(comment.getId());
        this.setGameId(comment.getGameId());
        this.setUserId(comment.getUserId());
        this.setContent(comment.getContent());
        this.setTimestamp(comment.getTimestamp());
        
        // Default values for vote counts
        this.likeCount = 0;
        this.dislikeCount = 0;
        this.userVoteStatus = VoteStatus.NONE;
    }
}