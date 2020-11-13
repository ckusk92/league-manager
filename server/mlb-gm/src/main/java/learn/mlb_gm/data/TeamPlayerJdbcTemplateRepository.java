package learn.mlb_gm.data;

import learn.mlb_gm.data.mappers.TeamPlayerMapper;
import learn.mlb_gm.models.Team;
import learn.mlb_gm.models.TeamPlayer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TeamPlayerJdbcTemplateRepository implements TeamPlayerRepository {

    private final JdbcTemplate jdbcTemplate;

    public TeamPlayerJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    @Override
    public List<TeamPlayer> findAll() {
        final String sql = "select team_player_id, user_team_id, player_id from team_player;";
        return jdbcTemplate.query(sql, new TeamPlayerMapper());
    }

    @Override
    public List<TeamPlayer> findAllForTeam(int userTeamId) {
        final String sql = "select team_player_id, user_team_id, player_id from team_player where user_team_id = ?";
        return jdbcTemplate.query(sql, new TeamPlayerMapper(), userTeamId);
    }

    @Override
    public TeamPlayer findById(int teamPlayerId) {
        final String sql = "select team_player_id, user_team_id, player_id from team_player where team_player_id = ?";
        return jdbcTemplate.query(sql, new TeamPlayerMapper(), teamPlayerId).stream().findFirst().orElse(null);
    }

    @Override
    public TeamPlayer add(TeamPlayer teamPlayer) {
        final String sql = "insert into team_player (team_player_id, user_team_id, player_id) values (?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, teamPlayer.getTeamPlayerId());
            ps.setInt(2, teamPlayer.getUserTeamId());
            ps.setInt(3, teamPlayer.getPlayerId());
            return ps;
        }, keyHolder);
        if(rowsAffected <= 0) {
            return null;
        }
        teamPlayer.setTeamPlayerId(keyHolder.getKey().intValue());
        return teamPlayer;
    }

    @Override
    public boolean update(TeamPlayer teamPlayer) {
        final String sql = "update team_player set user_team_id = ?, player_id = ? where team_player_id = ?";
        return jdbcTemplate.update(sql, teamPlayer.getUserTeamId(), teamPlayer.getPlayerId(), teamPlayer.getTeamPlayerId()) > 0;
    }

    @Override
    public boolean deleteById(int teamPlayerId) {
        return jdbcTemplate.update("delete from team_player where team_player_id = ?", teamPlayerId) > 0;
    }
}
