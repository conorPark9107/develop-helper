package com.albionhelper.helper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/calculator")
public class CalculatorController {

    @GetMapping("/refining")
    public String showRefining(){
        return "calculator/refining";
    }

    @GetMapping("/cooking")
    public String showCookingPage(){
        return "calculator/cooking";
    }



    @GetMapping("/potion")
    public String showPotionPage(){
        return "calculator/potion";
    }


}
