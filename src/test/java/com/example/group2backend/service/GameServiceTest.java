package com.example.group2backend.service;

import com.example.group2backend.controller.bodies.GameDetailResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GameService gameService;

    private final String baseUrl = "https://api.rawg.io/api";
    private final String apiKey = "test_api_key";

    @BeforeEach
    public void setUp() throws Exception {
        java.lang.reflect.Field baseUrlField = GameService.class.getDeclaredField("baseUrl");
        baseUrlField.setAccessible(true);
        baseUrlField.set(gameService, baseUrl);

        java.lang.reflect.Field apiKeyField = GameService.class.getDeclaredField("apiKey");
        apiKeyField.setAccessible(true);
        apiKeyField.set(gameService, apiKey);
    }

    @Test
    void testGetGameDetailById_shouldReturnGameDetailResponse() {
        // Given
        String gameId = "elden-ring";
        String expectedUrl = baseUrl + "/games/" + gameId + "?key=" + apiKey;

        GameDetailResponse mockResponse = new GameDetailResponse();
        mockResponse.setName("Elden Ring");

        // When
        when(restTemplate.getForObject(expectedUrl, GameDetailResponse.class)).thenReturn(mockResponse);

        // Then
        GameDetailResponse result = gameService.getGameDetailById(gameId);
        assertEquals("Elden Ring", result.getName());
    }
}
