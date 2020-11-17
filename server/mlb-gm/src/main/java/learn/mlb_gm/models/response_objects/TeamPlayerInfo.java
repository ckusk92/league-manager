package learn.mlb_gm.models.response_objects;

import learn.mlb_gm.models.Position;

public class TeamPlayerInfo {

    private String teamName;
    private String firstName;
    private String lastName;
    private Position position;
    private int rating;

    public TeamPlayerInfo() {

    }

    public TeamPlayerInfo(String teamName, String firstName, String lastName, Position position, int rating) {
        this.teamName = teamName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.rating = rating;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}

