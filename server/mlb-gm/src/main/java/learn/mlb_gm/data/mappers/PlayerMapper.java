package learn.mlb_gm.data.mappers;

import learn.mlb_gm.models.Player;
import learn.mlb_gm.models.Position;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerMapper implements RowMapper<Player> {

    @Override
    public Player mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Player player = new Player();
        player.setPlayerId(resultSet.getInt("player_id"));
        player.setFirstName(resultSet.getString("first_name"));
        player.setLastName(resultSet.getString("last_name"));
        // Offset by one for some reason?
        player.setPosition(Position.values()[(resultSet.getInt("position_id")) - 1]);
        player.setRating(resultSet.getInt("rating"));
        return player;
    }

}
