package com.example.group2backend.controller.bodies;

import com.example.group2backend.database.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserInfoResponse {
    private User user;
}
