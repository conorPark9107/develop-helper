package com.albionhelper.helper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/")
    public String index(Model model){
        return "home";
    }
}
