package com.travel.travel.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller()
@RequestMapping("/Predict")
public class PredictController {

    @GetMapping("/Travel")
    public ModelAndView Travel(){
        ModelAndView mav=new ModelAndView();

        mav.setViewName("Predict/Travel");
        return mav;
    }

    @GetMapping("/Money/money")
    public ModelAndView money(){
        ModelAndView mav=new ModelAndView();

        mav.setViewName("Predict/Money/money");
        return mav;
    }

}