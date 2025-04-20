package com.example.group2backend.database.mapper;

import com.example.group2backend.database.entity.Team;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType; 

import java.util.List;

@Mapper
public interface TeamMapper {

     @Insert("INSERT INTO team (game_id, creator_id, team_name, team_size, description, from_time, to_time, created_at, member_ids) " +
            "VALUES (#{gameId}, #{creatorId}, #{teamName}, #{teamSize}, #{description}, #{fromTime}, #{toTime}, #{createdAt}, #{memberIds})")
    void insertTeam(Team team);

    @Results(id = "TeamResultMap", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "gameId", column = "game_id"),
            @Result(property = "creatorId", column = "creator_id"),
            @Result(property = "teamName", column = "team_name"),
            @Result(property = "teamSize", column = "team_size"),
            @Result(property = "description", column = "description"),
            @Result(property = "fromTime", column = "from_time"),
            @Result(property = "toTime", column = "to_time"),
            @Result(property = "createdAt", column = "created_at"),

            @Result(property = "memberIds", column = "member_ids", jdbcType = JdbcType.VARCHAR) 
    })
    @Select("SELECT * FROM team WHERE id = #{id}")
    Team findById(Long id);

    @Update("UPDATE team SET game_id = #{gameId}, creator_id = #{creatorId}, team_name = #{teamName}, " +
            "team_size = #{teamSize}, description = #{description}, from_time = #{fromTime}, to_time = #{toTime}, " +
            "member_ids = #{memberIds} WHERE id = #{id}")
    void updateTeam(Team team);

    @ResultMap("TeamResultMap") 
    @Select({
            "<script>",
            "SELECT * FROM team",
            "WHERE 1=1",
            "<if test='gameId != null'>AND game_id = #{gameId}</if>",
            "<if test='creatorId != null'>AND creator_id = #{creatorId}</if>",
            "</script>"
    })
    List<Team> findByGameIdOrCreatorId(@Param("gameId") Long gameId, @Param("creatorId") Long creatorId);

    @ResultMap("TeamResultMap") 
    @Select("SELECT * FROM team WHERE team_name LIKE #{keyword}")
    List<Team> searchByTeamNameLike(@Param("keyword") String keyword);

}