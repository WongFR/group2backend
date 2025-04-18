package com.example.group2backend.controller;

import com.example.group2backend.controller.bodies.GameDetailResponse;
import com.example.group2backend.database.entity.Comment;
import com.example.group2backend.database.service.CommentService;
import com.example.group2backend.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game_info")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class GameInfoController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private GameService gameService;

    // Get game detail by ID
    @GetMapping("/{id}")
    public ResponseEntity<GameDetailResponse> getGameDetail(@PathVariable Long id) {
        GameDetailResponse game = gameService.getGameDetailById(id.toString());
        if (game != null) {
            return ResponseEntity.ok(game);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get all comments of a game
    @GetMapping("/{id}/comments")
    public ResponseEntity<List<Comment>> getGameComments(@PathVariable Long id) {
        List<Comment> comments = commentService.getCommentsByGameId(id);
        return ResponseEntity.ok(comments);
    }

    // Post a new comment for a game
    @PostMapping("/{id}/comments")
    public ResponseEntity<String> postGameComment(@PathVariable Long id, @RequestBody Comment comment, Authentication auth) {
        comment.setUserId(Long.parseLong(auth.getName()));
        comment.setGameId(id);
        commentService.addComment(comment);
        return ResponseEntity.ok("Comment added successfully.");
    }
}

