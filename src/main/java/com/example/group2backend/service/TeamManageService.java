package com.example.group2backend.service;

import com.example.group2backend.database.entity.JoinTeam;
import com.example.group2backend.database.entity.Team;
import com.example.group2backend.database.entity.User;
import com.example.group2backend.database.service.JoinTeamService;
import com.example.group2backend.database.service.TeamService;
import com.example.group2backend.database.service.UserService;
import com.example.group2backend.service.model.JoinTeamWithUser;
import com.example.group2backend.service.model.TeamWithMembers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    @Autowired
    private JoinTeamService joinTeamService;

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

    public void apply(Long teamId, Long userId) {
        Team team = teamService.getTeam(teamId);

        JoinTeam joinTeam = new JoinTeam();
        joinTeam.setTeamId(teamId);
        joinTeam.setUserId(userId);
        joinTeam.setHostId(team.getCreatorId());
        joinTeam.setStatus("PENDING");
        joinTeam.setRequestTime(LocalDateTime.now());

        joinTeamService.insert(joinTeam);
    }

    @Transactional
    public void approveTeam(Long joinTeamId) {
        JoinTeam joinTeam = joinTeamService.findById(joinTeamId);
        Team team = teamService.getTeam(joinTeam.getTeamId());

        addTeamMembers(team, joinTeam.getUserId());
        teamService.updateTeam(team);
        joinTeamService.approveRequest(joinTeamId);
    }

    public void rejectTeam(Long joinTeamId) {
        joinTeamService.rejectRequest(joinTeamId);
    }

    public List<JoinTeamWithUser> getPendingJoinTeams(Long hostId) {
        List<JoinTeam> pending = joinTeamService.getRequestsByHostAndStatus(hostId, "Pending");
        return pending.stream()
                .map(joinTeam -> {
                    User user = userService.findUsersById(joinTeam.getUserId());
                    JoinTeamWithUser item = new JoinTeamWithUser();
                    item.setJoinTeam(joinTeam);
                    item.setUser(user);
                    return item;
                })
                .toList();
    }
}
