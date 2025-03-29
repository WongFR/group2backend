import java.util.List;

public interface UserMapper {
    User selectById(Integer id);
    List<User> selectAll();
    void insert(User user);
    void update(User user);
    void delete(Integer id);
}
