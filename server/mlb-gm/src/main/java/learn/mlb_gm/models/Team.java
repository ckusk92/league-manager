package learn.mlb_gm.models;

public class Team {

    private int teamId;
    private String name;

    public Team() {

    }

    public Team(int teamId, String name) {
        this.teamId = teamId;
        this.name = name;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
