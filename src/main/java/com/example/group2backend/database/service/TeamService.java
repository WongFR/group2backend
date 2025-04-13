package com.example.group2backend.database.service;

import com.example.group2backend.database.entity.Team;
import com.example.group2backend.database.mapper.TeamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamMapper teamMapper;

    public void createTeam(Team team) {
        team.setCreatedAt(LocalDateTime.now());
        teamMapper.insertTeam(team);
    }

    public Team getTeam(Long id) {
        return teamMapper.findById(id);
    }

    public void updateTeam(Team team) {
        teamMapper.updateTeam(team);
    }

    public List<Team> getAllTeams(Long gameId, Long creatorId) {
        return teamMapper.findByGameIdOrCreatorId(gameId, creatorId);
    }

    public List<Team> searchByName(String keyword) {
        return teamMapper.searchByTeamNameLike("%" + keyword + "%");
    }
}
