package learn.mlb_gm.data.mappers;

import learn.mlb_gm.models.UserTeam;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserTeamMapper implements RowMapper<UserTeam> {

    @Override
    public UserTeam mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        UserTeam userTeam = new UserTeam();
        userTeam.setUserTeamId(resultSet.getInt("user_team_id"));
        userTeam.setUserId(resultSet.getInt("app_user_id"));
        userTeam.setTeamId(resultSet.getInt("team_id"));
        if(resultSet.getInt("user_controlled") == 0) {
            userTeam.setUserControlled(false);
        } else {
            userTeam.setUserControlled(true);
        }
        userTeam.setRating(resultSet.getInt("rating"));
        return userTeam;
    }

}
