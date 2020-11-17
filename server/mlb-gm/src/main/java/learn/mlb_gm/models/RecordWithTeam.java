package learn.mlb_gm.models;

public class RecordWithTeam {

    private String teamName;
    private int win;
    private int loss;

    public RecordWithTeam(){

    }
    public RecordWithTeam(String teamName, int win, int loss) {
        this.teamName = teamName;
        this.win = win;
        this.loss = loss;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLoss() {
        return loss;
    }

    public void setLoss(int loss) {
        this.loss = loss;
    }
}
