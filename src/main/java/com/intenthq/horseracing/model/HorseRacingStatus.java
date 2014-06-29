package com.intenthq.horseracing.model;

/**
 * Created by Alfréd on 27/06/2014.
 */
public enum HorseRacingStatus {
    RACE {
        @Override
        public boolean isRace() {
            return true;
        }

        @Override
        public boolean isFinished() {
            return false;
        }
    },
    FINISHED {
        @Override
        public boolean isRace() {
            return false;
        }

        @Override
        public boolean isFinished() {
            return true;
        }
    };

    public abstract boolean isRace();

    public abstract boolean isFinished();
}
