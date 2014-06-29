package com.intenthq.horseracing;

import com.intenthq.horseracing.model.HorseRacing;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static com.intenthq.horseracing.Fixtures.SAMPLE_OUTPUT;
import static com.intenthq.horseracing.Fixtures.VALID_INPUT;
import static com.intenthq.horseracing.HorseRacingController.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HorseRacingControllerTest {
    public static final String MESSAGE = "Error";

    private ModelMap model = new ModelMap();

    @Mock
    private InputProcessor inputProcessor;

    @Mock
    private HorseRacing horseRacing;

    @Mock
    private OutputFormatter outputFormatter;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private Exception exception;

    private HorseRacingController horseRacingController;

    @Before
    public void setUp() throws Exception {
        horseRacingController = new HorseRacingController(inputProcessor, outputFormatter);
        when(inputProcessor.processInput(VALID_INPUT)).thenReturn(horseRacing);
        when(outputFormatter.formatOutput(horseRacing)).thenReturn(SAMPLE_OUTPUT);
    }

    @Test
    public void horseRacingShouldReturnTheViewContainingTheWordingOfTheExercise() throws Exception {
        final String actual = horseRacingController.horseRacing();
        assertThat(actual, is(HORSE_RACING));
    }

    @Test
    public void exerciseShouldReturnTheExerciseView() throws Exception {
        final String actual = horseRacingController.exercise(VALID_INPUT, model);
        assertThat(actual, is(EXERCISE));
    }

    @Test
    public void exerciseWithNoInputShouldNotReturnOutput() throws Exception {
        horseRacingController.exercise(null, model);
        assertThat(model.containsAttribute(OUTPUT_ATTRIBUTE_NAME), is(false));
    }

    @Test
    public void exerciseWithInputShouldReturnSomeOutput() throws Exception {
        horseRacingController.exercise(VALID_INPUT, model);
        assertThat(model.containsAttribute(OUTPUT_ATTRIBUTE_NAME), is(true));
    }

    @Test
    public void exerciseWithInputShouldAddTheInputToTheModel() throws Exception {
        horseRacingController.exercise(VALID_INPUT, model);
        final String input = (String) model.get(INPUT_ATTRIBUTE_NAME);
        assertThat(input, is(VALID_INPUT));
    }

    @Test
    public void exerciseWithSampleInputShouldReturnSampleOutput() throws Exception {
        horseRacingController.exercise(VALID_INPUT, model);
        final String output = (String) model.get(OUTPUT_ATTRIBUTE_NAME);
        assertThat(output, is(SAMPLE_OUTPUT));
    }

    @Test
    public void handleExceptionShouldReturnModelMapWithInputAndExceptionMessage() throws Exception {
        when(httpServletRequest.getParameter(INPUT_ATTRIBUTE_NAME)).thenReturn(VALID_INPUT);
        when(exception.getMessage()).thenReturn(MESSAGE);

        ModelAndView result = horseRacingController.handleException(httpServletRequest, exception);

        final String input = (String) result.getModelMap().get(INPUT_ATTRIBUTE_NAME);
        final String output = (String) result.getModelMap().get(OUTPUT_ATTRIBUTE_NAME);
        assertThat(input, is(VALID_INPUT));
        assertThat(output, is(MESSAGE));
        assertThat(result.getViewName(), is(EXERCISE));
    }
}
