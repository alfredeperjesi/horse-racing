package com.intenthq.horseracing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static com.intenthq.horseracing.Fixtures.BALL_THROWS;
import static com.intenthq.horseracing.Fixtures.SAMPLE_OUTPUT;
import static com.intenthq.horseracing.Fixtures.VALID_INPUT;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by Alfréd on 29/06/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration
public class HorseRacingIntegrationTest {
    public static final String TEXT_HTML = "text/html";
    public static final String URL = "/horse-racing/exercise?input=";
    public static final String ERROR_OUTPUT = "Invalid input: only one horse name or horse names are missing";
    @Autowired
    private InputProcessor inputProcessor;

    @Autowired
    private OutputFormatter outputFormatter;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(new HorseRacingController(inputProcessor, outputFormatter))
                .setViewResolvers(viewResolver)
                .build();    }

    @Test
    public void exerciseWithValidInputShouldReturnTheInputAndOutputAsModelAttribute() throws Exception {
        this.mockMvc.perform(get(String.format("%s%s", URL, VALID_INPUT)).accept(TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(model().attribute(HorseRacingController.INPUT_ATTRIBUTE_NAME, equalTo(VALID_INPUT)))
                .andExpect(model().attribute(HorseRacingController.OUTPUT_ATTRIBUTE_NAME, equalTo(SAMPLE_OUTPUT)));
    }

    @Test
    public void exerciseWithInvalidInputShouldReturnTheInputAndTheErrorOutputAsModelAttribute() throws Exception {
        this.mockMvc.perform(get(String.format("%s%s", URL, BALL_THROWS)).accept(TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(model().attribute(HorseRacingController.INPUT_ATTRIBUTE_NAME, equalTo(BALL_THROWS)))
                .andExpect(model().attribute(HorseRacingController.OUTPUT_ATTRIBUTE_NAME, equalTo(ERROR_OUTPUT)));
    }

    @Configuration
    @ComponentScan("com.intenthq.horseracing")
    public static class HorseRacingTestConfig {

    }
}
