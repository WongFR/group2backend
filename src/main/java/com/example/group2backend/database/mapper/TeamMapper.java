package com.example.group2backend.database.mapper;

import com.example.group2backend.database.entity.Team;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeamMapper {

    @Insert("INSERT INTO team (game_id, creator_id, team_name, team_size, description, from_time, to_time, created_at, member_ids) " +
            "VALUES (#{gameId}, #{creatorId}, #{teamName}, #{teamSize}, #{description}, #{fromTime}, #{toTime}, #{createdAt}, #{memberIds})")
    void insertTeam(Team team);

    @Select("SELECT * FROM team WHERE id = #{id}")
    Team findById(Long id);

    @Update("UPDATE team SET game_id = #{gameId}, creator_id = #{creatorId}, team_name = #{teamName}, " +
            "team_size = #{teamSize}, description = #{description}, from_time = #{fromTime}, to_time = #{toTime}, " +
            "member_ids = #{memberIds} WHERE id = #{id}")
    void updateTeam(Team team);

    @Select({
            "<script>",
            "SELECT * FROM team",
            "WHERE 1=1",
            "<if test='gameId != null'>AND game_id = #{gameId}</if>",
            "<if test='creatorId != null'>AND creator_id = #{creatorId}</if>",
            "</script>"
    })
    List<Team> findByGameIdOrCreatorId(@Param("gameId") Long gameId, @Param("creatorId") Long creatorId);

    @Select("SELECT * FROM team WHERE team_name LIKE #{keyword}")
    List<Team> searchByTeamNameLike(@Param("keyword") String keyword);

}
