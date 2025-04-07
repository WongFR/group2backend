package com.example.group2backend.service;

import com.example.group2backend.controller.bodies.GameDetailResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GameService {

    private final RestTemplate restTemplate;

    @Value("${rawg.api.base-url}")
    private String baseUrl;

    @Value("${rawg.api.key}")
    private String apiKey;

    public GameService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Call RAWG API to get game detail by ID or slug
    public GameDetailResponse getGameDetailById(String idOrSlug) {
        String url = String.format("%s/games/%s?key=%s", baseUrl, idOrSlug, apiKey);
        return restTemplate.getForObject(url, GameDetailResponse.class);
    }
}

