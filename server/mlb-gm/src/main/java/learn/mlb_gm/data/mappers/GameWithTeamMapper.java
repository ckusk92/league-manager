package learn.mlb_gm.data.mappers;

import learn.mlb_gm.models.Game;
import learn.mlb_gm.models.GameWithTeam;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameWithTeamMapper implements RowMapper<GameWithTeam> {

    @Override
    public GameWithTeam mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        GameWithTeam game = new GameWithTeam();
        game.setGameId(resultSet.getInt("game_id"));
        game.setHomeTeamName(resultSet.getString("home_team_name"));
        game.setAwayTeamName(resultSet.getString("away_team_name"));
        game.setGameNumber(resultSet.getInt("game_number"));
        game.setHomeScore(resultSet.getInt("home_score"));
        game.setAwayScore(resultSet.getInt("away_score"));
        game.setPlayed(resultSet.getBoolean("played"));
        return game;
    }
}
