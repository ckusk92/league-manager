package learn.mlb_gm.models;

public enum Position {
    PITCHER(1, "P"),
    CATCHER(2, "C"),
    FIRST_BASE(3, "1B"),
    SECOND_BASE(4, "2B"),
    THIRD_BASE(5, "3B"),
    SHORT_STOP(6, "SS"),
    LEFT_FIELD(7, "LF"),
    CENTER_FIELD(8, "CF"),
    RIGHT_FIELD(9, "RF");

    private int value;
    private String abbreviation;

    private Position(int value, String abbreviation) {
        this.value = value;
        this.abbreviation = abbreviation;
    }

    public int getValue() {
        return value;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

}
