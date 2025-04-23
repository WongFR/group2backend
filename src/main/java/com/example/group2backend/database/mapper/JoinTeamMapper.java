package com.example.group2backend.database.mapper;

import com.example.group2backend.database.entity.JoinTeam;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface JoinTeamMapper {

    @Insert("INSERT INTO join_team (team_id, user_id, host_id, status, request_time) " +
            "VALUES (#{teamId}, #{userId}, #{hostId}, #{status}, #{requestTime})")
    void insert(JoinTeam joinTeam);

    @Select("SELECT * FROM join_team WHERE team_id = #{teamId} AND user_id = #{userId}")
    JoinTeam findByTeamIdAndUserId(@Param("teamId") Long teamId, @Param("userId") Long userId);

    @Select("SELECT * FROM join_team WHERE host_id = #{hostId} AND status = #{status}")
    List<JoinTeam> getRequestsByHostAndStatus(@Param("hostId") Long hostId, @Param("status") String status);

    @Update("UPDATE join_team SET status = 'APPROVED' WHERE id = #{id}")
    void approveRequest(@Param("id") Long id);

    @Update("UPDATE join_team SET status = 'REJECTED' WHERE id = #{id}")
    void rejectRequest(@Param("id") Long id);

    @Select("SELECT * FROM join_team WHERE id = #{id}")
    JoinTeam findById(@Param("id") Long id);
}

