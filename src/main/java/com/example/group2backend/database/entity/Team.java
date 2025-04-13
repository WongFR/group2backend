package com.example.group2backend.database.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Team {
    private Long id;
    private Integer gameId;
    private Long creatorId;
    private String teamName;
    private Integer teamSize;
    private String description;
    private LocalDateTime from;
    private LocalDateTime to;
    private LocalDateTime createdAt;
    //Json string
    private String memberIds;
}
