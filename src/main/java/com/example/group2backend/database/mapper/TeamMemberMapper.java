package com.example.group2backend.database.mapper;

import com.example.group2backend.database.entity.TeamMember;

import java.util.List;

public interface TeamMemberMapper {
    TeamMember selectById(Integer id);
    List<TeamMember> selectAll();
    void insert(TeamMember teammember);
    void update(TeamMember teammember);
    void delete(Integer id);
}
