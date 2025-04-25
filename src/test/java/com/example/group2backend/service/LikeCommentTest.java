package com.example.group2backend.service;

import com.example.group2backend.database.service.CommentLikeService;
import com.example.group2backend.database.service.CommentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class LikeCommentTest {
    @Mock
    private CommentService commentService;

    @Mock
    private CommentLikeService commentLikeService;

    @InjectMocks
    private GameService gameService;

    @Test
    void testLikeComment_shouldCallIncrementAndInsert() {
        // Given
        Long userId = 1L;
        Long commentId = 100L;

        // When
        gameService.likeComment(userId, commentId);

        // Then
        verify(commentService, times(1)).incrementLike(commentId);
        verify(commentLikeService, times(1)).insert(userId, commentId);
    }
}
