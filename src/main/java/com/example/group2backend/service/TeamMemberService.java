import java.util.List;

public interface TeamMemberService {
    TeamMember getTeamMemberById(Integer id);
    List<TeamMember> getAllTeamMembers();
    void createTeamMember(TeamMember teammember);
    void updateTeamMember(TeamMember teammember);
    void deleteTeamMember(Integer id);
}
