package com.example.group2backend.service.model;

import com.example.group2backend.database.entity.Team;
import com.example.group2backend.database.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class TeamWithMembers {
    private Team team;
    private List<User> members;
}
