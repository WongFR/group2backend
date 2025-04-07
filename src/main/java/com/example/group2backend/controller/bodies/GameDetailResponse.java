package com.example.group2backend.controller.bodies;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class GameDetailResponse {
    private Integer id; // ID
    private String slug; // Slug
    private String name; // Name
    private String name_original; // Original name
    private String description; // Description
    private Integer metacritic; // Metacritic score
    private List<GamePlatformMetacritic> metacritic_platforms; // Platform Metacritic scores
    private String released; // Released date
    private Boolean tba; // To Be Announced
    private Date updated; // Last update datetime
    private String background_image; // Background image URL
    private String background_image_additional; // Additional background image
    private String website; // Official website
    private Double rating; // Average rating
    private Integer rating_top; // Top rating
    private Object ratings; // Ratings detail
    private Object reactions; // Reactions detail
    private Integer added; // Added count
    private Object added_by_status; // Added by status
    private Integer playtime; // Average playtime in hours
    private Integer screenshots_count; // Number of screenshots
    private Integer movies_count; // Number of movies
    private Integer creators_count; // Number of creators
    private Integer achievements_count; // Number of achievements
    private String parent_achievements_count; // Parent achievements count
    private String reddit_url; // Reddit URL
    private String reddit_name; // Reddit name
    private String reddit_description; // Reddit description
    private String reddit_logo; // Reddit logo URL
    private Integer reddit_count; // Reddit post count
    private String twitch_count; // Twitch count
    private String youtube_count; // YouTube count
    private String reviews_text_count; // Reviews with text count
    private Integer ratings_count; // Total ratings count
    private Integer suggestions_count; // Suggestions count
    private List<String> alternative_names; // Alternative names
    private String metacritic_url; // Metacritic URL
    private Integer parents_count; // Parents count
    private Integer additions_count; // Additions count
    private Integer game_series_count; // Game series count
    private EsrbRating esrb_rating; // ESRB rating
    private List<GamePlatformInfo> platforms; // Supported platforms

    // Nested class for metacritic platform info
    @Data
    public static class GamePlatformMetacritic {
        private Integer metascore; // Score
        private String url; // URL
    }

    // Nested class for ESRB rating
    @Data
    public static class EsrbRating {
        private Integer id; // ESRB ID
        private String slug; // Slug
        private String name; // ESRB name
    }

    // Nested class for platform info
    @Data
    public static class GamePlatformInfo {
        private Platform platform; // Platform detail
        private String released_at; // Released at
        private Requirements requirements; // Requirements

        @Data
        public static class Platform {
            private Integer id; // Platform ID
            private String slug; // Slug
            private String name; // Name
        }

        @Data
        public static class Requirements {
            private String minimum; // Minimum requirements
            private String recommended; // Recommended requirements
        }
    }
}
