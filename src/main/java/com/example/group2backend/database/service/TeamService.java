package com.example.group2backend.database.service;

import com.example.group2backend.database.entity.Team;

import java.util.List;

public interface TeamService {
    Team getTeamById(Integer id);
    List<Team> getAllTeams();
    void createTeam(Team team);
    void updateTeam(Team team);
    void deleteTeam(Integer id);
}
