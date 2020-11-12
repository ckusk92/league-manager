package learn.mlb_gm.models;

public class Game {

    private int gameId;
    private int homeTeamId;
    private int awayTeamId;
    private int gameNumber;
    private int homeScore;
    private int awayScore;
    private boolean played;

    public Game() {

    }

    public Game(int homeTeamId, int awayTeamId, int gameNumber, int homeScore, int awayScore, boolean played) {
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
        this.gameNumber = gameNumber;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.played = played;
    }

    public Game(int gameId, int homeTeamId, int awayTeamId, int gameNumber, int homeScore, int awayScore, boolean played) {
        this.gameId = gameId;
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
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

    public int getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(int homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public int getAwayTeamId() {
        return awayTeamId;
    }

    public void setAwayTeamId(int awayTeamId) {
        this.awayTeamId = awayTeamId;
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
}
