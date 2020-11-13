package learn.mlb_gm.data;

import learn.mlb_gm.data.mappers.RecordMapper;
import learn.mlb_gm.models.Record;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class RecordJdbcTemplateRepository implements RecordRepository {

    private final JdbcTemplate jdbcTemplate;

    public RecordJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    @Override
    public List<Record> findAll() {
        final String sql = "select user_team_id, win, loss from record";
        return jdbcTemplate.query(sql, new RecordMapper());
    }

    @Override
    public Record findForTeam(int userTeamId) {
        final String sql = "select user_team_id, win, loss from record where user_team_id = ?";
        return jdbcTemplate.query(sql, new RecordMapper(), userTeamId).stream().findFirst().orElse(null);
    }

    @Override
    public Record add(Record record) {
        final String sql = "insert into record (user_team_id, win, loss) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, record.getUserTeamId());
            ps.setInt(2, record.getWin());
            ps.setInt(3, record.getLoss());
            return ps;
        }, keyHolder);
        if(rowsAffected <= 0) {
            return null;
        }
        //record.setUserTeamId(keyHolder.getKey().intValue());
        return record;
    }

    @Override
    public boolean update(Record record) {
        final String sql = "update record set win = ?, loss = ? where user_team_id = ?";
        return jdbcTemplate.update(sql, record.getWin(), record.getLoss(), record.getUserTeamId()) > 0;
    }

    @Override
    public boolean deleteForTeam(int userTeamId) {
        return jdbcTemplate.update("delete from record where user_team_id = ?", userTeamId) > 0;
    }
}
