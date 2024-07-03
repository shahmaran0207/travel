package com.travel.travel.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/Order")
public class OrderController {

    @GetMapping("/Cart")
    public ModelAndView Cart(){
        ModelAndView mav = new ModelAndView();

        mav.setViewName("/Order/Cart");
        return mav;
    }
}