package com.example.group2backend.service;

import com.example.group2backend.database.entity.Team;
import com.example.group2backend.database.service.TeamService;
import com.example.group2backend.database.service.UserService;
import com.example.group2backend.service.model.TeamWithMembers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.example.group2backend.service.TeamUtils.parseJsonMemberIds;
import static com.example.group2backend.service.TeamUtils.toJson;

@Service
public class TeamManageService {
    @Autowired
    public TeamService teamService;

    @Autowired
    private UserService userService;


    public void addTeamMembers(Team team, Long userId) {
        List<Long> memberIds = parseJsonMemberIds(team.getMemberIds());
        memberIds.add(userId);
        team.setMemberIds(toJson(memberIds));
    }

    public void removeTeamMembers(Team team, Long userId) {
        List<Long> memberIds = parseJsonMemberIds(team.getMemberIds());
        memberIds.removeIf(id -> Objects.equals(id, userId));
        team.setMemberIds(toJson(memberIds));
    }

    public TeamWithMembers getTeam(Long id) {
        return getTeamWithMembers(teamService.getTeam(id));
    }

    public List<TeamWithMembers> getAllTeams(Long userId, Long gameId) {
        return teamService.getAllTeams(userId, gameId)
                .stream()
                .map(this::getTeamWithMembers)
                .toList();
    }

    public List<TeamWithMembers> searchByName(String keywords) {
        return teamService.searchByName(keywords)
                .stream()
                .map(this::getTeamWithMembers)
                .toList();
    }
    private TeamWithMembers getTeamWithMembers(Team team) {
        return new TeamWithMembers(team,
                userService.findUsersByIds(parseJsonMemberIds(team.getMemberIds())));
    }
}
