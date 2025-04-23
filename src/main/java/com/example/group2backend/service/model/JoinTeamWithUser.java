package com.example.group2backend.service.model;

import com.example.group2backend.database.entity.JoinTeam;
import com.example.group2backend.database.entity.User;
import lombok.Data;

@Data
public class JoinTeamWithUser {
    private JoinTeam joinTeam;
    private User user;
}
