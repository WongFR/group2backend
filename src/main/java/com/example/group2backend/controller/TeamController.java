package com.example.group2backend.controller;

import com.example.group2backend.database.entity.Team;
import com.example.group2backend.database.service.TeamService;
import com.example.group2backend.service.TeamManageService;
import com.example.group2backend.service.model.TeamWithMembers;
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

    @PostMapping("/create")
    public ResponseEntity<String> createTeam(@RequestBody Team team, Authentication auth) {
        Long userId = Long.parseLong(auth.getName());
        team.setCreatorId(userId);
        manageService.addTeamMembers(team, userId);
        teamService.createTeam(team);
        return ResponseEntity.ok("Team created");
    }

    @PostMapping("/join")
    public ResponseEntity<String> joinTeam(@RequestBody Team team, Authentication auth) {
        Long userId = Long.parseLong(auth.getName());
        team = teamService.getTeam(team.getId());

        manageService.addTeamMembers(team, userId);
        teamService.updateTeam(team);

        return ResponseEntity.ok("Joined team");
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamWithMembers> getTeam(@PathVariable Long id) {
        return ResponseEntity.ok(manageService.getTeam(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTeam(@PathVariable Long id, @RequestBody Team team) {
        team.setId(id);
        teamService.updateTeam(team);
        return ResponseEntity.ok("Team updated");
    }

    @GetMapping
    public ResponseEntity<List<TeamWithMembers>> getTeamsByGameOrCreator(
            @RequestParam(required = false) Long gameId,
            @RequestParam(required = false) Long creatorId
    ) {
        List<TeamWithMembers> teams = manageService.getAllTeams(gameId, creatorId);
        return ResponseEntity.ok(teams);
    }
}

