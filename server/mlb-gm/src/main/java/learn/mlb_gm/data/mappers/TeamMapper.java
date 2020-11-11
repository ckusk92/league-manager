package learn.mlb_gm.data.mappers;

import learn.mlb_gm.models.Team;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamMapper implements RowMapper<Team> {

    @Override
    public Team mapRow(ResultSet resultSet, int i) throws SQLException {

        Team team = new Team();
        team.setTeamId(resultSet.getInt("team_id"));
        team.setName(resultSet.getString("name"));
        return team;
    }


}
