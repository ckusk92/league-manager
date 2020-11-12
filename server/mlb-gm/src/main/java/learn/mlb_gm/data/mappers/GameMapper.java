package learn.mlb_gm.data.mappers;

import learn.mlb_gm.models.Game;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameMapper implements RowMapper<Game> {

    @Override
    public Game mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Game game = new Game();
        game.setGameId(resultSet.getInt("game_id"));
        game.setHomeTeamId(resultSet.getInt("home_team_id"));
        game.setAwayTeamId(resultSet.getInt("away_team_id"));
        game.setGameNumber(resultSet.getInt("game_number"));
        game.setHomeScore(resultSet.getInt("home_score"));
        game.setAwayScore(resultSet.getInt("away_score"));
        game.setPlayed(resultSet.getBoolean("played"));
        return game;
    }
}
