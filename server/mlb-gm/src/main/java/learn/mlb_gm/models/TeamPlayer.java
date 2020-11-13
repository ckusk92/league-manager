package learn.mlb_gm.models;

public class TeamPlayer {

    private int teamPlayerId;
    private int userTeamId;
    private int playerId;

    public TeamPlayer() {

    }

    public TeamPlayer(int userTeamId, int playerId) {
        this.userTeamId = userTeamId;
        this.playerId = playerId;
    }

    public TeamPlayer(int teamPlayerId, int userTeamId, int playerId) {
        this.teamPlayerId = teamPlayerId;
        this.userTeamId = userTeamId;
        this.playerId = playerId;
    }

    public int getTeamPlayerId() {
        return teamPlayerId;
    }

    public void setTeamPlayerId(int teamPlayerId) {
        this.teamPlayerId = teamPlayerId;
    }

    public int getUserTeamId() {
        return userTeamId;
    }

    public void setUserTeamId(int userTeamId) {
        this.userTeamId = userTeamId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
