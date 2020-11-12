package learn.mlb_gm.models;

public class UserTeam {

    private int userTeamId;
    private int userId;
    private int teamId;
    private boolean userControlled;
    private int rating;

    public UserTeam() {

    }

    public UserTeam(int userId, int teamId, boolean userControlled, int rating) {
        this.userId = userId;
        this.teamId = teamId;
        this.userControlled = userControlled;
        this.rating = rating;
    }

    public UserTeam(int userTeamId, int userId, int teamId, boolean userControlled, int rating) {
        this.userTeamId = userTeamId;
        this.userId = userId;
        this.teamId = teamId;
        this.userControlled = userControlled;
        this.rating = rating;
    }

    public int getUserTeamId() {
        return userTeamId;
    }

    public void setUserTeamId(int userTeamId) {
        this.userTeamId = userTeamId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public boolean isUserControlled() {
        return userControlled;
    }

    public void setUserControlled(boolean userControlled) {
        this.userControlled = userControlled;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
