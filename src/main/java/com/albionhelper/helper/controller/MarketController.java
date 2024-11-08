package com.albionhelper.helper.controller;

import com.albionhelper.helper.domain.market.ItemPrice;
import com.albionhelper.helper.service.MarketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping(value = "/market")
public class MarketController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // api 정보.
    // https://www.albion-online-data.com/

    @Autowired
    MarketService marketService;

    @GetMapping("")
    public String market(){
        return "market/market";
    }

    @GetMapping("/getPrice")
    @ResponseBody
    public List<ItemPrice> showPrice(@RequestParam("server")String server,
                                     @RequestParam("quality")String quality,
                                     @RequestParam("tier")String tier,
                                     @RequestParam("dotTier")String dotTier,
                                     @RequestParam("itemName")String itemName) throws JsonProcessingException {
        return marketService.getPrice(server, quality, tier, dotTier, itemName);
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

}
