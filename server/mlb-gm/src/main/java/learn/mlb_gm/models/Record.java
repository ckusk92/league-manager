package learn.mlb_gm.models;

public class Record {

    private int userTeamId;
    private int win;
    private int loss;

    public Record(){

    }
    public Record(int userTeamId, int win, int loss) {
        this.userTeamId = userTeamId;
        this.win = win;
        this.loss = loss;
    }

    public int getUserTeamId() {
        return userTeamId;
    }

    public void setUserTeamId(int userTeamId) {
        this.userTeamId = userTeamId;
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
