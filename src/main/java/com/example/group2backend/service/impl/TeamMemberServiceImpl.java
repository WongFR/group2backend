import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TeamMemberServiceImpl implements TeamMemberService {

    @Autowired
    private TeamMemberMapper teammemberMapper;

    @Override
    public TeamMember getTeamMemberById(Integer id) {
        return teammemberMapper.selectById(id);
    }

    @Override
    public List<TeamMember> getAllTeamMembers() {
        return teammemberMapper.selectAll();
    }

    @Override
    public void createTeamMember(TeamMember teammember) {
        teammemberMapper.insert(teammember);
    }

    @Override
    public void updateTeamMember(TeamMember teammember) {
        teammemberMapper.update(teammember);
    }

    @Override
    public void deleteTeamMember(Integer id) {
        teammemberMapper.delete(id);
    }
}
