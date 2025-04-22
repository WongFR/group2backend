package com.example.group2backend.database.mapper;
import com.example.group2backend.database.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentLikeMapper {
    @Select("SELECT COUNT(*) > 0 FROM comment_like WHERE user_id = #{userId} AND comment_id = #{commentId}")
    boolean existsByUserIdAndCommentId(@Param("userId") Long userId, @Param("commentId") Long commentId);

    @Insert("INSERT INTO comment_like (user_id, comment_id) VALUES (#{userId}, #{commentId})")
    void insert(@Param("userId") Long userId, @Param("commentId") Long commentId);
}
