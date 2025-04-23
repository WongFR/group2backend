package com.example.group2backend.database.service;

import com.example.group2backend.database.entity.JoinTeam;
import com.example.group2backend.database.mapper.JoinTeamMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JoinTeamService {

    @Autowired
    private JoinTeamMapper joinTeamMapper;

    public void insert(JoinTeam joinTeam){
        joinTeamMapper.insert(joinTeam);
    }

    public List<JoinTeam> getRequestsByHostAndStatus(Long hostId,String status){
       return joinTeamMapper.getRequestsByHostAndStatus(hostId,status);
    }

    public void approveRequest(Long id){
        joinTeamMapper.approveRequest(id);
    }

    public void rejectRequest(Long id){
        joinTeamMapper.rejectRequest(id);
    }

    public JoinTeam findById(Long id){
        return joinTeamMapper.findById(id);
    }
}
