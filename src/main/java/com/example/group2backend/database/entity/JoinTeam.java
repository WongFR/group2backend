package com.example.group2backend.database.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class JoinTeam {
    private Long id;
    private Long teamId;
    private Long userId;
    private String status; // PENDING, APPROVED, REJECTED
    private LocalDateTime requestTime;
    private Long hostId;
}

