package learn.mlb_gm.data;

import learn.mlb_gm.models.Team;

import java.util.List;

public interface TeamRepository {

    List<Team> findAll();

    Team findById(int teamId);

    Team add(Team team);

    boolean update(Team team);

    boolean deleteById(int teamId);

}
