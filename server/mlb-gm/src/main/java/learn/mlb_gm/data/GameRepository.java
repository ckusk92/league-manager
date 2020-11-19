package learn.mlb_gm.data;

import learn.mlb_gm.models.Game;

import java.util.List;

public interface GameRepository {

    public List<Game> findAll();

    public List<Game> findAllForUserInOrderOfGame(int userId, int userTeamId);

   // public List<GameWithTeam> findAllForUserInOrderOfGameWithTeam(int userId);

    public Game findById(int gameId);

    public Game add(Game game);

    public boolean update(Game game);

    public boolean deleteById(int gameId);
}
