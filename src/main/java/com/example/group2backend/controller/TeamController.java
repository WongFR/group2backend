package com.example.group2backend.controller;

import com.example.group2backend.database.entity.Team;
import com.example.group2backend.database.entity.User;
import com.example.group2backend.database.service.TeamService;
import com.example.group2backend.database.service.UserService;
import com.example.group2backend.service.TeamManageService;
import com.example.group2backend.service.model.TeamWithMembers;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamManageService manageService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Create a new team")
    @PostMapping("/create")
    public ResponseEntity<String> createTeam(@RequestBody Team team, Authentication auth) {
        Long userId = userService.getUserByUsername(auth.getName()).getId();
        team.setCreatorId(userId);
        manageService.addTeamMembers(team, userId);
        teamService.createTeam(team);
        return ResponseEntity.ok("Team created");
    }

    @Operation(summary = "Join an existing team")
    @PostMapping("/join")
    public ResponseEntity<String> joinTeam(@RequestBody Team team, Authentication auth) {
        Long userId = userService.getUserByUsername(auth.getName()).getId();
        team = teamService.getTeam(team.getId());

        manageService.addTeamMembers(team, userId);
        teamService.updateTeam(team);

        return ResponseEntity.ok("Joined team");
    }

    @Operation(summary = "Get team by ID")
    @GetMapping("/{id}")
    public ResponseEntity<TeamWithMembers> getTeam(@PathVariable Long id) {
        return ResponseEntity.ok(manageService.getTeam(id));
    }

    @Operation(summary = "Update team info")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTeam(@RequestBody Team team) {
        teamService.updateTeam(team);
        return ResponseEntity.ok("Team updated");
    }

    @Operation(summary = "Find teams by creator ID or game ID")
    @GetMapping("/find")
    public ResponseEntity<List<TeamWithMembers>> getTeamsByGameOrCreator(@RequestBody Team team) {
        List<TeamWithMembers> teams = manageService.getAllTeams(team.getCreatorId(), team.getGameId());
        return ResponseEntity.ok(teams);
    }

    @Operation(summary = "Search teams by name keyword")
    @GetMapping("/search")
    public ResponseEntity<List<TeamWithMembers>> searchTeams(@RequestParam String keyword) {
        List<TeamWithMembers> teams = manageService.searchByName(keyword);
        return ResponseEntity.ok(teams);
    }

    @Operation(summary = "Get teams created by current user")
    @GetMapping("/me")
    public ResponseEntity<List<TeamWithMembers>> searchTeams(Authentication auth) {
        String username = auth.getName();
        User user = userService.getUserByUsername(username);

        List<TeamWithMembers> teams = manageService.getAllTeams(user.getId(), null);
        return ResponseEntity.ok(teams);
    }
}
