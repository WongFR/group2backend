package com.example.group2backend.database.mapper;

import com.example.group2backend.database.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    // Insert a new comment
    @Insert("INSERT INTO comment (game_id, user_id, content, timestamp, likes, dislikes) " +
            "VALUES (#{gameId}, #{userId}, #{content}, #{timestamp}, #{likes}, #{dislikes})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertComment(Comment comment);

    // Query all comments for a game
    @Select("SELECT * FROM comment WHERE game_id = #{gameId} ORDER BY timestamp DESC")
    List<Comment> getCommentsByGameId(@Param("gameId") Integer gameId);
    
    // Increment like count for a comment
    @Update("UPDATE comment SET likes = likes + 1 WHERE id = #{commentId}")
    void incrementLike(@Param("commentId") Long commentId);
    
    // Increment dislike count for a comment
    @Update("UPDATE comment SET dislikes = dislikes + 1 WHERE id = #{commentId}")
    void incrementDislike(@Param("commentId") Long commentId);
    
    // Decrement like count for a comment
    @Update("UPDATE comment SET likes = GREATEST(likes - 1, 0) WHERE id = #{commentId}")
    void decrementLike(@Param("commentId") Long commentId);
    
    // Decrement dislike count for a comment
    @Update("UPDATE comment SET dislikes = GREATEST(dislikes - 1, 0) WHERE id = #{commentId}")
    void decrementDislike(@Param("commentId") Long commentId);
}