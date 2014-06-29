package com.intenthq.horseracing.model;

import org.springframework.util.Assert;

import java.util.*;

import static com.intenthq.horseracing.model.HorseRacingStatus.RACE;
import static com.intenthq.horseracing.model.Lane.laneWith;

/**
 * Created by Alfréd on 27/06/2014.
 */
public class HorseRacing {
    public static final int MAX_NUMBER_OF_HORSES = 7;

    private final List<Lane> result;

    public HorseRacing(final List<Lane> result) {
        this.result = result;
    }

    public List<Lane> getResult() {
        return result;
    }

    public static HorseRacing.Builder builder() {
        return new HorseRacing.Builder();
    }

    public static class Builder {
        private static final List<Integer> validYards = Arrays.asList(5, 10, 20, 40, 60);

        private final List<Lane> lanes = new ArrayList<>();

        private HorseRacingStatus horseRacingStatus = RACE;

        public HorseRacing build() {
            validateGameOver();
            Collections.sort(this.lanes);
            return new HorseRacing(Collections.unmodifiableList(this.lanes));
        }

        private void validateGameOver() {
            Assert.state(horseRacingStatus.isFinished(), String.format("Build not allowed in status %s", horseRacingStatus.name()));
        }

        public Builder addHorse(final String horseName) {
            validateHorsesCount();
            this.lanes.add(laneWith(laneNumber(), horseName));
            return this;
        }

        private void validateHorsesCount() {
            Assert.isTrue(lanes.size() < MAX_NUMBER_OF_HORSES, String.format("Invalid input: too many horses (maximum is %d)", MAX_NUMBER_OF_HORSES));
        }

        private int laneNumber() {
            return this.lanes.size() + 1;
        }

        public Builder throwBall(final int laneNumber, final int yards) {
            validateThrowBall(laneNumber, yards);
            Lane lane = this.lanes.get(laneNumber - 1);
            lane.throwBall(yards);
            checkGameOver(lane);
            return this;
        }

        private void validateThrowBall(int laneNumber, int yards) {
            Assert.state(horseRacingStatus.isRace(), String.format("Throw ball not allowed in status %s", horseRacingStatus.name()));
            Assert.isTrue(this.lanes.size() >= laneNumber, String.format("Invalid lane number %d (allowed range between 1 and %d)", laneNumber, lanes.size()));
            Assert.isTrue(validYards.contains(yards), String.format("Invalid yards value %d (allowed values %s)", yards, validYards.toString()));
        }

        private void checkGameOver(Lane lane) {
            if (lane.isFurlongLength()) {
                horseRacingStatus = HorseRacingStatus.FINISHED;
            }
        }

        public boolean isFinished() {
            return horseRacingStatus.isFinished();
        }
    }
}
