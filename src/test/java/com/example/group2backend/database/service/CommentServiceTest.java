package com.example.group2backend.database.service;

import com.example.group2backend.database.entity.Comment;
import com.example.group2backend.database.mapper.CommentMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    private CommentMapper commentMapper;
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        commentMapper = mock(CommentMapper.class);
        commentService = new CommentService();
        // Use reflection to inject mock (since the field is private)
        try {
            var field = CommentService.class.getDeclaredField("commentMapper");
            field.setAccessible(true);
            field.set(commentService, commentMapper);
        } catch (Exception e) {
            fail("Failed to inject mock CommentMapper: " + e.getMessage());
        }
    }

    @Test
    void addComment_shouldSetTimestampAndCallMapper() {
        Comment comment = new Comment();
        comment.setGameId(1);
        comment.setUserId(2L);
        comment.setContent("Great game!");

        commentService.addComment(comment);

        assertNotNull(comment.getTimestamp(), "Timestamp should be set");
        verify(commentMapper, times(1)).insertComment(comment);
    }

    @Test
    void getCommentsByGameId_shouldReturnCommentList() {
        Comment c1 = new Comment();
        Comment c2 = new Comment();
        List<Comment> expectedList = Arrays.asList(c1, c2);

        when(commentMapper.getCommentsByGameId(123)).thenReturn(expectedList);

        List<Comment> result = commentService.getCommentsByGameId(123);

        assertEquals(2, result.size(), "Should return 2 comments");
        verify(commentMapper, times(1)).getCommentsByGameId(123);
    }
}
