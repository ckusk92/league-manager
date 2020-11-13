package learn.mlb_gm.data.mappers;

import learn.mlb_gm.models.Record;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RecordMapper implements RowMapper<Record> {

    @Override
    public Record mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Record record = new Record();
        record.setUserTeamId(resultSet.getInt("user_team_id"));
        record.setWin(resultSet.getInt("win"));
        record.setLoss(resultSet.getInt("loss"));
        return record;
    }
}
