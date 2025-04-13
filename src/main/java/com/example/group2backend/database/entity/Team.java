package com.example.group2backend.database.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Team {
    private Long id;
    private Long gameId;
    private Long creatorId;
    private String teamName;
    private Integer teamSize;
    private String description;
    private LocalDateTime fromTime;
    private LocalDateTime toTime;
    private LocalDateTime createdAt;
    //Json string
    private String memberIds;
}
