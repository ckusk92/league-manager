package learn.mlb_gm.data.mappers;

import learn.mlb_gm.models.Record;
import learn.mlb_gm.models.RecordWithTeam;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RecordWithTeamMapper implements RowMapper<RecordWithTeam> {

    @Override
    public RecordWithTeam mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        RecordWithTeam record = new RecordWithTeam();
        record.setTeamName(resultSet.getString("name"));
        record.setWin(resultSet.getInt("win"));
        record.setLoss(resultSet.getInt("loss"));
        return record;
    }
}
