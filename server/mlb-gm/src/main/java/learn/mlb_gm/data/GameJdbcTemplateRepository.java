package learn.mlb_gm.data;

import learn.mlb_gm.data.mappers.GameMapper;
import learn.mlb_gm.models.Game;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class GameJdbcTemplateRepository implements GameRepository {

    private final JdbcTemplate jdbcTemplate;

    public GameJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Game> findAll() {
        final String sql = "select game_id, home_team_id, away_team_id, " +
                "game_number, home_score, away_score, played from game;";
        return jdbcTemplate.query(sql, new GameMapper());
    }

    @Override
    public List<Game> findAllForUserInOrderOfGame(int userId) {
        final String sql = "select game_id, home_team_id, away_team_id, game_number, home_score, away_score, played from " +
                "game g inner join user_team ut on g.home_team_id = ut.user_team_id or g.away_team_id = ut.user_team_id " +
                "where ut.app_user_id = ? and ut.user_controlled = 1 " +
                "order by g.game_number asc;";

        return jdbcTemplate.query(sql, new GameMapper(), userId);
    }


    @Override
    public Game findById(int gameId) {
        final String sql = "select game_id, home_team_id, away_team_id, game_number, " +
                "home_score, away_score, played from game where game_id = ?";
        return jdbcTemplate.query(sql, new GameMapper(), gameId).stream().findFirst().orElse(null);
    }

    @Override
    public Game add(Game game) {
        final String sql = "insert into game (home_team_id, away_team_id, game_number, " +
                "home_score, away_score, played) values (?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, game.getHomeTeamId());
            ps.setInt(2, game.getAwayTeamId());
            ps.setInt(3, game.getGameNumber());
            ps.setInt(4, game.getHomeScore());
            ps.setInt(5, game.getAwayScore());
            ps.setBoolean(6, game.isPlayed());
            return ps;
        }, keyHolder);
        if(rowsAffected <= 0) {
            return null;
        }
        game.setGameId(keyHolder.getKey().intValue());
        return game;
    }

    @Override
    public boolean update(Game game) {
        final String sql = "update game set home_team_id = ?, away_team_id = ?, game_number = ?, " +
                "home_score = ?, away_score = ?, played = ? where game_id = ?";
        return jdbcTemplate.update(sql, game.getHomeTeamId(), game.getAwayTeamId(), game.getGameNumber(),
                game.getHomeScore(), game.getAwayScore(), game.isPlayed(), game.getGameId()) > 0;
    }

    @Override
    public boolean deleteById(int gameId) {
        return jdbcTemplate.update("delete from game where game_id = ?", gameId) > 0;
    }
}
