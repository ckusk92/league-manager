package learn.mlb_gm.data;

import learn.mlb_gm.data.mappers.UserTeamMapper;
import learn.mlb_gm.models.UserTeam;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserTeamJdbcTemplateRepository implements UserTeamRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserTeamJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<UserTeam> findAll() {
        final String sql = "select user_team_id, app_user_id, team_id, user_controlled, rating from user_team";
        return jdbcTemplate.query(sql, new UserTeamMapper());
    }

    @Override
    public List<UserTeam> findAllByUser(int userId) {
        final String sql = "select user_team_id, app_user_id, team_id, user_controlled, rating from user_team where app_user_id = ?";
        return jdbcTemplate.query(sql, new UserTeamMapper(), userId);
    }

    @Override
    public UserTeam findById(int userTeamId) {
        final String sql = "select user_team_id, app_user_id, team_id, user_controlled, rating from user_team where user_team_id = ?";
        return jdbcTemplate.query(sql, new UserTeamMapper(), userTeamId).stream().findFirst().orElse(null);
    }


    @Override
    public UserTeam add(UserTeam userTeam) {
        final String sql = "insert into user_team (app_user_id, team_id, user_controlled, rating) values (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, userTeam.getUserId());
            ps.setInt(2, userTeam.getTeamId());
            ps.setBoolean(3, userTeam.isUserControlled());
            ps.setInt(4, userTeam.getRating());
            return ps;
        }, keyHolder);
        if(rowsAffected <= 0) {
            return null;
        }
        userTeam.setUserTeamId(keyHolder.getKey().intValue());
        return userTeam;
    }

    @Override
    public boolean update(UserTeam userTeam) {
        final String sql = "update user_team set app_user_id = ?, team_id = ?, user_controlled = ?, rating = ? where user_team_id = ?";
        return jdbcTemplate.update(sql, userTeam.getUserId(), userTeam.getTeamId(),
                userTeam.isUserControlled(), userTeam.getRating(), userTeam.getUserTeamId()) > 0;
    }

    @Override
    public boolean deleteById(int userTeamId) {
        return jdbcTemplate.update("delete from user_team where user_team_id = ?", userTeamId) > 0;
    }
}
