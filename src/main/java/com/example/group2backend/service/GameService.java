package com.example.group2backend.service;

import com.example.group2backend.controller.bodies.GameDetailResponse;
import com.example.group2backend.database.entity.Comment;
import com.example.group2backend.database.entity.User;
import com.example.group2backend.database.service.CommentLikeService;
import com.example.group2backend.database.service.CommentService;
import com.example.group2backend.database.service.UserService;
import com.example.group2backend.service.model.GameComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
public class GameService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${rawg.api.base-url}")
    private String baseUrl;

    @Value("${rawg.api.key}")
    private String apiKey;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentLikeService commentLikeService;

    @Autowired
    private UserService userService;

    // Call RAWG API to get game detail by ID or slug
    public GameDetailResponse getGameDetailById(String idOrSlug) {
        String url = String.format("%s/games/%s?key=%s", baseUrl, idOrSlug, apiKey);
        return restTemplate.getForObject(url, GameDetailResponse.class);
    }

    @Transactional
    public void likeComment(Long user, Long comment) {
        commentService.incrementLike(comment);
        commentLikeService.insert(user, comment);
    }

    public GameComment getGameComment(Comment comment) {
        User user = userService.findUsersById(comment.getUserId());
        boolean liked = commentLikeService.existsByUserIdAndCommentId(comment.getUserId(), comment.getId());

        return new GameComment(comment, user, liked);
    }
}

