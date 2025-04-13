package com.example.group2backend.service;

import com.example.group2backend.database.entity.Team;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class TeamUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<Long> parseJsonMemberIds(String json) {
        if (json == null || json.isBlank()) return new LinkedList<>();
        try {
            return new LinkedList<>(mapper.readValue(json, new TypeReference<List<Long>>() {}));
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON memberIds", e);
        }
    }

    public static String toJson(List<Long> memberIds) {
        try {
            return mapper.writeValueAsString(memberIds);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize memberIds", e);
        }
    }
}
