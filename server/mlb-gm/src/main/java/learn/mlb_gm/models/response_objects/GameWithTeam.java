package learn.mlb_gm.models.response_objects;

public class GameWithTeam {

    private int gameId;
    private String homeTeamName;
    private String awayTeamName;
    private int gameNumber;
    private int homeScore;
    private int awayScore;
    private boolean played;
    private String result;

    public GameWithTeam() {

    }

    public GameWithTeam(String homeTeamName, String awayTeamName, int gameNumber, int homeScore, int awayScore, boolean played) {
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.gameNumber = gameNumber;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.played = played;
    }

    public GameWithTeam(int gameId, String homeTeamName, String awayTeamName, int gameNumber, int homeScore, int awayScore, boolean played) {
        this.gameId = gameId;
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.gameNumber = gameNumber;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.played = played;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public int getGameNumber() {
        return gameNumber;
    }

    public void setGameNumber(int gameNumber) {
        this.gameNumber = gameNumber;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
