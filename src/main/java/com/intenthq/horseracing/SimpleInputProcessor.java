package com.intenthq.horseracing;

import com.intenthq.horseracing.model.HorseRacing;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Alfréd on 28/06/2014.
 */
@Component
public class SimpleInputProcessor implements InputProcessor {

    public static final int MIN_NUMBER_OF_HORSES = 2;
    public static final String NEW_LINE = "\n";

    @Override
    public HorseRacing processInput(final String input) {
        List<String> inputLines = Arrays.asList(StringUtils.tokenizeToStringArray(input, NEW_LINE));
        validateInputLines(inputLines);
        HorseRacing.Builder horseRacing = HorseRacing.builder();
        parseHorses(inputLines.get(0), horseRacing);
        parseBallThrows(inputLines.subList(1, inputLines.size()), horseRacing);
        return horseRacing.build();
    }

    private void validateInputLines(final List<String> inputLines) {
        Assert.isTrue(inputLines.size() > 1, "Invalid input: not enough lines");
    }

    private void parseHorses(final String horsesLine, final HorseRacing.Builder horseRacing) {
        String[] horses = StringUtils.tokenizeToStringArray(horsesLine, ",");
        validateHorsesCount(horses);
        for (String horse : horses) {
            horseRacing.addHorse(horse);
        }
    }

    private void validateHorsesCount(final String[] horses) {
        Assert.isTrue(horses.length >= MIN_NUMBER_OF_HORSES, "Invalid input: only one horse name or horse names are missing");
    }

    private void parseBallThrows(final List<String> ballThrowsLines, final HorseRacing.Builder horseRacing) {
        int lineNumber = 2;
        for (String ballThrowsLine : ballThrowsLines) {
            String[] ballThrow = StringUtils.tokenizeToStringArray(ballThrowsLine, " ");
            validateBallThrow(ballThrowsLine, ballThrow, lineNumber);
            horseRacing.throwBall(parseNumber(ballThrow[0], lineNumber), parseNumber(ballThrow[1], lineNumber));
            if (horseRacing.isFinished()) {
                break;
            }
            lineNumber++;
        }
    }

    private void validateBallThrow(final String ballThrowsLine, final String[] ballThrow, final int lineNumber) {
        if(ballThrow.length != 2) {
            throw new IllegalArgumentException(String.format("Invalid input: invalid ball throw %s in line %d (format LANE_NUMBER YARDS)", ballThrowsLine, lineNumber));
        }
    }

    private int parseNumber(final String number, final int lineNumber) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("Invalid input: not a number %s in line %d", number, lineNumber));
        }
    }
}
