package com.intenthq.horseracing.model;

/**
 * Created by Alfréd on 27/06/2014.
 */
public class Lane implements Comparable<Lane> {
    private static final int FURLONG = 220;

    private final int laneNumber;
    private final String horseName;
    private int yards;

    private Lane(final int laneNumber, final String horseName) {
        this.laneNumber = laneNumber;
        this.horseName = horseName;
    }

    public static Lane laneWith(final int laneNumber, final String horseName) {
        return new Lane(laneNumber, horseName);
    }

    public void throwBall(final int yards) {
        this.yards += yards;
    }

    public boolean isFurlongLength() {
        return yards >= FURLONG;
    }

    @Override
    public int compareTo(final Lane lane) {
        return lane.yards < this.yards ? -1 : lane.yards == this.yards ? 0 : 1;
    }

    public int getLaneNumber() {
        return laneNumber;
    }

    public String getHorseName() {
        return horseName;
    }
}
