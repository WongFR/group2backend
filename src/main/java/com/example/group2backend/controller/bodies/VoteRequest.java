package com.example.group2backend.controller.bodies;

import lombok.Data;

/**
 * Request body for voting on a comment
 */
@Data
public class VoteRequest {
    private Boolean isLike; // true for like, false for dislike
}