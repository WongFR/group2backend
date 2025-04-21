package com.example.group2backend.controller;

import com.example.group2backend.controller.bodies.LoginRequest;
import com.example.group2backend.database.entity.User;
import com.example.group2backend.database.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            userService.register(user);
            return ResponseEntity.ok("Register success");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Login and get JWT token")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        try {
            String token = userService.login(request.getUsername(), request.getPassword());
            return ResponseEntity.ok().body(token);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @Operation(summary = "Get current logged-in user info")
    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok().body(user);
    }

    @Operation(summary = "Edit user profile")
    @PostMapping("/edit")
    public ResponseEntity<String> edit(@RequestBody User user) {
        try {
            userService.edit(user);
            return ResponseEntity.ok("Update success");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private final String rootPath = System.getProperty("user.dir");
    private final String uploadDir = rootPath + File.separator + "avatar" + File.separator;

    @PostMapping("/avatar")
    public ResponseEntity<String> avatar(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("The uploaded file is empty.");
        }

        try {

            // Create directory if it doesn't exist
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            // Get original file extension
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            int index = originalFilename.lastIndexOf(".");
            if (index != -1) {
                extension = originalFilename.substring(index);
            }

            // Generate random hash name (e.g. UUID or SHA-256 of UUID)
            String hashName = generateHashFilename() + extension;

            // Save file
            String filePath = uploadDir + File.separator + hashName;
            file.transferTo(new File(filePath));

            return ResponseEntity.ok(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }

    // Generate SHA-256 hash based on UUID
    private String generateHashFilename() {
        try {
            String uuid = UUID.randomUUID().toString();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(uuid.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString(); // 64 characters
        } catch (Exception e) {
            // Fallback to UUID if hashing fails
            return UUID.randomUUID().toString().replace("-", "");
        }
    }
}
