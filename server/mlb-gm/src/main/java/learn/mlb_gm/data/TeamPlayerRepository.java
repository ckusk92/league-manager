package learn.mlb_gm.data;

import learn.mlb_gm.models.Player;
import learn.mlb_gm.models.TeamPlayer;

import java.util.List;

public interface TeamPlayerRepository {

    public List<TeamPlayer> findAll();

    public List<TeamPlayer> findAllForTeam(int userTeamId);

    public List<Player> findAllPlayersForTeam(int userTeamId);

//    public List<Player> findAllForTeam(int userTeamId);

    public TeamPlayer findById(int teamPlayerId);

    public TeamPlayer add(TeamPlayer teamPlayer);

    public boolean update(TeamPlayer teamPlayer);

    public boolean deleteById(int teamPlayerId);
}
