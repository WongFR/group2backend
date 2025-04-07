package com.example.group2backend.controller.bodies;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
