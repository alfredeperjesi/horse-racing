package com.intenthq.horseracing;

import com.intenthq.horseracing.model.HorseRacing;
import com.intenthq.horseracing.model.Lane;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static com.intenthq.horseracing.Fixtures.*;
import static com.intenthq.horseracing.model.Lane.laneWith;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by Alfréd on 28/06/2014.
 */
@RunWith(MockitoJUnitRunner.class)
public class SimpleOutputFormatterTest {
    private static final List<Lane> LANES = Arrays.asList(lane(1, STAR, 220), lane(3, CHEYENNE, 25), lane(4, MISTY, 15), lane(5, SPIRIT, 10), lane(2, DAKOTA, 5));

    @Mock
    private HorseRacing horseRacing;
    private OutputFormatter outputFormatter;

    @Before
    public void setUp() {
        outputFormatter = new SimpleOutputFormatter();
        when(horseRacing.getResult()).thenReturn(LANES);
    }

    @Test
    public void formatOutputShouldFormatProperOutput() {
        String output = outputFormatter.formatOutput(horseRacing);

        assertThat(output, equalTo(SAMPLE_OUTPUT));
    }

    private static Lane lane(final int laneNumber, final String horseName, final int yards) {
        Lane lane = laneWith(laneNumber, horseName);
        lane.throwBall(yards);
        return lane;
    }
}
