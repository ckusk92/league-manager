package learn.mlb_gm.models;

public class InitInfo {

    private int numberOfTeams;
    private int numberOfGames;
    private int userTeamChoiceId;

    public int getNumberOfTeams() {
        return numberOfTeams;
    }

    public void setNumberOfTeams(int numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public void setNumberOfGames(int numberOfGames) {
        this.numberOfGames = numberOfGames;
    }

    public int getUserTeamChoiceId() {
        return userTeamChoiceId;
    }

    public void setUserTeamChoiceId(int userTeamChoiceId) {
        this.userTeamChoiceId = userTeamChoiceId;
    }
}
