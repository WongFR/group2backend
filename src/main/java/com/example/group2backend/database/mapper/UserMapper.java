package com.example.group2backend.database.mapper;

import com.example.group2backend.database.entity.User;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO users (username, password, email, phone, name, bio, favorite_genres, created_at, updated_at) " +
            "VALUES (#{username}, #{password}, #{email}, #{phone}, #{name}, #{bio}, #{favoriteGenres}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(User user);


    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(@Param("username") String username);


    @Select({
                "<script>",
                "SELECT * FROM users",
                "<where>",
                "  <if test='userIds != null and userIds.size() > 0'>",
                "    id IN",
                "    <foreach collection='userIds' item='id' open='(' separator=',' close=')'>",
                "      #{id}",
                "    </foreach>",
                "  </if>",
                "</where>",
                "</script>"
     })
     List<User> findUsersByIds(@Param("userIds") List<Long> userIds);




}
