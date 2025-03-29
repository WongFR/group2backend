import java.util.List;

public interface UserProfileMapper {
    UserProfile selectById(Integer id);
    List<UserProfile> selectAll();
    void insert(UserProfile userprofile);
    void update(UserProfile userprofile);
    void delete(Integer id);
}
