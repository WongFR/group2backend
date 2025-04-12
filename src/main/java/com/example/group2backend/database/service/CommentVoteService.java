package com.example.group2backend.database.service;

import com.example.group2backend.database.entity.CommentVote;
import com.example.group2backend.database.mapper.CommentVoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentVoteService {

    @Autowired
    private CommentVoteMapper commentVoteMapper;

    /**
     * Vote on a comment (like or dislike)
     * If user already voted, update their vote
     * If same vote type, remove the vote (toggle off)
     * @param commentId The comment ID
     * @param userId The user ID
     * @param isLike true for like, false for dislike
     * @return The count of likes and dislikes after the operation
     */
    public VoteResult voteOnComment(Long commentId, Long userId, Boolean isLike) {
        // Check if user already voted on this comment
        CommentVote existingVote = commentVoteMapper.getUserVote(commentId, userId);
        
        if (existingVote != null) {
            // If voting the same way, remove the vote (toggle behavior)
            if (existingVote.getIsLike() == isLike) {
                commentVoteMapper.deleteVote(commentId, userId);
            } else {
                // Change vote type
                existingVote.setIsLike(isLike);
                existingVote.setTimestamp(LocalDateTime.now());
                commentVoteMapper.updateVote(existingVote);
            }
        } else {
            // Create new vote
            CommentVote vote = new CommentVote();
            vote.setCommentId(commentId);
            vote.setUserId(userId);
            vote.setIsLike(isLike);
            vote.setTimestamp(LocalDateTime.now());
            commentVoteMapper.insertVote(vote);
        }
        return getVoteCounts(commentId);
    }
    
    /**
     * Get the vote counts for a comment
     * @param commentId The comment ID
     * @return The counts of likes and dislikes
     */
    public VoteResult getVoteCounts(Long commentId) {
        int likes = commentVoteMapper.countLikes(commentId);
        int dislikes = commentVoteMapper.countDislikes(commentId);
        return new VoteResult(likes, dislikes);
    }
    
    /**
     * Get the current vote status for a user on a comment
     * @param commentId The comment ID
     * @param userId The user ID
     * @return The vote status, or null if no vote
     */
    public VoteStatus getUserVoteStatus(Long commentId, Long userId) {
        CommentVote vote = commentVoteMapper.getUserVote(commentId, userId);
        if (vote == null) {
            return VoteStatus.NONE;
        }
        return vote.getIsLike() ? VoteStatus.LIKE : VoteStatus.DISLIKE;
    }
    
    /**
     * Class to hold vote counts
     */
    public static class VoteResult {
        private int likes;
        private int dislikes;
        
        public VoteResult(int likes, int dislikes) {
            this.likes = likes;
            this.dislikes = dislikes;
        }
        
        public int getLikes() {
            return likes;
        }
        
        public int getDislikes() {
            return dislikes;
        }
    }
    
    /**
     * Enum for vote status
     */
    public enum VoteStatus {
        NONE,
        LIKE,
        DISLIKE
    }
}