package com.example.group2backend.controller;

import com.example.group2backend.controller.bodies.GameDetailResponse;
import com.example.group2backend.database.entity.User;
import com.example.group2backend.database.service.CommentService;
import com.example.group2backend.database.service.UserService;
import com.example.group2backend.service.GameService;

import com.example.group2backend.service.model.GameComment;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.example.group2backend.database.entity.Comment;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;

@RestController
@RequestMapping("/game_info")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class GameInfoController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Get game detail by ID")
    @GetMapping("/{id}")
    public ResponseEntity<GameDetailResponse> getGameDetail(@PathVariable Long id) {
        GameDetailResponse game = gameService.getGameDetailById(id.toString());
        if (game != null) {
            return ResponseEntity.ok(game);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get all comments of a game")
    @GetMapping("/{id}/comments")
    public ResponseEntity<List<GameComment>> getGameComments(@PathVariable Long id) {
        List<GameComment> comments = commentService.getCommentsByGameId(id).stream()
                .map(comment -> gameService.getGameComment(comment))
                .toList();
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "Get all my comments")
    @GetMapping("/comments/me")
    public ResponseEntity<List<Comment>> getMyComments(Authentication auth) {
        String username = auth.getName();
        User user = userService.getUserByUsername(username);
        List<Comment> comments = commentService.getCommentsByUserId(user.getId());
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "Post a new comment for a game")
    @PostMapping("/{id}/comments")
    public ResponseEntity<String> postGameComment(@PathVariable Long id, @RequestBody Comment comment, Authentication auth) {
        String username = auth.getName();
        User user = userService.getUserByUsername(username);

        comment.setUserId(user.getId());
        comment.setGameId(id);
        commentService.addComment(comment);
        return ResponseEntity.ok("Comment added successfully.");
    }

    @Operation(summary = "Like a comment")
    @PostMapping("/comments/{id}/like")
    public ResponseEntity<String> likeComment(@PathVariable Long id, Authentication auth) {
        Long userId = userService.getUserByUsername(auth.getName()).getId();

        try {
            gameService.likeComment(userId, id);
            return ResponseEntity.ok("Liked");
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body("You have already liked this comment.");
        }
    }
}