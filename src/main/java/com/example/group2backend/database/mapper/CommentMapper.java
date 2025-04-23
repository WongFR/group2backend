package com.example.group2backend.database.mapper;

import com.example.group2backend.database.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    // Insert a new comment
    @Insert("INSERT INTO comment (game_id, user_id, content, timestamp, like_count) " +
            "VALUES (#{gameId}, #{userId}, #{content}, #{timestamp}, #{likeCount})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertComment(Comment comment);

    // Query all comments for a game
    @Select("SELECT * FROM comment WHERE game_id = #{gameId} ORDER BY timestamp DESC")
    List<Comment> getCommentsByGameId(@Param("gameId") Long gameId);

    // Query all comments for a game
    @Select("SELECT * FROM comment WHERE user_id = #{userId} ORDER BY timestamp DESC")
    List<Comment> getCommentsByUserId(@Param("userId") Long userId);

    @Update("UPDATE comment SET like_count = like_count + 1 WHERE id = #{id}")
    void incrementLike(@Param("id") Long id);
}

