package learn.mlb_gm.data;

import learn.mlb_gm.models.UserTeam;

import java.util.List;

public interface UserTeamRepository {

    public List<UserTeam> findAll();

    public List<UserTeam> findAllByUser(int userId);

    public UserTeam findById(int userTeamId);

    public UserTeam add(UserTeam userTeam);

    public boolean update(UserTeam userTeam);

    public boolean deleteById(int userTeamId);
}
