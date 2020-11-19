package learn.mlb_gm.models.response_objects;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class InitInfo {

    @Min(value = 4, message = "Must have atleast four teams")
    @Max(value = 16, message = "May only have sixteen teams at most")
    private int numberOfTeams;

    @Min(value = 1, message = "Must have atleast one game in season")
    @Max(value = 162, message = "May have up to 162 games in a season")
    private int numberOfGames;

    private int userTeamChoiceId;

    private int userId;

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
