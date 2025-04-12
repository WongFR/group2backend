package com.example.group2backend.database.mapper;

import com.example.group2backend.database.entity.CommentVote;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentVoteMapper {

    // For inserting new vote
    @Insert("INSERT INTO comment_vote (comment_id, user_id, is_like, timestamp) " +
            "VALUES (#{commentId}, #{userId}, #{isLike}, #{timestamp})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertVote(CommentVote vote);

    // For Updating,i.e. Like to dislike or dislike to like
    @Update("UPDATE comment_vote SET is_like = #{isLike}, timestamp = #{timestamp} " +
            "WHERE comment_id = #{commentId} AND user_id = #{userId}")
    void updateVote(CommentVote vote);

    // Delete a vote
    @Delete("DELETE FROM comment_vote WHERE comment_id = #{commentId} AND user_id = #{userId}")
    void deleteVote(@Param("commentId") Long commentId, @Param("userId") Long userId);

    // Check if a user has already voted on a comment
    @Select("SELECT * FROM comment_vote WHERE comment_id = #{commentId} AND user_id = #{userId}")
    CommentVote getUserVote(@Param("commentId") Long commentId, @Param("userId") Long userId);

    // Get all votes for a comment
    @Select("SELECT * FROM comment_vote WHERE comment_id = #{commentId}")
    List<CommentVote> getVotesForComment(@Param("commentId") Long commentId);

    // Count likes for a comment
    @Select("SELECT COUNT(*) FROM comment_vote WHERE comment_id = #{commentId} AND is_like = true")
    int countLikes(@Param("commentId") Long commentId);

    // Count dislikes for a comment
    @Select("SELECT COUNT(*) FROM comment_vote WHERE comment_id = #{commentId} AND is_like = false")
    int countDislikes(@Param("commentId") Long commentId);
}