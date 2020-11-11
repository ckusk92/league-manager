package learn.mlb_gm.data;

import learn.mlb_gm.models.Player;

import java.util.List;

public interface PlayerRepository {

    public List<Player> findAll();

    public Player findById(int playerId);

    public Player add(Player player);

    public boolean update(Player player);

    public boolean deleteById(int playerId);
}
