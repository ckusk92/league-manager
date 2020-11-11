package learn.mlb_gm.models;

public class Player {

    private int playerId;
    private String firstName;
    private String lastName;
    private Position position;
    private int rating;

    public Player() {

    }

    public Player(String firstName, String lastName, Position position, int rating) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.rating = rating;
    }

    public Player(int playerId, String firstName, String lastName, Position position, int rating) {
        this.playerId = playerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.rating = rating;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
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
