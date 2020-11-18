package learn.mlb_gm.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SeasonJdbcTemplateRepository implements SeasonRepository{

    private final JdbcTemplate jdbcTemplate;

    public SeasonJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void startNewLeague(int userId) {
        userId = 1;
        final String sql = "delete g from game g " +
                "inner join user_team ut on g.home_team_id = ut.user_team_id " +
                "where ut.app_user_id = ?; ";
        jdbcTemplate.update(sql, userId);

        final String sql2 = "delete r from record r " +
                "inner join user_team ut on r.user_team_id = ut.user_team_id " +
                "where ut.app_user_id = ?; ";
        jdbcTemplate.update(sql2, userId);

        final String sql3 = "delete tp from team_player tp " +
                "inner join user_team ut on tp.user_team_id = ut.user_team_id " +
                "where ut.app_user_id = ?; ";
        jdbcTemplate.update(sql3, userId);

        final String sql4 = "delete from user_team where app_user_id = ?;";
        jdbcTemplate.update(sql4, userId);
    }

    @Override
    public void startNewSeason(int userId) {
        userId = 1;
        final String sql = "delete g from game g " +
                "inner join user_team ut on g.home_team_id = ut.user_team_id " +
                "where ut.app_user_id = ?; ";
        jdbcTemplate.update(sql, userId);

        final String sql2 =  "delete r from record r " +
                "inner join user_team ut on r.user_team_id = ut.user_team_id " +
                "where ut.app_user_id = ?; ";
        jdbcTemplate.update(sql2, userId);
    }
}
