package com.albionhelper.helper.controller;

import com.albionhelper.helper.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping(value = "/market")
public class MarketController {

    @Autowired
    MarketService marketService;

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
