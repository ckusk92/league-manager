package learn.mlb_gm.models;

public class TeamPlayer {

    private int teamPlayerId;
    private int userTeamId;
    private int playerId;
    private int rating;

    public TeamPlayer() {

    }

    public TeamPlayer(int userTeamId, int playerId) {
        this.userTeamId = userTeamId;
        this.playerId = playerId;
    }

    public TeamPlayer(int userTeamId, int playerId, int rating) {
        this.userTeamId = userTeamId;
        this.playerId = playerId;
        this.rating = rating;
    }

    public TeamPlayer(int teamPlayerId, int userTeamId, int playerId, int rating) {
        this.teamPlayerId = teamPlayerId;
        this.userTeamId = userTeamId;
        this.playerId = playerId;
        this.rating = rating;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
