package learn.mlb_gm.data;

import learn.mlb_gm.data.mappers.TeamMapper;
import learn.mlb_gm.models.Team;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TeamJdbcTemplateRepository implements TeamRepository {

    private final JdbcTemplate jdbcTemplate;

    public TeamJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Team> findAll() {
        final String sql = "select team_id, name from team";
        return jdbcTemplate.query(sql, new TeamMapper());
    }

    @Override
    public Team findById(int teamId) {
        final String sql = "select team_id, name from team where team_id = ?;";

        return jdbcTemplate.query(sql, new TeamMapper(), teamId).stream().findFirst().orElse(null);
    }

    @Override
    public Team add(Team team) {
        final String sql = "insert into team (name) values(?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, team.getName());
            return ps;
        }, keyHolder);
        if(rowsAffected <= 0) {
            return null;
        }
        team.setTeamId(keyHolder.getKey().intValue());
        return team;
    }

    @Override
    public boolean update(Team team) {
        final String sql = "update team set name = ? where team_id = ?";

        return jdbcTemplate.update(sql, team.getName(), team.getTeamId()) > 0;
    }

    @Override
    public boolean deleteById(int teamId) {
        return jdbcTemplate.update("delete from team where team_id = ?;", teamId) > 0;
    }

}
