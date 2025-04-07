package com.example.group2backend.database.service;

import com.example.group2backend.database.entity.TeamMember;

import java.util.List;

public interface TeamMemberService {
    TeamMember getTeamMemberById(Integer id);
    List<TeamMember> getAllTeamMembers();
    void createTeamMember(TeamMember teammember);
    void updateTeamMember(TeamMember teammember);
    void deleteTeamMember(Integer id);
}
