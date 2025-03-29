import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamMapper teamMapper;

    @Override
    public Team getTeamById(Integer id) {
        return teamMapper.selectById(id);
    }

    @Override
    public List<Team> getAllTeams() {
        return teamMapper.selectAll();
    }

    @Override
    public void createTeam(Team team) {
        teamMapper.insert(team);
    }

    @Override
    public void updateTeam(Team team) {
        teamMapper.update(team);
    }

    @Override
    public void deleteTeam(Integer id) {
        teamMapper.delete(id);
    }
}
