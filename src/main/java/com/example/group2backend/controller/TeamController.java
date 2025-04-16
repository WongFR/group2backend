package com.example.group2backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.group2backend.database.entity.Team;
import com.example.group2backend.database.service.TeamService;
import com.example.group2backend.database.service.UserService;
import com.example.group2backend.service.TeamManageService;
import com.example.group2backend.service.model.TeamWithMembers;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamManageService manageService;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> createTeam(@RequestBody Team team, Authentication auth) {
        Long userId = userService.getUserByUsername(auth.getName()).getId();
        team.setCreatorId(userId);
        manageService.addTeamMembers(team, userId);
        teamService.createTeam(team);
        return ResponseEntity.ok("Team created");
    }

    @PostMapping("/join")
    public ResponseEntity<String> joinTeam(@RequestBody Team team, Authentication auth) {
        Long userId = userService.getUserByUsername(auth.getName()).getId();
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
    public ResponseEntity<String> updateTeam(@RequestBody Team team) {
        teamService.updateTeam(team);
        return ResponseEntity.ok("Team updated");
    }

    @GetMapping("/find")
    public ResponseEntity<List<TeamWithMembers>> getTeamsByGameOrCreator(@RequestBody Team team) {
        List<TeamWithMembers> teams = manageService.getAllTeams(team.getCreatorId(),team.getGameId());
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TeamWithMembers>> searchTeams(@RequestParam String keyword) {
        List<TeamWithMembers> teams = manageService.searchByName(keyword);
        return ResponseEntity.ok(teams);
    }
    @GetMapping("/all")
    public ResponseEntity<List<TeamWithMembers>> getAllTeams() {
        List<TeamWithMembers> teams = manageService.getAllTeams(null, null);
        return ResponseEntity.ok(teams);
}

}

