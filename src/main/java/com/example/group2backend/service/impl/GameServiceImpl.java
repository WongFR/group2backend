import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameMapper gameMapper;

    @Override
    public Game getGameById(Integer id) {
        return gameMapper.selectById(id);
    }

    @Override
    public List<Game> getAllGames() {
        return gameMapper.selectAll();
    }

    @Override
    public void createGame(Game game) {
        gameMapper.insert(game);
    }

    @Override
    public void updateGame(Game game) {
        gameMapper.update(game);
    }

    @Override
    public void deleteGame(Integer id) {
        gameMapper.delete(id);
    }
}
