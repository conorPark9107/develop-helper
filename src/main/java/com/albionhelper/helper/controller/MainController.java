package com.albionhelper.helper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;


// 이미지 관련
// https://wiki.albiononline.com/wiki/API:Render_service

// 데이터 관련
// https://www.albion-online-data.com/#i-want-all-the-data-from-the-api

// 드디어 찾았으.
// https://openalbion.com/categories.html

// 참고
// https://albion-profit-calculator.com/

@Controller
public class MainController {

    private final String TEST_URL = "https://api.openalbion.com/api/v3/weapons?tier=4";
    private final String TEST_URL_MARKET_PREFIX = "https://east.albion-online-data.com";

    @GetMapping("/")
    public String index(Model model){
//        WebClient webClient = WebClient.builder().build();
//        String responseData = webClient.get().uri(TEST_URL).header("x-test", "header").retrieve().bodyToMono(String.class).block();
//        model.addAttribute("text", responseData);
        return "home";
    }
}
