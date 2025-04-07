package com.example.group2backend.database.mapper;

import com.example.group2backend.database.entity.Game;

import java.util.List;

public interface GameMapper {
    Game selectById(Integer id);
    List<Game> selectAll();
    void insert(Game game);
    void update(Game game);
    void delete(Integer id);
}
