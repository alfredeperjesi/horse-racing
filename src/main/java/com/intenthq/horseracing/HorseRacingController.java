package com.intenthq.horseracing;

import com.intenthq.horseracing.model.HorseRacing;
import com.intenthq.horseracing.model.Lane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HorseRacingController {

    public static final String EXERCISE = "exercise";
    public static final String HORSE_RACING = "horse-racing";
    public static final String INPUT_ATTRIBUTE_NAME = "input";
    public static final String OUTPUT_ATTRIBUTE_NAME = "output";

    private final InputProcessor inputProcessor;
    private final OutputFormatter outputFormatter;

    @Autowired
    public HorseRacingController(final InputProcessor inputProcessor, final OutputFormatter outputFormatter) {
        this.inputProcessor = inputProcessor;
        this.outputFormatter = outputFormatter;
    }

    @RequestMapping("/horse-racing")
    public String horseRacing() {
        return HORSE_RACING;
    }

    @RequestMapping("/horse-racing/exercise")
    public String exercise(@RequestParam(value=INPUT_ATTRIBUTE_NAME, required=false) final String input, final ModelMap model) {
		if (!StringUtils.isEmpty(input)) {
            model.addAttribute(INPUT_ATTRIBUTE_NAME, input);
            HorseRacing horseRacing = inputProcessor.processInput(input);
            model.addAttribute(OUTPUT_ATTRIBUTE_NAME, outputFormatter.formatOutput(horseRacing));
		}
        return EXERCISE;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(final HttpServletRequest request, final Exception e) {
        ModelAndView model = new ModelAndView(EXERCISE);
        model.addObject(INPUT_ATTRIBUTE_NAME, request.getParameter(INPUT_ATTRIBUTE_NAME));
        model.addObject(OUTPUT_ATTRIBUTE_NAME, e.getMessage());
        return model;
    }
}
