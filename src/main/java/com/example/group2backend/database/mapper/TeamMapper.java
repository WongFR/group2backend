package com.example.group2backend.database.mapper;

import com.example.group2backend.database.entity.Team;

import java.util.List;

public interface TeamMapper {
    Team selectById(Integer id);
    List<Team> selectAll();
    void insert(Team team);
    void update(Team team);
    void delete(Integer id);
}
