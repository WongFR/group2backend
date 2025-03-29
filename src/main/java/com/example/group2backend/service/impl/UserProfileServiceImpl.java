import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserProfileMapper userprofileMapper;

    @Override
    public UserProfile getUserProfileById(Integer id) {
        return userprofileMapper.selectById(id);
    }

    @Override
    public List<UserProfile> getAllUserProfiles() {
        return userprofileMapper.selectAll();
    }

    @Override
    public void createUserProfile(UserProfile userprofile) {
        userprofileMapper.insert(userprofile);
    }

    @Override
    public void updateUserProfile(UserProfile userprofile) {
        userprofileMapper.update(userprofile);
    }

    @Override
    public void deleteUserProfile(Integer id) {
        userprofileMapper.delete(id);
    }
}
