package com.albionhelper.helper.controller;

import com.albionhelper.helper.service.BoardService;
import com.albionhelper.helper.service.GoldService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;


// 이미지 관련
// https://wiki.albiononline.com/wiki/API:Render_service
// https://render.albiononline.com/v1/item/T4_HEAD_LEATHER_FEY@1.png?quality=4


// 데이터 관련
// https://www.albion-online-data.com/#i-want-all-the-data-from-the-api

// api document
// https://openalbion.com/categories.html

// 참고
// https://albion-profit-calculator.com/

@Controller
public class MainController {

    private final String TEST_URL = "https://api.openalbion.com/api/v3/weapons?tier=4";
    private final String TEST_URL_MARKET_PREFIX = "https://east.albion-online-data.com";

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    GoldService goldService;

    @Autowired
    BoardService boardService;

    @GetMapping("/")
    public String index(Model model){
        return "home";
    }

    @GetMapping("/gold")
    public String getGoldPricies(Model model) throws JsonProcessingException {
        model.addAttribute("east", goldService.getGoldPriciesOnEast());
        model.addAttribute("west", goldService.getGoldPriciesOnWest());
        model.addAttribute("europe", goldService.getGoldPriciesOnEurope());
        return "gold/gold";
    }

    @GetMapping("/toinquire")
    public String showToInquirePage(Model model){
        model.addAttribute("list", boardService.findAllInquire());
        return "toinquire";
    }

    @PostMapping("/inquire/register")
    @ResponseBody
    public String registerInquire(@RequestParam("text")String text){
        return boardService.registerInquire(text);
    }

    @GetMapping("/info")
    public String showInfoPage(){
        return "info";
    }


    @ResponseBody
    @GetMapping("/test")
    public String testHttpApi(){
        return "Hi! This page made by conorpark, it's a request from docker. right? ";
    }

}
