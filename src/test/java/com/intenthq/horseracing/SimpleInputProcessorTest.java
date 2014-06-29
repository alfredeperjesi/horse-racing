package com.intenthq.horseracing;

import com.intenthq.horseracing.model.HorseRacing;
import com.intenthq.horseracing.model.Lane;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

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
public class SimpleInputProcessorTest {
    private InputProcessor inputProcessor;

    @Before
    public void setUp() {
        inputProcessor = new SimpleInputProcessor();
    }

    @Test
    public void processInputWithOnlyTheHorseNamesLineShouldThrowIllegalArgumentException() {
        catchException(inputProcessor).processInput(HORSE_NAMES);

        assertThat(caughtException(), allOf(
                is(IllegalArgumentException.class),
                hasMessage("Invalid input: not enough lines"),
                hasNoCause()
        ));
    }

    @Test
    public void processInputWithMissingHorseNamesShouldThrowIllegalArgumentException() {
        catchException(inputProcessor).processInput(BALL_THROWS);

        assertThat(caughtException(), allOf(
                is(IllegalArgumentException.class),
                hasMessage("Invalid input: only one horse name or horse names are missing"),
                hasNoCause()
        ));
    }

    @Test
    public void processInputWithInvalidBallThrowLineShouldThrowIllegalArgumentException() {
        catchException(inputProcessor).processInput(String.format("%s\n%s", HORSE_NAMES, INVALID_BALL_THROW));

        assertThat(caughtException(), allOf(
                is(IllegalArgumentException.class),
                hasMessage("Invalid input: invalid ball throw 60 in line 2 (format LANE_NUMBER YARDS)"),
                hasNoCause()
        ));
    }

    @Test
    public void processInputWithNotNumberBallThrowLineShouldThrowIllegalArgumentException() {
        catchException(inputProcessor).processInput(String.format("%s\n%s", HORSE_NAMES, INVALID_NOT_NUMBER_BALL_THROW));

        assertThat(caughtException(), allOf(
                is(IllegalArgumentException.class),
                hasMessage("Invalid input: not a number 6o in line 2"),
                hasNoCause()
        ));
    }

    @Test
    public void processInputWithValidInputShouldReturnProperHorseRacing() {
        HorseRacing horseRacing = inputProcessor.processInput(VALID_INPUT);

        List<Lane> result = horseRacing.getResult();
        assertThat(result.get(0).getHorseName(), equalTo(STAR));
        assertThat(result.get(1).getHorseName(), equalTo(CHEYENNE));
        assertThat(result.get(2).getHorseName(), equalTo(MISTY));
        assertThat(result.get(3).getHorseName(), equalTo(SPIRIT));
        assertThat(result.get(4).getHorseName(), equalTo(DAKOTA));
    }
}
