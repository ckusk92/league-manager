package learn.mlb_gm.data.mappers;

import learn.mlb_gm.models.TeamPlayer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamPlayerMapper implements RowMapper<TeamPlayer> {

    @Override
    public TeamPlayer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        TeamPlayer teamPlayer = new TeamPlayer();
        teamPlayer.setTeamPlayerId(resultSet.getInt("team_player_id"));
        teamPlayer.setUserTeamId(resultSet.getInt("user_team_id"));
        teamPlayer.setPlayerId(resultSet.getInt("player_id"));
        return teamPlayer;
    }

}
