package com.albionhelper.helper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
@RequestMapping(value = "/market")
public class MarketController {



    @GetMapping("")
    public String market(){
        return "market";
    }

    @GetMapping("/price")
    public String showPrice(@RequestParam("type")String type){

        switch (type) {
            case "warrior" -> {

            }
            case "mage" -> {

            }
            case "hunter" -> {

            }
            case "plate" -> {

            }
            case "jacket" -> {

            }
            case "robe" -> {

            }
            case "cape" -> {

            }
            case "bag" -> {

            }
        }

        return "market";
    }




}
