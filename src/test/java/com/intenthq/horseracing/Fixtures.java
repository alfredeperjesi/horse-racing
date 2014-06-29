package com.intenthq.horseracing;

/**
 * Created by Alfréd on 28/06/2014.
 */
public interface Fixtures {
    public static final String NEW_LINE = "\n";
    public static final String COMMA = ", ";
    public static final String STAR = "Star";
    public static final String CHEYENNE = "Cheyenne";
    public static final String MISTY = "Misty";
    public static final String SPIRIT = "Spirit";
    public static final String DAKOTA = "Dakota";
    public static final String HORSE_NAMES = STAR + COMMA + DAKOTA + COMMA + CHEYENNE + COMMA + MISTY + COMMA + SPIRIT + NEW_LINE;
    public static final String BALL_THROWS = "1 60" + NEW_LINE +
            "3 5" + NEW_LINE +
            "1 60" + NEW_LINE +
            "4 5" + NEW_LINE +
            "4 10" + NEW_LINE +
            "2 5" + NEW_LINE +
            "5 10" + NEW_LINE +
            "1 60" + NEW_LINE +
            "3 20" + NEW_LINE +
            "1 40" + NEW_LINE +
            "2 60";
    public static final String VALID_INPUT = HORSE_NAMES + BALL_THROWS;
    public static final String ARISTIDES = "Aristides";
    public static final String VAGRANT = "Vagrant";
    public static final String FONSO = "Fonso";
    public static final String INVALID_BALL_THROW = "60";
    public static final String INVALID_NOT_NUMBER_BALL_THROW = "2 6o";
    public static final int LANE_NUMBER_1 = 1;
    public static final int YARDS_60 = 60;
    public static final int YARDS_40 = 40;
    public static final int INVALID_LANE_NUMBER = 8;
    public static final int YARDS_INVALID = 1;
    public static final String SAMPLE_OUTPUT = "Position, Lane, Horse name\n"+
            "1, 1, Star\n"+
            "2, 3, Cheyenne\n"+
            "3, 4, Misty\n"+
            "4, 5, Spirit\n"+
            "5, 2, Dakota";

}
