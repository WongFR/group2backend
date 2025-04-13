package com.example.group2backend.controller;

import com.example.group2backend.controller.bodies.GameDetailResponse;
import com.example.group2backend.database.entity.Comment;
import com.example.group2backend.database.service.CommentService;
import com.example.group2backend.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game_info")
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
    public ResponseEntity<List<Comment>> getGameComments(@PathVariable Integer id) {
        List<Comment> comments = commentService.getCommentsByGameId(id);
        return ResponseEntity.ok(comments);
    }

    // Post a new comment for a game
    @PostMapping("/{id}/comments")
    public ResponseEntity<String> postGameComment(@PathVariable Integer id, @RequestBody Comment comment) {
        // Set user ID to a fixed value temporarily
        comment.setUserId(1L);
        comment.setGameId(id);
        commentService.addComment(comment);
        return ResponseEntity.ok("Comment added successfully.");
    }
    
    // Like a comment
    @PostMapping("/comments/{commentId}/like")
    public ResponseEntity<String> likeComment(@PathVariable Long commentId) {
        commentService.likeComment(commentId);
        return ResponseEntity.ok("Comment liked successfully.");
    }
    
    // Dislike a comment
    @PostMapping("/comments/{commentId}/dislike")
    public ResponseEntity<String> dislikeComment(@PathVariable Long commentId) {
        commentService.dislikeComment(commentId);
        return ResponseEntity.ok("Comment disliked successfully.");
    }
    
    // Unlike a comment
    @PostMapping("/comments/{commentId}/unlike")
    public ResponseEntity<String> unlikeComment(@PathVariable Long commentId) {
        commentService.unlikeComment(commentId);
        return ResponseEntity.ok("Comment unliked successfully.");
    }
    
    // Undislike a comment
    @PostMapping("/comments/{commentId}/undislike")
    public ResponseEntity<String> undislikeComment(@PathVariable Long commentId) {
        commentService.undislikeComment(commentId);
        return ResponseEntity.ok("Comment undisliked successfully.");
    }
}