package com.example.group2backend.database.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.group2backend.database.entity.Team;
import com.example.group2backend.database.mapper.TeamMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class TeamServiceTest {
    private TeamService teamService;
    private TeamMapper teamMapper;

    @BeforeEach
    public void setUp() throws Exception {
        teamMapper = mock(TeamMapper.class);
        teamService = new TeamService();

        java.lang.reflect.Field field = TeamService.class.getDeclaredField("teamMapper");
        field.setAccessible(true);
        field.set(teamService, teamMapper);
    }

    @Test
    public void testGetTeam_withId21() {

        Long teamId = 21L;
        Team mockTeam = new Team();
        mockTeam.setId(teamId);
        mockTeam.setGameId(3498L);
        mockTeam.setCreatorId(1L);
        mockTeam.setTeamName("Good");
        mockTeam.setTeamSize(3);
        mockTeam.setDescription("Good Game");
        mockTeam.setFromTime(LocalDateTime.of(2025, 4, 14, 18, 0) );  // 可选
        mockTeam.setToTime(LocalDateTime.of(2025, 4, 14, 22, 0) );    // 可选
        mockTeam.setCreatedAt(LocalDateTime.of(2025, 4, 14, 12, 13) );  // 可选
        mockTeam.setMemberIds("[1]");

        when(teamMapper.findById(teamId)).thenReturn(mockTeam);

        Team result = teamService.getTeam(teamId);

        assertNotNull(result);
        assertEquals(21L, result.getId());
        assertEquals("Good", result.getTeamName());
        assertEquals("Good Game", result.getDescription());
        assertEquals("[1]", result.getMemberIds());

        verify(teamMapper).findById(teamId);
    }
}
