package com.example.group2backend.service;

import com.example.group2backend.controller.bodies.GameDetailResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameServiceTest {

    private RestTemplate restTemplate;
    private GameService gameService;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        gameService = new GameService(restTemplate);

        // Inject @Value fields via reflection (baseUrl and apiKey)
        try {
            var baseUrlField = GameService.class.getDeclaredField("baseUrl");
            var apiKeyField = GameService.class.getDeclaredField("apiKey");
            baseUrlField.setAccessible(true);
            apiKeyField.setAccessible(true);
            baseUrlField.set(gameService, "https://api.rawg.io/api");
            apiKeyField.set(gameService, "dummy-key");
        } catch (Exception e) {
            fail("Failed to inject baseUrl/apiKey: " + e.getMessage());
        }
    }

    @Test
    void testGetGameDetailById_shouldCallRestTemplateAndReturnResponse() {
        // Prepare dummy response
        GameDetailResponse dummyResponse = new GameDetailResponse();
        dummyResponse.setId(123);
        dummyResponse.setName("Mock Game");

        String expectedUrl = "https://api.rawg.io/api/games/test-game?key=dummy-key";

        // Mock RestTemplate behavior
        when(restTemplate.getForObject(expectedUrl, GameDetailResponse.class))
                .thenReturn(dummyResponse);

        // Call the method
        GameDetailResponse result = gameService.getGameDetailById("test-game");

        // Verify RestTemplate was called
        verify(restTemplate, times(1)).getForObject(expectedUrl, GameDetailResponse.class);

        // Assert result
        assertNotNull(result);
        assertEquals("Mock Game", result.getName());
        assertEquals(123, result.getId());
    }
}
