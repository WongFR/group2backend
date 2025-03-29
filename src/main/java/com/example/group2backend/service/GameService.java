import java.util.List;

public interface GameService {
    Game getGameById(Integer id);
    List<Game> getAllGames();
    void createGame(Game game);
    void updateGame(Game game);
    void deleteGame(Integer id);
}
