package com.example.group2backend.database.mapper;

import com.example.group2backend.database.entity.User;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO users (username, password, email, created_at, updated_at) " +
            "VALUES (#{username}, #{password}, #{email}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(User user);

    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

    @Select({
            "<script>",
            "SELECT * FROM users WHERE id IN",
            "<foreach item='id' collection='userIds' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    List<User> findUsersByIds(@Param("userIds") List<Long> userIds);
}
