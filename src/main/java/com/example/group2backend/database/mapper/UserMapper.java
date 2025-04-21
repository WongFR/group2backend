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

    @Select("SELECT * FROM users WHERE id = #{id}")
    User findById(@Param("id") Long id);

    @Select({
            "<script>",
            "SELECT * FROM users WHERE id IN",
            "<foreach item='id' collection='userIds' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    List<User> findUsersByIds(@Param("userIds") List<Long> userIds);

    @Update("UPDATE users SET " +
            "username = #{username}, " +
            "password = #{password}, " +
            "email = #{email}, " +
            "phone = #{phone}, " +
            "name = #{name}, " +
            "bio = #{bio}, " +
            "favorite_genres = #{favoriteGenres}, " +
            "updated_at = #{updatedAt} " +
            "WHERE id = #{id}")
    void updateUser(User user);
}
