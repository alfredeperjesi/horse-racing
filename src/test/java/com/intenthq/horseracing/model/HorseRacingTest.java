package com.intenthq.horseracing.model;

import org.junit.Before;
import org.junit.Test;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static com.googlecode.catchexception.apis.CatchExceptionHamcrestMatchers.hasMessage;
import static com.googlecode.catchexception.apis.CatchExceptionHamcrestMatchers.hasNoCause;
import static com.intenthq.horseracing.Fixtures.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by Alfréd on 28/06/2014.
 */
public class HorseRacingTest {
    private HorseRacing.Builder horseRacing;

    @Before
    public void setUp() {
        horseRacing = HorseRacing.builder();
        horseRacing.addHorse(STAR);
        horseRacing.addHorse(CHEYENNE);
        horseRacing.addHorse(MISTY);
        horseRacing.addHorse(SPIRIT);
        horseRacing.addHorse(DAKOTA);
        horseRacing.addHorse(ARISTIDES);
        horseRacing.addHorse(VAGRANT);
    }

    @Test
    public void addHorseWithTooManyHorseNamesShouldThrowIllegalArgumentException() {
        catchException(horseRacing).addHorse(FONSO);

        assertThat(caughtException(), allOf(
                is(IllegalArgumentException.class),
                hasMessage("Invalid input: too many horses (maximum is 7)"),
                hasNoCause()
        ));
    }

    @Test
    public void throwBallWithFinishedGameShouldThrowIllegalStateException() {
        horseRacing.throwBall(LANE_NUMBER_1, YARDS_60);
        horseRacing.throwBall(LANE_NUMBER_1, YARDS_60);
        horseRacing.throwBall(LANE_NUMBER_1, YARDS_60);
        horseRacing.throwBall(LANE_NUMBER_1, YARDS_40);

        catchException(horseRacing).throwBall(LANE_NUMBER_1, YARDS_40);

        assertThat(caughtException(), allOf(
                is(IllegalStateException.class),
                hasMessage("Throw ball not allowed in status FINISHED"),
                hasNoCause()
        ));
    }

    @Test
    public void throwBallWithFinishedGameShouldChangeStatusToFinished() {
        horseRacing.throwBall(LANE_NUMBER_1, YARDS_60);
        horseRacing.throwBall(LANE_NUMBER_1, YARDS_60);
        horseRacing.throwBall(LANE_NUMBER_1, YARDS_60);
        horseRacing.throwBall(LANE_NUMBER_1, YARDS_40);

        assertThat(horseRacing.isFinished(), equalTo(true));
    }

    @Test
    public void throwBallWithInvalidLaneNumberShouldThrowIllegalArgumentException() {
        catchException(horseRacing).throwBall(INVALID_LANE_NUMBER, YARDS_40);

        assertThat(caughtException(), allOf(
                is(IllegalArgumentException.class),
                hasMessage("Invalid lane number 8 (allowed range between 1 and 7)"),
                hasNoCause()
        ));
    }

    @Test
    public void throwBallWithInvalidYardsShouldThrowIllegalArgumentException() {
        catchException(horseRacing).throwBall(LANE_NUMBER_1, YARDS_INVALID);

        assertThat(caughtException(), allOf(
                is(IllegalArgumentException.class),
                hasMessage("Invalid yards value 1 (allowed values [5, 10, 20, 40, 60])"),
                hasNoCause()
        ));
    }

    @Test
    public void getResultsWithInvalidYardsShouldThrowIllegalArgumentException() {
        catchException(horseRacing).throwBall(LANE_NUMBER_1, YARDS_INVALID);

        assertThat(caughtException(), allOf(
                is(IllegalArgumentException.class),
                hasMessage("Invalid yards value 1 (allowed values [5, 10, 20, 40, 60])"),
                hasNoCause()
        ));
    }

    @Test
    public void buildWithInProgressGameShouldThrowIllegalStateException() {

        catchException(horseRacing).build();

        assertThat(caughtException(), allOf(
                is(IllegalStateException.class),
                hasMessage("Build not allowed in status RACE"),
                hasNoCause()
        ));
    }


}
