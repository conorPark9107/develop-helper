package com.albionhelper.helper.controller;

import com.albionhelper.helper.domain.market.ItemPrice;
import com.albionhelper.helper.service.MarketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/calculator")
public class CalculatorController {

    @Autowired
    MarketService marketService;

    @GetMapping("/refining")
    public String showRefining(){
        return "calculator/refining";
    }

    // https://west.albion-online-data.com/api/v2/stats/prices/T2_METALBAR,T2_ORE?locations=Martlock&qualities=1
    @GetMapping("/getResourcePrice")
    @ResponseBody
    public List<List<ItemPrice>> showPrice(@RequestParam("server")String server,
                                           @RequestParam("city")String city,
                                           @RequestParam("before")String[] beforeArr,
                                           @RequestParam("after")String[] afterArr) throws JsonProcessingException {

        return marketService.getResourcePrice(server, city, beforeArr, afterArr);
    }

    @GetMapping("/cooking")
    public String showCookingPage(){
        return "calculator/cooking";
    }

    @GetMapping("/butcher")
    public String showbutcherPage(){
        return "calculator/butcher";
    }

    @GetMapping("/potion")
    public String showPotionPage(){
        return "calculator/potion";
    }


}
