package com.travel.travel.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/Mall")
public class MallController {

    @GetMapping("/Self-Defense/Defense_Item")
    public ModelAndView SelfDefenseItem() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("Mall/Self-Defense/Defense_Item");
        return mav;
    }

    @GetMapping("/Travel/Travel_Item")
    public ModelAndView Travel_Item() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("Mall/Travel/Travel_Item");
        return mav;
    }
}
