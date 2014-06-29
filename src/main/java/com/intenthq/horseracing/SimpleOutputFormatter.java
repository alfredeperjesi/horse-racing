package com.intenthq.horseracing;

import com.intenthq.horseracing.model.HorseRacing;
import com.intenthq.horseracing.model.Lane;
import org.springframework.stereotype.Component;

/**
 * Created by Alfréd on 28/06/2014.
 */
@Component
public class SimpleOutputFormatter implements OutputFormatter {

    public static final String HEADER = "Position, Lane, Horse name";

    @Override
    public String formatOutput(final HorseRacing horseRacing) {
        StringBuilder result = new StringBuilder(HEADER);
        int position = 1;
        for (Lane lane : horseRacing.getResult()) {
            result.append(String.format("\n%d, %d, %s", position++, lane.getLaneNumber(), lane.getHorseName()));
        }
        return result.toString();
    }
}
