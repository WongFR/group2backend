package com.example.group2backend.service;

import com.example.group2backend.database.entity.JoinTeam;
import com.example.group2backend.database.entity.Team;
import com.example.group2backend.database.service.JoinTeamService;
import com.example.group2backend.database.service.TeamService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JoinTeamTest {
    @Mock
    private TeamService teamService;

    @Mock
    private JoinTeamService joinTeamService;

    @InjectMocks
    private TeamManageService applicationService;

    @Test
    void testApply_Success() {
        Long teamId = 1L;
        Long userId = 2L;

        Team mockTeam = new Team();
        mockTeam.setId(teamId);
        mockTeam.setCreatorId(99L);
        mockTeam.setTeamSize(5);
        mockTeam.setMemberIds("[1, 3]"); // 当前2人，没满

        when(teamService.getTeam(teamId)).thenReturn(mockTeam);

        applicationService.apply(teamId, userId);

        ArgumentCaptor<JoinTeam> captor = ArgumentCaptor.forClass(JoinTeam.class);
        verify(joinTeamService).insert(captor.capture());

        JoinTeam applied = captor.getValue();
        assertEquals(teamId, applied.getTeamId());
        assertEquals(userId, applied.getUserId());
        assertEquals("PENDING", applied.getStatus());
        assertEquals(mockTeam.getCreatorId(), applied.getHostId());
        assertNotNull(applied.getRequestTime());
    }

    @Test
    void testApply_FullTeam_ThrowsException() {
        Long teamId = 1L;
        Long userId = 2L;

        Team mockTeam = new Team();
        mockTeam.setTeamSize(3);
        mockTeam.setMemberIds("[1,2,3]");

        when(teamService.getTeam(teamId)).thenReturn(mockTeam);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                applicationService.apply(teamId, userId)
        );

        assertEquals("Team is full", ex.getMessage());
        verify(joinTeamService, never()).insert(any());
    }

    @Test
    void testApply_AlreadyApplied_ThrowsException() {
        Long teamId = 1L;
        Long userId = 2L;

        Team mockTeam = new Team();
        mockTeam.setCreatorId(88L);
        mockTeam.setTeamSize(4);
        mockTeam.setMemberIds("[]");

        when(teamService.getTeam(teamId)).thenReturn(mockTeam);
        doThrow(new RuntimeException("duplicate")).when(joinTeamService).insert(any());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                applicationService.apply(teamId, userId)
        );

        assertEquals("You have already applied for this team", ex.getMessage());
    }
}
